package org.apps.butler.web.user;

import java.util.List;

import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.ServletActionRedirectResult;
import org.apps.butler.entity.user.Authority;
import org.apps.butler.entity.user.Role;
import org.apps.butler.service.user.UserManager;
import org.apps.butler.util.CollectionUtils;
import org.apps.butler.web.struts2.CRUDActionSupport;
import org.springframework.beans.factory.annotation.Required;

/**
 * 角色管理Action.
 *
 * @see CRUDActionSupport
 * @author calvin
 */
@ParentPackage("default")
@Results( { @Result(name = CRUDActionSupport.RELOAD, value = "/role", type = ServletActionRedirectResult.class) })
public class RoleAction extends CRUDActionSupport<Role> {

	private static final long serialVersionUID = 8517636900960039101L;

	private UserManager manager;

	private List<Role> allRoles;

	private Role entity;

	private Long id;

	private List<Long> checkedAuthIds;

	private List<Authority> allAuths;

	public Role getModel() {
		return entity;
	}

	public List<Role> getAllRoles() {
		return allRoles;
	}

	public List<Authority> getAllAuths() {
		return allAuths;
	}

	public List<Long> getCheckedAuthIds() {
		return checkedAuthIds;
	}

	public void setCheckedAuthIds(List<Long> checkedAuthIds) {
		this.checkedAuthIds = checkedAuthIds;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	protected void prepareModel() throws Exception {
		if (id != null) {
			entity = manager.getRole(id);
		} else {
			entity = new Role();
		}
	}

	@Override
	public String list() throws Exception {
		allRoles = manager.getAllRoles();
		return SUCCESS;
	}

	@Override
	public String input() throws Exception {
		allAuths = manager.getAllAuths();
		checkedAuthIds = entity.getAuthIds();
		return INPUT;
	}

	@Override
	public String save() throws Exception {
		//根据页面上的checkbox 整合entity的auth Set.
		CollectionUtils.mergeByCheckedIds(entity.getAuths(), checkedAuthIds, Authority.class);
		manager.saveRole(entity);
		addActionMessage("保存角色成功");
		return RELOAD;
	}

	@Override
	public String delete() throws Exception {
		manager.deleteRole(id);
		addActionMessage("删除角色成功");
		return RELOAD;
	}

	@Required
	public void setUserManager(UserManager userManager) {
		manager = userManager;
	}
}