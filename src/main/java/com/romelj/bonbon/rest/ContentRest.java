package com.romelj.bonbon.rest;


import com.google.inject.Inject;
import com.google.inject.Provider;
import com.romelj.bonbon.context.RequestContext;
import com.romelj.bonbon.entities.json.UserInfoJson;
import org.jboss.resteasy.annotations.GZIP;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RolesAllowed("User")
@Path("/rest/content")
public class ContentRest {

	private final Provider<RequestContext> requestContextProvider;

	@Inject
	public ContentRest(Provider<RequestContext> requestContextProvider) {
		this.requestContextProvider = requestContextProvider;
	}

	/**
	 * User info endpoint
	 *
	 * @return userInfoJson
	 */
	@GET
	@Path("/info")
	@Produces(MediaType.APPLICATION_JSON)
	@GZIP
	public Response info() {
		RequestContext requestContext = requestContextProvider.get();
		UserInfoJson userInfoJson = null;
		if (requestContext != null && requestContext.getUser() != null) {
			userInfoJson = new UserInfoJson(requestContext.getUser());
		}
		return Response.status(Response.Status.OK).entity(userInfoJson).type(MediaType.APPLICATION_JSON_TYPE).build();
	}

}
