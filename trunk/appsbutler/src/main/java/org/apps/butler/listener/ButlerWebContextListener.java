package org.apps.butler.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apps.butler.mbean.ButlerServerImpl;
import org.apps.butler.mbean.RegisterHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ButlerWebContextListener implements ServletContextListener {

	private final Logger logger = LoggerFactory.getLogger(ButlerWebContextListener.class);


	public void contextDestroyed(ServletContextEvent context) {
		logger.info("{} stopped.", context.getServletContext().getServletContextName());

	}

	public void contextInitialized(ServletContextEvent context) {
		logger.info("{} starting . . .", context.getServletContext().getServletContextName());
		logger.debug("registering mbean . . .");
		RegisterHelper.register(new ButlerServerImpl("ButlerServer"));
		logger.debug("mbean registered success.");
	}

}
