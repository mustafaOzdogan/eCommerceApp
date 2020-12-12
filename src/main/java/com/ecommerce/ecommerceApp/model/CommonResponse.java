package com.ecommerce.ecommerceApp.model;

public class CommonResponse 
{	
	private Boolean success;
	private int code;
	private String message;
	private String internalMessage;
	private Object data;
	
	private CommonResponse() {}
		
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

	public static class Builder
	{
		private Boolean success;
		private int code;
		private String message;
		private String internalMessage;
		private Object data;
		
		public Builder isSuccessful(Boolean success) {
			this.success = success;
			return this;
		}

		public Builder withCode(int code) {
			this.code = code;
			return this;
		}

		public Builder withMessage(String message) {
			this.message = message;
			return this;
		}

		public Builder withInternalMessage(String internalMessage) {
			this.internalMessage = internalMessage;
			return this;
		}

		public Builder withData(Object data) {
			this.data = data;
			return this;
		}
		
		public CommonResponse build()
		{
			CommonResponse response = new CommonResponse();
			response.code = this.code;
			response.setSuccess(this.success);
			response.internalMessage = this.internalMessage;
			response.message = this.message;
			response.data = this.data;
			return response;
		}
	}	
}
