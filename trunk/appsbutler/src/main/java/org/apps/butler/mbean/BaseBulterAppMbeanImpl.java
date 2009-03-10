package org.apps.butler.mbean;


public abstract class BaseBulterAppMbeanImpl implements ButlerAppMbean {

	private long uptime = System.currentTimeMillis();

	public long uptime() {
		return System.currentTimeMillis() - uptime;
	}


}
