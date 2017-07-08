package com.jyoffice.actflow.service;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jyoffice.BaseService;
import com.jyoffice.actflow.model.ActDefProcess;
import com.jyoffice.actflow.model.ActInstanceExt;

@Service
public class InterService extends BaseService<ActInstanceExt, Integer> {
	
	@Autowired
	ActFlowControlService actFlowControlService;
	
	public Task startProcess(ActDefProcess process,ActInstanceExt instance) throws Exception{
		
		Task task = actFlowControlService.startProcess(process, instance.getAppUserid(), instance.getBusKey());
		instance.setInstanceId(task.getProcessInstanceId());
		sqlManager.insert(instance);
		
		return task;
	}
	
	
	public ActInstanceExt getByInstanceId(String instanceId){
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("instanceId", instanceId);
		return this.get("ActInstanceExt.list", ActInstanceExt.class, param);
	}
	
}
