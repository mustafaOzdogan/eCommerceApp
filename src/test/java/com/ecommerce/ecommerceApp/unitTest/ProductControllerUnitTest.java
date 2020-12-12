package com.ecommerce.ecommerceApp.unitTest;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.ecommerce.ecommerceApp.controller.ProductController;
import com.ecommerce.ecommerceApp.helper.JsonUtil;
import com.ecommerce.ecommerceApp.model.Category;
import com.ecommerce.ecommerceApp.model.Product;
import com.ecommerce.ecommerceApp.service.CategoryService;
import com.ecommerce.ecommerceApp.service.ProductService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ProductController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
public class ProductControllerUnitTest
{
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private ProductService productService;
	
	@MockBean
	private CategoryService categoryService;
		
	@Before
	public void setUp() throws Exception {}
		
	@Test
	public void givenProductId_whenGetProduct_thenReturnJson() throws IOException, Exception
	{
		Product laptop = new Product("laptop", 1);
		
		given(productService.getProductById(Mockito.anyLong())).willReturn(laptop);
		
		mvc.perform(get("/products/{id}", laptop.getProductId()).contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(laptop)))
		   .andExpect(status().isOk())
		   .andExpect(jsonPath("$.data.name", is(laptop.getName())))
		   .andExpect(jsonPath("$.data.productId").value(laptop.getProductId()))
		   .andExpect(jsonPath("$.data.categoryId").value(laptop.getCategoryId()));
		
		verify(productService, VerificationModeFactory.times(1)).getProductById(Mockito.anyLong());
		reset(productService);
	}
	
	@Test
	public void givenCategoryId_whenGetProducts_thenReturnJsonArray() throws IOException, Exception
	{
		Category electronics = new Category("electronics");
		Product laptop 	  	 = new Product("laptop", electronics.getCategoryId());
		Product tv      	 = new Product("tv", electronics.getCategoryId());
		
		List<Product> allProducts = Arrays.asList(laptop, tv);
		
		given(categoryService.getCategoryById(Mockito.anyLong())).willReturn(electronics);
		given(productService.getProductsByCategoryId(Mockito.anyLong())).willReturn(allProducts);
		
		mvc.perform(get("/categories/{id}/products", Long.toString(electronics.getCategoryId())).contentType(MediaType.APPLICATION_JSON))
		   .andExpect(status().isOk())
		   .andExpect(jsonPath("$.data.length()").value(2))
		   .andExpect(jsonPath("$.data[0].name", is(laptop.getName()))) 
		   .andExpect(jsonPath("$.data[0].productId").value(laptop.getProductId()))
		   .andExpect(jsonPath("$.data[0].categoryId").value(laptop.getCategoryId()))
		   .andExpect(jsonPath("$.data[1].name", is(tv.getName())))
		   .andExpect(jsonPath("$.data[1].productId").value(tv.getProductId()))
		   .andExpect(jsonPath("$.data[1].categoryId").value((tv.getCategoryId())));
		
		verify(categoryService, VerificationModeFactory.times(1)).getCategoryById(Mockito.anyLong());
		verify(productService, VerificationModeFactory.times(1)).getProductsByCategoryId(Mockito.anyLong());
		reset(productService);
	}
	
	@Test
	public void whenPostProduct_thenCreateProduct() throws IOException, Exception
	{
		Product laptop = new Product("laptop", 1);
		given(productService.save(Mockito.any())).willReturn(laptop);
		
		mvc.perform(post("/products").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(laptop)))
		   .andExpect(status().isOk())
		   .andExpect(jsonPath("$.data.name", is(laptop.getName())))
		   .andExpect(jsonPath("$.data.productId").value(laptop.getProductId()))
		   .andExpect(jsonPath("$.data.categoryId").value(laptop.getCategoryId()));
		
	   verify(productService, VerificationModeFactory.times(1)).save(Mockito.any());
	   reset(productService);	    
	}
	
	@Test
	public void whenUpdateProduct_thenReturnJson() throws IOException, Exception
	{
		Product laptop = new Product("laptop", 1);
		given(productService.save(Mockito.any())).willReturn(laptop);
		
		long newCategoryId = 2;
		laptop.setCategoryId(newCategoryId);
		
		mvc.perform(put("/products").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(laptop)))
		   .andExpect(status().isOk())
		   .andExpect(jsonPath("$.data.name", is(laptop.getName())))
		   .andExpect(jsonPath("$.data.productId").value(laptop.getProductId()))
		   .andExpect(jsonPath("$.data.categoryId").value(newCategoryId));
		
	   verify(productService, VerificationModeFactory.times(1)).save(Mockito.any());
	   reset(productService);	 
	}
	
	@Test
	public void whenDeleteProduct_thenReturnJson() throws IOException, Exception
	{
        Product laptop = new Product("laptop", 1);
        
        mvc.perform(delete("/products/{id}", laptop.getProductId()).contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(laptop)))
		   .andExpect(status().isOk());
        
	    verify(productService, VerificationModeFactory.times(1)).deleteProductById(Mockito.anyLong());;
	    reset(productService);	 
	}	
}
