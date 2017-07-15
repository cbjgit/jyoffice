package com.jyoffice.actflow.inter.controller;
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
import com.jyoffice.actflow.inter.request.StartProcessRequest;
import com.jyoffice.actflow.inter.response.BaseResponse;
import com.jyoffice.actflow.inter.response.StartProcessResponse;
import com.jyoffice.actflow.model.ActDefProcess;
import com.jyoffice.actflow.model.ActInstanceExt;
import com.jyoffice.actflow.service.ActFlowControlService;
import com.jyoffice.actflow.service.ActProcessService;
import com.jyoffice.util.StringUtil;

@Controller
@RequestMapping(value = "/process/start")
public class StartProcessInter extends BaseInter {

	@Autowired
	ActProcessService actProcessService;
	@Autowired
	ActFlowControlService actFlowControlService;
	
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody BaseResponse start(HttpServletRequest request,
			@RequestBody String body) {
		
		log.info("body:"+body);
		
		StartProcessRequest spRequest = jsonToBean(body, StartProcessRequest.class);
		BaseResponse response = validate(spRequest);
		if(response != null){
			return response;
		}
		
		if(this.validSign(toMap(spRequest),spRequest.getSignMsg())){
			log.info("验签成功");
			try{
				//启动流程
				ActDefProcess process = actProcessService.getActiveProcess(spRequest.getProcessKey());
				if(process == null || StringUtil.isBlank(process.getDefinitionId())){
					throw new ActFlowException("流程不存在或未部署");
				}
				ActInstanceExt instance = new ActInstanceExt();
				instance.setAppUserid(spRequest.getAppUserId());
				instance.setAppUsername(spRequest.getAppUserName());
				instance.setBusKey(spRequest.getBusKey());
				instance.setTitle(spRequest.getTitle());
				instance.setProcessId(process.getId());
				instance.setProcessKey(process.getProcessKey());
				
				Task task = actFlowControlService.startProcess(process, instance);
				
				//返回
				StartProcessResponse srResponse = new StartProcessResponse();
				srResponse.setInstanceId(task.getProcessInstanceId());
				srResponse.setSuccess(true);
				srResponse.setTaskId(task.getId());
				srResponse.setTaskKey(task.getTaskDefinitionKey());
				srResponse.setTaskName(task.getName());
				return srResponse;
				
			}catch(ActFlowException e){
				return BaseResponse.errResponse(e.getMessage(),e.getMessage());
			}catch(Exception e){
				log.error("启动流程错误:", e);
				return BaseResponse.errResponse(e.getMessage(),"请联系管理员解决");
				
			}
		}else{
			log.info("验签失败");
			return BaseResponse.errResponse("签名错误", "请检查签名字符串是否正确");
		}
	}

	
	private TreeMap<String, Object> toMap(StartProcessRequest spRequest) {
		
		TreeMap<String, Object> map = new TreeMap<String, Object>();
		map.put("appUserId", spRequest.getAppUserId());
		map.put("processKey", spRequest.getProcessKey());
		map.put("title", spRequest.getTitle());
		map.put("busKey", spRequest.getBusKey());
		map.put("appUserName", spRequest.getAppUserName());
		map.put("timestamp", spRequest.getTimestamp());
		return map;
	}
	
	private BaseResponse validate(StartProcessRequest spRequest){
		if(spRequest == null){
			return BaseResponse.errResponse("数据格式错误", "请检查body值是否正确");
		}
		
		if(StringUtil.isBlank(spRequest.getAppUserId())){
			return BaseResponse.errResponse("申请人为空", "请传入申请人Id");
			
		}else if(StringUtil.isBlank(spRequest.getAppUserName())){
			return BaseResponse.errResponse("申请人为空", "请传入申请人名字");
			
		}else if(StringUtil.isBlank(spRequest.getBusKey())){
			return BaseResponse.errResponse("业务ID为空", "请传入业务ID");
			
		}else if(StringUtil.isBlank(spRequest.getProcessKey())){
			return BaseResponse.errResponse("流程Key为空", "请传入流程Key");
			
		}else if(StringUtil.isBlank(spRequest.getTitle())){
			return BaseResponse.errResponse("业务标题为空", "请传入业务标题");
			
		}else if(StringUtil.isBlank(spRequest.getSignMsg())){
			return BaseResponse.errResponse("签名内容为空", "请传入签名字符串");
			
		}else if(StringUtil.isBlank(spRequest.getTimestamp()) || spRequest.getTimestamp().length() != 10){
			return BaseResponse.errResponse("时间戳不正确", "请传入正确的时间戳");
			
		}
		
		return null;
	}
}
