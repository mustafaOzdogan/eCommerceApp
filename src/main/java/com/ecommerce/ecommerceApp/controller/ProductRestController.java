package com.ecommerce.ecommerceApp.controller;

import java.util.List;
import java.util.Optional;

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
import com.ecommerce.ecommerceApp.repository.CategoryRepository;
import com.ecommerce.ecommerceApp.repository.ProductRepository;

@RestController
public class ProductRestController 
{
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@RequestMapping(path = "products/{id}", method = RequestMethod.GET)
	public ResponseEntity<CommonResponse> getProduct(@PathVariable("id") String id)
	{
		CommonResponse response;
		
		try
		{
			Optional<Product> product = productRepository.findById(Long.valueOf(id));			
			response = getSuccessfulResponse(product.get());
			response.setCode(HttpStatus.FOUND.value());
		}
		catch(Exception e)
		{
			response = getUnsuccessfulResponse(e);
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
			Optional<Category> category = categoryRepository.findById(Long.valueOf(id)); 
			
			if(!category.isPresent())
				throw new Exception("Category Not Found for id:" + id);
					
			long categoryId = category.get().getCategoryId();
			List<Product> productList = productRepository.findByCategoryId(categoryId);
			
			if(productList.size() == 0)
				throw new Exception("Product Not Found for Category Id:" + categoryId);
			
			response = getSuccessfulResponse();
			response.setData(productList);
			response.setCode(HttpStatus.FOUND.value());			
		}				
		catch(Exception e)
		{
			response = getUnsuccessfulResponse(e);	
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
			Product createdProduct = productRepository.save(product);
			
			response = getSuccessfulResponse(createdProduct);
			response.setData(createdProduct);
			response.setCode(HttpStatus.CREATED.value());		
 		}
		catch(Exception e)
		{
			response = getUnsuccessfulResponse(e);
			response.setMessage("Customer cannot be added.");
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
			
			response = getSuccessfulResponse(updatedProduct);
			response.setData(updatedProduct);
			response.setCode(HttpStatus.OK.value());				
		}
		catch(Exception e)
		{
			response = getUnsuccessfulResponse(e);
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
			
			response = getSuccessfulResponse();
			response.setCode(HttpStatus.MOVED_PERMANENTLY.value());	
		}
		catch(Exception e)
		{
			response = getUnsuccessfulResponse(e);
			response.setMessage("Product is not deleted.");
			response.setInternalMessage("Product is not deleted with id:" + id);
			response.setCode(HttpStatus.EXPECTATION_FAILED.value());
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
		
		return new ResponseEntity<CommonResponse>(response, HttpStatus.OK);
	}

	private CommonResponse getSuccessfulResponse(List<Object> data)
	{
		CommonResponse response = new CommonResponse();
		response.setSuccess(Boolean.TRUE);
		response.setMessage("");
		response.setInternalMessage("");
		response.setData(data);
		
		return response;
	}
	
	private CommonResponse getSuccessfulResponse(Object data)
	{
		CommonResponse response = new CommonResponse();
		response.setSuccess(Boolean.TRUE);
		response.setMessage("");
		response.setInternalMessage("");
		response.setData(data);
		
		return response;
	}
	
	private CommonResponse getSuccessfulResponse()
	{
		CommonResponse answer = new CommonResponse();
		answer.setSuccess(Boolean.TRUE);
		answer.setMessage("");
		answer.setInternalMessage("");
		
		return answer;
	}
	
	private CommonResponse getUnsuccessfulResponse(Exception e)
	{
		CommonResponse answer = new CommonResponse();
		answer.setSuccess(Boolean.FALSE);
		answer.setMessage("Please check your information and try again.");
		answer.setInternalMessage(e.getMessage());
		
		return answer;
	}
}
