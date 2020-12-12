package com.ecommerce.ecommerceApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecommerceApp.model.Category;
import com.ecommerce.ecommerceApp.model.CommonResponse;
import com.ecommerce.ecommerceApp.service.CategoryService;

@RestController
public class CategoryController 
{
	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping(path="categories", method = RequestMethod.POST)
	public ResponseEntity<CommonResponse> addCategory(@RequestBody Category category) 
	{
		CommonResponse response;
		
		try 
		{
			Category createdCategory = categoryService.save(category);
			
			response = new CommonResponse.Builder()
					.isSuccessful(true)
					.withData(createdCategory)
					.withCode(HttpStatus.CREATED.value())
					.build();
 		}
		catch(Exception e)
		{
			response = new CommonResponse.Builder()
					.isSuccessful(false)
					.withMessage("Category cannot be added.")
					.withInternalMessage(e.getMessage())
					.withCode(HttpStatus.EXPECTATION_FAILED.value())
					.build();
		}	
		
		return new ResponseEntity<CommonResponse>(response, HttpStatus.OK);
	}
}
