package com.romelj.bonbon.rest;

import com.google.inject.Inject;
import com.romelj.bonbon.entities.Session;
import com.romelj.bonbon.entities.User;
import com.romelj.bonbon.entities.json.UserInfoJson;
import com.romelj.bonbon.entities.mapper.UserMapper;
import com.romelj.bonbon.exceptions.BonBonError;
import com.romelj.bonbon.exceptions.BonBonException;
import com.romelj.bonbon.services.SessionService;
import com.romelj.bonbon.services.UserService;
import org.jboss.resteasy.annotations.GZIP;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


/**
 * Users with admin role can use this endpoints
 */
@RolesAllowed("Admin")
@Path("/rest/admin")
public class AdminRest {

	private final UserService userService;
	private final SessionService sessionService;

	@Inject
	public AdminRest(UserService userService, SessionService sessionService) {
		this.userService = userService;
		this.sessionService = sessionService;
	}

	/**
	 * Get all users in the db
	 *
	 * @return list of userInfo objects
	 */
	@GET
	@Path("/users")
	@Produces(MediaType.APPLICATION_JSON)
	@GZIP
	public List<UserInfoJson> getUsers() {
		List<User> userList = userService.getAllUsers();
		return UserMapper.map(userList);
	}

	/**
	 * get info for certain user
	 *
	 * @param userEmail - user email
	 * @return info about user
	 * @throws BonBonException - user does not exist
	 */
	@GET
	@Path("/users/{userEmail}")
	@Produces(MediaType.APPLICATION_JSON)
	@GZIP
	public Response getUserInfo(@PathParam("userEmail") String userEmail) throws BonBonException{
		User user = userService.getUserBy(userEmail);
		UserInfoJson userInfoJson = UserMapper.map(user);
		if (userInfoJson == null) {
			throw new BonBonException(BonBonError.UserNotExists);
		}
		return Response.status(Response.Status.OK).type(MediaType.APPLICATION_JSON_TYPE).entity(userInfoJson).build();
	}

	/**
	 * delete user by email
	 *
	 * @param userInfoJson - userInfoJson with email
	 * @return ok, if user was deleted or exception otherwise
	 * @throws BonBonException - user does not exist
	 */
	@POST
	@Path("/users/delete")
	@Produces(MediaType.APPLICATION_JSON)
	@GZIP
	public Response removeUser(UserInfoJson userInfoJson) throws BonBonException {
		if (userInfoJson != null && userInfoJson.getEmail() != null && userInfoJson.getEmail().length() > 0){
			userService.removeUserBy(userInfoJson.getEmail());
			return Response.ok().build();
		}
		return Response.status(Response.Status.BAD_REQUEST).build();

	}

	/**
	 * Get all sessions
	 *
	 * @return list of sessions
	 */
	@GET
	@Path("/sessions")
	@Produces(MediaType.APPLICATION_JSON)
	@GZIP
	public List<Session> getSessions() {
		return sessionService.getAllSessions();
	}

	/**
	 * Delete all sessions
	 *
	 * @return ok
	 */
	@POST
	@Path("/sessions/delete")
	@Produces(MediaType.APPLICATION_JSON)
	@GZIP
	public Response deleteAllSessions() {
		sessionService.deleteAllSessions();
		return Response.ok().build();
	}


}
