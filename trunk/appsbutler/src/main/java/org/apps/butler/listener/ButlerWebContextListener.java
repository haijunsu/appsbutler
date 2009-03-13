package org.apps.butler.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apps.butler.mbean.ButlerApp;
import org.apps.butler.mbean.RegisterHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ButlerWebContextListener implements ServletContextListener {

	private final Logger logger = LoggerFactory
			.getLogger(ButlerWebContextListener.class);

	public void contextDestroyed(ServletContextEvent context) {
		logger.info("{} stopped.", context.getServletContext()
				.getServletContextName());

	}

	public void contextInitialized(ServletContextEvent context) {
		logger.info("{} starting . . .", context.getServletContext()
				.getServletContextName());
		logger.info("Registering mbean . . .");
		try {
			RegisterHelper.register(context.getServletContext(),
					new ButlerApp());
			logger.info("Register MBean success.");
		} catch (Exception e) {
			logger.error("Register MBean failed.", e);
		}
		logger.info("{} in running", context.getServletContext()
				.getServletContextName());
	}

}
