package com.jyoffice.actflow.inter.response;

public class BaseResponse {

	private boolean success;
	
	private String errormsg;
	
	private String solution;

	private Object data;
	
	public BaseResponse(){}
	
	public BaseResponse(String errormsg,String solution){
		this.errormsg = errormsg;
		this.solution = solution;
	}
	
	public BaseResponse(boolean success){
		this.success = success;
	}
	
	public static BaseResponse errResponse(String errormsg,String solution){
		return new BaseResponse(errormsg,solution);
	}
	
	public static BaseResponse successResponse(){
		return new BaseResponse(true);
	}
	
	public boolean getSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getErrormsg() {
		return errormsg;
	}

	public void setErrormsg(String errormsg) {
		this.errormsg = errormsg;
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
}
