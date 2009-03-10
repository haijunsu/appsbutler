package org.apps.butler.mbean;

import java.util.ArrayList;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;

public class RegisterHelper {

	private static MBeanServer getServer() {
		MBeanServer mbserver = null;

		ArrayList<MBeanServer> mbservers = MBeanServerFactory
				.findMBeanServer(null);

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

	public static void register(ButlerAppMbean mbean) {
		MBeanServer server = getServer();

		ObjectName name = null;
		try {
			name = new ObjectName("Application:Name=" + mbean.getMbeanName()
					+ ",Type=Server");
			server.registerMBean(mbean, name);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
