package com.ecommerce.ecommerceApp.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.hateoas.RepresentationModel;

@Entity
public class Category extends RepresentationModel<Product> implements Serializable
{
	private static final long serialVersionUID = 4465454647924670319L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long categoryId;	
	private String name;
		
	public Category() { super(); }
	
	public Category(String name) { this.name = name; }

	public long getCategoryId() { return categoryId; }
	
	public void setCategoryId(long categoryId) { this.categoryId = categoryId; }
	
	public String getName() { return name; }
	
	public void setName(String name) { this.name = name; }
}
