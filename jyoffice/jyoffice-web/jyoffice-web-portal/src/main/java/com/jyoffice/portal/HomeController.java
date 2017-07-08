package com.jyoffice.portal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jyoffice.common.BaseController;
import com.jyoffice.form.service.FrmFormService;
@Controller
@RequestMapping(value = "/home")
public class HomeController extends BaseController{

	@Autowired
	private FrmFormService frmFormService;
	
	/**
	 * 表单列表
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model) {
		model.addAttribute("list", frmFormService.getFormListByPublsih());
		return "center";
	}
}
