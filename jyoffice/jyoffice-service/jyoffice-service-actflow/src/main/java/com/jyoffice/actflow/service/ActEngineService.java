package com.jyoffice.actflow.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.beetl.sql.core.SQLManager;
import org.beetl.sql.core.engine.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jyoffice.util.Pager;

@Service
public class ActEngineService {

	private ProcessEngine processEngine;
	
	private RuntimeService runtimeService;
	
	private TaskService taskService;
	
	@Autowired
	protected SQLManager sqlManager;
	
	public ProcessEngine getProcessEngine() {
		return processEngine;
	}

	public RuntimeService getRuntimeService() {
		return runtimeService;
	}

	public TaskService getTaskService() {
		return taskService;
	}

	public void setProcessEngine(ProcessEngine processEngine) {
		this.processEngine = processEngine;
	}

	public void setRuntimeService(RuntimeService runtimeService) {
		this.runtimeService = runtimeService;
	}

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	/**
	 * 启动流程
	 * 
	 * @param processKey
	 * @return
	 * @throws Exception
	 */
	public ProcessInstance startProcessByKey(String processKey, String businessKey) {

		return runtimeService.startProcessInstanceByKey(processKey, businessKey);
	}

	/**
	 * 启动流程
	 * 
	 * @param processKey
	 * @param businessKey
	 * @return
	 * @throws Exception
	 */
	public ProcessInstance startProcessByKey(String processKey, String businessKey,
			Map<String, Object> var) {
		return runtimeService.startProcessInstanceByKey(processKey, businessKey, var);
	}

	/**
	 * 启动流程
	 * 
	 * @param processDefinitionId
	 * @param businessKey
	 * @return
	 * @throws Exception
	 */
	public ProcessInstance startProcessInstanceById(String processDefinitionId, String businessKey,
			Map<String, Object> var) {
		return runtimeService.startProcessInstanceById(processDefinitionId, businessKey, var);
	}

	/**
	 * 删除流程
	 * 
	 * @param processKey
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public void closeProcessInstanceId(String instanceId, String reason) {
		this.runtimeService.deleteProcessInstance(instanceId, reason);
	}

	/**
	 * 获取任务备注信息
	 * 
	 * @param taskId
	 * @return
	 */
	public List<Comment> getTaskComment(String taskId) {
		return this.taskService.getTaskComments(taskId);
	}

	/**
	 * 获取流程的备注信息
	 * 
	 * @param instanceId
	 * @return
	 */
	public List<Comment> getProcessComment(String instanceId) {

		return this.taskService.getProcessInstanceComments(instanceId);
	}
	
	/**
	 * 获取流程的备注信息
	 * 
	 * @param instanceId
	 * @return
	 */
	public Comment saveComment(String taskId,String userId,String message) {
		Task task = getTask(taskId);
		Authentication.setAuthenticatedUserId(userId);
		return this.taskService.addComment(taskId, task.getProcessInstanceId(), message);
	}

	/**
	 * 发布流程
	 * 
	 * @param bpmnModel
	 * @return
	 */
	public String deploy(BpmnModel bpmnModel) {
		org.activiti.bpmn.model.Process process = bpmnModel.getProcesses().get(0);
		Deployment deploy = processEngine.getRepositoryService().createDeployment()
				.addBpmnModel(process.getId() + ".bpmn", bpmnModel).name(process.getName()).deploy();

		return deploy.getId();
	}

	/**
	 * 根据部署ID获取流程定义
	 * 
	 * @param deployId
	 * @return
	 */
	public ProcessDefinition getDefinitionId(String deployId) {
		ProcessDefinition definition = processEngine.getRepositoryService()
				.createProcessDefinitionQuery().deploymentId(deployId).singleResult();

		return definition;
	}

	/**
	 * 获取流程当前任务
	 * 
	 * @param instanceId
	 * @return
	 */
	public List<Task> getCurrentTask(String instanceId) {
		List<Task> tasklist = taskService.createTaskQuery().processInstanceId(instanceId).list();
		return tasklist;
	}
	
	/**
	 * 获取流程当前任务
	 * 
	 * @param instanceId
	 * @return
	 */
	public Task getTask(String taskId) {
		return taskService.createTaskQuery().taskId(taskId).singleResult();
	}
	
	/**完成任务
	 * @param taskId
	 * @param var
	 * @param memo
	 */
	public void completeTask(String taskId, Map<String, Object> var) {
		this.taskService.complete(taskId, var);
	}
	
	/**
	 * 
	 * 
	 * @param definitionId
	 * @return
	 */
	public BpmnModel getBpmnModel(String definitionId) {
		return processEngine.getRepositoryService().getBpmnModel(definitionId);
	}
	
	public void getTaskList(Pager<Map<String,Object>> pager) {
		PageQuery<Map<String,Object>> page = new PageQuery<Map<String,Object>>(pager.getCurrentPage(), pager.getParam());
		page.setPageSize(pager.getPageSize());
		
		sqlManager.pageQuery("ActRunTask.list", Map.class, page);
		
		pager.setTotal(page.getTotalRow());
		pager.setCountPage((int)page.getTotalPage());
		pager.setResultList(page.getList());
	}
	
	public List<Map> getTaskList(String userId,String processKey,long periodStart,long periodend) {
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("processKey", processKey);
		if(periodStart > 0){
			param.put("createTimeStart", new Date(periodStart));
		}
		if(periodend > 0){
			param.put("createTimeEnd",  new Date(periodend));
		}
		return sqlManager.select("ActRunTask.list", Map.class, param);
		
	}
	
	public void complete(String taskId,String destTask) {
		taskService.complete(taskId, destTask);
	}
}
