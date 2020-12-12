package com.ecommerce.ecommerceApp.repository;

import java.util.List;

import com.ecommerce.ecommerceApp.model.Product;

public interface ProductRepositoryCustom 
{
	public Product findByName(String productName);
	public List<Product> findProductsByCategoryId(Long categoryId);
}
