package com.ecommerce.ecommerceApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecommerceApp.helper.CommonResponseHelper;
import com.ecommerce.ecommerceApp.model.Category;
import com.ecommerce.ecommerceApp.model.CommonResponse;
import com.ecommerce.ecommerceApp.repository.CategoryRepository;

@RestController
public class CategoryController 
{
	@Autowired
	private CategoryRepository categoryRepository;
	
	@RequestMapping(path="categories", method = RequestMethod.POST)
	public ResponseEntity<CommonResponse> addCategory(@RequestBody Category category) 
	{
		CommonResponse answer;
		
		try 
		{
			Category createdCategory = categoryRepository.save(category);
			
			//TODO Builder pattern 
			answer = CommonResponseHelper.getSuccessfulResponse(createdCategory);
			answer.setData(createdCategory);
			answer.setCode(HttpStatus.CREATED.value());		
 		}
		catch(Exception e)
		{
			answer = CommonResponseHelper.getUnsuccessfulResponse(e);
			answer.setMessage("Category cannot be added.");
			answer.setCode(HttpStatus.EXPECTATION_FAILED.value());
		}	
		
		return new ResponseEntity<CommonResponse>(answer, HttpStatus.OK);
	}
}
