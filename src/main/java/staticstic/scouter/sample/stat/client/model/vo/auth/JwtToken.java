package staticstic.scouter.sample.stat.client.model.vo.auth;

import lombok.Data;

@Data
public class JwtToken {
	String agentId;
	String iat;
	String exp;
	String role;
}
