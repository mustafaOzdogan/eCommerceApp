package com.ecommerce.ecommerceApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecommerceApp.model.Category;
import com.ecommerce.ecommerceApp.model.CommonResponse;
import com.ecommerce.ecommerceApp.model.Product;
import com.ecommerce.ecommerceApp.service.CategoryService;
import com.ecommerce.ecommerceApp.service.ProductService;

@RestController
public class ProductController 
{
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping(path = "products/{id}", method = RequestMethod.GET)
	public ResponseEntity<CommonResponse> getProduct(@PathVariable("id") String id)
	{
		CommonResponse response;
		
		try
		{
			Product product = productService.getProductById(Long.valueOf(id));	
			
			if(product == null)
				throw new NullPointerException("Product Not Found for Id:" + id);
						
			response = new CommonResponse.Builder()
					.isSuccessful(true)
					.withData(product)
					.withCode(HttpStatus.FOUND.value())
					.build();
		}
		catch(Exception e)
		{			
			response = new CommonResponse.Builder()
					.isSuccessful(false)
					.withCode(HttpStatus.NOT_FOUND.value())
					.withInternalMessage(e.getMessage())
					.build();
		}
		
		return new ResponseEntity<CommonResponse>(response, HttpStatus.OK);
	}
	
	@RequestMapping(path = "categories/{id}/products", method = RequestMethod.GET)
	public ResponseEntity<CommonResponse> getProductByCategory(@PathVariable("id") String id)
	{
		CommonResponse response;
		
		try
		{		
			Category category = categoryService.getCategoryById(Long.valueOf(id));
			if(category == null)
				throw new Exception("Category Not Found for Id:" + id); 
			
			List<Product> productList = productService.getProductsByCategoryId(Long.valueOf(id));
			
			if(productList.size() == 0)
				throw new Exception("Product Not Found for Category Id:" + id);
		
			response = new CommonResponse.Builder()
					.isSuccessful(true)
					.withData(productList)
					.withCode(HttpStatus.FOUND.value())
					.build();		
		}				
		catch(Exception e)
		{			
			response = new CommonResponse.Builder()
					.isSuccessful(false)
					.withInternalMessage(e.getMessage())
					.withMessage("Products could not found.")
					.withCode(HttpStatus.EXPECTATION_FAILED.value())
					.build();			
		}
		
		return new ResponseEntity<CommonResponse>(response, HttpStatus.OK);
	}
	
	@RequestMapping(path="products", method = RequestMethod.POST)
	public ResponseEntity<CommonResponse> addProduct(@RequestBody Product product) 
	{
		CommonResponse response;
		
		try 
		{
			// if category not exist throws
			Category category = categoryService.getCategoryById(product.getCategoryId());
			if(category == null)
				throw new Exception("Category Not Found for Id:" + product.getCategoryId()); 
			
			Product createdProduct = productService.save(product);
			
			response = new CommonResponse.Builder()
					.isSuccessful(true)
					.withData(createdProduct)
					.withCode(HttpStatus.CREATED.value())
					.build();
 		}
		catch(Exception e)
		{		
			response = new CommonResponse.Builder()
					.isSuccessful(false)
					.withInternalMessage(e.getMessage())
					.withMessage("Product cannot be added.")
					.withCode(HttpStatus.EXPECTATION_FAILED.value())
					.build();	
		}	
		
		return new ResponseEntity<CommonResponse>(response, HttpStatus.OK);
	}
	
	@RequestMapping(path = "products", method = RequestMethod.PUT)
	public ResponseEntity<CommonResponse> updateProduct(@RequestBody Product product)
	{
		CommonResponse response;
		
		try
		{
			Product updatedProduct = productService.save(product);
					
			response = new CommonResponse.Builder()
					.isSuccessful(true)
					.withData(updatedProduct)
					.withCode(HttpStatus.OK.value())
					.build();
		}
		catch(Exception e)
		{			
			response = new CommonResponse.Builder()
					.isSuccessful(false)
					.withInternalMessage(e.getMessage())
					.withMessage("Product cannot be updated.")
					.withCode(HttpStatus.EXPECTATION_FAILED.value())
					.build();	
		}
		
		return new ResponseEntity<CommonResponse>(response, HttpStatus.OK);
	}

	@RequestMapping(path = "products/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<CommonResponse> deleteProduct(@PathVariable("id") String id)
	{
		CommonResponse response;
		
		try
		{
			productService.deleteProductById(Long.valueOf(id));
			
			response = new CommonResponse.Builder()
					.isSuccessful(true)
					.withCode(HttpStatus.MOVED_PERMANENTLY.value())
					.build();
		}
		catch(Exception e)
		{			
			response = new CommonResponse.Builder()
					.isSuccessful(false)
					.withMessage("Product is not deleted with id:" + id)
					.withInternalMessage(e.getMessage())
					.withCode(HttpStatus.EXPECTATION_FAILED.value())
					.build();	
		}
		
		return new ResponseEntity<CommonResponse>(response, HttpStatus.OK);
	}

	
}
