package com.jyoffice.actflow.inter.response;

import java.util.List;

public class TaskSearchResponse extends BaseResponse{

	private List<RspTask> tasks;

	public List<RspTask> getTask() {
		return tasks;
	}

	public void setTask(List<RspTask> task) {
		this.tasks = task;
	}
	
	public class RspTask{
		
		private String busKey;
		
		private String taskId;
		
		private String taskKey;
		
		private String taskName;
		
		private String url;

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

		public String getBusKey() {
			return busKey;
		}

		public void setBusKey(String busKey) {
			this.busKey = busKey;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}
		
	}
}
