package com.jyoffice.actflow.model;

import java.io.Serializable;

/*
 * 
 * gen by beetlsql 2017-07-02
 */
public class ActInstanceExt implements Serializable {

	private String id;
	private String instanceId;
	private String busKey;
	private String appUserid;
	private String appUsername;
	private String title;
	private Integer processId;
	private String processKey;

	public ActInstanceExt() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getBusKey() {
		return busKey;
	}

	public void setBusKey(String busKey) {
		this.busKey = busKey;
	}

	public String getAppUserid() {
		return appUserid;
	}

	public void setAppUserid(String appUserid) {
		this.appUserid = appUserid;
	}

	public String getAppUsername() {
		return appUsername;
	}

	public void setAppUsername(String appUsername) {
		this.appUsername = appUsername;
	}


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getProcessId() {
		return processId;
	}

	public void setProcessId(Integer processId) {
		this.processId = processId;
	}

	public String getProcessKey() {
		return processKey;
	}

	public void setProcessKey(String processKey) {
		this.processKey = processKey;
	}

}
