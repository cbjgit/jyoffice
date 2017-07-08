package com.jyoffice.actflow.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.beetl.sql.core.SQLReady;
import org.springframework.stereotype.Service;

import com.jyoffice.BaseService;
import com.jyoffice.actflow.model.ActDefNode;
import com.jyoffice.util.Pager;

@Service
public class ActNodeService extends BaseService<ActDefNode, Integer> {

	public ActDefNode get(Integer id) {
		return super.get(ActDefNode.class, id);
	}
	
	public void getPager(Pager<ActDefNode> pager) {
		super.getPager("ActDefNode.list", ActDefNode.class, pager);
	}
	
	public void deleteById(Integer id) {
		sqlManager.deleteById(ActDefNode.class, id);
	}
	
	public void deleteByProcessId(Integer processId) {
		sqlManager.executeUpdate(new SQLReady("delete from act_def_node where process_id = ?", processId));
	}
	
	public ActDefNode getActNode(String nodeId,int processId){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("nodeId",nodeId);
		param.put("processId",processId);
		return super.get("ActDefNode.list", ActDefNode.class, param);
	}
	
	public List<ActDefNode> getList(int processId){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("processId",processId);
		return getNodeList(param);
	}
	
	public List<ActDefNode> getUserTaskList(int processId){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("processId",processId);
		param.put("nodeTypes","userTask");
		return getNodeList(param);
	}
	
	public List<ActDefNode> getNodeList(Map<String, Object> param){
		return super.getList("ActDefNode.list", ActDefNode.class, param);
	}
}
