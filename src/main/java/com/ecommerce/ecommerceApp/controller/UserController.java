package com.ecommerce.ecommerceApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecommerceApp.model.CommonResponse;
import com.ecommerce.ecommerceApp.model.User;
import com.ecommerce.ecommerceApp.service.UserService;

@RestController
public class UserController 
{
	@Autowired
	private UserService userService;		
	private BCryptPasswordEncoder bCryptPasswordEncoder;;
	
	public UserController(BCryptPasswordEncoder bCryptPasswordEncoder) 
	{
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
		
	@RequestMapping(path="signup", method = RequestMethod.POST)
	public ResponseEntity<CommonResponse> createUser(@RequestBody User user)
	{
		CommonResponse response;
		
		try 
		{
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			userService.save(user);
			
			response = getSuccessfulResponse(user);
			response.setCode(HttpStatus.CREATED.value());	
		}
		catch(Exception e)
		{
			//TODO COMMON METHOD HELPER
			response = getUnsuccessfulResponse(e);
			response.setMessage("User cannot be created.");
			response.setCode(HttpStatus.EXPECTATION_FAILED.value());
		}
		
		return new ResponseEntity<CommonResponse>(response, HttpStatus.OK);
	}
	
	private CommonResponse getSuccessfulResponse(Object data)
	{
		CommonResponse response = new CommonResponse();
		response.setSuccess(Boolean.TRUE);
		response.setMessage("");
		response.setInternalMessage("");
		response.setData(data);
		
		return response;
	}
	
	private CommonResponse getUnsuccessfulResponse(Exception e)
	{
		CommonResponse answer = new CommonResponse();
		answer.setSuccess(Boolean.FALSE);
		answer.setMessage("Please check your information and try again.");
		answer.setInternalMessage(e.getMessage());
		
		return answer;
	}
}
