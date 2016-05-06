package com.cx.web.utils;

import net.sf.json.JSONObject;


public class Json {
	public static String getJson(AjaxDone ajax) {
		JSONObject data = new JSONObject();
		data.put("statusCode", ajax.getStatusCode());
		data.put("message", ajax.getMessage());
		data.put("navTabId", ajax.getNavTabId());
		data.put("rel", ajax.getRel());
		data.put("callbackType", ajax.getCallbackType());
		data.put("forwardUrl", ajax.getForwardUrl());
		data.put("confirmMsg", ajax.getConfirmMsg());

		return data.toString();
	}
}
