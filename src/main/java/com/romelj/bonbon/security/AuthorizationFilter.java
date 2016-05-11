package com.romelj.bonbon.security;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.romelj.bonbon.context.RequestContext;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import java.io.IOException;

/**
 * Authorize user from request context
 */
@javax.ws.rs.ext.Provider
@Priority(Priorities.AUTHORIZATION)
public class AuthorizationFilter implements ContainerRequestFilter {

	@Inject
	private Provider<RequestContext> requestContextProvider;

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		requestContext.setSecurityContext(new BonBonSecurityContext(requestContextProvider.get()));
	}
}
