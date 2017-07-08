package com.jyoffice.actflow.model;
import java.io.Serializable;

/*
* 
* gen by beetlsql 2017-02-08
*/
public class ActDefSequence   implements Serializable{
	private Integer id ;
	private Integer processId;
	private String conditionExpression ;
	private String nodeId ;
	private String toNodeId ;
	
	public ActDefSequence() {
	}
	
	public Integer getId(){
		return  id;
	}
	public void setId(Integer id ){
		this.id = id;
	}
	
	public Integer getProcessId(){
		return  processId;
	}
	public void setProcessId(Integer processId ){
		this.processId = processId;
	}
	
	public String getConditionExpression() {
		return conditionExpression;
	}

	public void setConditionExpression(String conditionExpression) {
		this.conditionExpression = conditionExpression;
	}

	public String getNodeId(){
		return  nodeId;
	}
	public void setNodeId(String nodeId ){
		this.nodeId = nodeId;
	}
	
	public String getToNodeId(){
		return  toNodeId;
	}
	public void setToNodeId(String toNodeId ){
		this.toNodeId = toNodeId;
	}
	
	
	

}
