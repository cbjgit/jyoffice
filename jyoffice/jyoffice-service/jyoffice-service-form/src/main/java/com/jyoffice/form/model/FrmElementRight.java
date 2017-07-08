package com.jyoffice.form.model;
import java.io.Serializable;
import java.math.*;
import java.util.Date;
import java.sql.Timestamp;

/*
* 
* gen by beetlsql 2017-02-08
*/
public class FrmElementRight   implements Serializable{
	private Integer id ;
	private Integer formId ;
	private Integer rights ;
	private String fieldName ;
	private String nodeId ;
	
	public FrmElementRight() {
	}
	
	public Integer getId(){
		return  id;
	}
	public void setId(Integer id ){
		this.id = id;
	}
	
	public Integer getFormId(){
		return  formId;
	}
	public void setFormId(Integer formId ){
		this.formId = formId;
	}
	
	public Integer getRights(){
		return  rights;
	}
	public void setRights(Integer rights ){
		this.rights = rights;
	}
	
	public String getFieldName(){
		return  fieldName;
	}
	public void setFieldName(String fieldName ){
		this.fieldName = fieldName;
	}
	
	public String getNodeId(){
		return  nodeId;
	}
	public void setNodeId(String nodeId ){
		this.nodeId = nodeId;
	}
	
	
	

}
