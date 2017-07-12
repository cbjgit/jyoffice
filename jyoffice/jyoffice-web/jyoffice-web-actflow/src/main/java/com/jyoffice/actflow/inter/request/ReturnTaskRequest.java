package com.jyoffice.actflow.inter.request;


public class ReturnTaskRequest extends BaseRequest{

	private String taskId;
	
	private String destTaskKey;

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getDestTaskKey() {
		return destTaskKey;
	}

	public void setDestTaskKey(String destTaskKey) {
		this.destTaskKey = destTaskKey;
	}
	
}
