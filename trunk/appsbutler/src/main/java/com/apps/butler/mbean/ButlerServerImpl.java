package com.apps.butler.mbean;

import java.util.ArrayList;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;

public class ButlerServerImpl implements ButlerServerMbean {

	public AppState getAppState() {
		// TODO Auto-generated method stub
		return null;
	}

	public void start() throws Exception {
		// TODO Auto-generated method stub

	}

	public void stop() throws Exception {
		// TODO Auto-generated method stub

	}

	public ButlerServerImpl(String serverName) {
		MBeanServer server = getServer();

		ObjectName name = null;
		try {
			name = new ObjectName("Application:Name=" + serverName
					+ ",Type=Server");
			server.registerMBean(this, name);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private MBeanServer getServer() {
		MBeanServer mbserver = null;

		ArrayList mbservers = MBeanServerFactory.findMBeanServer(null);

		if (mbservers.size() > 0) {
			mbserver = (MBeanServer) mbservers.get(0);
		}

		if (mbserver != null) {
			System.out.println("Found our MBean server");
		} else {
			mbserver = MBeanServerFactory.createMBeanServer();
		}

		return mbserver;
	}
}
