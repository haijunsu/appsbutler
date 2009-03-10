package org.apps.butler.mbean;

public abstract class BaseBulterAppMbeanImpl implements ButlerAppMbean {

	private String mbeanName;

	private long uptime = System.currentTimeMillis();

	public String getMbeanName() {
		return mbeanName;
	}

	public BaseBulterAppMbeanImpl(String mbeanName) {
		this.mbeanName = mbeanName;
	}

	public long uptime() {
		return System.currentTimeMillis() - uptime;
	}

}
