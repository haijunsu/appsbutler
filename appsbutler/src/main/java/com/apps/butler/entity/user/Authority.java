package com.apps.butler.entity.user;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import com.apps.butler.entity.IdEntity;

/**
 * 权限.
 * 
 * @Cache使用READ_ONLY策略.
 * 
 * @author calvin
 */
@Entity
@Table(name = "AUTHORITIES")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class Authority extends IdEntity {

	private String name;

	private String displayName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
