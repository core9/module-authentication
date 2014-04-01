package io.core9.module.auth;

import io.core9.core.plugin.Core9Plugin;

import org.apache.shiro.mgt.SecurityManager;

public interface AuthenticationConnector extends Core9Plugin {
	
	SecurityManager getSecurityManager();

}
