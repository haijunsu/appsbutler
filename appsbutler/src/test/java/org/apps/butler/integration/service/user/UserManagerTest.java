package org.apps.butler.integration.service.user;

import org.apache.commons.lang.RandomStringUtils;
import org.apps.butler.entity.user.Role;
import org.apps.butler.entity.user.User;
import org.apps.butler.service.user.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.ExpectedException;
import org.springside.modules.test.SpringTransactionalTestCase;

/**
 * UserManager的集成测试用例.
 * 
 * 调用实际的DAO类进行操作,默认在操作后进行回滚.
 * 
 * @author calvin
 */
public class UserManagerTest extends SpringTransactionalTestCase {

	@Autowired
	private UserManager userManager;

	public void testCRUD() {
		//保存角色并验证.
		User entity = new User();
		// 因为LoginName要求唯一性，因此添加random字段。
		entity.setLoginName("tester" + RandomStringUtils.randomAlphabetic(5));
		entity.setName("foo");
		entity.setEmail("foo@bar.com");
		entity.setPassword("foo");
		userManager.saveUser(entity);
		flush();
		assertNotNull(entity.getId());

		//删除角色并验证
		userManager.deleteUser(entity.getId());
		flush();
	}

	public void testUserRoles() {
		//保存带角色的用户并验证
		User entity = new User();
		entity.setLoginName("tester" + RandomStringUtils.randomAlphabetic(5));

		Role role = new Role();
		role.setId(1L);
		entity.getRoles().add(role);
		userManager.saveUser(entity);
		flush();
		entity = userManager.getUser(entity.getId());
		assertEquals(1, entity.getRoles().size());

		//删除角色并验证
		entity.getRoles().remove(role);
		flush();
		entity = userManager.getUser(entity.getId());
		assertEquals(0, entity.getRoles().size());
	}

	@ExpectedException(value = org.hibernate.exception.ConstraintViolationException.class)
	public void testSavenEntityNotUnique() {
		User entity = new User();
		entity.setLoginName("admin");
		userManager.saveUser(entity);
		flush();
	}

	public void testAuth() {
		assertTrue(userManager.auth("admin", "admin"));
		assertFalse(userManager.auth("admin", ""));
	}
}