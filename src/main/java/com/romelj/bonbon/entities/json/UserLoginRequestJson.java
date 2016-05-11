package com.romelj.bonbon.entities.json;

import com.romelj.bonbon.annotations.NotNullAndIgnoreUnknowns;

@NotNullAndIgnoreUnknowns
public class UserLoginRequestJson {

	private String email;
	private String password; //SHA-256 encoded user password


	//getters and setters
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
