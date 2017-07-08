package com.jyoffice.sys.model;
import java.io.Serializable;
import java.math.*;
import java.util.Date;
import java.sql.Timestamp;

/*
* 
* gen by beetlsql 2017-02-15
*/
public class SysRoleAuth   implements Serializable{
	private Integer id ;
	private Integer authId ;
	private Integer roleId ;
	private String createBy ;
	private Date createDate ;
	
	public SysRoleAuth() {
	}
	
	public Integer getId(){
		return  id;
	}
	public void setId(Integer id ){
		this.id = id;
	}
	
	public Integer getAuthId(){
		return  authId;
	}
	public void setAuthId(Integer authId ){
		this.authId = authId;
	}
	
	public Integer getRoleId(){
		return  roleId;
	}
	public void setRoleId(Integer roleId ){
		this.roleId = roleId;
	}
	
	public String getCreateBy(){
		return  createBy;
	}
	public void setCreateBy(String createBy ){
		this.createBy = createBy;
	}
	
	public Date getCreateDate(){
		return  createDate;
	}
	public void setCreateDate(Date createDate ){
		this.createDate = createDate;
	}
	
	
	

}
