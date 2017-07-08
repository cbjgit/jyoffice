package com.jyoffice.actflow.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.beetl.sql.core.SQLReady;
import org.springframework.stereotype.Service;

import com.jyoffice.BaseService;
import com.jyoffice.actflow.model.ActDefSequence;
import com.jyoffice.util.Pager;

@Service
public class ActSequenceService extends BaseService<ActDefSequence, Integer> {

	public ActDefSequence get(Integer id) {
		return super.get(ActDefSequence.class, id);
	}

	public void deleteById(Integer id) {
		super.deleteById(ActDefSequence.class,id);
	}
	
	public void deleteByProcessId(Integer processId) {
		sqlManager.executeUpdate(new SQLReady("delete from act_def_sequence where process_id = ?", processId));
	}
	
	public void getPager(Pager<Map<String, Object>> pager) {
		super.getPager("ActDefSequence.list",pager);
	}
	
	public List<ActDefSequence> getListByProcessId(int processId){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("processId",processId);
		return getList(param);
	}
	
	public List<ActDefSequence> getListByNodeId(int processId,String nodeId){
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("processId",processId);
		param.put("nodeId",nodeId);
		return getList(param);
	}
	
	public List<ActDefSequence> getListByToNodeId(int processId,String nodeId){
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("processId",processId);
		param.put("toNodeId",nodeId);
		return getList(param);
	}

	public List<ActDefSequence> getList(Map<String, Object> param){
		return super.sqlManager.select("ActDefSequence.list", ActDefSequence.class, param);
	}
}
