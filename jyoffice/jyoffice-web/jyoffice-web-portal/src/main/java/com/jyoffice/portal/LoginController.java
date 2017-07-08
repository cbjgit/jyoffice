package com.jyoffice.portal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jyoffice.common.BaseController;
import com.jyoffice.sys.model.SysMenu;
import com.jyoffice.sys.service.SysMenuService;
import com.jyoffice.sys.service.SysUserService;

@Controller
@RequestMapping(value = "/login")
public class LoginController extends BaseController {

	@Autowired
	SysMenuService sysMenuService;
	@Autowired
	SysUserService sysUserService;
	/*@Autowired
	RmqProducer rmqProducer;*/
	
	@RequestMapping
	public String login(HttpServletRequest request, HttpServletResponse response,Model model) {

		/*RabbitMessage message = new RabbitMessage();
		message.setExchange("jyoffice.test.exchange");
		message.setRouteKey("jyoffice.test.queue.key");
		rmqProducer.sendMessage(message);*/
		
		
		String loginId = request.getParameter("loginId");
		String password = request.getParameter("password");

		//存储登录信息到缓存
		HttpSession session = request.getSession();
		session.setAttribute("loginId", loginId);
		
		List<SysMenu> menuList = sysMenuService.selectAll(SysMenu.class);
		List<Map<String, String>> mList = new ArrayList<Map<String, String>>();
		for (SysMenu menu : menuList) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("F_ModuleId", menu.getId() + "");
			map.put("F_ParentId", menu.getParentId() + "");
			map.put("F_EnCode", menu.getMenuCode());
			map.put("F_FullName", menu.getMenuName());
			map.put("F_UrlAddress", menu.getMenuUrl());
			map.put("F_UrlAddress", menu.getMenuUrl());
			map.put("context", menu.getContext());
			if (menu.getIsParent() == 1) {
				map.put("F_Icon", "fa fa-desktop");
				map.put("F_Target", "expand");
				map.put("F_IsMenu", "0");
			} else {
				map.put("F_Icon", "fa fa-leaf");
				map.put("F_Target", "iframe");
				map.put("F_IsMenu", "1");
			}
			mList.add(map);
		}

		
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		try {
			String json = mapper.writeValueAsString(mList);
			log.debug("jsonmenu:" + json);
			session.setAttribute("menuList", json);
		} catch (JsonProcessingException e) {
			log.debug("", e);
			session.setAttribute("menuList", "[]");
		}

		return "redirect:/index/main";
	}

	@RequestMapping("/outlogin")
	public String loginout(HttpServletRequest request, Model model) {

		return "login";
	}
}
