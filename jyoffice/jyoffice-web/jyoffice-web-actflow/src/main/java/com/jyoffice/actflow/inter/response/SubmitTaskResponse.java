package com.jyoffice.actflow.inter.response;

import java.util.List;

/**
 * @author chenbj
 */
public class SubmitTaskResponse extends BaseResponse{

	/**
	 * 是否已提交
	 */
	private boolean submit;
	
	/**
	 * 是否会签
	 */
	private Integer multi; 
	
	/**
	 * 选择处理人范围
	 */
	private String scope;
	
	/**
	 * 范围类型
	 */
	private Integer scopeType;
	
	/**
	 * 提交成功后的任务
	 */
	private List<RspTask> tasks;

	
	public boolean getSubmit() {
		return submit;
	}

	public Integer getMulti() {
		return multi;
	}

	public void setMulti(Integer multi) {
		this.multi = multi;
	}

	public void setSubmit(boolean submit) {
		this.submit = submit;
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

	public List<RspTask> getTask() {
		return tasks;
	}

	public void setTask(List<RspTask> task) {
		this.tasks = task;
	}
	
	public class RspTask{
		
		private String taskKey;
		
		private String taskName;

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

