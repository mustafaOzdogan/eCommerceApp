package com.ecommerce.ecommerceApp.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.hateoas.RepresentationModel;

@Entity
public class Product extends RepresentationModel<Product> implements Serializable
{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long productId;
	
	private long categoryId;	
	private String name;
	private static final long serialVersionUID = 1L;
	
	public Product() 
	{
		super();
	}

	public long getProductId() 
	{
		return productId;
	}
	
	public void setProductId(long productId)
	{
		this.productId = productId;
	}
	
	public String getName() 
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	@Override
	public String toString() 
	{
		return "Product [productId=" + productId + ", name=" + name + ", categoryId=" + categoryId + "]";
	}
}
