package com.romelj.bonbon.services;


import com.romelj.bonbon.context.RequestContext;
import com.romelj.bonbon.entities.Session;
import com.romelj.bonbon.exceptions.BonBonException;

import java.util.List;

public interface SessionService {

	Session initSession(RequestContext requestContext) throws BonBonException;

	Session getSession(String token) throws BonBonException;

	List<Session> getAllSessions();

	void deleteSessionBy(String sessionId);

	void deleteAllSessions();

}
