package com.ecommerce.ecommerceApp.integrationTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.ecommerce.ecommerceApp.EcommerceAppApplication;
import com.ecommerce.ecommerceApp.helper.JsonUtil;
import com.ecommerce.ecommerceApp.model.Category;
import com.ecommerce.ecommerceApp.model.Product;
import com.ecommerce.ecommerceApp.repository.CategoryRepository;
import com.ecommerce.ecommerceApp.repository.ProductRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = EcommerceAppApplication.class)
@AutoConfigureMockMvc 
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class ProductRestControllerIntegrationTest 
{

	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@After
	public void resetDb()
	{
		productRepository.deleteAll();
	}
		
	@Test
	public void givenValidProductId_whenGetProduct_thenStatus200() throws Exception
	{
		long categoryId = 1;
		Product laptop = createTestProduct("laptop", categoryId);
				
		mvc.perform(get("/products/" + Long.toString(laptop.getProductId())).contentType(MediaType.APPLICATION_JSON))
		   .andDo(print())
		   .andExpect(status().isOk())
		   .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		   .andExpect(jsonPath("$.data.name", is("laptop")))
		   .andExpect(jsonPath("$.data.categoryId").value(laptop.getCategoryId()));
	}
	
	@Test
	public void givenValidCategoryId_whenGetProducts_thenStatus200() throws Exception
	{
		Category electronics = createTestCategory("electronics");
		createTestProduct("laptop", electronics.getCategoryId());
		createTestProduct("tv", electronics.getCategoryId());
		
		mvc.perform(get("/categories/" + electronics.getCategoryId() + "/products").contentType(MediaType.APPLICATION_JSON))
		 .andDo(print())
		 .andExpect(status().isOk())
		 .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		 .andExpect(jsonPath("$.data[0].name", is("laptop")))
		 .andExpect(jsonPath("$.data[0].categoryId").value(electronics.getCategoryId()))
		 .andExpect(jsonPath("$.data[1].name", is("tv")))
		 .andExpect(jsonPath("$.data[1].categoryId").value(electronics.getCategoryId()));
	}
	
	@Test 
	public void givenValidInput_thenCreateProduct() throws IOException, Exception
	{
		Product laptop = new Product("laptop", 1);
		mvc.perform(post("/products").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(laptop)));
		
		List<Product> found = productRepository.findAll();
		assertThat(found).extracting(Product::getName, Product::getCategoryId)
		                 .containsOnly(tuple(laptop.getName(), laptop.getCategoryId()));	
	}
	
	@Test 
	public void givenValidInput_thenUpdateProduct() throws IOException, Exception
	{
		long categoryId = 1;
		Product laptop = createTestProduct("laptop", categoryId);
		
		laptop.setCategoryId(2);
		
		mvc.perform(put("/products").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(laptop)));
		
		Product fromDb = productRepository.findById(laptop.getProductId()).orElse(null);
		assertThat(fromDb.getCategoryId()).isEqualTo(laptop.getCategoryId());
	}
	
	@Test 
	public void givenValidInput_thenDeleteProduct() throws IOException, Exception
	{
		long categoryId = 1;
		Product product = createTestProduct("laptop", categoryId);
		
		mvc.perform(delete("/products/" + Long.toString(product.getProductId())).contentType(MediaType.APPLICATION_JSON));
		
		Product fromDb = productRepository.findById(categoryId).orElse(null);
		assertThat(fromDb).isNull();
	}
	
	private Product createTestProduct(String name, long categoryId)
	{
		Product product = new Product(name, categoryId);
		productRepository.saveAndFlush(product);
		return product;
	}
	
	private Category createTestCategory(String name)
	{
		Category category = new Category(name);
		categoryRepository.saveAndFlush(category);
		return category;
	}
}
