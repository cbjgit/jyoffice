package com.jyoffice.actflow.inter.controller;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jyoffice.actflow.exception.ActFlowException;
import com.jyoffice.actflow.inter.request.ReturnTaskRequest;
import com.jyoffice.actflow.inter.response.BaseResponse;
import com.jyoffice.actflow.inter.response.ReturnTaskResponse;
import com.jyoffice.actflow.model.ActDefNode;
import com.jyoffice.actflow.service.ActEngineService;
import com.jyoffice.actflow.service.ActFlowControlService;
import com.jyoffice.util.StringUtil;

@Controller
@RequestMapping(value = "/task/return")
public class ReturnTaskInter extends BaseInter {

	@Autowired
	ActFlowControlService actFlowControlService;
	@Autowired
	ActEngineService actEngineService;
	
	/**
	 * @param request
	 * @param body
	 * @return {"success":true,"task":[{"taskKey":"writelevel","taskName":"填写请假单"}]}
	 */
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody BaseResponse completeTask(HttpServletRequest request,
			@RequestBody String body) {
		
		log.info("body:"+body);
		
		ReturnTaskRequest spRequest = jsonToBean(body, ReturnTaskRequest.class);
		BaseResponse response = validate(spRequest);
		if(response != null){
			return response;
		}
		
		if(this.validSign(toMap(spRequest),spRequest.getSignMsg())){
			log.info("验签成功");
			try{
				Task task = actEngineService.getTask(spRequest.getTaskId());
				if(task == null){
					return BaseResponse.errResponse("任务不存在", "请传递正确的TaskId");
				}
				
				ReturnTaskResponse rtResponse = new ReturnTaskResponse();
				List<ActDefNode> nodeList = actFlowControlService.jumpCompleteTask(task,spRequest.getDestTaskKey());
				
				List<ReturnTaskResponse.RspTask> tList = new ArrayList<ReturnTaskResponse.RspTask>();
				for(ActDefNode node : nodeList){
					ReturnTaskResponse.RspTask rsptask = rtResponse.new RspTask();
					rsptask.setTaskKey(node.getNodeId());
					rsptask.setTaskName(node.getNodeNames());
					tList.add(rsptask);
				}
				
				rtResponse.setTask(tList);
				rtResponse.setSuccess(true);
				return rtResponse;
				
			}catch(ActFlowException e){
				log.error("任务完成错误:", e);
				return BaseResponse.errResponse(e.getMessage(),e.getMessage());
			}catch(Exception e){
				log.error("任务完成错误:", e);
				return BaseResponse.errResponse(e.getMessage(),"请联系管理员解决");
			}
		}else{
			log.info("验签失败");
			return BaseResponse.errResponse("签名错误", "请检查签名字符串是否正确");
		}
	}
	
	/** 批量退回任务
	 * @param request
	 * @param body
	 * @return {"success":true,"task":[{"instanceId":"170023","taskKey":"writelevel","taskName":"填写请假单"},
	 * 			{"instanceId":"187501","taskKey":"writelevel","taskName":"填写请假单"}]}
	 */
	@RequestMapping(value = "/batch", method = RequestMethod.POST)
	public @ResponseBody BaseResponse batchCompleteTask(HttpServletRequest request,
			@RequestBody String body) {
		
		log.info("body:"+body);
		
		ReturnTaskRequest spRequest = jsonToBean(body, ReturnTaskRequest.class);
		BaseResponse response = validate(spRequest);
		if(response != null){
			return response;
		}
		
		if(this.validSign(toMap(spRequest),spRequest.getSignMsg())){
			log.info("验签成功");
			try{
				
				String [] taskIds = spRequest.getTaskId().split(",");
				if(taskIds.length > 1 && StringUtil.notBlank(spRequest.getDestTaskKey())){
					return BaseResponse.errResponse("批量退回不能指定退回节点", "请不要传递退回节点");
				}
				
				List<Task> taskList = new ArrayList<Task>();
				for(String taskId : taskIds){
					Task task = actEngineService.getTask(taskId);
					if(task == null){
						return BaseResponse.errResponse("任务不存在", "请传递正确的TaskId");
					}
					taskList.add(task);
				}
				
				ReturnTaskResponse rtResponse = new ReturnTaskResponse();
				
				Map<String,List<ActDefNode>> map = actFlowControlService.jumpCompleteTask(taskList,spRequest.getDestTaskKey());
				
				List<ReturnTaskResponse.RspTask> tList = new ArrayList<ReturnTaskResponse.RspTask>();
				for(Entry<String,List<ActDefNode>> entry : map.entrySet()){
					for(ActDefNode node : entry.getValue()){
						ReturnTaskResponse.RspTask rsptask = rtResponse.new RspTask();
						rsptask.setInstanceId(entry.getKey());
						rsptask.setTaskKey(node.getNodeId());
						rsptask.setTaskName(node.getNodeNames());
						tList.add(rsptask);
					}
				}
				rtResponse.setTask(tList);
				rtResponse.setSuccess(true);
				return rtResponse;
				
			}catch(ActFlowException e){
				log.error("任务完成错误:", e);
				return BaseResponse.errResponse(e.getMessage(),e.getMessage());
			}catch(Exception e){
				log.error("任务完成错误:", e);
				return BaseResponse.errResponse(e.getMessage(),"请联系管理员解决");
			}
		}else{
			log.info("验签失败");
			return BaseResponse.errResponse("签名错误", "请检查签名字符串是否正确");
		}
	}

	private TreeMap<String, Object> toMap(ReturnTaskRequest rtRequest) {
		
		TreeMap<String, Object> map = new TreeMap<String, Object>();
		map.put("taskId", rtRequest.getTaskId());
		map.put("destTaskKey", rtRequest.getDestTaskKey());
		map.put("timestamp", rtRequest.getTimestamp());
		return map;
	}
	
	private BaseResponse validate(ReturnTaskRequest stRequest){
		if(stRequest == null){
			return BaseResponse.errResponse("数据格式错误", "请检查body值是否正确");
		}
		
		if(StringUtil.isBlank(stRequest.getTaskId())){
			return BaseResponse.errResponse("任务ID为空", "请传入TaskId");
			
		}else if(StringUtil.isBlank(stRequest.getTimestamp()) || stRequest.getTimestamp().length() != 10){
			return BaseResponse.errResponse("时间戳不正确", "请传入正确的时间戳");
			
		}else if(StringUtil.isBlank(stRequest.getSignMsg())){
			return BaseResponse.errResponse("签名内容为空", "请传入签名字符串");
			
		}
		
		return null;
	}
}
