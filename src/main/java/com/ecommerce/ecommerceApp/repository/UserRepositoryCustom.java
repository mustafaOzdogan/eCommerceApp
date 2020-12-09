package com.ecommerce.ecommerceApp.repository;

import com.ecommerce.ecommerceApp.model.User;

public interface UserRepositoryCustom 
{
	User findByUsername(String username);
}
