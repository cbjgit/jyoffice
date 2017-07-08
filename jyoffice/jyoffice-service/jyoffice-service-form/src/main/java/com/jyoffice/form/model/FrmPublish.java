package com.jyoffice.form.model;

import java.io.Serializable;
import java.util.Date;

import org.beetl.sql.core.annotatoin.AssignID;

/*
 * 
 * gen by beetlsql 2017-02-08
 */
public class FrmPublish implements Serializable {
	
	private Integer formId;
	private String formHtml;
	private Date publishDate;
	private Integer target;
	
	public FrmPublish() {
	}

	@AssignID
	public Integer getFormId() {
		return formId;
	}

	public void setFormId(Integer formId) {
		this.formId = formId;
	}

	public String getFormHtml() {
		return formHtml;
	}

	public void setFormHtml(String formHtml) {
		this.formHtml = formHtml;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public Integer getTarget() {
		return target;
	}

	public void setTarget(Integer target) {
		this.target = target;
	}
}
