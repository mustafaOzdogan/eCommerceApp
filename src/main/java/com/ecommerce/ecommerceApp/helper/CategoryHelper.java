package com.ecommerce.ecommerceApp.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecommerce.ecommerceApp.model.Category;
import com.ecommerce.ecommerceApp.repository.CategoryRepository;

@Component
public class CategoryHelper 
{
	@Autowired
	CategoryRepository categoryRepository;
	
	public Category isCategoryExist(long categoryId)
	{
		try
		{
			Category category = categoryRepository.findById(categoryId).orElse(null); 
		
			if(category == null)
				throw new Exception();
		
			return category;
		}
		catch(Exception e)
		{
			throw new NullPointerException("Category Not Found for Id:" + categoryId); 
		}	
	}

}
