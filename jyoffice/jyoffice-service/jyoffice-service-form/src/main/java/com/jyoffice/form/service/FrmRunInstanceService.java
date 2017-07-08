package com.jyoffice.form.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jyoffice.actflow.service.IActFlow;
import com.jyoffice.ca.SessionUtil;
import com.jyoffice.form.help.ElementType;
import com.jyoffice.form.model.FrmElement;
import com.jyoffice.form.model.FrmForm;
import com.jyoffice.form.model.FrmRunData;
import com.jyoffice.form.model.FrmRunInstance;
import com.jyoffice.util.GenOrderSN;
import com.jyoffice.util.Pager;

@Service
public class FrmRunInstanceService extends BaseService<FrmRunInstance, String> implements IActFlow{

	@Autowired
	FrmFormService frmFormService;
	
	public void getPager(Pager<Map<String, Object>> pager) {
		super.getPager("FrmRunInstance.list", pager);
	}

	public FrmRunInstance get(String id) {
		return super.get(FrmRunInstance.class,  id);
	}

	public void deleteById(String id) {
		super.deleteById(FrmRunInstance.class, id);
	}

	public String save(HttpServletRequest request) {

		int fromId = Integer.parseInt(request.getParameter("formId"));
		String frmInstanceId = request.getParameter("frmInstanceId");
		String taskKey = request.getParameter("taskKey");
		
		FrmForm form = frmFormService.get(fromId);
		List<FrmRunData> dataList = getData(request,form.getId());
		
		FrmRunInstance entity = new FrmRunInstance();
		entity.setFormId(form.getId());
		entity.setFormName(form.getFormName());
		entity.setStatus(taskKey);
		entity.setProcessId(form.getProcessId());
		
		if(frmInstanceId != null && !"-".equals(frmInstanceId)){
			entity.setId(frmInstanceId);
		}
		
		if (entity.getId() == null || entity.getId().length() == 0) {
			entity.setCreateBy(SessionUtil.getUserId(request));
			entity.setCreateDate(new Date());
		}
		entity.setUpdateBy(SessionUtil.getUserId(request));
		entity.setUpdateDate(new Date());

		if (entity.getId() != null && entity.getId().length() > 0) {
			super.updateTemplateById(entity);
			for (FrmRunData data : dataList) {
				data.setFrmInstanceId(frmInstanceId);
				super.sqlManager.update("FrmRunData.updateData", data);
			}

		} else {
			String id = GenOrderSN.getKey();
			entity.setId(id);
			super.insert(entity,false);

			for (FrmRunData data : dataList) {
				data.setFrmInstanceId(id);
				super.sqlManager.insert(data);
			}
		}
		
		return entity.getId();
	}

	private List<FrmRunData> getData(HttpServletRequest request,int formId) {

		List<FrmElement> elementList = frmFormService.getElementList(formId);

		FrmRunData data = null;
		List<FrmRunData> dataList = new ArrayList<FrmRunData>();
		String val = null;
		String[] vals = null;
		ObjectMapper mapper = new ObjectMapper();
		for (FrmElement elm : elementList) {
			if (ElementType.FIELD_CHECKBOX.equals(elm.getFieldType())) {
				vals = request.getParameterValues(elm.getFieldName());
				val = StringUtils.join(vals, ",");
			} else {
				val = request.getParameter(elm.getFieldName());
			}
			data = new FrmRunData();
			data.setFieldName(elm.getFieldName());
			if (ElementType.FIELD_TEXTAREA.equals(elm.getFieldType())) {
				data.setBigValue(val);
			} else if (ElementType.DATA_FLOAT.equals(elm.getDataType())) {
				if(val.length() > 0){
					data.setDoubleValue(new BigDecimal(val));
				}
			} else if (ElementType.DATA_INTEGER.equals(elm.getDataType())) {
				if(val.length() > 0){
					data.setIntegerValue(Integer.parseInt(val));
				}
			} else if (ElementType.DATA_DATE.equals(elm.getDataType())) {
				if(val.length() > 0){
					Map<String, String> map;
					try {
						map = mapper.readValue(elm.getOtherParam(), Map.class);
						data.setDateValue(DateUtils.parseDate(val, map.get("datestyle")));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else {
				data.setStringValue(val);
			}
			dataList.add(data);
		}

		return dataList;
	}

	public List<FrmRunData> getFormData(String instanceId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("instanceId",instanceId);
		return super.sqlManager.select("FrmRunData.list", FrmRunData.class, param);
	}

	@Override
	public String submit(HttpServletRequest request) {
		
		String instanceId = save(request);
		
		return instanceId;
	}

	@Override
	public String complete(HttpServletRequest request) {
		
		return null;
	}

	@Override
	public void withdraw(String taskKey, String instanceId) {
		
		
	}

	@Override
	public void reject(String taskId, String instanceId, String taskKey) {
		
		
	}

	@Override
	public void updateInstace(String instanceId, String businessKey, String taskKey) {
		
		FrmRunInstance entity = new FrmRunInstance();
		entity.setInstanceId(instanceId);
		entity.setId(businessKey);
		entity.setStatus(taskKey);
		entity.setSubmit(1);
		super.updateTemplateById(entity);
	}
}
