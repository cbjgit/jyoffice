package com.jyoffice.sys.model;
import java.io.Serializable;
import java.math.*;
import java.util.Date;
import java.sql.Timestamp;

/*
* 
* gen by beetlsql 2017-02-15
*/
public class SysRoleUser   implements Serializable{
	private Integer id ;
	private Integer roleId ;
	private Integer userId ;
	private String createBy ;
	private Date createDate ;
	
	public SysRoleUser() {
	}
	
	public Integer getId(){
		return  id;
	}
	public void setId(Integer id ){
		this.id = id;
	}
	
	public Integer getRoleId(){
		return  roleId;
	}
	public void setRoleId(Integer roleId ){
		this.roleId = roleId;
	}
	
	public Integer getUserId(){
		return  userId;
	}
	public void setUserId(Integer userId ){
		this.userId = userId;
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
