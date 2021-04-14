package staticstic.scouter.sample.stat.client.components;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import lombok.extern.log4j.Log4j2;

/**
 * 토큰 실패처리
 *
 * @author  : smcho
 * @Date    : 2019. 12. 26.
 * @Version : 1.0
 */
@Log4j2
public class JwtFailureHandler implements AuthenticationFailureHandler {
	@Override
	public void onAuthenticationFailure(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException exception) {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	}
}
