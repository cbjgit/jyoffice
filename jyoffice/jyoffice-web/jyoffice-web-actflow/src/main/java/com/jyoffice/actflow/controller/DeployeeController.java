package com.jyoffice.actflow.controller;


import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jyoffice.actflow.bpmn.DynamicProcess;
import com.jyoffice.actflow.model.ActDefNode;
import com.jyoffice.actflow.model.ActDefProcess;
import com.jyoffice.actflow.model.ActDefSequence;
import com.jyoffice.actflow.service.ActEngineService;
import com.jyoffice.actflow.service.ActNodeService;
import com.jyoffice.actflow.service.ActProcessService;
import com.jyoffice.actflow.service.ActSequenceService;
import com.jyoffice.common.BaseController;

@Controller
@RequestMapping(value = "/process/deployee")
public class DeployeeController extends BaseController{

	@Autowired
	ActProcessService actProcessService;
	
	@Autowired
	ActNodeService actNodeService;
	
	@Autowired
	ActSequenceService actSequenceService;
	
	@Autowired
	ActEngineService actEngineService;

	/**查看流程图
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/diagram/{id}")
	public void diagram(HttpServletResponse response, Model model,@PathVariable Integer id) {
		
		List<ActDefNode> nodeList = actNodeService.getList(id);
		List<ActDefSequence> seqList = actSequenceService.getListByProcessId(id);
		
		ActDefProcess process = actProcessService.get(id);
		try {
			BpmnModel bpmnModel = DynamicProcess.createBpmn(process,nodeList,seqList);
			
			DynamicProcess.convertToDiagram(bpmnModel, response.getOutputStream());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**部署流程
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/deploy/{id}")
	public String deploy(HttpServletResponse response, RedirectAttributes redirect,@PathVariable Integer id) {
		
		List<ActDefNode> nodeList = actNodeService.getList(id);
		List<ActDefSequence> seqList = actSequenceService.getListByProcessId(id);
		
		ActDefProcess process = actProcessService.get(id);
		
		BpmnModel bpmnModel;
		try {
			bpmnModel = DynamicProcess.createBpmn(process,nodeList,seqList);
			
			String deployId = actEngineService.deploy(bpmnModel);

			ProcessDefinition definition = actEngineService.getDefinitionId(deployId);
			if(definition != null){
				process.setDefinitionId(definition.getId());
				process.setVersion(definition.getVersion());
			}
			process.setDeployId(deployId);
			process.setDeployeeDate(new Date());
			
			actProcessService.updateById(process);
			addMessage(redirect, "部署成功");
		} catch (Exception e) {
			addMessage(redirect, "部署失败");
			e.printStackTrace();
		}
		
		return "redirect:/process/definition/list";
	}

	@RequestMapping("/xml/{id}")
	public void xml(HttpServletResponse response, Model model,@PathVariable Integer id) {
		
		BpmnModel bpmnModel = null;
		ActDefProcess process = actProcessService.get(id);
		if(process.getDefinitionId() != null && process.getDefinitionId().length() > 0){
			bpmnModel = actEngineService.getBpmnModel(process.getDefinitionId());
		}else{
			List<ActDefNode> nodeList = actNodeService.getList(id);
			List<ActDefSequence> seqList = actSequenceService.getListByProcessId(id);
			try {
				bpmnModel = DynamicProcess.createBpmn(process,nodeList,seqList);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		response.setContentType("text/xml; charset=UTF-8");
		try {
			response.getOutputStream().write(DynamicProcess.convertToXML(bpmnModel).getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
