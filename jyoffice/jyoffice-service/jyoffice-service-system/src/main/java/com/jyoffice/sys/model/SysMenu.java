package com.jyoffice.sys.model;
import java.io.Serializable;
import java.math.*;
import java.util.Date;
import java.sql.Timestamp;

/*
* 
* gen by beetlsql 2017-02-15
*/
public class SysMenu   implements Serializable{
	private Integer id ;
	private Integer isParent ;
	private Integer parentId ;
	private String createBy ;
	private String menuCode ;
	private String menuDesc ;
	private String menuName ;
	private String context ;
	private String menuUrl ;
	private String updateBy ;
	private Date createDate ;
	private Date updateDate ;
	
	public SysMenu() {
	}
	
	public Integer getId(){
		return  id;
	}
	public void setId(Integer id ){
		this.id = id;
	}
	
	public Integer getIsParent(){
		return  isParent;
	}
	public void setIsParent(Integer isParent ){
		this.isParent = isParent;
	}
	
	public Integer getParentId(){
		return  parentId;
	}
	public void setParentId(Integer parentId ){
		this.parentId = parentId;
	}
	
	public String getCreateBy(){
		return  createBy;
	}
	public void setCreateBy(String createBy ){
		this.createBy = createBy;
	}
	
	public String getMenuCode(){
		return  menuCode;
	}
	public void setMenuCode(String menuCode ){
		this.menuCode = menuCode;
	}
	
	public String getMenuDesc(){
		return  menuDesc;
	}
	public void setMenuDesc(String menuDesc ){
		this.menuDesc = menuDesc;
	}
	
	public String getMenuName(){
		return  menuName;
	}
	public void setMenuName(String menuName ){
		this.menuName = menuName;
	}
	
	public String getMenuUrl(){
		return  menuUrl;
	}
	public void setMenuUrl(String menuUrl ){
		this.menuUrl = menuUrl;
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

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}
	
}
