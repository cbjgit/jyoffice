package com.jyoffice.sys.model;

import java.io.Serializable;
import java.util.Date;

import com.jyoffice.ca.IUser;

/*
 * 
 * gen by beetlsql 2017-02-15
 */
public class SysUser implements IUser, Serializable {
	private Integer id;
	private String empId;
	private String lastLoginIp;
	private String loginId;
	private String password;
	private String userName;
	private Date lastLoginTime;
	private String locked;
	
	@Override
	public Integer getId() {
		return id;
	}
	@Override
	public String getEmpId() {
		return empId;
	}
	@Override
	public String getLoginId() {
		return loginId;
	}
	@Override
	public String getUserName() {
		return userName;
	}
	@Override
	public String getLocked() {
		return locked;
	}
	public String getLastLoginIp() {
		return lastLoginIp;
	}
	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
