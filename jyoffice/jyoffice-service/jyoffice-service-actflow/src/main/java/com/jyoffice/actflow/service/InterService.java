package com.jyoffice.actflow.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.jyoffice.BaseService;
import com.jyoffice.actflow.model.ActInstanceExt;

@Service
public class InterService extends BaseService<ActInstanceExt, Integer> {
	
	public ActInstanceExt getByInstanceId(String instanceId){
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("instanceId", instanceId);
		return this.get("ActInstanceExt.list", ActInstanceExt.class, param);
	}
	
}
