package io.core9.module.auth.wrappers;

import io.core9.plugin.server.VirtualHost;

public class UsernameVirtualHostPrincipal {

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
