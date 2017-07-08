package com.jyoffice.actflow.inter.request;

import java.util.Map;

public class SubmitTaskRequest extends BaseRequest{

	private String taskId;
	
	private String userId;

	private Map<String,String> actvar;
	
	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Map<String, String> getActvar() {
		return actvar;
	}

	public void setActvar(Map<String, String> actvar) {
		this.actvar = actvar;
	}
	
}
