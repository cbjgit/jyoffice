package com.jyoffice.actflow.service;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jyoffice.BaseService;
import com.jyoffice.actflow.model.ActDefProcess;
import com.jyoffice.util.Pager;

@Service
public class ActProcessService extends BaseService<ActDefProcess, Integer> {
	
	@Autowired
	ActNodeService actNodeService;
	@Autowired
	ActSequenceService actSequenceService;
	
	public void getPager(Pager<ActDefProcess> pager) {
		super.getPager("ActDefProcess.list", ActDefProcess.class, pager);
	}
	
	public void deleteById(int pk) {
		actSequenceService.deleteByProcessId(pk);
		actNodeService.deleteByProcessId(pk);
		super.deleteById(ActDefProcess.class, pk);
	}
	
	public ActDefProcess get(int pk) {
		return super.get(ActDefProcess.class, pk);
	}
	
	public ActDefProcess getActiveProcess(String processKey) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("processKey", processKey);
		param.put("status", 1);
		List<ActDefProcess> list = super.getList("ActDefProcess.list", ActDefProcess.class, param);
		if(list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	public List<ActDefProcess> getList() {
		return super.getList("ActDefProcess.list", ActDefProcess.class, null);
	}
	
	public void copyProcess(ActDefProcess process){
		ActDefProcess copyprocess = new ActDefProcess();
		copyprocess.setDescption(process.getDescption());
		copyprocess.setCreateDate(new Date());
		copyprocess.setUpdateDate(new Date());
		copyprocess.setProcessKey(process.getProcessKey());
		copyprocess.setProcessName(process.getProcessName());
		copyprocess.setStatus(0);
		long key = this.insertKey(copyprocess);
		
		actNodeService.copyNode(process.getId(), key);
		actSequenceService.copySequence(process.getId(), key);
	}
	
}
