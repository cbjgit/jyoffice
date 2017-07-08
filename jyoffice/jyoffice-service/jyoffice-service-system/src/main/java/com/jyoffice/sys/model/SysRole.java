package com.jyoffice.sys.model;
import java.io.Serializable;
import java.math.*;
import java.util.Date;
import java.sql.Timestamp;

/*
* 
* gen by beetlsql 2017-02-15
*/
public class SysRole   implements Serializable{
	private Integer id ;
	private String createBy ;
	private String roleCode ;
	private String roleDesc ;
	private String roleName ;
	private String updateBy ;
	private Date createDate ;
	private Date updateDate ;
	
	public SysRole() {
	}
	
	public Integer getId(){
		return  id;
	}
	public void setId(Integer id ){
		this.id = id;
	}
	
	public String getCreateBy(){
		return  createBy;
	}
	public void setCreateBy(String createBy ){
		this.createBy = createBy;
	}
	
	public String getRoleCode(){
		return  roleCode;
	}
	public void setRoleCode(String roleCode ){
		this.roleCode = roleCode;
	}
	
	public String getRoleDesc(){
		return  roleDesc;
	}
	public void setRoleDesc(String roleDesc ){
		this.roleDesc = roleDesc;
	}
	
	public String getRoleName(){
		return  roleName;
	}
	public void setRoleName(String roleName ){
		this.roleName = roleName;
	}
	
	public String getUpdateBy(){
		return  updateBy;
	}
	public void setUpdateBy(String updateBy ){
		this.updateBy = updateBy;
	}
	
	public Date getCreateDate(){
		return  createDate;
	}
	public void setCreateDate(Date createDate ){
		this.createDate = createDate;
	}
	
	public Date getUpdateDate(){
		return  updateDate;
	}
	public void setUpdateDate(Date updateDate ){
		this.updateDate = updateDate;
	}
	
	
	

}
