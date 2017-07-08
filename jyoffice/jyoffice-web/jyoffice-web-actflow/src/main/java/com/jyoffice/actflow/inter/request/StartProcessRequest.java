package com.jyoffice.actflow.inter.request;

public class StartProcessRequest extends BaseRequest{

	private String processKey;
	
	private String busKey;
	
	private String appUserId;
	
	private String appUserName;
	
	private String title;

	public String getProcessKey() {
		return processKey;
	}

	public void setProcessKey(String processKey) {
		this.processKey = processKey;
	}

	public String getBusKey() {
		return busKey;
	}

	public void setBusKey(String busKey) {
		this.busKey = busKey;
	}

	public String getAppUserId() {
		return appUserId;
	}

	public void setAppUserId(String appUserId) {
		this.appUserId = appUserId;
	}

	public String getAppUserName() {
		return appUserName;
	}

	public void setAppUserName(String appUserName) {
		this.appUserName = appUserName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "StartProcessRequest [processKey=" + processKey + ", busKey=" + busKey + ", appUserId="
				+ appUserId + ", appUserName=" + appUserName + ", title=" + title + "]";
	}
	
}
