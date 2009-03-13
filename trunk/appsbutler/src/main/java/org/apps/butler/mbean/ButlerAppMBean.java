package org.apps.butler.mbean;

public interface ButlerAppMBean {

	public long uptime();

	public AppState appState();

	public void start() throws Exception;

	public void stop() throws Exception;

}
