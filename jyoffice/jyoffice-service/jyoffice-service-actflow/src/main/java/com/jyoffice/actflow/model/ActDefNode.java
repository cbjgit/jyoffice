package com.jyoffice.actflow.model;
import java.io.Serializable;

/*
* 
* gen by beetlsql 2017-02-08
*/
public class ActDefNode   implements Serializable{
	private Integer id ;
	private Integer back ;
	private Integer completionCondition ;
	private Integer multi ;
	private Integer multiType ;
	private Integer processId ;
	private Integer rate ;
	private Integer recall ;
	private String assgnee ;
	private Integer assigneeType ;
	private String nodeId ;
	private String nodeNames ;
	private String nodeTypes ;
	
	private String taskUrl;
	private String hitaskUrl;
	private String scope;
	private Integer scopeType;
	
	public ActDefNode() {
	}
	
	public Integer getId(){
		return  id;
	}
	public void setId(Integer id ){
		this.id = id;
	}
	
	public Integer getAssigneeType(){
		return  assigneeType;
	}
	public void setAssigneeType(Integer assigneeType ){
		this.assigneeType = assigneeType;
	}
	
	public Integer getBack(){
		return  back;
	}
	public void setBack(Integer back ){
		this.back = back;
	}
	
	public Integer getCompletionCondition(){
		return  completionCondition;
	}
	public void setCompletionCondition(Integer completionCondition ){
		this.completionCondition = completionCondition;
	}
	
	public Integer getMulti(){
		return  multi;
	}
	public void setMulti(Integer multi ){
		this.multi = multi;
	}
	
	public Integer getMultiType(){
		return  multiType;
	}
	public void setMultiType(Integer multiType ){
		this.multiType = multiType;
	}
	
	public Integer getProcessId(){
		return  processId;
	}
	public void setProcessId(Integer processId ){
		this.processId = processId;
	}
	
	public Integer getRate(){
		return  rate;
	}
	public void setRate(Integer rate ){
		this.rate = rate;
	}
	
	public Integer getRecall(){
		return  recall;
	}
	public void setRecall(Integer recall ){
		this.recall = recall;
	}
	
	public String getAssgnee(){
		return  assgnee;
	}
	public void setAssgnee(String assgnee ){
		this.assgnee = assgnee;
	}
	
	public String getNodeId(){
		return  nodeId;
	}
	public void setNodeId(String nodeId ){
		this.nodeId = nodeId;
	}
	
	public String getNodeNames(){
		return  nodeNames;
	}
	public void setNodeNames(String nodeNames ){
		this.nodeNames = nodeNames;
	}
	
	public String getNodeTypes(){
		return  nodeTypes;
	}
	public void setNodeTypes(String nodeTypes ){
		this.nodeTypes = nodeTypes;
	}

	public String getTaskUrl() {
		return taskUrl;
	}

	public void setTaskUrl(String taskUrl) {
		this.taskUrl = taskUrl;
	}

	public String getHitaskUrl() {
		return hitaskUrl;
	}

	public void setHitaskUrl(String hitaskUrl) {
		this.hitaskUrl = hitaskUrl;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public Integer getScopeType() {
		return scopeType;
	}

	public void setScopeType(Integer scopeType) {
		this.scopeType = scopeType;
	}
}
