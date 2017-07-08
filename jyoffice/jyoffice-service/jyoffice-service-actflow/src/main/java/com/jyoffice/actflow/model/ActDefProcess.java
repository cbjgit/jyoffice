package com.jyoffice.actflow.model;
import java.io.Serializable;
import java.util.Date;

/*
* 
* gen by beetlsql 2017-02-08
*/
public class ActDefProcess   implements Serializable{
	private Integer id ;
	private Integer status ;
	//版本
	private Integer version ;
	private String createBy ;
	private String definitionId ;
	private String deployId ;
	//描述
	private String descption ;
	//流程编码
	private String processKey ;
	//流程名称
	private String processName ;
	private String updateBy ;
	private Date createDate ;
	private Date deployeeDate ;
	private Date updateDate ;
	
	public ActDefProcess() {
	}
	
	public Integer getId(){
		return  id;
	}
	public void setId(Integer id ){
		this.id = id;
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
	
	public String getDefinitionId(){
		return  definitionId;
	}
	public void setDefinitionId(String definitionId ){
		this.definitionId = definitionId;
	}
	
	public String getDeployId(){
		return  deployId;
	}
	public void setDeployId(String deployId ){
		this.deployId = deployId;
	}
	
	public String getDescption(){
		return  descption;
	}
	public void setDescption(String descption ){
		this.descption = descption;
	}
	
	public String getProcessKey(){
		return  processKey;
	}
	public void setProcessKey(String processKey ){
		this.processKey = processKey;
	}
	
	public String getProcessName(){
		return  processName;
	}
	public void setProcessName(String processName ){
		this.processName = processName;
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
	
	public Date getDeployeeDate(){
		return  deployeeDate;
	}
	public void setDeployeeDate(Date deployeeDate ){
		this.deployeeDate = deployeeDate;
	}
	
	public Date getUpdateDate(){
		return  updateDate;
	}
	public void setUpdateDate(Date updateDate ){
		this.updateDate = updateDate;
	}
	
	
	

}
