package org.apps.butler.mbean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ButlerApp extends BaseBulterAppMbeanImpl {

	private static final Logger logger = LoggerFactory.getLogger(ButlerApp.class);

	private AppState state = AppState.UNKNOW;

	public AppState appState() {
		logger.info("Query state.");
		return state;
	}

	public void start() throws Exception {
		logger.info("Invoke start.");
		state = AppState.Running;

	}

	public void stop() throws Exception {
		logger.info("Invoke stop.");
		state = AppState.Stopped;

	}

	public ButlerApp() {
	}

}
