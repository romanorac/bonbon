package com.romelj.bonbon.services;

import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.romelj.bonbon.context.RequestContext;
import com.romelj.bonbon.entities.Session;
import com.romelj.bonbon.entities.User;
import com.romelj.bonbon.exceptions.BonBonError;
import com.romelj.bonbon.exceptions.BonBonException;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * Basic handlers for managing sessions
 */
public class SessionServiceImpl implements SessionService {

	private final static Logger logger = Logger.getLogger(SessionServiceImpl.class.getSimpleName());

	private final static Long sessionDuration = 1800000L; //session max duration is 30 minutes

	private final Provider<Objectify> ofyProvider;

	@Inject
	public SessionServiceImpl(Provider<Objectify> ofyProvider) {
		this.ofyProvider = ofyProvider;
	}

	/**
	 * get session by session token
	 *
	 * @param token - session token
	 * @return session or null if it does not exists.
	 */
	private Session getSessionBy(String token) {
		Session session = null;
		if (!Strings.isNullOrEmpty(token)) {
			final Objectify ofy = ofyProvider.get();
			session = ofy.load().type(Session.class).id(token).now();
		}
		return session;
	}

	/**
	 * Check if session has expired
	 *
	 * @param session - session object
	 * @return - session or null if session expired
	 */
	private Session checkDuration(Session session) {
		if (session != null) {
			//remove session if it is older than session duration
			Long sessionTimeout = session.getCreated() + sessionDuration;
			if (sessionTimeout < System.currentTimeMillis()) {
				deleteSessionBy(session.getId());
				session = null;
			}
		}
		return session;
	}


	/**
	 * Create a new session with new session token
	 *
	 * @return session
	 */
	private Session createNewSession() {
		final Objectify ofy = ofyProvider.get();

		String token = UUID.randomUUID().toString();
		long currentTime = System.currentTimeMillis();
		Session session = new Session(token, currentTime);
		ofy.save().entity(session).now();
		return session;
	}


	/**
	 * Used for login and register flow
	 *
	 * @param requestContext - request context
	 * @return session
	 * @throws BonBonException - invalid context, User does not exist
	 */
	@Override
	public Session initSession(RequestContext requestContext) throws BonBonException {
		if (requestContext == null) {
			throw new BonBonException(BonBonError.InvalidContext);
		}

		final User user = requestContext.getUser();

		if (user == null) {
			throw new BonBonException(BonBonError.UserNotExists);
		}

		Session session = requestContext.getSession();
		if (session == null) {
			session = getSession(null);
		}

		//update session with userId
		final Objectify ofy = ofyProvider.get();
		final String sessionId = session.getId();
		session = ofy.transact(() -> {
			Session obj = ofy.load().type(Session.class).id(sessionId).now();
			if (obj != null) {
				logger.info("Session with sessionId: " + sessionId + " updated with userEmail: " + user.getEmail());
				obj.setUserEmail(user.getEmail()); //new registered users does not have session set
				ofy.save().entity(obj).now();
			}
			return obj;
		});

		return session;
	}

	/**
	 *  get session by sessionId
	 *
	 * @param sessionId - sessionId
	 * @return session
	 */
	@Override
	public Session getSession(String sessionId){
		Session session;

		if (Strings.isNullOrEmpty(sessionId)) {
			session = createNewSession(); //create new session when user registers or login

		} else {
			//used in every request
			session = getSessionBy(sessionId); //load existing session
			session = checkDuration(session); //todo test what happens if user is logged in and session expires.
		}

		if (session == null) {
			logger.warning("Session with " + String.valueOf(sessionId) + " + not found");
		}

		return session;
	}

	/**
	 *  Get all sessions from db
	 *
	 * @return list with sessions
	 */
	@Override
	public List<Session> getAllSessions() {
		final Objectify ofy = ofyProvider.get();
		List<Session> sessionList = ofy.load().type(Session.class).list();
		if (sessionList == null || sessionList.size() == 0) {
			logger.warning("No sessions found in db");
		} else {
			logger.info("Sessions retrieved: " + String.valueOf(sessionList.size()));
		}

		return sessionList;
	}

	/**
	 * Delete session by sessionId
	 *
	 * @param sessionId - session id
	 */
	@Override
	public void deleteSessionBy(String sessionId) {
		if (!Strings.isNullOrEmpty(sessionId)) {
			final Objectify ofy = ofyProvider.get();
			ofy.delete().type(Session.class).id(sessionId).now();
			logger.info("Session with id: " + sessionId + " was removed.");
		}else{
			logger.info("Invalid sessionId: " + String.valueOf(sessionId) + ".");
		}
	}

	/**
	 * Clear all sessions from db
	 */
	@Override
	public void deleteAllSessions() {
		final Objectify ofy = ofyProvider.get();
		List<Key<Session>> sessionKeys = ofy.load().type(Session.class).keys().list();
		if (sessionKeys != null && sessionKeys.size() > 0) {
			ofy.delete().keys(sessionKeys).now();
			logger.info("sessions removed: " + String.valueOf(sessionKeys.size()));
		}else{
			logger.info("No sessions found.");
		}
	}


}
