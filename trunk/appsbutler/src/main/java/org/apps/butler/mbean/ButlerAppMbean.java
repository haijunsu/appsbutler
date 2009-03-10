package org.apps.butler.mbean;

public interface ButlerAppMbean {

	public long uptime();

	public AppState appState();

	public String getMbeanName();

	public void start() throws Exception;

	public void stop() throws Exception;

}
