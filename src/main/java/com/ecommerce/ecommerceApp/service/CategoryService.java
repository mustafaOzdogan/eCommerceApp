package com.ecommerce.ecommerceApp.service;

import java.util.List;

import com.ecommerce.ecommerceApp.model.Category;


public interface CategoryService 
{
	public Category getCategoryById(long id);
	public List<Category> getAllCategories();
	public Category save(Category category);
}
