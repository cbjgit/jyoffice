package com.jyoffice.actflow.inter.request;


public class ReturnTaskRequest extends BaseRequest{

	private String taskId;

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
}
