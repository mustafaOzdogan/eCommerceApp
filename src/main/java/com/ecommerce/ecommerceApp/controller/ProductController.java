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

import com.ecommerce.ecommerceApp.helper.CategoryHelper;
import com.ecommerce.ecommerceApp.helper.CommonResponseHelper;
import com.ecommerce.ecommerceApp.model.Category;
import com.ecommerce.ecommerceApp.model.CommonResponse;
import com.ecommerce.ecommerceApp.model.Product;
import com.ecommerce.ecommerceApp.repository.CategoryRepository;
import com.ecommerce.ecommerceApp.repository.ProductRepository;

@RestController
public class ProductController 
{
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryHelper categoryHelper;
	
	@RequestMapping(path = "products/{id}", method = RequestMethod.GET)
	public ResponseEntity<CommonResponse> getProduct(@PathVariable("id") String id)
	{
		CommonResponse response;
		
		try
		{
			Product product = productRepository.findById(Long.valueOf(id)).orElse(null);	
			
			if(product == null)
				throw new NullPointerException("Product Not Found for Id:" + id);
			
			response = CommonResponseHelper.getSuccessfulResponse(product);
			response.setCode(HttpStatus.FOUND.value());
		}
		catch(Exception e)
		{
			response = CommonResponseHelper.getUnsuccessfulResponse(e);
			response.setCode(HttpStatus.NOT_FOUND.value());
		}
		
		return new ResponseEntity<CommonResponse>(response, HttpStatus.OK);
	}
	
	@RequestMapping(path = "categories/{id}/products", method = RequestMethod.GET)
	public ResponseEntity<CommonResponse> getProductByCategory(@PathVariable("id") String id)
	{
		CommonResponse response;
		
		try
		{		
			Category category = categoryHelper.isCategoryExist(Long.valueOf(id));
			long categoryId = category.getCategoryId();
			List<Product> productList = productRepository.findByCategoryId(categoryId);
			
			if(productList.size() == 0)
				throw new Exception("Product Not Found for Category Id:" + categoryId);
			
			response = CommonResponseHelper.getSuccessfulResponse();
			response.setData(productList);
			response.setCode(HttpStatus.FOUND.value());			
		}				
		catch(Exception e)
		{
			response = CommonResponseHelper.getUnsuccessfulResponse(e);	
			response.setMessage("Products could not found.");
			response.setInternalMessage(e.getMessage());
			response.setCode(HttpStatus.EXPECTATION_FAILED.value()); 
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
			long categoryId = product.getCategoryId();
			categoryHelper.isCategoryExist(categoryId);
			
			Product createdProduct = productRepository.save(product);
			
			response = CommonResponseHelper.getSuccessfulResponse(createdProduct);
			response.setCode(HttpStatus.CREATED.value());		
 		}
		catch(Exception e)
		{
			response = CommonResponseHelper.getUnsuccessfulResponse(e);
			response.setMessage("Product cannot be added.");
			response.setCode(HttpStatus.EXPECTATION_FAILED.value());
		}	
		
		return new ResponseEntity<CommonResponse>(response, HttpStatus.OK);
	}
	
	@RequestMapping(path = "products", method = RequestMethod.PUT)
	public ResponseEntity<CommonResponse> updateProduct(@RequestBody Product product)
	{
		CommonResponse response;
		
		try
		{
			Product updatedProduct = productRepository.save(product);
			
			response = CommonResponseHelper.getSuccessfulResponse(updatedProduct);
			response.setData(updatedProduct);
			response.setCode(HttpStatus.OK.value());				
		}
		catch(Exception e)
		{
			response = CommonResponseHelper.getUnsuccessfulResponse(e);
			response.setMessage("Customer cannot be updated.");
			response.setCode(HttpStatus.EXPECTATION_FAILED.value());
		}
		
		return new ResponseEntity<CommonResponse>(response, HttpStatus.OK);
	}

	@RequestMapping(path = "products/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<CommonResponse> deleteProduct(@PathVariable("id") String id)
	{
		CommonResponse response;
		
		try
		{
			productRepository.deleteById(Long.valueOf(id));
			
			response = CommonResponseHelper.getSuccessfulResponse();
			response.setCode(HttpStatus.MOVED_PERMANENTLY.value());	
		}
		catch(Exception e)
		{
			response = CommonResponseHelper.getUnsuccessfulResponse(e);
			response.setMessage("Product is not deleted.");
			response.setInternalMessage("Product is not deleted with id:" + id);
			response.setCode(HttpStatus.EXPECTATION_FAILED.value());
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
		
		return new ResponseEntity<CommonResponse>(response, HttpStatus.OK);
	}

	
}
