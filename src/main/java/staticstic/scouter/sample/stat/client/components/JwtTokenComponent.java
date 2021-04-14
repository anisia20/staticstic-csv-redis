package staticstic.scouter.sample.stat.client.components;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

/**
 * 토큰 예외
 *
 * @author : smcho
 * @Date : 2019. 12. 26.
 */
@Log4j2
@Component
public class JwtTokenComponent {

	@Autowired
	JWTCmd jwtCmd;
	
	@Autowired
	ResourcesManager mResourcesManager;
	
	public static String token;
	@Value("${agent.id}") String agentId;
	
	public void refreshJwtToken(int minute) {
		checkToken(minute);
	}

	public void checkToken(int minute) {
		if (token == null || jwtCmd.isTokenRefresh(token, minute))
			refreshToken();
	}

	public void refreshToken() {
		InetAddress ip = null;
		try {
			ip = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			log.error("do not read ip ",e);
		}
		
		token = jwtCmd.getToken(mResourcesManager.getJwtTokenKey(), agentId, ip.getHostAddress());
		log.info("Sucess refresh token");
	}

	public String getToken() {
		return token;
	}
}
