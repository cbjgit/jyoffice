package com.jyoffice.actflow.controller;

import java.util.List;
import java.util.Map;

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
import com.jyoffice.actflow.model.ActDefSequence;
import com.jyoffice.actflow.service.ActNodeService;
import com.jyoffice.actflow.service.ActProcessService;
import com.jyoffice.actflow.service.ActSequenceService;
import com.jyoffice.common.BaseController;
import com.jyoffice.common.JsonMesage;
import com.jyoffice.util.Pager;
import com.jyoffice.util.RequestUtil;

@Controller
@RequestMapping(value = "/process/sequnece")
public class ProcessSequneceController extends BaseController {

	@Autowired
	ActNodeService actNodeService;

	@Autowired
	ActProcessService actProcessService;

	@Autowired
	ActSequenceService actSequenceService;

	/**
	 * 流程流向主页
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/seqmain")
	public String seqmain(HttpServletRequest request, Model model) {
		return "process/seqmain";
	}

	/**
	 * 流程流向列表
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/list/{processId}")
	public String list(HttpServletRequest request, Model model, @PathVariable String processId) {

		ActDefProcess actProcess = actProcessService.get(Integer.parseInt(processId));
		
		Pager<Map<String, Object>> pager =RequestUtil.getPager(request);
		pager.getParam().put("processId", processId);
		actSequenceService.getPager(pager);
		model.addAttribute("pager", pager);
		model.addAttribute("processId", processId);
		model.addAttribute("actProcess", actProcess);
		return "process/seqlist";
	}

	/**
	 * 创建编辑流程节点
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/editseq/{processId}/{seqId}")
	public String editseq(HttpServletRequest request, Model model, @PathVariable String processId,
			@PathVariable String seqId) {

		ActDefProcess actProcess = actProcessService.get(Integer.parseInt(processId));
		model.addAttribute("process", actProcess);

		List<ActDefNode> nodeList = actNodeService.getList(Integer.parseInt(processId));
		model.addAttribute("nodeList", nodeList);

		if (seqId != null && seqId.length() > 0) {
			int id = Integer.parseInt(seqId);
			ActDefSequence actSeq = null;
			if (id > 0) {
				// 编辑
				actSeq = actSequenceService.get(id);
			} else {
				// 新增
				actSeq = new ActDefSequence();
			}
			model.addAttribute("actSequence", actSeq);
		}

		return "process/editseq";
	}

	/**
	 * 创建编辑流程节点
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/saveseq")
	@ResponseBody
	public JsonMesage saveseq(HttpServletRequest request, HttpServletResponse response) {

		try {
			// 保存
			ActDefSequence seq = RequestUtil.getFormData(request, new ActDefSequence());
			if(seq.getId() > 0){
				actSequenceService.updateTemplateById(seq);
			}else{
				actSequenceService.insert(seq);
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

		ActDefSequence seq = actSequenceService.get(id);
		int processId = 0;
		if (seq != null) {
			processId = seq.getProcessId();
			actSequenceService.deleteById(id);
			addMessage(redirect, "环节流向删除成功");
		} else {
			addMessage(redirect, "你要删除的环节流向已经不存在");
		}
		return "redirect:/process/sequnece/list/" + processId;
	}

}
