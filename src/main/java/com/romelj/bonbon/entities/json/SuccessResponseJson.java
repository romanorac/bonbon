package com.romelj.bonbon.entities.json;


import com.romelj.bonbon.annotations.NotNullAndIgnoreUnknowns;
import com.romelj.bonbon.entities.Session;
import com.romelj.bonbon.entities.User;

@NotNullAndIgnoreUnknowns
public class SuccessResponseJson {

	private boolean success;
	private String name;
	private String lastname;
	private String token;

	public SuccessResponseJson(boolean success) {
		this.success = success;
	}

	public SuccessResponseJson(User user, Session session) {
		if (user != null && session != null) {
			this.success = true;
			this.token = session.getId();
			this.name = user.getName();
			this.lastname = user.getLastName();
		} else {
			this.success = false;
		}
	}

	//getters and setters
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
}
