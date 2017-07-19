package com.jyoffice.actflow.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jyoffice.actflow.bpmn.NodeType;
import com.jyoffice.actflow.exception.ActFlowException;
import com.jyoffice.actflow.model.ActDefNode;
import com.jyoffice.actflow.model.ActDefProcess;
import com.jyoffice.actflow.model.ActDefSequence;
import com.jyoffice.actflow.model.ActInstanceExt;
import com.jyoffice.ca.SessionUtil;
import com.jyoffice.util.ElExpressionUtil;
import com.jyoffice.util.SpringContextHolder;
import com.jyoffice.util.StringUtil;

@Service
public class ActFlowControlService {
	
	@Autowired
	ActEngineService actEngineService;
	@Autowired
	ActProcessService actProcessService;
	@Autowired
	ActNodeService actNodeService;
	@Autowired
	ActSequenceService actSequenceService;
	@Autowired
	InterService interService;
	
	
	/**
	 * 提交任务
	 * 
	 * @param frmInstanceId
	 * @param taskKey
	 * @param taskId
	 * @param data
	 */
	@SuppressWarnings("unchecked")
	public void doSubmit(HttpServletRequest request) throws Exception{
		
		Integer processId = Integer.parseInt(request.getParameter("processId"));
		String taskKey = request.getParameter("taskKey");
		String taskId = request.getParameter("taskId");
		String instanceId = request.getParameter("instanceId");
		String assignee = request.getParameter("assignee");
		String nexttaskKey = request.getParameter("nexttaskKey");
		
		ActDefProcess process = actProcessService.get(processId);
		if(process.getDefinitionId() == null || process.getDefinitionId().length() == 0){
			throw new ActFlowException(process.getProcessName()+"["+process.getProcessKey()+"]流程还未部署");
		}else if(process.getStatus() == -1){
			throw new ActFlowException(process.getProcessName()+"["+process.getProcessKey()+"]流程已经被禁用");
		}
		
		if(assignee == null || assignee.length() == 0){
			throw new ActFlowException("请选择处理人");
		}
		
		String service = request.getParameter("service");
		IActFlow flow = SpringContextHolder.getBean(service);
		//处理业务
		String businessKey = flow.submit(request);
		
		Map<String, Object> varMap = null;
		
		if(instanceId == null ||instanceId.length() == 0){
			varMap = new HashMap<String, Object>();
			setAssignee(varMap, taskKey, processId, SessionUtil.getUser(request).getLoginId());
			//启动流程
			ProcessInstance instance = actEngineService.startProcessInstanceById(process.getDefinitionId(), businessKey,varMap);
			instanceId = instance.getId();
			
			Task taskInfo = actEngineService.getTaskService().createTaskQuery().processInstanceId(instanceId).singleResult();
			taskId = taskInfo.getId();
			taskKey = taskInfo.getTaskDefinitionKey();
			
		}
		
		//完成提交动作
		String var = request.getParameter("actvar");
		if(var != null && var.length() > 0){
			ObjectMapper mapper = new ObjectMapper();
			try {
				varMap = mapper.readValue(var, Map.class);
				setAssignee(varMap, nexttaskKey, processId, assignee);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		actEngineService.completeTask(taskId, varMap,"1");
		
		//更新流程实例到业务表
		Task taskInfo = actEngineService.getTaskService().createTaskQuery().processInstanceId(instanceId).singleResult();
		taskKey = taskInfo.getTaskDefinitionKey();
		flow.updateInstace(instanceId, businessKey,taskKey);
	}

	private void setAssignee(Map<String, Object> varMap,String taskKey,Integer processId,String assignee){
		ActDefNode node = actNodeService.getActNode(taskKey, processId);
		setAssignee(varMap, node, assignee);
	}
	
	private void setAssignee(Map<String, Object> varMap,ActDefNode node,String assignee){
		
		if(assignee == null || assignee.length() == 0 || node == null)
			return;
		
		if(node.getMulti() == 1){
			List<String> list = new ArrayList<String>();
			if(assignee.indexOf(",") > -1){
				String [] arr = assignee.split(",");
				for(String s : arr){
					list.add(s);
				}
			}else{
				list.add(assignee);
			}
			varMap.put("ag_"+node.getId(), list);
		}else{
			varMap.put("ag_"+node.getId(), assignee);
		}
	}
	
	/**
	 * 启动流程
	 * 
	 * @param frmInstanceId
	 * @param taskKey
	 * @param taskId
	 * @param data
	 */
	public Task startProcess(ActDefProcess process,String assignee,String busKey) throws Exception{
		
		ActDefNode node = getNextNode(process.getId(), "start", null);
		
		Map<String,Object> varMap = new HashMap<String, Object>();
		setAssignee(varMap, node.getNodeId(), process.getId(), assignee);
		//启动流程
		ProcessInstance instance = actEngineService.startProcessInstanceById(process.getDefinitionId(), busKey, varMap);
		String instanceId = instance.getId();
		
		Task taskInfo = actEngineService.getTaskService().createTaskQuery().processInstanceId(instanceId).singleResult();
		return taskInfo;
	}
	
	/**
	 * 启动流程
	 * 
	 * @param frmInstanceId
	 * @param taskKey
	 * @param taskId
	 * @param data
	 */
	public Task startProcess(ActDefProcess process,ActInstanceExt instance) throws Exception{
		
		Task task = startProcess(process, instance.getAppUserid(), instance.getBusKey());
		instance.setInstanceId(task.getProcessInstanceId());
		interService.insert(instance);
		return task;
	}
	
	/**
	 * 完成任务
	 * 
	 * @param frmInstanceId
	 * @param taskKey
	 * @param taskId
	 * @param data
	 */
	public List<ActDefNode> completeTask(Task task,String assignee,ActDefNode node, Map<String,Object> actvar) throws Exception{
		
		setAssignee(actvar, node, assignee);
		
		actEngineService.completeTask(task.getId(), actvar, "complete");
		
		return getCurrentTaskNode(task.getProcessInstanceId(), node.getProcessId());
		
	}
	
	public ActDefNode getNode(String nodeId,int processId){
		return actNodeService.getActNode(nodeId, processId);
	}
	
	
	/**
	 * 完成任务
	 * 
	 * @param frmInstanceId
	 * @param taskKey
	 * @param taskId
	 * @param data
	 */
	public List<ActDefNode> completeTask(List<Task> taskList,String assignee,ActDefNode node, Map<String,Object> actvar) throws Exception{
		
		setAssignee(actvar, node, assignee);
		
		String instaceId = null;
		for(Task task : taskList){
			actEngineService.completeTask(task.getId(), actvar, "complete");
			if(instaceId == null){
				instaceId = task.getProcessInstanceId();
			}
		}
		
		return getCurrentTaskNode(instaceId, node.getProcessId());
	}
	
	/**
	 * 回退任务
	 * 
	 * @param frmInstanceId
	 * @param taskKey
	 * @param taskId
	 * @param data
	 */
	public List<ActDefNode> jumpCompleteTask(Task task,String destTask) throws ActFlowException{
		
		ActInstanceExt insext = interService.getByInstanceId(task.getProcessInstanceId());
		
		ActDefNode node = actNodeService.getActNode(task.getTaskDefinitionKey(), insext.getProcessId());
		if(NodeType.TYPE_STARTEVENT.equals(node.getNodeId()) && node.getBack().intValue() != 1){
			throw new ActFlowException("当前环节不能退回");
		}
		
		if(destTask == null || destTask.length() == 0){
			//获取上一步环节
			destTask = actEngineService.getUpTaskKey(task);
		}else{
			//检查任务节点是否已处理过
			List<String> list = actEngineService.getUpTaskKeyAll(task);
			boolean flag = false;
			for(String taskKey : list){
				if(taskKey.equals(destTask)){
					flag = true;
				}
			}
			if(!flag){
				throw new ActFlowException("当前流程从未处理过任务【"+destTask+"】,不能退回");
			}
		}
		
		if(destTask == null || destTask.length() == 0){
			throw new ActFlowException("退回目的环节错误");
		}
		
		Map<String,Object> actvar = new HashMap<String, Object>();
		actEngineService.completeTask(task.getId(),destTask, actvar, "reject");
		
		return getCurrentTaskNode(insext.getInstanceId(), insext.getProcessId());
		
	}
	
	public Map<String,List<ActDefNode>> jumpCompleteTask(List<Task> taskList,String destTask) throws ActFlowException{
		
		Map<String,List<ActDefNode>> map = new HashMap<String, List<ActDefNode>>();
		for(Task task : taskList){
			map.put(task.getProcessInstanceId(), jumpCompleteTask(task, destTask));
		}
		
		return map;
	}
	
	private List<ActDefNode> getCurrentTaskNode(String instanceId,int processId){
		List<Execution> list = actEngineService.getCurrentTaskNode(instanceId);
		List<ActDefNode> nodelist = new ArrayList<ActDefNode>();
		for(Execution e : list){
			if(StringUtil.notBlank(e.getActivityId())){
				ActDefNode newnode = getNode(e.getActivityId(), processId);
				nodelist.add(newnode);
			}
		}
		return nodelist;
	}

	/**
	 * 获取下一个环节
	 * 
	 * @param processId
	 * @param taskKey
	 * @return
	 */
	public ActDefNode getNextNode(int processId, String taskKey, Map<String, String> varMap) {
		List<ActDefSequence> seqList = actSequenceService.getListByNodeId(processId, taskKey);
		for (ActDefSequence seq : seqList) {
			
			if(NodeType.TYPE_ENDEVENT.equals(seq.getToNodeId())){
				continue;
			}
			
			ActDefNode entity = actNodeService.getActNode(seq.getToNodeId(), processId);

			if (NodeType.TYPE_USERTASK.equals(entity.getNodeTypes())) {
				Object result = ElExpressionUtil.execute(seq.getConditionExpression(), varMap);
				boolean flag = result == null ? true : "true".equals(String.valueOf(result)) ? true : false;
				if (flag) {
					return entity;
				}
			} else {
				return getNextNode(processId, seq.getToNodeId(), varMap);
			}
		}
		return null;
	}
}
