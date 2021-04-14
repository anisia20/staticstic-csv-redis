package staticstic.scouter.sample.stat.client.model.vo.auth;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class JwtTokenDetail implements UserDetails {

    private String agentId;
    private String token;
    private String logKey;
    private Collection<? extends GrantedAuthority> authorities;


    public JwtTokenDetail(String id, String token, List<GrantedAuthority> grantedAuthorities, String logKey) {
        this.agentId = id;
        this.token= token;
        this.authorities = grantedAuthorities;
        this.logKey = logKey;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return agentId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    public String getToken() {
        return token;
    }


    public String getId() {
        return agentId;
    }

	public String getLogKey() {
		return logKey;
	}

	public void setLogKey(String logKey) {
		this.logKey = logKey;
	}

}