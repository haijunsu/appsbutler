package com.apps.butler.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.apps.butler.mbean.ButlerServerImpl;

public class ButlerWebContextListener implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void contextInitialized(ServletContextEvent arg0) {
		new ButlerServerImpl("ButlerServer");

	}

}
