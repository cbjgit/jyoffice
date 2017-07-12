package com.jyoffice.actflow.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jyoffice.actflow.model.ActDefNode;
import com.jyoffice.actflow.model.ActDefProcess;
import com.jyoffice.actflow.service.ActNodeService;
import com.jyoffice.actflow.service.ActProcessService;
import com.jyoffice.common.BaseController;
import com.jyoffice.common.JsonMesage;
import com.jyoffice.util.Pager;
import com.jyoffice.util.RequestUtil;

@Controller
@RequestMapping(value = "/process/node")
public class ProcessNodeController extends BaseController {

	@Autowired
	ActNodeService actNodeService;

	@Autowired
	ActProcessService actProcessService;

	/**
	 * 流程环节主页
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/nodemain")
	public String nodemain(HttpServletRequest request, Model model) {
		return "process/nodemain";
	}

	/**
	 * 流程环节列表
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/list/{processId}")
	public String list(HttpServletRequest request, Model model, @PathVariable String processId) {

		ActDefProcess actProcess = actProcessService.get(Integer.parseInt(processId));
		
		Pager<ActDefNode> pager = RequestUtil.getPager(request);
		pager.getParam().put("processId", processId);
		actNodeService.getPager(pager);
		model.addAttribute("pager", pager);
		model.addAttribute("processId", processId);
		model.addAttribute("actProcess", actProcess);
		return "process/nodelist";
	}

	/**
	 * 创建编辑流程节点
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/editnode/{processId}/{nodeId}")
	public String editNode(HttpServletRequest request, Model model, @PathVariable String processId,
			@PathVariable String nodeId) {

		ActDefProcess actProcess = actProcessService.get(Integer.parseInt(processId));
		model.addAttribute("process", actProcess);

		if (nodeId != null && nodeId.length() > 0) {
			int id = Integer.parseInt(nodeId);
			ActDefNode actNode = null;
			if (id > 0) {
				// 编辑
				actNode = actNodeService.get(id);
			} else {
				// 新增
				actNode = new ActDefNode();
				actNode.setId(0);
			}
			model.addAttribute("actNode", actNode);
		}

		model.addAttribute("view",  request.getParameter("view"));
		return "process/editnode";
	}

	/**
	 * 保存流程节点
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/saveNode")
	@ResponseBody
	public JsonMesage saveNode(HttpServletRequest request, HttpServletResponse response) {

		// 保存
		try {
			ActDefNode node = RequestUtil.getFormData(request, new ActDefNode());
			if(node.getId() > 0){
				actNodeService.updateTemplateById(node);
			}else{
				actNodeService.insert(node);
			}
			return new JsonMesage(STATE_SUCCESS, "保存成功");
		} catch (Exception e) {
			log.error("",e);
			return new JsonMesage(STATE_ERROR, "保存失败");
		}

	}

	/**
	 * 删除流程
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/delete/{id}")
	public String delete(RedirectAttributes redirect, @PathVariable Integer id) {

		ActDefNode node = actNodeService.get(id);
		int processId = 0;
		if (node != null) {
			processId = node.getProcessId();
			actNodeService.deleteById(id);
			addMessage(redirect, "节点删除成功");
		} else {
			addMessage(redirect, "你要删除的节点已经不存在");
		}
		return "redirect:/process/node/list/" + processId;
	}
}
