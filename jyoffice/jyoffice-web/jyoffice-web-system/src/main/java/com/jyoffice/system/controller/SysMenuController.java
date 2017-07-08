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
import com.jyoffice.sys.model.SysMenu;
import com.jyoffice.sys.service.SysMenuService;
import com.jyoffice.util.Pager;
import com.jyoffice.util.RequestUtil;

@Controller
@RequestMapping(value = "sys/menu")
public class SysMenuController extends BaseController {

	@Autowired
	SysMenuService sysMenuService;

	/**
	 * 菜单列表
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model) {
		
		Pager<SysMenu> pager = RequestUtil.getPager(request);
		sysMenuService.getPager(pager);
		model.addAttribute("pager", pager);
		return "sys/menulist";
	}

	/**
	 * 创建/编辑菜单
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/edit/{id}")
	public String edit(HttpServletRequest request, Model model, @PathVariable Integer id) {
		SysMenu menu = null;

		if (id != null && id > 0) {
			// 编辑
			menu = sysMenuService.get(SysMenu.class, id);
		} else {
			// 新增
			menu = new SysMenu();
			menu.setId(0);
		}
		model.addAttribute("menu", menu);
		return "sys/menuedit";
	}

	/**
	 * 创建/编辑菜单
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public JsonMesage save(HttpServletRequest request, HttpServletResponse response, Model model) {
		SysMenu menu = null;
		try {
			// 保存
			menu = RequestUtil.getFormData(request, new SysMenu());
			if (menu.getId() > 0) {
				sysMenuService.updateTemplateById(menu);
			} else {
				sysMenuService.insert(menu);
			}
			return new JsonMesage( STATE_SUCCESS, "保存成功");
		} catch (Exception e) {
			log.error("",e);
			return new JsonMesage(STATE_ERROR, "保存失败");
		}

	}

	/**
	 * 创建/编辑菜单
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/delete/{id}")
	public String delete(RedirectAttributes redirect, @PathVariable Integer id) {

		SysMenu menu = sysMenuService.get(id);
		if (menu != null) {
			sysMenuService.deleteById(id);
			addMessage(redirect, "菜单删除成功");
		} else {
			addMessage(redirect, "你要删除的菜单已经不存在");
		}
		return "redirect:/sys/menu/list";
	}

}
