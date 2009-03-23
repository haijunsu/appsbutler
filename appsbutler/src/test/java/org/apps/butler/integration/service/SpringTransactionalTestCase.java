package org.apps.butler.integration.service;

import org.hibernate.SessionFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit38.AbstractTransactionalJUnit38SpringContextTests;

/**
 * Spring支持数据库事务、依赖注入的基于JUnit 3.8的基类的便捷简写与flush()函数.
 *
 * 如果需要默认载入更多的applicatioContext.xml,在项目中重写本类.
 *
 * @author calvin
 */
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class SpringTransactionalTestCase extends AbstractTransactionalJUnit38SpringContextTests {

	/**
	 * 刷新默认的sessionFactory,强制Hibernate执行SQL以验证ORM配置.
	 * SQL执行的结果只要不进行提交就不会影响测试数据库的实际数据.
	 */
	public void flush() {
		flush("sessionFactory");
	}

	/**
	 * 刷新sessionFactory,强制Hibernate执行SQL以验证ORM配置.
	 * SQL执行的结果只要不进行提交就不会影响测试数据库的实际数据.
	 */
	public void flush(String sessionFactoryName) {
		((SessionFactory) applicationContext.getBean(sessionFactoryName)).getCurrentSession().flush();
	}
}
