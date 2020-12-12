package com.ecommerce.ecommerceApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ecommerce.ecommerceApp.model.Category;
import com.ecommerce.ecommerceApp.repository.CategoryRepository;

@Repository
public class CategoryServiceImpl implements CategoryService  
{
	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public Category getCategoryById(long id) 
	{
		return categoryRepository.findById(id).orElse(null);
	}

	@Override
	public List<Category> getAllCategories() 
	{
		return categoryRepository.findAll();
	}

	@Override
	public Category save(Category category) 
	{
		return categoryRepository.save(category);
	}
}
