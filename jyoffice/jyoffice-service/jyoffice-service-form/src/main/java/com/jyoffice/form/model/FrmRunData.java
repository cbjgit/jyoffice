package com.jyoffice.form.model;
import java.io.Serializable;
import java.math.*;
import java.util.Date;
import java.sql.Timestamp;

/*
* 
* gen by beetlsql 2017-02-08
*/
public class FrmRunData   implements Serializable{
	private Integer id ;
	private Integer integerValue ;
	private String bigValue ;
	private BigDecimal doubleValue ;
	private String fieldName ;
	private String frmInstanceId ;
	private String stringValue ;
	private Date dateValue ;
	
	public FrmRunData() {
	}
	
	public Integer getId(){
		return  id;
	}
	public void setId(Integer id ){
		this.id = id;
	}
	
	public Integer getIntegerValue(){
		return  integerValue;
	}
	public void setIntegerValue(Integer integerValue ){
		this.integerValue = integerValue;
	}
	
	public String getBigValue(){
		return  bigValue;
	}
	public void setBigValue(String bigValue ){
		this.bigValue = bigValue;
	}
	
	public BigDecimal getDoubleValue(){
		return  doubleValue;
	}
	public void setDoubleValue(BigDecimal doubleValue ){
		this.doubleValue = doubleValue;
	}
	
	public String getFieldName(){
		return  fieldName;
	}
	public void setFieldName(String fieldName ){
		this.fieldName = fieldName;
	}
	
	public String getFrmInstanceId(){
		return  frmInstanceId;
	}
	public void setFrmInstanceId(String frmInstanceId ){
		this.frmInstanceId = frmInstanceId;
	}
	
	public String getStringValue(){
		return  stringValue;
	}
	public void setStringValue(String stringValue ){
		this.stringValue = stringValue;
	}
	
	public Date getDateValue(){
		return  dateValue;
	}
	public void setDateValue(Date dateValue ){
		this.dateValue = dateValue;
	}
	
	
	

}
