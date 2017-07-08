package com.jyoffice.form.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.beetl.sql.core.SQLReady;
import org.beetl.sql.core.db.KeyHolder;
import org.beetl.sql.core.engine.PageQuery;
import org.springframework.stereotype.Service;

import com.jyoffice.ca.SessionUtil;
import com.jyoffice.form.model.FrmElement;
import com.jyoffice.form.model.FrmElementRight;
import com.jyoffice.form.model.FrmForm;
import com.jyoffice.form.model.FrmOption;
import com.jyoffice.form.model.FrmPublish;
import com.jyoffice.util.Pager;

@Service
public class FrmFormService extends BaseService<FrmForm, Integer> {

	public void getPager(Pager<FrmForm> pager) {
		super.getPager("FrmForm.list", FrmForm.class, pager);
	}
	
	public List<FrmForm> getList() {
		return super.selectAll(FrmForm.class);
	}
	
	public List<FrmForm> getFormListByPublsih() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("status",1);
		return getFormList(param);
	}
	
	public FrmForm get(Integer id) {
		return super.get(FrmForm.class, id);
	}

	public void save(FrmForm entity) {
		if(entity.getId() != null && entity.getId() > 0){
			super.updateTemplateById(entity);
		}else{
			super.insert(entity);
		}
	}
	
	public boolean formExists(String formKey, int id) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("formKey",formKey);
		List<FrmForm> list = getFormList(param);
		if (id > 0 && list.size() > 0) {
			if (list.get(0).getId() == id) {
				return false;
			} else {
				return true;
			}
		}
		return list.size() > 0;
	}
	
	public List<FrmForm> getFormList(Map<String, Object> param) {
		return super.getList("FrmForm.list", FrmForm.class, param);
	}

	public void deleteById(Integer id) {
		sqlManager.executeUpdate(new SQLReady("delete from frm_option where form_id = ?",id));
		sqlManager.executeUpdate(new SQLReady("delete from frm_element_right where form_id = ?",id));
		sqlManager.executeUpdate(new SQLReady("delete from frm_element where form_id = ?",id));
		super.deleteById(FrmForm.class, id);
	}

	public void getElmPager(Pager<FrmElement> pager) {
		PageQuery<FrmElement> page = new PageQuery<FrmElement>(pager.getCurrentPage(), pager.getParam());
		sqlManager.pageQuery("FrmElement.list", FrmElement.class, page);
		pager.setTotal(page.getTotalRow());
		pager.setCountPage((int)page.getTotalPage());
		pager.setResultList(page.getList());
	}

	public FrmElement getElement(Integer id) {
		return super.sqlManager.unique(FrmElement.class, id);
	}

	public boolean elementExists(String fieldName, int id) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("fieldName",fieldName);
		List<FrmElement> list = getElementList(param);
		if (id > 0 && list.size() > 0) {
			if (list.get(0).getId() == id) {
				return false;
			} else {
				return true;
			}
		}
		return list.size() > 0;
	}

	public List<FrmElement> getElementList(Integer formId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("formId",formId);
		return getElementList(param);
	}
	public List<FrmElement> getElementList(Map<String, Object> param){
		return sqlManager.select("FrmElement.list", FrmElement.class, param);
	}
	
	
	public void saveElement(FrmElement entity, String[] options) {
		if (entity.getId() > 0) {
			sqlManager.updateTemplateById(entity);
		} else {
			KeyHolder holder = new KeyHolder();
			sqlManager.insert(FrmElement.class, entity, holder);
			entity.setId(holder.getInt());
		}
		if ("radio".equals(entity.getFieldType()) || "checkbox".equals(entity.getFieldType())
				|| "select".equals(entity.getFieldType())) {
			sqlManager.executeUpdate(new SQLReady("delete from Frm_Option where field_Id = ?", entity.getId()));
			if (options != null && options.length > 0) {

				FrmOption optentity = null;
				String[] opt = null;
				for (String option : options) {
					optentity = new FrmOption();
					opt = option.split("==");
					optentity.setFieldId(entity.getId());
					optentity.setFormId(entity.getFormId());
					optentity.setOptionText(opt[1]);
					optentity.setOptionValue(opt[0]);
					sqlManager.insert(optentity);
				}
			}
		}
	}

	public void deleteElement(Integer id) {
		sqlManager.deleteById(FrmElement.class, id);
	}

	
	public void deleteOptionByFieldId(Integer fieldId){
		sqlManager.executeUpdate(new SQLReady("delete from frm_option where field_id=?", fieldId));
	}
	
	public void deleteOptionByFormId(Integer formId){
		sqlManager.executeUpdate(new SQLReady("delete from frm_option where form_id=?", formId));
	}
	
	public List<FrmOption> getOptionListByFieldId(Integer fieldId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("fieldId",fieldId);
		return getOptionList(param);
	}
	
	public List<FrmOption> getOptionListByFormId(Integer formId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("formId",formId);
		return getOptionList(param);
	}
	
	public List<FrmOption> getOptionList(Map<String, Object> param) {
		return sqlManager.select("FrmOption.list", FrmOption.class, param);
	}
	
	
	
	
	public void saveRight(int formId,List<FrmElementRight> rightList) {
		sqlManager.executeUpdate(new SQLReady("delete from Frm_element_right where form_id=?", formId));
		for(FrmElementRight right : rightList){
			sqlManager.insert(right);
		}
	}
	
	public List<FrmElementRight> getRight(int formId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("formId",formId);
		return getNodeRightList(param);
	}
	
	public List<FrmElementRight> getNodeRight(int formId,String nodeId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("formId",formId);
		param.put("nodeId",nodeId);
		return getNodeRightList(param);
	}
	
	public List<FrmElementRight> getNodeRightList(Map<String, Object> param) {
		return sqlManager.select("FrmElementRight.list", FrmElementRight.class, param);
	}
	
	public int maxVersion(String formKey) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("formKey", formKey);
		return sqlManager.intValue("FrmForm.maxVersion",param);
	}
	
	public void copyForm(int formId,HttpServletRequest request) {
		FrmForm form = get(formId);
		
		int ver = maxVersion(form.getFormKey());
		form.setVersion(ver + 1);
		
		form.setFormName(form.getFormName()+" V"+form.getVersion());
		form.setCreateBy(SessionUtil.getUserId(request));
		form.setUpdateBy(SessionUtil.getUserId(request));
		form.setCreateDate(new Date());
		form.setUpdateDate(new Date());
		form.setStatus(0);
		
		long newformId = this.insertKey(form);
		
		List<FrmElement> elmList = this.getElementList(formId);
		List<FrmElementRight> rightList = this.getRight(formId);
		
		List<FrmOption> optList = null;
		for(FrmElement elm : elmList){
			optList = this.getOptionListByFieldId(elm.getId());
			
			KeyHolder holder = new KeyHolder();
			elm.setFormId(Long.valueOf(newformId).intValue());
			sqlManager.insert(FrmElement.class, elm, holder);
			
			for(FrmOption opt : optList){
				opt.setFieldId(holder.getInt());
				opt.setFormId(Long.valueOf(newformId).intValue());
			}
			sqlManager.insertBatch(FrmOption.class, optList);
		}
		
		for(FrmElementRight right : rightList){
			right.setFormId(Long.valueOf(newformId).intValue());
		}
		sqlManager.insertBatch(FrmElementRight.class, rightList);
	}
	
	public void savePublish(FrmPublish entity) {
		sqlManager.executeUpdate(new SQLReady("update frm_form set status=1 where id=?", entity.getFormId()));
		sqlManager.insert(entity);
	}
	
	public void cancelPublish(int formId) {
		sqlManager.executeUpdate(new SQLReady("update frm_form set status=0 where id=?", formId));
		sqlManager.deleteById(FrmPublish.class, formId);
	}
	
	public String getFormHtml(int formId) {
		FrmPublish publish = sqlManager.single(FrmPublish.class, formId);
		if(publish != null){
			return publish.getFormHtml();
		}
		return null;
	}
}
