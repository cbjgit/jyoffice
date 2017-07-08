package com.jyoffice.actflow.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jyoffice.actflow.exception.ActFlowException;
import com.jyoffice.actflow.model.ActDefNode;
import com.jyoffice.actflow.service.ActFlowControlService;
import com.jyoffice.actflow.service.ActNodeService;
import com.jyoffice.actflow.service.ActSequenceService;
import com.jyoffice.common.BaseController;

@Controller
@RequestMapping(value = "/flowtask")
public class ActFlowController extends BaseController {

	@Autowired
	ActFlowControlService actFlowControlService;
	@Autowired
	ActNodeService actNodeService;
	@Autowired
	ActSequenceService actSequenceService;

	/**
	 * 提交表单
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/submit")
	public String submit(HttpServletRequest request, Model model) {

		try {

			actFlowControlService.doSubmit(request);

		} catch (ActFlowException e) {
			e.printStackTrace();
			addMessage(model, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			addMessage(model, "出现以下错误:" + e.getMessage());
		}

		return "redirect:/process/application/list";
	}

	@RequestMapping("/assignee/{processId}/{taskKey}")
	public String assignee(HttpServletRequest request, Model model, @PathVariable String processId,
			@PathVariable String taskKey) {

		ActDefNode entity =  actNodeService.getActNode(taskKey, Integer.parseInt(processId));
		model.addAttribute("actNode", entity);
		return "app/assignee";
	}

	@RequestMapping("/assigneeMode/{processId}/{taskKey}/{var}")
	@ResponseBody
	public Map<String, String> assigneeMode(HttpServletRequest request, @PathVariable String processId,
			@PathVariable String taskKey, @PathVariable String var) {

		Map<String, String> varMap = null;
		if (var != null && var.length() > 0) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				varMap = mapper.readValue(var, Map.class);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		Map<String, String> result = new HashMap<String, String>();
		if (varMap == null) {
			result.put("assigneeType", "-1");
		} else {
			ActDefNode entity = actFlowControlService.getNextNode(Integer.parseInt(processId), taskKey, varMap);
			if (entity != null) {
				result.put("assigneeType", entity.getAssigneeType() + "");
				result.put("taskKey", entity.getNodeId());
			} else {
				result.put("assigneeType", "-1");
			}
		}
		return result;
	}
	
	
}
