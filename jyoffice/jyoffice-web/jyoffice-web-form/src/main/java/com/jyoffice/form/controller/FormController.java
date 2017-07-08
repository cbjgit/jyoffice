package com.jyoffice.form.controller;

import java.util.Date;
import java.util.List;

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
import com.jyoffice.form.help.BuildForm;
import com.jyoffice.form.model.FrmElement;
import com.jyoffice.form.model.FrmElementRight;
import com.jyoffice.form.model.FrmForm;
import com.jyoffice.form.model.FrmOption;
import com.jyoffice.form.model.FrmPublish;
import com.jyoffice.form.service.FrmFormService;
import com.jyoffice.util.Pager;
import com.jyoffice.util.RequestUtil;

@Controller
@RequestMapping(value = "/form")
public class FormController extends BaseController {

	@Autowired
	private FrmFormService frmFormService;

	@Autowired
	private ActProcessService actProcessService;

	@Autowired
	private ActNodeService actNodeService;

	/**
	 * 表单列表
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model) {
		Pager<FrmForm> pager = RequestUtil.getPager(request);
		frmFormService.getPager(pager);
		model.addAttribute("pager", pager);
		return "form/list";
	}

	/**
	 * 创建表单
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/edit/{id}")
	public String edit(HttpServletRequest request, Model model, @PathVariable Integer id) {

		List<ActDefProcess> list = actProcessService.getList();
		model.addAttribute("processlist", list);
		FrmForm form = null;
		if (id != null && id > 0) {
			// 编辑
			form = frmFormService.get(id);
		} else {
			// 新增
			form = new FrmForm();
			form.setVersion(1);
			form.setStatus(0);
		}
		model.addAttribute("form", form);
		return "form/edit";
	}

	/**
	 * 创建表单
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public JsonMesage save(HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			FrmForm form = RequestUtil.getFormData(request, new FrmForm());
			if (frmFormService.formExists(form.getFormKey(), form.getId())) {
				return new JsonMesage(STATE_VALID, "已存在表单Key，请使用其他值");
			}

			frmFormService.save(form);
			return new JsonMesage(STATE_SUCCESS, "保存成功");
		} catch (Exception e) {
			return new JsonMesage(STATE_ERROR, "保存出错");
		}

	}

	/**
	 * 删除
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/delete/{id}")
	public String delete(RedirectAttributes redirect, @PathVariable Integer id) {

		FrmForm form = frmFormService.get(id);
		if (form != null) {
			frmFormService.deleteById(id);
			addMessage(redirect, "表单删除成功");
		} else {
			addMessage(redirect, "你要删除的表单不存在");
		}
		return "redirect:/form/list";
	}

	@RequestMapping("/preview/{formId}/{nodeId}")
	public String preview(HttpServletRequest request, Model model, @PathVariable Integer formId,
			@PathVariable String nodeId) {

		FrmForm form = frmFormService.get(formId);
		String html = frmFormService.getFormHtml(formId);
		if (html == null) {
			List<FrmElement> elementList = frmFormService.getElementList(formId);
			List<FrmOption> optList = frmFormService.getOptionListByFormId(formId);
			html = BuildForm.layout(form, elementList, optList, request);
		}

		model.addAttribute("formHtml", html);

		List<ActDefNode> nodeList = actNodeService.getUserTaskList(form.getProcessId());
		if (nodeId == null || nodeId.equals("-")) {
			nodeId = nodeList.get(0).getNodeId();
		}
		List<FrmElementRight> rightList = frmFormService.getNodeRight(formId, nodeId);
		StringBuffer hidebf = new StringBuffer();
		StringBuffer readbf = new StringBuffer();
		for (FrmElementRight right : rightList) {
			if (right.getRights() == 1) {
				readbf.append(right.getFieldName()).append(",");
			} else if (right.getRights() == 3) {
				hidebf.append(right.getFieldName()).append(",");
			}
		}
		model.addAttribute("hideList", hidebf);
		model.addAttribute("readList", readbf);
		model.addAttribute("nodeList", nodeList);
		model.addAttribute("form", form);
		model.addAttribute("nodeId", nodeId);
		return "form/preview";
	}

	/**
	 * 复制
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/copy/{id}")
	public String copy(HttpServletRequest request, RedirectAttributes redirect, @PathVariable Integer id) {

		frmFormService.copyForm(id, request);

		return "redirect:/form/list";
	}

	/**
	 * 发布
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/publish/{id}")
	public String publish(HttpServletRequest request, RedirectAttributes redirect,
			@PathVariable Integer id) {

		FrmForm form = frmFormService.get(id);
		List<FrmElement> elementList = frmFormService.getElementList(id);
		List<FrmOption> optList = frmFormService.getOptionListByFormId(id);

		String html = BuildForm.layout(form, elementList, optList, request);

		FrmPublish publish = new FrmPublish();
		publish.setFormHtml(html);
		publish.setFormId(id);
		publish.setPublishDate(new Date());
		publish.setTarget(1);
		frmFormService.savePublish(publish);

		return "redirect:/form/list";
	}

	/**
	 * 撤销发布
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/cancelpublish/{id}")
	public String cancelpublish(RedirectAttributes redirect, @PathVariable Integer id) {

		frmFormService.cancelPublish(id);
		return "redirect:/form/list";
	}
}
