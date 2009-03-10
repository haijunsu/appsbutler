package org.apps.butler.mbean;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;

import java.util.ArrayList;

public class Server implements ServerMBean {
	private int _logLevel = 1;
	private long _startTime = 0L;

	public Server() {
		MBeanServer server = getServer();

		ObjectName name = null;
		try {
			name = new ObjectName("Application:Name=HaijunServer,Type=Server");
			server.registerMBean(this, name);
		} catch (Exception e) {
			e.printStackTrace();
		}

		_startTime = System.currentTimeMillis();
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

	// interface method implementations
//	public void setLoggingLevel(int level) {
//		_logLevel = level;
//		System.out.println("setLoggingLevel: " + _logLevel);
//	}

	public long getUptime() {
		return System.currentTimeMillis() - _startTime;
	}

	public void start() {
		System.out.println("calling start!");

	}
}
