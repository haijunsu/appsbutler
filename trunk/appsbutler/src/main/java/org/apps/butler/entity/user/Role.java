package org.apps.butler.entity.user;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apps.butler.entity.IdEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springside.modules.utils.CollectionUtils;

/**
 * 角色.
 * 
 * 注意@Cache(Entity与集合的缓存),@ManyToMany/@JoinTable(多对多关系),@OrderBy/LinkedHashSet(集合排序),@Transient(非持久化属性)的应用.
 * 
 * @author calvin
 */
@Entity
@Table(name = "ROLES")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Role extends IdEntity {

	private String name;

	private Set<Authority> auths = new LinkedHashSet<Authority>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "ROLES_AUTHORITIES", joinColumns = { @JoinColumn(name = "ROLE_ID") }, inverseJoinColumns = { @JoinColumn(name = "AUTHORITY_ID") })
	@OrderBy("id")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public Set<Authority> getAuths() {
		return auths;
	}

	public void setAuths(Set<Authority> auths) {
		this.auths = auths;
	}

	@Transient
	public String getAuthNames() throws Exception {
		return CollectionUtils.fetchPropertyToString(auths, "displayName", ", ");
	}

	@SuppressWarnings("unchecked")
	@Transient
	public List<Long> getAuthIds() throws Exception {
		return CollectionUtils.fetchPropertyToList(auths, "id");
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
