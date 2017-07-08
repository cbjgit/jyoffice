package com.jyoffice.actflow.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jyoffice.actflow.service.ActEngineService;
import com.jyoffice.common.BaseController;
import com.jyoffice.util.Pager;
import com.jyoffice.util.RequestUtil;

@Controller
@RequestMapping(value = "/process/task")
public class TaskController extends BaseController {

	@Autowired
	ActEngineService actEngineService;

	@RequestMapping("/runlist")
	public String runlist(HttpServletRequest request, Model model) {
		Pager<Map<String, Object>> pager = RequestUtil.getPager(request);
		actEngineService.getTaskList(pager);
		model.addAttribute("pager", pager);
		return "task/runlist";
	}	
	
	@RequestMapping("/hilist")
	public String hilist(HttpServletRequest request, Model model) {
		Pager<Map<String, Object>> pager = RequestUtil.getPager(request);
		actEngineService.getTaskList(pager);
		model.addAttribute("pager", pager);
		return "task/hilist";
	}	
	
}
