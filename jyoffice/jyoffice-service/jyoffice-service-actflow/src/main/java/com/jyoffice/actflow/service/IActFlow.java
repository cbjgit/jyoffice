package com.jyoffice.actflow.service;

import javax.servlet.http.HttpServletRequest;

public interface IActFlow {

	/**
	 * 提交任务
	 * 
	 * @param frmInstanceId
	 * @param taskKey
	 * @param taskId
	 * @param data
	 */
	String submit(HttpServletRequest request);

	/**
	 * 完成任务
	 * 
	 * @param frmInstanceId
	 * @param taskKey
	 * @param taskId
	 * @param data
	 */
	String complete(HttpServletRequest request);

	/**
	 * 撤回
	 * 
	 * @param taskKey
	 * @param instanceId
	 */
	void withdraw(String taskKey, String instanceId);

	/**
	 * 回退
	 * 
	 * @param taskId
	 * @param instanceId
	 * @param taskKey
	 */
	void reject(String taskId, String instanceId, String taskKey);

	/**修改业务表
	 * @param instanceId
	 * @param businessKey
	 * @param taskKey
	 */
	void updateInstace(String instanceId, String businessKey, String taskKey);
}
