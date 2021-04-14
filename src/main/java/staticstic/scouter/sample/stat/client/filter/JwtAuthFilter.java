package staticstic.scouter.sample.stat.client.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import io.jsonwebtoken.Claims;
import lombok.extern.log4j.Log4j2;
import staticstic.scouter.sample.common.util.KeyMaker;
import staticstic.scouter.sample.stat.client.components.JWTCmd;
import staticstic.scouter.sample.stat.client.components.ResourcesManager;
import staticstic.scouter.sample.stat.client.model.vo.auth.JwtAuthToken;
import staticstic.scouter.sample.stat.client.utils.SmchoUtil;
import staticstic.scouter.sample.stat.client.utils.ValidUtil;

/**
 * 토큰 최초 필터
 *
 * @Date    : 2019. 12. 26.
 * @Version : 1.0
 */
@Log4j2
public class JwtAuthFilter extends AbstractAuthenticationProcessingFilter {
	
	@Autowired
    JWTCmd jwtCmd;
	
	@Autowired
    KeyMaker keyMaker;
	
	@Autowired
    ResourcesManager mResourcesManager;
	
	public JwtAuthFilter() {
		super("/jwttest**");
	}

	@Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        String header = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        String uri = httpServletRequest.getRequestURI();
        String method = httpServletRequest.getMethod();
        
		String logKey = keyMaker.nextKey(KeyMaker.TT_CAB);

// skip 처리        
//        if(조건) {
//        	JwtAuthToken token = new JwtAuthToken("");
//            return getAuthenticationManager().authenticate(token);
//        }
        	
        if (header == null || !header.startsWith("Bearer ")) {
        	httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        	log.warn("LK={}, token missing.", logKey);
        	return null;
        }
        String authenticationToken = header.substring(7);
        log.debug("LK={}, authenticationToken = {}", logKey, authenticationToken);
        
        String tokenKey = mResourcesManager.getJwtTokenKey();
        log.debug("LK={}, uri={}, token={}", logKey, uri, tokenKey);
        
        if(jwtCmd.validateToken(tokenKey, authenticationToken)==false) {
        	httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        	log.warn("LK={}, token incorrect. uri={}, tokenExpire={}", logKey, uri, jwtCmd.getExpirationDateFromToken(authenticationToken));
        	return null;        	
        }
        
    	String sub = jwtCmd.getSubject(authenticationToken);
        Claims claims = jwtCmd.getAllClaimsFromToken(authenticationToken);
        String sIp = (String) claims.get("sIp");
        try {
			// localhost 에서 gradle test 시, ip address 를 못가져오는 경우가 발생하여 추가함
			String cliIp = null;
			if (System.getProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME) != null) {
				cliIp = SmchoUtil.getRemoteIpAddr(httpServletRequest);
				if (cliIp == null) {
					log.error("LK={}, cannot gathering ip. C={}", logKey, sub);
					httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					return null;
				}
				
				boolean isPass = ValidUtil.isValidIPAddr(cliIp, sIp.split(","));
				if (isPass == false) {
					log.warn("LK={}, sIp is incorrect. C={}, token={}, request={}", logKey, sub, sIp, cliIp);
					httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					return null;
				}
			}
			log.debug("LK={}, ip pass. cliIp={}", logKey, cliIp);
		} catch (Exception e) {
			log.error("LK={}, err={}", logKey, sub, e.getMessage(), e);
			httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return null;
		}
        
        JwtAuthToken token = new JwtAuthToken(authenticationToken);
        token.setLogKey(logKey);
        return getAuthenticationManager().authenticate(token);
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
        chain.doFilter(request, response);
    }
    
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
    	super.unsuccessfulAuthentication(request, response, failed);
    	log.debug("unsuccessfulAuthentication = {}",failed);
    	response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	}

}
