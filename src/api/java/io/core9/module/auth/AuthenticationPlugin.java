package io.core9.module.auth;

import io.core9.core.executor.Executor;
import io.core9.core.plugin.Core9Plugin;
import io.core9.plugin.server.Cookie;
import io.core9.plugin.server.request.Request;

public interface AuthenticationPlugin extends Core9Plugin, Executor {
	
	/**
	 * Return the user from the request
	 * @param req
	 * @return the user object
	 */
	User getUser(Request req);

	/**
	 * Return the user, identified by a cookie
	 * @param req
	 * @param cookie
	 * @return
	 */
	User getUser(Request req, Cookie cookie);
	
	/**
	 * Return the session by id, or null if not found/expired
	 * @param sessionId
	 * @return
	 */
	Session getSessionById(String sessionId);

}
