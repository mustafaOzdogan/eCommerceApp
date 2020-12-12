package com.ecommerce.ecommerceApp.integrationTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

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
import com.ecommerce.ecommerceApp.repository.CategoryRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = EcommerceAppApplication.class)
@AutoConfigureMockMvc 
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class CategoryRestControllerIntegrationTest 
{
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@After
	public void resetDb()
	{
		categoryRepository.deleteAll();
	}
	
	@Test 
	public void givenValidInput_thenCreateCategory() throws IOException, Exception
	{
		Category electronics = new Category("electronics");
		mvc.perform(post("/categories").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(electronics)));
		
		List<Category> found = categoryRepository.findAll();
		assertThat(found).extracting(Category::getName).containsOnly("electronics");
		
	}
}
