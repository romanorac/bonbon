package com.romelj.bonbon.entities;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Session {

	@Id
	private String id;

	private String userEmail;

	private long created;

	public Session() {
	}

	public Session(String id, long created) {
		this.id = id;
		this.created = created;
	}

	public String getId() {
		return id;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public long getCreated() {
		return created;
	}


}
