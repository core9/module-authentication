package io.core9.module.auth;


public interface Session {

	Object getAttribute(Object key);
	
	Session setAttribute(Object key, Object value);
	
	Object removeAttribute(Object key);
	
}
