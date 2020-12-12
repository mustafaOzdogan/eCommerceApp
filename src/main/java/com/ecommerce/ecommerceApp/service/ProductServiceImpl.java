package com.ecommerce.ecommerceApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ecommerce.ecommerceApp.model.Product;
import com.ecommerce.ecommerceApp.repository.ProductRepository;

@Repository
public class ProductServiceImpl implements ProductService
{
	@Autowired
	private ProductRepository productRepository;

	@Override
	public Product getProductById(Long id) 
	{
		return productRepository.findById(id).orElse(null);
	}

	@Override
	public Product getProductByName(String productName) 
	{
		return productRepository.findByName(productName);
	}

	@Override
	public List<Product> getProductsByCategoryId(Long categoryId) 
	{
		return productRepository.findProductsByCategoryId(categoryId);
	}
	
	@Override
	public List<Product> getAllProducts() 
	{
		return productRepository.findAll();
	}
	
	@Override
	public Product save(Product product) 
	{
		return productRepository.save(product);
	}

	@Override
	public void deleteProductById(Long id) 
	{
		productRepository.deleteById(id);
	}
}
