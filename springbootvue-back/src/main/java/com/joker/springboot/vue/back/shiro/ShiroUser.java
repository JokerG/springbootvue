package com.joker.springboot.vue.back.shiro;

import java.io.Serializable;
import java.math.BigDecimal;

public class ShiroUser implements Serializable {
	private static final long serialVersionUID = 1L;
	private String loginId;
	private String userName;
	private String userType;
	private BigDecimal userOid;
	private String blocked;
	private String active;

	public ShiroUser() {
	}

	public ShiroUser(String loginId, String userName, String userType, BigDecimal userOid, String blocked,
			String active) {
		this.loginId = loginId;
		this.userName = userName;
		this.userType = userType;
		this.userOid = userOid;
		this.blocked = blocked;
		this.active = active;
	}

	@Override
	public String toString() {
		return this.loginId;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ShiroUser other = (ShiroUser) obj;
		if (loginId == null) {
			return other.loginId == null;
		} else {
			return loginId.equals(other.loginId);
		}
	}

	public String getBlocked() {
		return blocked;
	}

	public void setBlocked(String blocked) {
		this.blocked = blocked;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public BigDecimal getUserOid() {
		return userOid;
	}

	public void setUserOid(BigDecimal userOid) {
		this.userOid = userOid;
	}

}
