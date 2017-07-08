package com.jyoffice.sys.model;
import java.io.Serializable;
import java.util.Date;

/*
* 
* gen by beetlsql 2017-02-15
*/
public class SysAuth   implements Serializable{
	private Integer id ;
	private Integer menuId ;
	private String authName ;
	private String authPath ;
	private String authId ;
	private String createBy ;
	private Date createDate ;
	public SysAuth() {
	}
	
	public Integer getId(){
		return  id;
	}
	public void setId(Integer id ){
		this.id = id;
	}
	
	public Integer getMenuId(){
		return  menuId;
	}
	public void setMenuId(Integer menuId ){
		this.menuId = menuId;
	}
	
	public String getAuthName(){
		return  authName;
	}
	public void setAuthName(String authName ){
		this.authName = authName;
	}
	
	public String getAuthPath(){
		return  authPath;
	}
	public void setAuthPath(String authPath ){
		this.authPath = authPath;
	}
	
	public String getAuthId(){
		return  authId;
	}
	public void setAuthId(String authId ){
		this.authId = authId;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
