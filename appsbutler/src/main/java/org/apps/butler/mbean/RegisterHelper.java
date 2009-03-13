package org.apps.butler.mbean;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;
import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegisterHelper {

	private static final Logger logger = LoggerFactory
			.getLogger(RegisterHelper.class);

	private static MBeanServer mbserver = null;

	private static ReentrantLock lock = new ReentrantLock();

	private static MBeanServer getServer() {
		try {
			lock.lock();
			if (mbserver == null) {
				ArrayList<MBeanServer> mbservers = MBeanServerFactory
						.findMBeanServer(null);
				if (mbservers.size() > 0) {
					mbserver = (MBeanServer) mbservers.get(0);
				}
				if (mbserver != null) {
					logger.debug("MBean Server existed");
				} else {
					mbserver = MBeanServerFactory.createMBeanServer();
				}
			}
		} finally {
			lock.unlock();
		}

		return mbserver;
	}

	/**
	 * Used for web server to register their mbeans. Registered bean's name is
	 * combined with context name and class name.
	 *
	 * @param context
	 *            ServletContext
	 * @param mbean
	 *            implement xxxMBean' class
	 * @throws Exception
	 *             register failed
	 */
	public static void register(ServletContext context, Object mbean)
			throws Exception {
		String name = "";
		if (context != null) {
			name = context.getServletContextName() + "_";
		}
		name += mbean.getClass().getName();
		register(mbean, name);
	}

	/**
	 * Used for web server to register their mbeans. Registered bean's name is
	 * class name with prefix like 'ButlerApp_".
	 *
	 * @param context
	 *            ServletContext
	 * @param mbean
	 *            implement xxxMBean' class
	 * @throws Exception
	 *             register failed
	 */

	public static void register(Object mbean) throws Exception {
		register(mbean, "ButlerApp_" + mbean.getClass().getName());
	}

	private static void register(Object mbean, String mbeanName)
			throws Exception {
		MBeanServer server = getServer();

		ObjectName name = null;
		try {
			name = new ObjectName("Application:Name=" + mbeanName
					+ ",Type=Server");
			logger.info("name=" + name);
			server.registerMBean(mbean, name);
		} catch (Exception e) {
			logger.error("Register mbean error!", e);
			throw e;
		}

	}
}
