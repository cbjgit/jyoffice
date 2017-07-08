package com.jyoffice.form.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jyoffice.actflow.model.ActDefSequence;
import com.jyoffice.actflow.service.ActProcessService;
import com.jyoffice.actflow.service.ActSequenceService;
import com.jyoffice.common.BaseController;
import com.jyoffice.form.model.FrmElement;
import com.jyoffice.form.model.FrmElementRight;
import com.jyoffice.form.model.FrmForm;
import com.jyoffice.form.model.FrmRunData;
import com.jyoffice.form.service.FrmFormService;
import com.jyoffice.form.service.FrmRunInstanceService;
import com.jyoffice.util.DateUtil;
import com.jyoffice.util.Pager;
import com.jyoffice.util.RequestUtil;

@Controller
@RequestMapping(value = "/process/application")
public class ApplicationController extends BaseController {

	@Autowired
	FrmFormService frmFormService;

	@Autowired
	ActProcessService actProcessService;

	@Autowired
	ActSequenceService actSequenceService;

	@Autowired
	FrmRunInstanceService frmRunInstanceService;

	/**
	 * 发起流程
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model) {
		Pager<Map<String, Object>> pager = RequestUtil.getPager(request);
		if(pager.getParam().containsKey("createEDate") && pager.getParam().get("createEDate").length() > 0){
			pager.getParam().put("createEDate",pager.getParam().get("createEDate")+" 23:59:59");
		}
		frmRunInstanceService.getPager(pager);
		model.addAttribute("pager", pager);
		return "app/list";
	}

	/**
	 * 发起流程
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/{formId}/{frmInstanceId}")
	public String form(HttpServletRequest request, Model model, @PathVariable Integer formId,
			@PathVariable String frmInstanceId) {

		String instanceId = request.getParameter("instanceId");
		String taskKey = request.getParameter("taskKey");
		String taskId = request.getParameter("taskId");

		// 布局表单
		FrmForm form = frmFormService.get(formId);
		model.addAttribute("formHtml", frmFormService.getFormHtml(formId));
		
		if (instanceId == null || instanceId.length() == 0) {
			List<ActDefSequence> list = actSequenceService.getListByNodeId(form.getProcessId(), "start");
			if (list.size() > 0) {
				taskKey = list.get(0).getToNodeId();
			}
		}

		// 获取字段权限。控制表单字段状态
		List<FrmElementRight> rightList = frmFormService.getNodeRight(formId, taskKey);
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

		// 获取表单数据
		if (frmInstanceId != null && !"-".equals(frmInstanceId)) {
			model.addAttribute("formData", getFormData(frmInstanceId, formId));
		} else {
			model.addAttribute("formData", "[]");
		}

		model.addAttribute("taskKey", taskKey);
		model.addAttribute("formId", formId);
		model.addAttribute("taskId", taskId);
		model.addAttribute("frmInstanceId", frmInstanceId);
		model.addAttribute("processId", form.getProcessId());
		
		return "app/form";
	}

	@SuppressWarnings("unchecked")
	private String getFormData(String frmInstanceId, int formId) {
		List<FrmRunData> list = frmRunInstanceService.getFormData(frmInstanceId);
		List<FrmElement> elementList = frmFormService.getElementList(formId);
		
		ObjectMapper mapper = new ObjectMapper();

		Map<String, FrmElement> elmMap = new HashMap<String, FrmElement>();
		for (FrmElement elm : elementList) {
			elmMap.put(elm.getFieldName(), elm);
		}
		List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
		String val = null;
		for (FrmRunData frd : list) {
			val = null;
			Map<String, String> data = new HashMap<String, String>();
			if (frd.getStringValue() != null && frd.getStringValue().length() > 0) {
				val = frd.getStringValue();
			} else if (frd.getBigValue() != null && frd.getBigValue().length() > 0) {
				val = frd.getBigValue();
			} else if (frd.getDoubleValue() != null) {
				val = String.valueOf(frd.getDoubleValue());
			} else if (frd.getIntegerValue() != null) {
				val = String.valueOf(frd.getIntegerValue());
			} else if (frd.getDateValue() != null) {
				Map<String, String> map;
				try {
					if(elmMap.containsKey(frd.getFieldName())){
						map = mapper.readValue(elmMap.get(frd.getFieldName()).getOtherParam(), Map.class);
						val = DateUtil.formatDate(frd.getDateValue(), map.get("datestyle"));
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				val = "";
			}

			data.put("fieldName", frd.getFieldName());
			data.put("fieldValue", val);
			if(elmMap.containsKey(frd.getFieldName())){
				data.put("fieldType", elmMap.get(frd.getFieldName()).getFieldType());
			}
			dataList.add(data);

		}

		String json = "[]";
		try {
			json = mapper.writeValueAsString(dataList);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return json;
	}

	/**
	 * 保存数据
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/save")
	public String save(HttpServletRequest request, Model model, RedirectAttributes redirect) {

		int fromId = Integer.parseInt(request.getParameter("formId"));
		String frmInstanceId = request.getParameter("frmInstanceId");

		frmInstanceId = frmRunInstanceService.save(request);

		return "redirect:/process/application/" + fromId + "/" + frmInstanceId;
	}
}
