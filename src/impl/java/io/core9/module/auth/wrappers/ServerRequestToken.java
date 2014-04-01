package io.core9.module.auth.wrappers;

import io.core9.plugin.server.request.Request;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * An authentication token which contains the request
 * 
 * @author mark
 *
 */
public class ServerRequestToken extends UsernamePasswordToken implements AuthenticationToken {

	private static final long serialVersionUID = -2436885774597520308L;
	
	private Request request;
	
	public ServerRequestToken(Request request, String username, String password) {
		super(username, password);
		this.request = request;
	}
	
	public ServerRequestToken(Request request) {
		super();
		this.request = request;
	}
	
	/**
	 * Return the request
	 * @return
	 */
	public Request getRequest() {
		return this.request;
	}

}
