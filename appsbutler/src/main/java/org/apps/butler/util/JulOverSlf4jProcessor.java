package org.apps.butler.util;

import javax.annotation.PostConstruct;

import org.slf4j.bridge.SLF4JBridgeHandler;

/**
 * 初始化拦截Java.util.logging 到 Slf4j.
 *
 * @author calvin
 */
public class JulOverSlf4jProcessor {

	@PostConstruct
	public void init() {
		SLF4JBridgeHandler.install();
	}
}
