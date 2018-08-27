package com.joker.springboot.vue.back.VO;

import java.math.BigDecimal;
import java.util.Date;

public class UserVO {
	private String loginId;
	private String userName;
	private String loginPwd;
	private String userType;
	private BigDecimal userOid;
	private String blocked;
	private String active;
	private Date blockDate;

	public String getLoginPwd() {
		return loginPwd;
	}

	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}

	public Date getBlockDate() {
		return blockDate;
	}

	public void setBlockDate(Date blockDate) {
		this.blockDate = blockDate;
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

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public BigDecimal getUserOid() {
		return userOid;
	}

	public void setUserOid(BigDecimal userOid) {
		this.userOid = userOid;
	}
}
