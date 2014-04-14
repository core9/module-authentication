package io.core9.module.auth.wrappers;

import java.util.Set;

import io.core9.module.auth.Session;
import io.core9.module.auth.User;

import org.apache.shiro.subject.Subject;

/**
 * Wraps the User around the Apache Shiro Subject
 * @author mark
 *
 */
public class SubjectWrapper implements User {
	
	private Subject subject;
	
	public SubjectWrapper(Subject subject) {
		this.subject = subject;
	}

	@Override
	public boolean hasRole(String role) {
		return subject.hasRole(role);
	}

	@Override
	public boolean isAuthenticated() {
		return subject.isAuthenticated();
	}

	@Override
	public boolean isPermitted(String permission) {
		return subject.isPermitted(permission);
	}

	@Override
	public boolean isPermittedAll(String... permissions) {
		return subject.isPermittedAll(permissions);
	}

	@Override
	public boolean hasRole(Set<String> roles) {
		for(String role: roles) {
			if(subject.hasRole(role)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isPermitted(Set<String> permissions) {
		for(String permission : permissions) {
			if(subject.isPermitted(permission)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public Session getSession() {
		return new SessionWrapper(subject.getSession());
	}
	
}
