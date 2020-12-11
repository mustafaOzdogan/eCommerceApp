package com.ecommerce.ecommerceApp.helper;

import com.ecommerce.ecommerceApp.model.CommonResponse;

public class CommonResponseHelper 
{
	public static CommonResponse getSuccessfulResponse(Object data)
	{
		CommonResponse response = new CommonResponse();
		response.setSuccess(Boolean.TRUE);
		response.setMessage("");
		response.setInternalMessage("");
		response.setData(data);
		
		return response;
	}
	
	public static CommonResponse getSuccessfulResponse()
	{
		CommonResponse answer = new CommonResponse();
		answer.setSuccess(Boolean.TRUE);
		answer.setMessage("");
		answer.setInternalMessage("");
		
		return answer;
	}
	
	public static CommonResponse getUnsuccessfulResponse(Exception e)
	{
		CommonResponse answer = new CommonResponse();
		answer.setSuccess(Boolean.FALSE);
		answer.setMessage("Please check your information and try again.");
		answer.setInternalMessage(e.getMessage());
		
		return answer;
	}
}
