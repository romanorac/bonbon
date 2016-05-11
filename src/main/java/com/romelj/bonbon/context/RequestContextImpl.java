package com.romelj.bonbon.context;

import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.romelj.bonbon.entities.Session;
import com.romelj.bonbon.entities.User;
import com.romelj.bonbon.services.SessionService;
import com.romelj.bonbon.services.UserService;
import com.romelj.bonbon.utils.RequestUtils;
import org.jboss.resteasy.plugins.guice.RequestScoped;

import javax.servlet.http.HttpServletRequest;

@RequestScoped
public class RequestContextImpl implements RequestContext {

	private final Session session;
	private User user;

	@Inject
	public RequestContextImpl(HttpServletRequest servletRequest, SessionService sessionService, UserService userService) {
		String token = RequestUtils.getToken(servletRequest); //get session id from the header
		this.session = Strings.isNullOrEmpty(token) ? null : sessionService.getSession(token); //use existing session, create a new one or null;
		this.user = userService.getUserBy(session); //get user by session or null, if session is null
	}

	@Override
	public Session getSession() {
		return this.session;
	}

	@Override
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
