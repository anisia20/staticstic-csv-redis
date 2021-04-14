package staticstic.scouter.sample.stat.client.components;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;
import staticstic.scouter.sample.stat.client.model.vo.auth.JwtAuthToken;
import staticstic.scouter.sample.stat.client.model.vo.auth.JwtToken;
import staticstic.scouter.sample.stat.client.model.vo.auth.JwtTokenDetail;

/**
 * role 세팅
 * @author ectech
 *
 */
		
@Log4j2
@Component
public class JwtAuthProvider extends AbstractUserDetailsAuthenticationProvider{

	@Autowired
	private JWTCmd jwtCmd;

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
	}

	@Override
	protected JwtTokenDetail retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		JwtAuthToken jwtAuthenticationToken = (JwtAuthToken) authentication;
		String token = jwtAuthenticationToken.getToken();

//skip 처리
//		if("".equals(token)) {
//			List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("new");
//			JwtTokenDetail result = new JwtTokenDetail("new", "", grantedAuthorities);
//			return result;
//		}
		
		String sub = jwtCmd.getSubject(token);
		JwtToken t = new JwtToken();
		String role= "ADMIN";
		t.setAgentId(sub);
		t.setRole("ROLE_"+role);
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(t.getRole());
		
		JwtTokenDetail result = new JwtTokenDetail(t.getAgentId(),token, grantedAuthorities, jwtAuthenticationToken.getLogKey());
		return result;
	}

	@Override
	public boolean supports(Class<?> aClass) {
		return (JwtAuthToken.class.isAssignableFrom(aClass));
	}

}