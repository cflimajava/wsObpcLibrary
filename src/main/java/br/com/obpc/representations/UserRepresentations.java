package br.com.obpc.representations;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.obpc.entities.User;
import static br.com.obpc.utils.ObpcConstants.BASE_URI_USER_CRTL;

public class UserRepresentations extends Representation{

	private String id;

	private String username;

	private String role;

	private Boolean active;
	
	private String token;
	
	public UserRepresentations() {}

	public UserRepresentations(User user, HttpServletRequest request) {
		super(request, BASE_URI_USER_CRTL+user.getId());		
		this.id = user.getId();
		this.username = user.getUsername();
		this.role = user.getRole();
		this.active = user.getActive();
		this.token = user.getToken();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@JsonIgnore
	public String getHref() {
		if(get_link() != null && get_link().getSelf() != null)
			return get_link().getSelf().getHref();
		
		return null;
	}

}
