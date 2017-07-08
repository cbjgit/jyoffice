package com.jyoffice.form.model;
import java.io.Serializable;
import java.math.*;
import java.util.Date;
import java.sql.Timestamp;

/*
* 
* gen by beetlsql 2017-02-08
*/
public class FrmForm   implements Serializable{
	private Integer id ;
	private Integer columnNumber ;
	private Integer processId ;
	private Integer status = 0;
	private Integer version ;
	private String createBy ;
	private String descption ;
	private String formKey ;
	private String formName ;
	private String updateBy ;
	private Date createDate ;
	private Date updateDate ;
	
	public FrmForm() {
	}
	
	public Integer getId(){
		return  id;
	}
	public void setId(Integer id ){
		this.id = id;
	}
	
	public Integer getColumnNumber(){
		return  columnNumber;
	}
	public void setColumnNumber(Integer columnNumber ){
		this.columnNumber = columnNumber;
	}
	
	public Integer getProcessId(){
		return  processId;
	}
	public void setProcessId(Integer processId ){
		this.processId = processId;
	}
	
	public Integer getStatus(){
		return  status;
	}
	public void setStatus(Integer status ){
		this.status = status;
	}
	
	public Integer getVersion(){
		return  version;
	}
	public void setVersion(Integer version ){
		this.version = version;
	}
	
	public String getCreateBy(){
		return  createBy;
	}
	public void setCreateBy(String createBy ){
		this.createBy = createBy;
	}
	
	public String getDescption(){
		return  descption;
	}
	public void setDescption(String descption ){
		this.descption = descption;
	}
	
	public String getFormKey(){
		return  formKey;
	}
	public void setFormKey(String formKey ){
		this.formKey = formKey;
	}
	
	public String getFormName(){
		return  formName;
	}
	public void setFormName(String formName ){
		this.formName = formName;
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
