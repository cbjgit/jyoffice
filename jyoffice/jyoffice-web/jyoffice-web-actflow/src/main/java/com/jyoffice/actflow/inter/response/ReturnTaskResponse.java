package com.jyoffice.actflow.inter.response;

import java.util.List;

public class ReturnTaskResponse extends BaseResponse{

	/**
	 * 退回的任务
	 */
	private List<RspTask> tasks;

	public List<RspTask> getTask() {
		return tasks;
	}

	public void setTask(List<RspTask> task) {
		this.tasks = task;
	}
	
	public class RspTask{
		
		private String taskId;
		
		private String taskKey;
		
		private String taskName;

		public String getTaskId() {
			return taskId;
		}

		public void setTaskId(String taskId) {
			this.taskId = taskId;
		}

		public String getTaskKey() {
			return taskKey;
		}

		public void setTaskKey(String taskKey) {
			this.taskKey = taskKey;
		}

		public String getTaskName() {
			return taskName;
		}

		public void setTaskName(String taskName) {
			this.taskName = taskName;
		}
	}
}
