package io.core9.module.auth.wrappers;

import io.core9.module.auth.Session;

public class SessionWrapper implements Session {
	
	private org.apache.shiro.session.Session session;

	@Override
	public Object getAttribute(Object key) {
		return session.getAttribute(key);
	}

	@Override
	public Session setAttribute(Object key, Object value) {
		session.setAttribute(key, value);
		return this;
	}

	@Override
	public Object removeAttribute(Object key) {
		return session.removeAttribute(key);
	}
	
	public SessionWrapper(org.apache.shiro.session.Session session) {
		this.session = session;
	}

	@Override
	public void setTimeout(long maxIdleTimeInMillis) {
		this.session.setTimeout(maxIdleTimeInMillis);
	}

	@Override
	public String getId() {
		return this.session.getId().toString();
	}

}
