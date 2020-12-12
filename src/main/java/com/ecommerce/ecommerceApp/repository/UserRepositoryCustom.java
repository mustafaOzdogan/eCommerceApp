package com.ecommerce.ecommerceApp.repository;

import com.ecommerce.ecommerceApp.model.User;

public interface UserRepositoryCustom 
{
	public User findByUsername(String username);
}
