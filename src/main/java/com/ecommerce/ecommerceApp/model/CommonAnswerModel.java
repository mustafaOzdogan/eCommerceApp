package com.ecommerce.ecommerceApp.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonAnswerModel 
{	
	private Boolean success;
	private int code;
	private String message;
	private String internalMessage;
	private Object data;
	
	public CommonAnswerModel()
	{
	
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getInternalMessage() {
		return internalMessage;
	}

	public void setInternalMessage(String internalMessage) {
		this.internalMessage = internalMessage;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	
}
