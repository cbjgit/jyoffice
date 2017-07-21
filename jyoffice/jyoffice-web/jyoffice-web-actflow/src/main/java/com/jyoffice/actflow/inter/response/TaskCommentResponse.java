package com.jyoffice.actflow.inter.response;

import java.util.List;

public class TaskCommentResponse extends BaseResponse{

	private List<RspComment> comment;

	public List<RspComment> getComment() {
		return comment;
	}

	public void setComment(List<RspComment> comment) {
		this.comment = comment;
	}

	public class RspComment{
		
		private String time;
		
		private String userId;
		
		private String taskId;
		
		private String taskKey;
		
		private String taskName;
		
		private String message;

		public String getTime() {
			return time;
		}

		public void setTime(String time) {
			this.time = time;
		}

		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}

		public String getTaskId() {
			return taskId;
		}

		public void setTaskId(String taskId) {
			this.taskId = taskId;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
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
