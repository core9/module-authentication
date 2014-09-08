package io.core9.module.auth.session;

import io.core9.core.plugin.Core9Plugin;

import org.apache.shiro.session.mgt.SessionFactory;
import org.apache.shiro.session.mgt.eis.SessionDAO;

public interface SessionConnector extends Core9Plugin {

	SessionDAO getSessionDAO();

	SessionFactory getSessionFactory();
}
