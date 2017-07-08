package com.jyoffice.actflow.controller;

import java.util.ArrayList;
import java.util.HashMap;
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

import com.jyoffice.actflow.model.ActDefProcess;
import com.jyoffice.actflow.service.ActProcessService;
import com.jyoffice.common.BaseController;
import com.jyoffice.common.JsTree;
import com.jyoffice.common.JsonMesage;
import com.jyoffice.util.Pager;
import com.jyoffice.util.RequestUtil;

@Controller
@RequestMapping(value = "/process/definition")
public class ProcessController extends BaseController {

	@Autowired
	ActProcessService actProcessService;

	/**
	 * 流程列表
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model) {
		Pager<ActDefProcess> pager = RequestUtil.getPager(request);
		actProcessService.getPager(pager);
		model.addAttribute("pager", pager);
		return "process/list";
	}

	/**
	 * 创建/编辑流程
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/edit/{id}")
	public String edit(HttpServletRequest request, Model model, @PathVariable Integer id) {
		ActDefProcess process = null;

		if (id != null && id > 0) {
			// 编辑
			process = actProcessService.get(ActDefProcess.class, id);
		} else {
			// 新增
			process = new ActDefProcess();
			process.setId(0);
		}
		model.addAttribute("process", process);
		return "process/editprocess";
	}

	/**
	 * 创建/编辑流程
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public JsonMesage save(HttpServletRequest request, HttpServletResponse response, Model model) {
		ActDefProcess process = null;
		try {
			// 保存
			process = RequestUtil.getFormData(request, new ActDefProcess());
			if (process.getId() > 0) {
				actProcessService.updateTemplateById(process);
			} else {
				process.setStatus(0);
				actProcessService.insert(process);
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

		ActDefProcess process = actProcessService.get(id);
		if (process != null) {
			actProcessService.deleteById(id);
			addMessage(redirect, "流程删除成功");
		} else {
			addMessage(redirect, "你要删除的流程已经不存在");
		}
		return "redirect:/process/definition/list";
	}
	
	/**
	 * 启用/禁用流程
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/disenabled/{id}/{status}")
	public String disabledenabled(RedirectAttributes redirect, @PathVariable Integer id
			, @PathVariable Integer status) {

		ActDefProcess process = actProcessService.get(id);
		if (process != null) {
			process.setStatus(status);
			actProcessService.updateById(process);
			if(status == 1){
				addMessage(redirect, "启用成功");
			}else{
				addMessage(redirect, "停用成功");
			}
			
		} else {
			addMessage(redirect, "流程不存在");
		}
		return "redirect:/process/definition/list";
	}

	/**
	 * 流程树
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/jsonTree/{pid}")
	public @ResponseBody List<JsTree> jsonTree(@PathVariable Integer pid) {

		List<ActDefProcess> list = actProcessService.getList();
		List<JsTree> treeList = new ArrayList<JsTree>();
		JsTree tree = null;
		int i = 0;
		for (ActDefProcess process : list) {
			tree = new JsTree(process.getId() + "", "#", process.getProcessName());
			if (i++ == 0) {
				Map<String, String> state = new HashMap<String, String>();
				state.put("selected", "true");
				tree.setState(state);
			}
			treeList.add(tree);
		}
		return treeList;
	}
}
