package io.core9.module.auth;

import java.util.Set;

/**
 * Represents a user object
 *  
 * @author mark.wienk@core9.io
 */
public interface User {
	
	/**
	 * Returns true if the user is authenticated
	 * @return
	 */
	public boolean isAuthenticated();
	
	/**
	 * Returns true if the user has the specified role
	 * @param role
	 * @return
	 */
	public boolean hasRole(String role);
	
	/**
	 * Returns true if the user has one of the roles
	 * @param roles
	 * @return
	 */
	public boolean hasRole(Set<String> roles);
	
	/**
	 * Returns true if the user has the specified permission
	 * @param permission
	 * @return
	 */
	public boolean isPermitted(String permission);
	
	/**
	 * Returns true if the user has all the permissions 
	 * @param permission
	 * @return
	 */
	public boolean isPermittedAll(String... permissions);

	/**
	 * Returns true if the user has one of the permissions
	 * @param permissions
	 * @return
	 */
	public boolean isPermitted(Set<String> permissions);
}
