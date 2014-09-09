package io.core9.module.auth.wrappers;

import java.io.Serializable;

import io.core9.plugin.server.VirtualHost;

public class UsernameVirtualHostPrincipal implements Serializable {

	private static final long serialVersionUID = 2097482760827132620L;
	private VirtualHost vhost;
	private String username;
	
	public VirtualHost getVirtualHost() {
		return vhost;
	}
	
	public String getUsername() {
		return username;
	}
	
	public UsernameVirtualHostPrincipal(VirtualHost vhost, String username) {
		this.vhost = vhost;
		this.username = username;
	}
}
