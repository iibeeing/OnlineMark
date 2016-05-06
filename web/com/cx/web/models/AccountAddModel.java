package com.cx.web.models;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class AccountAddModel {
	@NotEmpty(message="{name.not.empty}")
	private String name;
	@NotEmpty(message="{email.not.empty}")
	@Email(message="{email.not.correct}")
	private String email;
	@NotEmpty(message="{username.not.empty}")
	private String username;
	@Length(min=8, message="{password.length.error}")  
	private String password;
	private String confirmPassword;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	
}
