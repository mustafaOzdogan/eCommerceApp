package com.ecommerce.ecommerceApp.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecommerceApp.exception.ProductNotFoundException;
import com.ecommerce.ecommerceApp.model.Product;
import com.ecommerce.ecommerceApp.repository.ProductRepository;

@RestController
public class ProductController 
{
	@Autowired
	private ProductRepository productRepository;
	
	// GET Product 
	@RequestMapping(path = "products/{id}", method = RequestMethod.GET)
	public ResponseEntity<Product> getProduct(@PathVariable("id") String id) throws ProductNotFoundException
	{
		try
		{
			Optional<Product> product = productRepository.findById(Long.valueOf(id));
			return new ResponseEntity<Product>(product.get(), HttpStatus.FOUND);
		}
		catch(Exception e)
		{
			throw new ProductNotFoundException("Product Not Found for id:" + id);
		}
	}
	
	// POST Product 
	@RequestMapping(path="products", method = RequestMethod.POST)
	public ResponseEntity<Void> addProduct(@RequestBody Product product) 
	{
		try 
		{
			Product createdProduct = productRepository.save(product);
			HttpHeaders headers = new HttpHeaders();
			headers.add("id", Long.toString(createdProduct.getProductId()));
			return new ResponseEntity<>(headers, HttpStatus.CREATED);
 		}
		catch(Exception e)
		{
			return new ResponseEntity<Void>(HttpStatus.EXPECTATION_FAILED);
		}		
	}

	// UPDATE Product
	@RequestMapping(path = "products", method = RequestMethod.PUT)
	public ResponseEntity<Product> updateProduct(@RequestBody Product product)
	{
		try
		{
			Product updatedProduct = productRepository.save(product);
			return new ResponseEntity<Product>(updatedProduct, HttpStatus.OK);
		}
		catch(Exception e)
		{
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}

	// DELETE Product
	@RequestMapping(path = "products/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteProduct(@PathVariable("id") String id)
	{
		try
		{
			productRepository.deleteById(Long.valueOf(id));
			return new ResponseEntity<>(HttpStatus.MOVED_PERMANENTLY);
		}
		catch(Exception e)
		{
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}

}
