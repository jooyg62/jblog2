package com.cafe24.jblog.controller.api;

public class JSONResult {
	String result;
	Boolean data;
	String message;

	public static JSONResult success(Boolean data) {
		return new JSONResult("success", data, null);
	}
	
	public static JSONResult fail(String message) {
		return new JSONResult("fail", null, message);
	}
	
	private JSONResult (String result, Boolean data, String message) {
		this.result = result;
		this.data = data;
		this.message = message;
	}
	
	/**
	 * JSON 형태로 만들때 getter을 사용한다.
	 */
	public String getResult() {
		return result;
	}

	public Boolean getData() {
		return data;
	}

	public String getMessage() {
		return message;
	}
}
