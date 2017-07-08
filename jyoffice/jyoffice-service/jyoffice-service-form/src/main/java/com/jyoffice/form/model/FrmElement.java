package com.jyoffice.form.model;

import java.io.Serializable;

/*
 * 
 * gen by beetlsql 2017-02-08
 */
public class FrmElement implements Serializable {
	private Integer id;
	private Integer columnNumber;
	private Integer dataSource;
	private Integer flowVar = 0;
	private Integer formId;
	private Integer required = 0;
	private Integer seq;
	private String dataType;
	private String defaultValue;
	private String fieldDescption;
	private String fieldName;
	private String fieldTitle;
	private String fieldType;
	private String otherParam;

	public FrmElement() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getColumnNumber() {
		return columnNumber;
	}

	public void setColumnNumber(Integer columnNumber) {
		this.columnNumber = columnNumber;
	}

	public Integer getDataSource() {
		return dataSource;
	}

	public void setDataSource(Integer dataSource) {
		this.dataSource = dataSource;
	}

	public Integer getFlowVar() {
		return flowVar;
	}

	public void setFlowVar(Integer flowVar) {
		this.flowVar = flowVar;
	}

	public Integer getFormId() {
		return formId;
	}

	public void setFormId(Integer formId) {
		this.formId = formId;
	}

	public Integer getRequired() {
		return required;
	}

	public void setRequired(Integer required) {
		this.required = required;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getFieldDescption() {
		return fieldDescption;
	}

	public void setFieldDescption(String fieldDescption) {
		this.fieldDescption = fieldDescption;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldTitle() {
		return fieldTitle;
	}

	public void setFieldTitle(String fieldTitle) {
		this.fieldTitle = fieldTitle;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public String getOtherParam() {
		return otherParam;
	}

	public void setOtherParam(String otherParam) {
		this.otherParam = otherParam;
	}

}
