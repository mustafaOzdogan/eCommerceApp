package com.ecommerce.ecommerceApp.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.ecommerce.ecommerceApp.model.User;
import com.ecommerce.ecommerceApp.repository.UserRepository;

@Component
public class UserDetailsServiceImpl implements UserDetailsService, UserService
{
	@Autowired
	private UserRepository userRepository;  

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        User user = userRepository.findByUsername(username);
        if(user == null)
        {
            throw new UsernameNotFoundException(username);
        }
        
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), Collections.emptyList());
    }

    @Override
	public User getUserByUsername(String username) 
	{
		return userRepository.findByUsername(username);
	}
    
	@Override
	public User save(User user)
    {
    	return userRepository.save(user);
    }
}
