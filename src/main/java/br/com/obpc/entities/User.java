package br.com.obpc.entities;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import br.com.obpc.dto.UserDTO;

@Document
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	
	private String username;
	
	private String password;
	
	private String role;
	
	private Boolean active;
	
	private String token;
		
	public User() {
	}
	
	public User(UserDTO dto) {
		this.id = dto.getId();
		this.username = dto.getUsername();
		this.password = dto.getPassword();
		this.role = dto.getRole();
		this.active = dto.getActive();
	}
	
	public User(String username, String password, String role, Boolean active) {
		this.username = username;
		this.password = password;
		this.role = role;
		this.active = active;
	}
	
	public User(String username, String password, String role, Boolean active, String token) {
		this.username = username;
		this.password = password;
		this.role = role;
		this.active = active;
		this.token = token;
	}

	public User(String id, String username, String password, String role, Boolean active) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.role = role;
		this.active = active;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
