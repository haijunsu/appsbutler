package org.springside.modules.webservice;

import javax.annotation.PostConstruct;

import net.sf.dozer.util.mapping.MapperIF;

import org.apps.butler.util.DozerMapperSingleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 可选的WebService辅助基类.
 * 暂时只提供了公共的logger和dozer及转换未知错误的方法,日后将提供更多公共能力.
 *
 * @see net.sf.dozer.util.mapping.DozerBeanMapper
 * @author calvin
 */
public abstract class WebServiceSupport {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected MapperIF dozer;

	public void setDozer(MapperIF dozer) {
		this.dozer = dozer;
	}

	@PostConstruct
	public void initDozer() {
		// 如果Spring初始化后dozer未注入,获取无配置文件的mapper单例.
		if (dozer == null) {
			logger.info("ApplicationContext中不存在dozer mapper,使用无配置文件的默认dozer.");
			dozer = DozerMapperSingleton.getInstance();
		}
	}
}
