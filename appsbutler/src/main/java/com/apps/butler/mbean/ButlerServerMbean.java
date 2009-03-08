package com.apps.butler.mbean;

public interface ButlerServerMbean {

	public AppState getAppState();

	public void start() throws Exception;

	public void stop() throws Exception;

}
