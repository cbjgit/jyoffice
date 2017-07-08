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
import com.jyoffice.sys.model.SysRole;
import com.jyoffice.sys.service.SysRoleService;
import com.jyoffice.util.Pager;
import com.jyoffice.util.RequestUtil;

@Controller
@RequestMapping(value = "sys/role")
public class SysRoleController extends BaseController {

	@Autowired
	SysRoleService sysRoleService;

	/**
	 * 角色列表
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model) {
		
		Pager<SysRole> pager = RequestUtil.getPager(request);
		sysRoleService.getPager(pager);
		model.addAttribute("pager", pager);
		return "sys/rolelist";
	}

	/**
	 * 创建/编辑角色
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/edit/{id}")
	public String edit(HttpServletRequest request, Model model, @PathVariable Integer id) {
		SysRole role = null;

		if (id != null && id > 0) {
			// 编辑
			role = sysRoleService.get(SysRole.class, id);
		} else {
			// 新增
			role = new SysRole();
			role.setId(0);
		}
		model.addAttribute("role", role);
		return "sys/roleedit";
	}

	/**
	 * 创建/编辑角色
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public JsonMesage save(HttpServletRequest request, HttpServletResponse response, Model model) {
		SysRole role = null;
		try {
			// 保存
			role = RequestUtil.getFormData(request, new SysRole());
			if (role.getId() > 0) {
				sysRoleService.updateTemplateById(role);
			} else {
				sysRoleService.insert(role);
			}
			return new JsonMesage(STATE_SUCCESS, "保存成功");
		} catch (Exception e) {
			log.error("",e);
			return new JsonMesage(STATE_ERROR, "保存失败");
		}

	}

	/**
	 * 创建/编辑角色
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/delete/{id}")
	public String delete(RedirectAttributes redirect, @PathVariable Integer id) {

		SysRole role = sysRoleService.get(id);
		if (role != null) {
			sysRoleService.deleteById(id);
			addMessage(redirect, "角色删除成功");
		} else {
			addMessage(redirect, "你要删除的角色已经不存在");
		}
		return "redirect:/sys/role/list";
	}

}
