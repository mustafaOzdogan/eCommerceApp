package com.ecommerce.ecommerceApp.repository;

import java.util.List;

import com.ecommerce.ecommerceApp.model.Product;

public interface ProductRepositoryCustom 
{
	List<Product> findByCategoryId(Long categoryId);
}
