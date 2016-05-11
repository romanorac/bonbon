package com.romelj.bonbon.guice;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.romelj.bonbon.context.RequestContext;
import com.romelj.bonbon.context.RequestContextImpl;
import com.romelj.bonbon.rest.AdminRest;
import com.romelj.bonbon.rest.AuthRest;
import com.romelj.bonbon.rest.ContentRest;
import com.romelj.bonbon.rest.HealthRest;
import com.romelj.bonbon.security.AuthorizationFilter;

/**
 * Bind RestEasy classes
 */
public class RestEasyModule implements Module {

	@Override
	public void configure(Binder binder) {

		binder.bind(RequestContext.class).to(RequestContextImpl.class);
		binder.bind(AuthorizationFilter.class);

		binder.bind(AuthRest.class);
		binder.bind(AdminRest.class);
		binder.bind(HealthRest.class);
		binder.bind(ContentRest.class);

	}
}
