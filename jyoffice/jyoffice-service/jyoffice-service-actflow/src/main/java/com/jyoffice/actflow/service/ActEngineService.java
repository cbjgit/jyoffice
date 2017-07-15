package com.jyoffice.actflow.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.beetl.sql.core.SQLManager;
import org.beetl.sql.core.engine.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jyoffice.actflow.model.ActHiActinst;
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
	 * 获取流程当前执行节点
	 * 
	 * @param instanceId
	 * @return
	 */
	public List<Execution> getCurrentTaskNode(String instanceId) {
		List<Execution> tasklist = runtimeService.createExecutionQuery().processInstanceId(instanceId).list();
		return tasklist;
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
	public void completeTask(String taskId, Map<String, Object> var,String oper) {
		this.taskService.complete(taskId, var, oper);
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
	
	public List<Map> getHiTaskList(String userId,String processKey,long periodStart,long periodend) {
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("processKey", processKey);
		if(periodStart > 0){
			param.put("createTimeStart", new Date(periodStart));
		}
		if(periodend > 0){
			param.put("createTimeEnd",  new Date(periodend));
		}
		return sqlManager.select("ActHiTask.list", Map.class, param);
		
	}
	
	public String getUpTaskKey(Task task) {
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("taskDefKey", task.getTaskDefinitionKey());
		param.put("instanceId", task.getProcessInstanceId());
		return sqlManager.selectSingle("ActHiTask.getUpTaskKey", param, String.class);
		
	}
	
	public List<String> getUpTaskKeyAll(Task task) {
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("taskDefKey", task.getTaskDefinitionKey());
		param.put("instanceId", task.getProcessInstanceId());
		return sqlManager.select("ActHiTask.getUpTaskKeyAll", String.class, param);
		
	}

	
	public List<ActHiActinst> getProcessExecPath(String instanceId) {
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("instanceId", instanceId);
		return sqlManager.select("ActHiActinst.list", ActHiActinst.class, param);
		
	}

	public void completeTask(String taskId,String destTask, Map<String, Object> var,String oper) {

		Task task = getTask(taskId);
		BpmnModel bpmnModel = getBpmnModel(task.getProcessDefinitionId());
		
		org.activiti.bpmn.model.Process process = bpmnModel.getProcesses().get(0);
		FlowNode sourceFlownode = (FlowNode)process.getFlowElement(task.getTaskDefinitionKey(), true);
		
		FlowNode destFlownode = (FlowNode)process.getFlowElement(destTask, true);
		
		// 清空原有流向
		List<SequenceFlow> oriSequenceFlowList = clearSequenceFlow(sourceFlownode);

		// 新建流向
		SequenceFlow newSequenceFlow = new SequenceFlow(task.getTaskDefinitionKey(),destTask);
		newSequenceFlow.setSourceFlowElement(sourceFlownode);
		newSequenceFlow.setTargetFlowElement(destFlownode);
		
		List<SequenceFlow> outgoingFlows = new ArrayList<SequenceFlow>();
		outgoingFlows.add(newSequenceFlow);
		sourceFlownode.setOutgoingFlows(outgoingFlows);
		
		taskService.complete(taskId,var,oper);
		
		// 删除目标节点新流入
		destFlownode.getIncomingFlows().remove(newSequenceFlow);
		// 还原以前流向
		restoreSequenceFlow(sourceFlownode, oriSequenceFlowList);
		
	}
	
	private List<SequenceFlow> clearSequenceFlow(FlowNode flowNode) {

		// 存储当前节点所有流向临时变量 
		List<SequenceFlow> oriSequenceFlowList = new ArrayList<SequenceFlow>();
		// 获取当前节点所有流向，存储到临时变量，然后清空 
		List<SequenceFlow> pvmTransitionList = flowNode.getOutgoingFlows();
		for (SequenceFlow seqflow : pvmTransitionList) {
			oriSequenceFlowList.add(seqflow);
		}
		pvmTransitionList.clear();
		return oriSequenceFlowList;
	}

	private void restoreSequenceFlow(FlowNode flowNode,
			List<SequenceFlow> oriSequenceFlowList) {
		// 清空现有流向 
		List<SequenceFlow> pvmTransitionList = flowNode.getOutgoingFlows();
		pvmTransitionList.clear();
		// 还原以前流向 
		for (SequenceFlow seqflow : oriSequenceFlowList) {
			pvmTransitionList.add(seqflow);
		}
	}
}
