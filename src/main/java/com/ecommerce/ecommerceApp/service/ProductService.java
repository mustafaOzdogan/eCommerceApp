package com.ecommerce.ecommerceApp.service;

import java.util.List;

import com.ecommerce.ecommerceApp.model.Product;

public interface ProductService 
{
	public Product getProductById(Long id);
	public Product getProductByName(String productName);
	public List<Product> getProductsByCategoryId(Long categoryId);	
	public List<Product> getAllProducts();	
	public void deleteProductById(Long id);
	public Product save(Product product);	
}
