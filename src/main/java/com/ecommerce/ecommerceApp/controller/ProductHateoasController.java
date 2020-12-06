package com.ecommerce.ecommerceApp.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecommerceApp.exception.ProductNotFoundException;
import com.ecommerce.ecommerceApp.model.Product;
import com.ecommerce.ecommerceApp.repository.ProductRepository;

@RestController
@RequestMapping("v2") 
public class ProductHateoasController 
{
	
	@Autowired
	ProductRepository productRepository;
	
	// GET Product
	@RequestMapping(path = "products/{id}", method = RequestMethod.GET)
	public EntityModel<Product> getUser(@PathVariable("id") String id) throws Exception
	{
		try
		{		
			Optional<Product> product = productRepository.findById(Long.valueOf(id));
			
			Link selfLink = linkTo(ProductHateoasController.class)
							.slash(product.get().getProductId())
							.withSelfRel();
			
			
			Link deleteLink = linkTo(methodOn(ProductController.class)
					  		  .deleteProduct(product.get().getProductId() + ""))
					  		  .withRel("delete");
			 
			
			return EntityModel.of(product.get(), selfLink, deleteLink);
		}
		catch(Exception e)
		{
			throw new ProductNotFoundException("Product Not Found for id:" + id);
		}
	}
	
	// TODO: ADD PRODUCT
	
	// TODO: DEL PRODUCT
	
	// TODO: UPD PRODUCT
}
