package com.jyoffice.actflow.model;
import java.io.Serializable;
import java.math.*;
import java.util.Date;
import java.sql.Timestamp;

/*
* 
* gen by beetlsql 2017-07-15
*/
public class ActHiActinst   implements Serializable{
	private String id ;
	private String actId ;
	private String actName ;
	private String actType ;
	private String assignee ;
	private String callProcInstId ;
	private String deleteReason ;
	private Long duration ;
	private String executionId ;
	private String procDefId ;
	private String procInstId ;
	private String taskId ;
	private String tenantId ;
	private Date endTime ;
	private Date startTime ;
	
	public ActHiActinst() {
	}
	
	public String getId(){
		return  id;
	}
	public void setId(String id ){
		this.id = id;
	}
	
	public String getActId(){
		return  actId;
	}
	public void setActId(String actId ){
		this.actId = actId;
	}
	
	public String getActName(){
		return  actName;
	}
	public void setActName(String actName ){
		this.actName = actName;
	}
	
	public String getActType(){
		return  actType;
	}
	public void setActType(String actType ){
		this.actType = actType;
	}
	
	public String getAssignee(){
		return  assignee;
	}
	public void setAssignee(String assignee ){
		this.assignee = assignee;
	}
	
	public String getCallProcInstId(){
		return  callProcInstId;
	}
	public void setCallProcInstId(String callProcInstId ){
		this.callProcInstId = callProcInstId;
	}
	
	public String getDeleteReason(){
		return  deleteReason;
	}
	public void setDeleteReason(String deleteReason ){
		this.deleteReason = deleteReason;
	}
	
	public Long getDuration(){
		return  duration;
	}
	public void setDuration(Long duration ){
		this.duration = duration;
	}
	
	public String getExecutionId(){
		return  executionId;
	}
	public void setExecutionId(String executionId ){
		this.executionId = executionId;
	}
	
	public String getProcDefId(){
		return  procDefId;
	}
	public void setProcDefId(String procDefId ){
		this.procDefId = procDefId;
	}
	
	public String getProcInstId(){
		return  procInstId;
	}
	public void setProcInstId(String procInstId ){
		this.procInstId = procInstId;
	}
	
	public String getTaskId(){
		return  taskId;
	}
	public void setTaskId(String taskId ){
		this.taskId = taskId;
	}
	
	public String getTenantId(){
		return  tenantId;
	}
	public void setTenantId(String tenantId ){
		this.tenantId = tenantId;
	}
	
	public Date getEndTime(){
		return  endTime;
	}
	public void setEndTime(Date endTime ){
		this.endTime = endTime;
	}
	
	public Date getStartTime(){
		return  startTime;
	}
	public void setStartTime(Date startTime ){
		this.startTime = startTime;
	}
	
	
	

}
