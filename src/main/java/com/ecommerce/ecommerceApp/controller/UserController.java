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
					
			response = new CommonResponse.Builder()
					.isSuccessful(true)
					.withData(user)
					.withCode(HttpStatus.CREATED.value())
					.build();
		}
		catch(Exception e)
		{			
			response = new CommonResponse.Builder()
					.isSuccessful(false)
					.withInternalMessage(e.getMessage())
					.withMessage("User cannot be created.")
					.withCode(HttpStatus.EXPECTATION_FAILED.value())
					.build();
		}
		
		return new ResponseEntity<CommonResponse>(response, HttpStatus.OK);
	}	
}
