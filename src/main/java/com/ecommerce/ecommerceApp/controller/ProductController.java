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
import com.ecommerce.ecommerceApp.model.CommonAnswerModel;
import com.ecommerce.ecommerceApp.model.Product;
import com.ecommerce.ecommerceApp.repository.CategoryRepository;
import com.ecommerce.ecommerceApp.repository.ProductRepository;

@RestController
public class ProductController 
{
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@RequestMapping(path = "products/{id}", method = RequestMethod.GET)
	public ResponseEntity<CommonAnswerModel> getProduct(@PathVariable("id") String id)
	{
		CommonAnswerModel answer;
		
		try
		{
			Optional<Product> product = productRepository.findById(Long.valueOf(id));			
			answer = getSuccessfulAnswer(product.get());
			answer.setCode(HttpStatus.FOUND.value());
		}
		catch(Exception e)
		{
			answer = getUnsuccessfulAnswer(e);
			answer.setCode(HttpStatus.NOT_FOUND.value());
		}
		
		return new ResponseEntity<CommonAnswerModel>(answer, HttpStatus.OK);
	}
	
	@RequestMapping(path = "categories/{id}/products", method = RequestMethod.GET)
	public ResponseEntity<CommonAnswerModel> getProductByCategory(@PathVariable("id") String id)
	{
		CommonAnswerModel answer;
		
		try
		{
			Optional<Category> category = categoryRepository.findById(Long.valueOf(id)); 
			
			if(!category.isPresent())
				throw new Exception("Category Not Found for id:" + id);
					
			long categoryId = category.get().getCategoryId();
			List<Product> productList = productRepository.findByCategoryId(categoryId);
			
			if(productList.size() == 0)
				throw new Exception("Product Not Found for Category Id:" + categoryId);
			
			answer = getSuccessfulAnswer();
			answer.setData(productList);
			answer.setCode(HttpStatus.FOUND.value());			
		}				
		catch(Exception e)
		{
			answer = getUnsuccessfulAnswer(e);	
			answer.setMessage("Products could not found.");
			answer.setInternalMessage(e.getMessage());
			answer.setCode(HttpStatus.EXPECTATION_FAILED.value()); 
		}
		
		return new ResponseEntity<CommonAnswerModel>(answer, HttpStatus.OK);
	}
	
	@RequestMapping(path="products", method = RequestMethod.POST)
	public ResponseEntity<CommonAnswerModel> addProduct(@RequestBody Product product) 
	{
		CommonAnswerModel answer;
		
		try 
		{
			Product createdProduct = productRepository.save(product);
			
			answer = getSuccessfulAnswer(createdProduct);
			answer.setData(createdProduct);
			answer.setCode(HttpStatus.CREATED.value());		
 		}
		catch(Exception e)
		{
			answer = getUnsuccessfulAnswer(e);
			answer.setMessage("Customer cannot be added.");
			answer.setCode(HttpStatus.EXPECTATION_FAILED.value());
		}	
		
		return new ResponseEntity<CommonAnswerModel>(answer, HttpStatus.OK);
	}
	

	@RequestMapping(path = "products", method = RequestMethod.PUT)
	public ResponseEntity<CommonAnswerModel> updateProduct(@RequestBody Product product)
	{
		CommonAnswerModel answer;
		
		try
		{
			Product updatedProduct = productRepository.save(product);
			
			answer = getSuccessfulAnswer(updatedProduct);
			answer.setData(updatedProduct);
			answer.setCode(HttpStatus.OK.value());				
		}
		catch(Exception e)
		{
			answer = getUnsuccessfulAnswer(e);
			answer.setMessage("Customer cannot be updated.");
			answer.setCode(HttpStatus.EXPECTATION_FAILED.value());
		}
		
		return new ResponseEntity<CommonAnswerModel>(answer, HttpStatus.OK);
	}
	

	@RequestMapping(path = "products/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<CommonAnswerModel> deleteProduct(@PathVariable("id") String id)
	{
		CommonAnswerModel answer;
		
		try
		{
			productRepository.deleteById(Long.valueOf(id));
			
			answer = getSuccessfulAnswer();
			answer.setCode(HttpStatus.MOVED_PERMANENTLY.value());	
		}
		catch(Exception e)
		{
			answer = getUnsuccessfulAnswer(e);
			answer.setMessage("Product is not deleted.");
			answer.setInternalMessage("Product is not deleted with id:" + id);
			answer.setCode(HttpStatus.EXPECTATION_FAILED.value());
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
		
		return new ResponseEntity<CommonAnswerModel>(answer, HttpStatus.OK);
	}

	
	private CommonAnswerModel getSuccessfulAnswer(List<Object> data)
	{
		CommonAnswerModel answer = new CommonAnswerModel();
		answer.setSuccess(Boolean.TRUE);
		answer.setMessage("");
		answer.setInternalMessage("");
		answer.setData(data);
		
		return answer;
	}
	
	private CommonAnswerModel getSuccessfulAnswer(Object data)
	{
		CommonAnswerModel answer = new CommonAnswerModel();
		answer.setSuccess(Boolean.TRUE);
		answer.setMessage("");
		answer.setInternalMessage("");
		answer.setData(data);
		
		return answer;
	}
	
	private CommonAnswerModel getSuccessfulAnswer()
	{
		CommonAnswerModel answer = new CommonAnswerModel();
		answer.setSuccess(Boolean.TRUE);
		answer.setMessage("");
		answer.setInternalMessage("");
		
		return answer;
	}
	
	private CommonAnswerModel getUnsuccessfulAnswer(Exception e)
	{
		CommonAnswerModel answer = new CommonAnswerModel();
		answer.setSuccess(Boolean.FALSE);
		answer.setMessage("Please check your information and try again.");
		answer.setInternalMessage(e.getMessage());
		
		return answer;
	}
}
