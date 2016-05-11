package com.romelj.bonbon.entities;


import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.romelj.bonbon.enums.UserRole;

import java.util.Set;

@Entity
public class User {

	@Id
	private String email;
	private String password;
	private String name;
	private String lastName;
	private long created;
	private Set<UserRole> userRoleSet;

	public User() {
	}

	public User(String email, String password, String name, String lastName, long created, Set<UserRole> userRoleSet) {
		this.email = email;
		this.password = password;
		this.name = name;
		this.lastName = lastName;
		this.created = created;
		this.userRoleSet = userRoleSet;
	}

	//setters

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	//getters

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public long getCreated() {
		return created;
	}

	public void setCreated(long created) {
		this.created = created;
	}

	public Set<UserRole> getUserRoleSet() {
		return userRoleSet;
	}

	public void setUserRoleSet(Set<UserRole> userRoleSet) {
		this.userRoleSet = userRoleSet;
	}
}
