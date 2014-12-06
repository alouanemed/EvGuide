package com.lpii.evma.model;

public class User {
	 private String user_id = "user_id";
	 private String username = "username";
	 private String password = "password";
	 private String role = "role";
	 private String full_name = "full_name";
	 private String email = "email";
	 private String d_created = "d_created";
	 private String d_modified = "d_modified";
	 
	public User(String user_id, String username, String password, String role,
			String full_name, String email, String d_created, String d_modified) {
		super();
		this.user_id = user_id;
		this.username = username;
		this.password = password;
		this.role = role;
		this.full_name = full_name;
		this.email = email;
		this.d_created = d_created;
		this.d_modified = d_modified;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
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

	public String getFull_name() {
		return full_name;
	}

	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getD_created() {
		return d_created;
	}

	public void setD_created(String d_created) {
		this.d_created = d_created;
	}

	public String getD_modified() {
		return d_modified;
	}

	public void setD_modified(String d_modified) {
		this.d_modified = d_modified;
	}

	@Override
	public String toString() {
		return "User [user_id=" + user_id + ", username=" + username
				+ ", password=" + password + ", role=" + role + ", full_name="
				+ full_name + ", email=" + email + ", d_created=" + d_created
				+ ", d_modified=" + d_modified + "]";
	}
	 
	
	 


}
