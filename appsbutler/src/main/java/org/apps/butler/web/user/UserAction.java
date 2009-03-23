package org.apps.butler.web.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.ServletActionRedirectResult;
import org.apps.butler.entity.user.Role;
import org.apps.butler.entity.user.User;
import org.apps.butler.hibernate.Page;
import org.apps.butler.service.ServiceException;
import org.apps.butler.service.user.UserManager;
import org.apps.butler.util.CollectionUtils;
import org.apps.butler.web.struts2.CRUDActionSupport;
import org.springframework.beans.factory.annotation.Required;


/**
 * 用户管理Action.
 *
 * @see CRUDActionSupport
 *
 * @author calvin
 */
@ParentPackage("default")
@Results( { @Result(name = CRUDActionSupport.RELOAD, value = "/user", type = ServletActionRedirectResult.class) })
public class UserAction extends CRUDActionSupport<User> {

	private static final long serialVersionUID = -2180690009159324387L;

	private UserManager manager;

	private Page<User> page = new Page<User>(5, true);//每页5项，自动查询计算总页数.

	private User entity;

	private Long id;

	private List<Role> allRoles;

	private List<Long> checkedRoleIds;

	public User getModel() {
		return entity;
	}

	public Page<User> getPage() {
		return page;
	}

	public List<Role> getAllRoles() {
		return allRoles;
	}

	public List<Long> getCheckedRoleIds() {
		return checkedRoleIds;
	}

	public void setCheckedRoleIds(List<Long> checkedRoleIds) {
		this.checkedRoleIds = checkedRoleIds;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	protected void prepareModel() throws Exception {
		if (id != null) {
			entity = manager.getUser(id);
		} else {
			entity = new User();
		}
	}

	@Override
	public String list() throws Exception {
		page = manager.getAllUsers(page);
		return SUCCESS;
	}

	@Override
	public String input() throws Exception {
		allRoles = manager.getAllRoles();
		checkedRoleIds = entity.getRoleIds();
		return INPUT;
	}

	@Override
	public String save() throws Exception {
		//根据页面上的checkbox 整合entity的roles Set
		CollectionUtils.mergeByCheckedIds(entity.getRoles(), checkedRoleIds, Role.class);
		manager.saveUser(entity);
		addActionMessage("保存用户成功");
		return RELOAD;
	}

	@Override
	public String delete() throws Exception {
		try {
			manager.deleteUser(id);
			addActionMessage("删除用户成功");
		} catch (ServiceException e) {
			logger.error(e.getMessage(), e);
			addActionMessage(e.getMessage());
		}
		return RELOAD;
	}

	/**
	 * 支持使用Jquery.validate Ajax检验用户名是否重复.
	 */
	public String checkLoginName() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String loginName = request.getParameter("loginName");
		String orgLoginName = request.getParameter("orgLoginName");

		if (manager.isLoginNameUnique(loginName, orgLoginName))
			return renderText("true");
		else
			return renderText("false");
	}

	@Required
	public void setUserManager(UserManager userManager) {
		manager = userManager;
	}
}
