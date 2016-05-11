package com.romelj.bonbon.guice;


import com.google.inject.Provider;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.romelj.bonbon.entities.Session;
import com.romelj.bonbon.entities.User;
import org.jboss.resteasy.plugins.guice.RequestScoped;

import static com.googlecode.objectify.ObjectifyService.factory;

/**
 * Provider for Objectify
 */
class ObjectifyProvider implements Provider<Objectify> {

	/**
	 * Register kind to dataStore
	 */
	static {
		factory().register(User.class);
		factory().register(Session.class);
	}

	@RequestScoped //use one objectify instance for one request
	@Override
	public Objectify get() {
		return ObjectifyService.ofy();
	}
}
