package com.romelj.bonbon.rest;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.romelj.bonbon.context.RequestContext;
import com.romelj.bonbon.entities.Session;
import com.romelj.bonbon.entities.User;
import com.romelj.bonbon.entities.json.SuccessResponseJson;
import com.romelj.bonbon.entities.json.UserLoginRequestJson;
import com.romelj.bonbon.entities.json.UserRegisterJson;
import com.romelj.bonbon.exceptions.BonBonException;
import com.romelj.bonbon.services.SessionService;
import com.romelj.bonbon.services.UserService;
import org.jboss.resteasy.annotations.GZIP;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Anybody can use this endpoints, except specifically annotated rest endpoints with RolesAllowed
 */
@PermitAll
@Path("/rest/user")
public class AuthRest {

	private final UserService userService;
	private final SessionService sessionService;
	private final Provider<RequestContext> requestContextProvider;

	@Inject
	public AuthRest(UserService userService, SessionService sessionService, Provider<RequestContext> requestContextProvider) {
		this.userService = userService;
		this.sessionService = sessionService;
		this.requestContextProvider = requestContextProvider;
	}

	/**
	 * Register a new user
	 *
	 * @param userRegisterJson - json object with new user info
	 * @return success with session id
	 * @throws BonBonException - user already exists, etc.
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/register")
	@GZIP
	public Response register(UserRegisterJson userRegisterJson) throws BonBonException {
		RequestContext requestContext = requestContextProvider.get();
		User user = userService.registerUser(userRegisterJson);
		requestContext.setUser(user); //set user to a requestContext. User is needed in initSession method

		Session session = sessionService.initSession(requestContext);

		SuccessResponseJson successResponseJson = new SuccessResponseJson(user, session);
		return Response.status(Response.Status.OK).entity(successResponseJson).type(MediaType.APPLICATION_JSON_TYPE).build();
	}


	/**
	 * User login
	 *
	 * @param userLoginRequestJson - user login info
	 * @return success with session id
	 * @throws BonBonException - user not found, incorrect password, etc.
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/login")
	@GZIP
	public Response login(UserLoginRequestJson userLoginRequestJson) throws BonBonException {
		RequestContext requestContext = requestContextProvider.get();
		User user = userService.userLogin(userLoginRequestJson); //get user by email
		requestContext.setUser(user);

		Session session = sessionService.initSession(requestContext); //get session with auth token

		SuccessResponseJson successResponseJson = new SuccessResponseJson(user, session);
		return Response.status(Response.Status.OK).entity(successResponseJson).type(MediaType.APPLICATION_JSON_TYPE).build();
	}

	/**
	 * User logout endpoint  removes user's session
	 *
	 * @return success
	 */
	@RolesAllowed("User")
	@POST
	@Path("/logout")
	@Produces(MediaType.APPLICATION_JSON)
	@GZIP
	public Response logout() {
		RequestContext requestContext = requestContextProvider.get();
		if (requestContext != null && requestContext.getSession() != null) {
			sessionService.deleteSessionBy(requestContext.getSession().getId());
		}

		return Response.status(Response.Status.OK).entity(new SuccessResponseJson(true)).type(MediaType.APPLICATION_JSON_TYPE).build();
	}


}
