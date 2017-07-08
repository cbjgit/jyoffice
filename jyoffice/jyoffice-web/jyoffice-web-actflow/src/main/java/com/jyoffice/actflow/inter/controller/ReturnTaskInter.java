package com.jyoffice.actflow.inter.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.jyoffice.actflow.inter.request.SubmitTaskRequest;
import com.jyoffice.actflow.inter.response.BaseResponse;
import com.jyoffice.actflow.inter.response.SubmitTaskResponse;
import com.jyoffice.actflow.model.ActDefNode;
import com.jyoffice.actflow.model.ActInstanceExt;
import com.jyoffice.actflow.service.ActEngineService;
import com.jyoffice.actflow.service.ActFlowControlService;
import com.jyoffice.actflow.service.ActProcessService;
import com.jyoffice.actflow.service.InterService;
import com.jyoffice.util.StringUtil;

@Controller
@RequestMapping(value = "/task/return")
public class ReturnTaskInter extends BaseInter {

	@Autowired
	ActFlowControlService actFlowControlService;
	@Autowired
	ActEngineService actEngineService;
	@Autowired
	ActProcessService actProcessService;
	@Autowired
	InterService interService;
	
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
				
				ActInstanceExt instance = interService.getByInstanceId(task.getProcessInstanceId());
				//获取上一环节
				ActDefNode node = actFlowControlService.getUpNode(instance.getProcessId(), task.getTaskDefinitionKey());
				if(node == null){
					return BaseResponse.errResponse("未找到上一环节", "请检查流程配置是否正确");
				}
				
				actEngineService.complete(task.getId(), node.getNodeId());
				SubmitTaskResponse stResponse = new SubmitTaskResponse();
				
				stResponse.setSuccess(true);
				return stResponse;
				
			}catch(Exception e){
				log.error("任务完成错误:", e);
				return BaseResponse.errResponse(e.getMessage(),"请联系管理员解决");
				
			}
		}else{
			log.info("验签失败");
			return BaseResponse.errResponse("签名错误", "请检查签名字符串是否正确");
		}
	}

	private void completeTask(SubmitTaskRequest spRequest,SubmitTaskResponse stResponse,Task task,ActDefNode node) throws Exception{
		
		Object obj = spRequest.getActvar();
		Map<String, Object> actvar = (Map<String, Object>)obj;
		if(actvar == null)
			actvar = new HashMap<String, Object>();
		
		List<Task> list = actFlowControlService.completeTask(task,spRequest.getUserId(),node,actvar);
		stResponse.setSubmit(true);
		
		List<SubmitTaskResponse.RspTask> tList = new ArrayList<SubmitTaskResponse.RspTask>();
		for(Task t : list){
			SubmitTaskResponse.RspTask rsptask = stResponse.new RspTask();
			rsptask.setTaskId(t.getId());
			rsptask.setTaskKey(t.getTaskDefinitionKey());
			rsptask.setTaskName(t.getName());
			tList.add(rsptask);
		}
		stResponse.setTask(tList);
	}
	
	private TreeMap<String, Object> toMap(ReturnTaskRequest rtRequest) {
		
		TreeMap<String, Object> map = new TreeMap<String, Object>();
		map.put("taskId", rtRequest.getTaskId());
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
