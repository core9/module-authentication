package io.core9.module.auth;

import io.core9.core.plugin.Core9Plugin;

import org.apache.shiro.mgt.DefaultSecurityManager;

public interface AuthenticationConnector extends Core9Plugin {
	
	DefaultSecurityManager getSecurityManager();

}
