package com.jyoffice.form.controller;

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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jyoffice.actflow.model.ActDefNode;
import com.jyoffice.actflow.service.ActNodeService;
import com.jyoffice.common.BaseController;
import com.jyoffice.common.JsonMesage;
import com.jyoffice.form.help.ElementType;
import com.jyoffice.form.model.FrmElement;
import com.jyoffice.form.model.FrmElementRight;
import com.jyoffice.form.model.FrmForm;
import com.jyoffice.form.model.FrmOption;
import com.jyoffice.form.service.FrmFormService;
import com.jyoffice.util.Pager;
import com.jyoffice.util.RequestUtil;

@Controller
@RequestMapping(value = "/form/element")
public class FormElementController extends BaseController {

	@Autowired
	private FrmFormService frmFormService;

	@Autowired
	private ActNodeService actNodeService;

	/**
	 * 表单元素列表
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/list/{formId}")
	public String elementList(HttpServletRequest request, Model model, @PathVariable Integer formId) {
		Pager<FrmElement> pager = new Pager<FrmElement>();
		pager.getParam().put("formId", formId + "");
		frmFormService.getElmPager(pager);
		model.addAttribute("pager",pager );

		model.addAttribute("formId", formId);
		return "form/elmlist";
	}

	/**
	 * 表单元素
	 * 
	 * @param request
	 * @param model
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping("/edit/{formId}/{elmId}/{fieldtype}")
	public String element(HttpServletRequest request, Model model, @PathVariable Integer formId,
			@PathVariable Integer elmId, @PathVariable String fieldtype) throws JsonProcessingException {

		FrmElement element = null;

		FrmForm frmForm = frmFormService.get(formId);
		if (elmId > 0) {
			element = frmFormService.getElement(elmId);
		} else {
			element = new FrmElement();
			element.setOtherParam("{}");
		}

		model.addAttribute("elmId", elmId);
		model.addAttribute("fieldtype", fieldtype);
		model.addAttribute("element", element);
		model.addAttribute("frmForm", frmForm);

		if (ElementType.FIELD_RADIO.equals(fieldtype)
				|| ElementType.FIELD_CHECKBOX.equals(fieldtype)
				|| ElementType.FIELD_SELECT.equals(fieldtype)) {
			if (elmId > 0) {
				List<FrmOption> optList = frmFormService.getOptionListByFieldId(elmId);
				model.addAttribute("optList", optList);
			}
			return "form/elm_mutivalue";
		}
		return "form/elm_" + fieldtype;
	}

	/**
	 * 表单元素
	 * 
	 * @param request
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping("/save/{formId}/{elmId}/{fieldtype}")
	@ResponseBody
	public JsonMesage save(HttpServletRequest request, HttpServletResponse response,
			@PathVariable Integer formId, @PathVariable Integer elmId, @PathVariable String fieldtype)
			throws JsonProcessingException {

		try {
			FrmElement element = RequestUtil.getFormData(request, new FrmElement());
			element.setId(elmId);
			element.setFormId(formId);
			element.setFieldType(fieldtype);

			if (frmFormService.elementExists(element.getFieldName(), element.getId())) {
				return new JsonMesage(STATE_VALID, "已存在表单元素名称，请使用其他名称");
			}

			String width = request.getParameter("width");
			String height = request.getParameter("height");
			String datestyle = request.getParameter("datestyle");
			
			Map<String, String> otherParam = new HashMap<String, String>();
			if (width != null && width.length() > 0) {
				otherParam.put("width", width);
			}
			if (height != null && height.length() > 0) {
				otherParam.put("height", height);
			}
			if (datestyle != null && datestyle.length() > 0 && ElementType.DATA_DATE.equals(element.getDataType())) {
				otherParam.put("datestyle", datestyle);
			}
			
			if (element.getDataType() == null || element.getDataType().length() == 0) {
				element.setDataType("text");
			}

			String[] options = null;
			if (ElementType.FIELD_RADIO.equals(element.getFieldType())
					|| ElementType.FIELD_CHECKBOX.equals(element.getFieldType())
					|| ElementType.FIELD_SELECT.equals(element.getFieldType())) {
				otherParam.put("showtype", request.getParameter("showtype"));
				options = request.getParameterValues("optonsList");
			}

			ObjectMapper mapper = new ObjectMapper();
			element.setOtherParam(mapper.writeValueAsString(otherParam));
			frmFormService.saveElement(element, options);

			return new JsonMesage(STATE_SUCCESS, "保存成功");
		} catch (Exception e) {
			log.error("",e);
			return new JsonMesage(STATE_ERROR, "保存失败");
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
	public String elementdelete(RedirectAttributes redirect, @PathVariable Integer id) {

		FrmElement elm = frmFormService.getElement(id);
		int formId = 0;
		if (elm != null) {
			formId = elm.getFormId();
			frmFormService.deleteElement(id);
			addMessage(redirect, "表单元素删除成功");
		} else {
			addMessage(redirect, "你要删除的表单元素不存在");
		}
		return "redirect:/form/element/list/" + formId;
	}

	@RequestMapping("/elmright/{formId}")
	public String elmright(HttpServletRequest request, Model model, @PathVariable Integer formId) {

		FrmForm form = frmFormService.get(formId);

		List<ActDefNode> nodeList = actNodeService.getUserTaskList(form.getProcessId());
		List<FrmElement> elmList = frmFormService.getElementList(formId);
		model.addAttribute("form", form);
		model.addAttribute("elmList", elmList);
		model.addAttribute("nodeList", nodeList);

		List<FrmElementRight> rightList = frmFormService.getRight(formId);
		if (rightList.size() > 0) {
			model.addAttribute("edit", "true");
		}
		model.addAttribute("rightList", rightList);

		return "form/elmright";
	}

	@RequestMapping("/elmright/save/{formId}")
	public String elmrightsave(HttpServletRequest request, Model model, @PathVariable Integer formId) {

		String fieldName[] = request.getParameterValues("fieldName");
		String nodeId[] = request.getParameterValues("nodeId");
		List<FrmElementRight> rightList = new ArrayList<FrmElementRight>();
		String rv = "";
		for (String fid : fieldName) {
			for (String nid : nodeId) {
				FrmElementRight right = new FrmElementRight();
				right.setFormId(formId);
				rv = request.getParameter(nid + "_" + fid);
				right.setRights(Integer.parseInt(rv));
				right.setFieldName(fid);
				right.setNodeId(nid);
				rightList.add(right);
			}
		}

		frmFormService.saveRight(formId, rightList);
		return "redirect:/form/element/elmright/" + formId;
	}

}
