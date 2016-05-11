package com.romelj.bonbon.entities.json;

import com.romelj.bonbon.annotations.NotNullAndIgnoreUnknowns;
import com.romelj.bonbon.entities.User;
import com.romelj.bonbon.enums.UserRole;

import java.util.Set;

@NotNullAndIgnoreUnknowns
public class UserInfoJson {

	private String email;
	private String name;
	private String lastname;
	private long created;
	private Set<UserRole> userRoles;

	public UserInfoJson() {
	}

	public UserInfoJson(User user) {
		if (user != null) {
			this.email = user.getEmail();
			this.name = user.getName();
			this.lastname = user.getLastName();
			this.created = user.getCreated();
			this.userRoles = user.getUserRoleSet();
		}
	}

	public String getEmail() {
		return email;
	}

	public String getName() {
		return name;
	}

	public String getLastname() {
		return lastname;
	}

	public long getCreated() {
		return created;
	}

	public Set<UserRole> getUserRoles() {
		return userRoles;
	}
}
