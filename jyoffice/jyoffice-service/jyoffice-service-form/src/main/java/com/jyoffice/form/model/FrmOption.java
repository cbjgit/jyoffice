package com.jyoffice.form.model;
import java.io.Serializable;
import java.math.*;
import java.util.Date;
import java.sql.Timestamp;

/*
* 
* gen by beetlsql 2017-02-08
*/
public class FrmOption   implements Serializable{
	private Integer id ;
	private Integer fieldId ;
	private Integer formId ;
	private String optionText ;
	private String optionValue ;
	
	public FrmOption() {
	}
	
	public Integer getId(){
		return  id;
	}
	public void setId(Integer id ){
		this.id = id;
	}
	
	public Integer getFieldId(){
		return  fieldId;
	}
	public void setFieldId(Integer fieldId ){
		this.fieldId = fieldId;
	}
	
	public Integer getFormId(){
		return  formId;
	}
	public void setFormId(Integer formId ){
		this.formId = formId;
	}
	
	public String getOptionText(){
		return  optionText;
	}
	public void setOptionText(String optionText ){
		this.optionText = optionText;
	}
	
	public String getOptionValue(){
		return  optionValue;
	}
	public void setOptionValue(String optionValue ){
		this.optionValue = optionValue;
	}
	
	
	

}
