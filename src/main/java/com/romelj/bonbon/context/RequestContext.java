package com.romelj.bonbon.context;


import com.romelj.bonbon.entities.Session;
import com.romelj.bonbon.entities.User;

public interface RequestContext {

	Session getSession();

	User getUser();

	void setUser(User user);
}
