package com.jyoffice.portal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jyoffice.common.BaseController;
@Controller
@RequestMapping(value = "/index")
public class IndexController extends BaseController{

	@RequestMapping("")
	public String index(HttpServletRequest request, Model model) {
		
		return "login";
	}
	
	@RequestMapping("main")
	public String main(HttpServletRequest request, Model model) {

		return "index";
	}
}
