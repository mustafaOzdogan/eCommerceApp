package com.ecommerce.ecommerceApp.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.hateoas.RepresentationModel;

@Entity
public class Product extends RepresentationModel<Product> implements Serializable
{
	
	@Id
	@GeneratedValue
	private long productId;
	private String name;
	private String category;
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
	
	public String getCategory() 
	{
		return category;
	}
	
	public void setCategory(String category) 
	{
		this.category = category;
	}
	
	@Override
	public String toString() 
	{
		return "Product [productId=" + productId + ", name=" + name + ", category=" + category + "]";
	}
}
