package org.apps.butler.mbean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ButlerServerImpl extends BaseBulterAppMbeanImpl implements
		ButlerAppMbean {

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

	public ButlerServerImpl(String serverName) {
		super(serverName);
	}

}
