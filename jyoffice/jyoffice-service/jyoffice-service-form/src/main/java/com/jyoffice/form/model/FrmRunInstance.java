package com.jyoffice.form.model;
import java.io.Serializable;
import java.math.*;
import java.util.Date;
import java.sql.Timestamp;

import org.beetl.sql.core.annotatoin.AssignID;

/*
* 
* gen by beetlsql 2017-02-08
*/
public class FrmRunInstance   implements Serializable{
	
	private String id ;
	private Integer formId ;
	private Integer processId ;
	private String createBy ;
	private String formName ;
	private String instanceId ;
	private String status ;
	private String updateBy ;
	private Date createDate ;
	private Date updateDate ;
	private Integer submit = 0;
	
	public FrmRunInstance() {
	}
	
	@AssignID
	public String getId(){
		return  id;
	}
	public void setId(String id ){
		this.id = id;
	}
	
	public Integer getFormId(){
		return  formId;
	}
	public void setFormId(Integer formId ){
		this.formId = formId;
	}
	
	public Integer getProcessId() {
		return processId;
	}

	public void setProcessId(Integer processId) {
		this.processId = processId;
	}

	public String getCreateBy(){
		return  createBy;
	}
	public void setCreateBy(String createBy ){
		this.createBy = createBy;
	}
	
	public String getFormName(){
		return  formName;
	}
	public void setFormName(String formName ){
		this.formName = formName;
	}
	
	public String getInstanceId(){
		return  instanceId;
	}
	public void setInstanceId(String instanceId ){
		this.instanceId = instanceId;
	}
	
	public String getStatus(){
		return  status;
	}
	public void setStatus(String status ){
		this.status = status;
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

	public Integer getSubmit() {
		return submit;
	}

	public void setSubmit(Integer submit) {
		this.submit = submit;
	}

}
