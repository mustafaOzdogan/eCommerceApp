package com.ecommerce.ecommerceApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecommerceApp.model.Category;
import com.ecommerce.ecommerceApp.model.CommonAnswerModel;
import com.ecommerce.ecommerceApp.repository.CategoryRepository;

@RestController
public class CategoryController 
{
	@Autowired
	private CategoryRepository categoryRepository;
	
	@RequestMapping(path="categories", method = RequestMethod.POST)
	public ResponseEntity<CommonAnswerModel> addCategory(@RequestBody Category category) 
	{
		CommonAnswerModel answer;
		
		try 
		{
			Category createdCategory = categoryRepository.save(category);
			
			answer = getSuccessfulAnswer(createdCategory);
			answer.setData(createdCategory);
			answer.setCode(HttpStatus.CREATED.value());		
 		}
		catch(Exception e)
		{
			answer = getUnsuccessfulAnswer(e);
			answer.setMessage("Category cannot be added.");
			answer.setCode(HttpStatus.EXPECTATION_FAILED.value());
		}	
		
		return new ResponseEntity<CommonAnswerModel>(answer, HttpStatus.OK);
	}
	
	private CommonAnswerModel getSuccessfulAnswer(Object data)
	{
		CommonAnswerModel answer = new CommonAnswerModel();
		answer.setSuccess(Boolean.TRUE);
		answer.setMessage("");
		answer.setInternalMessage("");
		answer.setData(data);
		
		return answer;
	}
	
	private CommonAnswerModel getUnsuccessfulAnswer(Exception e)
	{
		CommonAnswerModel answer = new CommonAnswerModel();
		answer.setSuccess(Boolean.FALSE);
		answer.setMessage("Please check your information and try again.");
		answer.setInternalMessage(e.getMessage());
		
		return answer;
	}
}
