package io.core9.module.auth;


public interface Session {
	
	String getId();

	Object getAttribute(Object key);
	
	Session setAttribute(Object key, Object value);
	
	Object removeAttribute(Object key);
	
	void setTimeout(long maxIdleTimeInMillis);
	
}
