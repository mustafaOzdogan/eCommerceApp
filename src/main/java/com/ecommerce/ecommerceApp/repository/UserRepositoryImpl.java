package com.ecommerce.ecommerceApp.repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.ecommerce.ecommerceApp.model.User;

@Repository
public class UserRepositoryImpl implements UserRepositoryCustom
{
	@Autowired
	EntityManager entityManager;
	
	@Override
	public User findByUsername(String username) 
	{
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
		
		Root<User> product = criteriaQuery.from(User.class);
		Predicate productPredicate = criteriaBuilder.equal(product.get("username"), username);
		criteriaQuery.where(productPredicate);
		
		TypedQuery<User> query = entityManager.createQuery(criteriaQuery);
		
		try
		{
			User result = query.getSingleResult();
			return result;
		}
		catch(Exception e)
		{
			return null;
		}
	}
}
