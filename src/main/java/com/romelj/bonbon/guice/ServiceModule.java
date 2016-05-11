package com.romelj.bonbon.guice;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.googlecode.objectify.Objectify;
import com.romelj.bonbon.services.SessionService;
import com.romelj.bonbon.services.SessionServiceImpl;
import com.romelj.bonbon.services.UserService;
import com.romelj.bonbon.services.UserServiceImpl;

/**
 * Bind services and providers
 */
public class ServiceModule implements Module {

	public void configure(Binder binder) {
		//providers
		binder.bind(Objectify.class).toProvider(ObjectifyProvider.class);

		//services
		binder.bind(UserService.class).to(UserServiceImpl.class);
		binder.bind(SessionService.class).to(SessionServiceImpl.class);

	}
}
