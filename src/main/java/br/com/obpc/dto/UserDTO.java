package br.com.obpc.dto;

import java.io.Serializable;

public class UserDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String username;
	private String password;
	private String role;
	private Boolean active;
	
	public UserDTO() {
	}

	public UserDTO(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public UserDTO(String id, String username, String password, String role, Boolean active) {
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	

}
