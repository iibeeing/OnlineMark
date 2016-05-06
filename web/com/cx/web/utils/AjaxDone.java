package com.cx.web.utils;

public class AjaxDone {
private String statusCode="",message="",navTabId="",rel="",callbackType="",forwardUrl="",confirmMsg="";

public String getStatusCode() {
	return statusCode;
}

public void setStatusCode(String statusCode) {
	this.statusCode = statusCode;
}

public String getMessage() {
	return message;
}

public void setMessage(String message) {
	this.message = message;
}

public String getNavTabId() {
	return navTabId;
}

public void setNavTabId(String navTabId) {
	this.navTabId = navTabId;
}

public String getRel() {
	return rel;
}

public void setRel(String rel) {
	this.rel = rel;
}

public String getCallbackType() {
	return callbackType;
}

public void setCallbackType(String callbackType) {
	this.callbackType = callbackType;
}

public String getForwardUrl() {
	return forwardUrl;
}

public void setForwardUrl(String forwardUrl) {
	this.forwardUrl = forwardUrl;
}

public String getConfirmMsg() {
	return confirmMsg;
}

public void setConfirmMsg(String confirmMsg) {
	this.confirmMsg = confirmMsg;
}
}
