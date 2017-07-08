package com.jyoffice.system.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jyoffice.common.BaseController;
import com.jyoffice.common.JsonMesage;
import com.jyoffice.sys.model.SysAuth;
import com.jyoffice.sys.service.SysAuthService;
import com.jyoffice.util.Pager;
import com.jyoffice.util.RequestUtil;

@Controller
@RequestMapping(value = "sys/auth")
public class SysAuthController extends BaseController {

	@Autowired
	SysAuthService sysAuthService;

	/**
	 * 权限列表
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model) {
		
		Pager<SysAuth> pager = RequestUtil.getPager(request);
		sysAuthService.getPager(pager);
		model.addAttribute("pager", pager);
		return "sys/userlist";
	}

	/**
	 * 创建/编辑权限
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/edit/{id}")
	public String edit(HttpServletRequest request, Model model, @PathVariable Integer id) {
		SysAuth user = null;

		if (id != null && id > 0) {
			// 编辑
			user = sysAuthService.get(SysAuth.class, id);
		} else {
			// 新增
			user = new SysAuth();
			user.setId(0);
		}
		model.addAttribute("user", user);
		return "sys/useredit";
	}

	/**
	 * 创建/编辑权限
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public JsonMesage save(HttpServletRequest request, HttpServletResponse response, Model model) {
		SysAuth user = null;
		try {
			// 保存
			user = RequestUtil.getFormData(request, new SysAuth());
			if (user.getId() > 0) {
				sysAuthService.updateTemplateById(user);
			} else {
				sysAuthService.insert(user);
			}
			return new JsonMesage(STATE_SUCCESS, "保存成功");
		} catch (Exception e) {
			log.error("",e);
			return new JsonMesage(STATE_ERROR, "保存失败");
		}

	}

	/**
	 * 创建/编辑权限
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/delete/{id}")
	public String delete(RedirectAttributes redirect, @PathVariable Integer id) {

		SysAuth user = sysAuthService.get(id);
		if (user != null) {
			sysAuthService.deleteById(id);
			addMessage(redirect, "权限删除成功");
		} else {
			addMessage(redirect, "你要删除的权限已经不存在");
		}
		return "redirect:/sys/user/list";
	}

}
