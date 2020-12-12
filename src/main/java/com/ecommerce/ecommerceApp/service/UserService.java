package com.ecommerce.ecommerceApp.service;

import com.ecommerce.ecommerceApp.model.User;

public interface UserService 
{	
	public User getUserByUsername(String username);
	public User save(User user);
}
