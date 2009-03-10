package org.apps.butler.mbean;

public interface ButlerAppMbean {

//	public long uptime();

	public void setAbc();

	public String getDef();

	public AppState appState();

	public void start() throws Exception;

	public void stop() throws Exception;

}
