package org.apps.butler.mbean;

import java.util.ArrayList;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ButlerServerImpl implements ButlerAppMbean {

	private final Logger logger = LoggerFactory
			.getLogger(ButlerServerImpl.class);

	public AppState appState() {
		logger.info("Query state.");
		return null;
	}

	public void start() throws Exception {
		logger.info("Invoke start.");

	}

	public void stop() throws Exception {
		logger.info("Invoke stop.");

	}

	public ButlerServerImpl() {
		MBeanServer server = getServer();

		ObjectName name = null;
		try {
			name = new ObjectName("Application:Name=" + "ButlerServer"
					+ ",Type=Server");
			server.registerMBean(this, name);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private long uptime = System.currentTimeMillis();

	public long uptime() {
		return System.currentTimeMillis() - uptime;
	}

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

	public String getDef() {
		return "test def";
	}

	public void setAbc() {
		logger.info("Invoke setAbc.");

	}

}
