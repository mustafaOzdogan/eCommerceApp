package com.ecommerce.ecommerceApp.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;

import com.ecommerce.ecommerceApp.model.Product;

public class ProductRepositoryImpl implements ProductRepositoryCustom
{
	@Autowired
	private EntityManager entityManager;
	
	@Override
	public List<Product> findProductsByCategoryId(Long categoryId) 
	{
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
		
		Root<Product> product = criteriaQuery.from(Product.class);
		Predicate productPredicate = criteriaBuilder.equal(product.get("categoryId"), categoryId);
		criteriaQuery.where(productPredicate);
		
		TypedQuery<Product> query = entityManager.createQuery(criteriaQuery);
		return query.getResultList();	
	}
	
	@Override
	public Product findByName(String name) 
	{
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
		
		Root<Product> product = criteriaQuery.from(Product.class);
		Predicate productPredicate = criteriaBuilder.equal(product.get("name"), name);
		criteriaQuery.where(productPredicate);
		
		TypedQuery<Product> query = entityManager.createQuery(criteriaQuery);
		
		try
		{
			Product result = query.getSingleResult();
			return result;
		}
		catch(Exception e)
		{
			return null;
		}		
	}
}
