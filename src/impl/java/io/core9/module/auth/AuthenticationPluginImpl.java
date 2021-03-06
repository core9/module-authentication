package io.core9.module.auth;

import io.core9.module.auth.session.SessionConnector;
import io.core9.module.auth.wrappers.ServerRequestToken;
import io.core9.module.auth.wrappers.SubjectWrapper;
import io.core9.plugin.server.Cookie;
import io.core9.plugin.server.Server;
import io.core9.plugin.server.handler.Middleware;
import io.core9.plugin.server.request.Request;

import java.util.Map;

import net.xeoh.plugins.base.annotations.PluginImplementation;
import net.xeoh.plugins.base.annotations.injections.InjectPlugin;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.subject.Subject;

@PluginImplementation
public class AuthenticationPluginImpl implements AuthenticationPlugin {
	
	private static final String REQ_SUBJECT_KEY = "auth.subject";
	
	@InjectPlugin
	private Server server;
	
	@InjectPlugin
	private AuthenticationConnector authenticationConnector;
	
	@InjectPlugin
	private SessionConnector sessionsConnector;

	@Override
	public User getUser(Request req) {
        return new SubjectWrapper(getSubject(req));
	}
	
	@Override
	public User getUser(Request req, Cookie cookie) {
        return new SubjectWrapper(getSubject(req, cookie));
	}
	
	@Override
	public void execute() {
		DefaultSecurityManager manager = authenticationConnector.getSecurityManager();
		if(manager != null) {
			SecurityUtils.setSecurityManager(manager);
		} else {
			manager = (DefaultSecurityManager) SecurityUtils.getSecurityManager();
		}
		if(sessionsConnector != null) {
			DefaultSessionManager sessionManager = (DefaultSessionManager) manager.getSessionManager();
			sessionManager.setSessionDAO(sessionsConnector.getSessionDAO());
			sessionManager.setSessionFactory(sessionsConnector.getSessionFactory());
		}
		server.use("/system/login", new Middleware() {
			@Override
			public void handle(Request request) {
				switch(request.getMethod()) {
				case POST:
					Map<String,Object> map = request.getBodyAsMap().toBlocking().last();
					Subject subject = getSubject(request);
					String username = (String) map.get("username");
					String password = (String) map.get("password");
					try {
						subject.login(new ServerRequestToken(request, username, password));
						String path = (String) map.get("path");
						if(path != null) {
							request.getResponse().sendRedirect(301, path);
						} else {
							request.getResponse().end();
						}
					} catch (AuthenticationException e) {
						request.getResponse().setStatusCode(401);
						request.getResponse().addValue("content", "Login failed, please supply an existing username/password combination");
					}
					break;
				default:
					request.getResponse().end("Only POST requests allowed.");
					break;
				}
			}
		});
	}
	
	/**
	 * Return the subject from a request
	 * @param req
	 * @return
	 */
	private Subject getSubject(Request req) {
		Subject currentUser = req.getContext(REQ_SUBJECT_KEY);
		if(currentUser == null) {
			currentUser = getSubject(req, req.getCookie("CORE9SESSIONID"));
		}
		currentUser.getSession().touch();
        return currentUser;
	}
	
	/**
	 * Return the subject identified by cookie
	 */
	private Subject getSubject(Request req, Cookie cookie) {
    	Subject.Builder builder = new Subject.Builder();
    	if(cookie != null) {
    		// Try to build an existing session
    		SessionsSecurityManager o = (SessionsSecurityManager) SecurityUtils.getSecurityManager();
    		Session session = null;
    		try {
    			session = ((DefaultSessionManager) o.getSessionManager()).getSessionDAO().readSession(cookie.getValue());
    			builder = builder.sessionId(session.getId());
    		} catch (SessionException e) {
    			// Unset existing cookie, as the session doesn't exist
    			cookie.setMaxAge(-10);
    			req.getResponse().addCookie(cookie);
    			cookie = null;
    		}
    	}
    	Subject currentUser = builder.buildSubject();
    	if(cookie == null) {
    		cookie = server.newCookie("CORE9SESSIONID", currentUser.getSession(true).getId().toString());
    		cookie.setPath("/");
    		req.getResponse().addCookie(cookie);
    	}
    	req.putContext(REQ_SUBJECT_KEY, currentUser);
        return currentUser;
	}
	
	/**
	 * Return the subject identified by cookie
	 */
	private Subject getSubject(String sessionId) {
    	Subject.Builder builder = new Subject.Builder();
    	SessionsSecurityManager o = (SessionsSecurityManager) SecurityUtils.getSecurityManager();
    	Session session = null;
    	try {
    		session = ((DefaultSessionManager) o.getSessionManager()).getSessionDAO().readSession(sessionId);
    		builder = builder.sessionId(session.getId());
    	} catch (SessionException e) {
    		return null;
    	}
    	Subject currentUser = builder.buildSubject();
        return currentUser;
	}

	@Override
	public io.core9.module.auth.Session getSessionById(String sessionId) {
		Subject user = getSubject(sessionId);
		if(user != null) {
			return new SubjectWrapper(user).getSession();
		} else {
			return null;
		}
	}
}
