package staticstic.scouter.sample.stat.client.components;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import lombok.extern.log4j.Log4j2;

/**
 * 성공 토큰 처리
 *
 * @author  : smcho
 * @Date    : 2019. 12. 26.
 * @Version : 1.0
 */
@Log4j2
public class JwtSuccessHandler implements AuthenticationSuccessHandler {

	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			Authentication authentication) throws IOException, ServletException {
		log.debug("Successfully authenticated....");
	}
}
