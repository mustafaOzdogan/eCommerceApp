package com.ecommerce.ecommerceApp.integrationTest;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

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
import com.ecommerce.ecommerceApp.repository.CategoryRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ProductController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
public class CategoryControllerUnitTest 
{
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private CategoryRepository categoryRepository;
	
	
	@Before
	public void setUp() throws Exception {}
		
	@Test
	public void whenPostCategory_thenCreateCategory() throws IOException, Exception
	{
		Category electronics = new Category("electronics");
		given(categoryRepository.save(Mockito.any())).willReturn(electronics);
		
		mvc.perform(post("categories").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(electronics)))
		   .andExpect(status().isCreated())
		   .andExpect(jsonPath("$.name", is(electronics.getName())))
		   .andExpect(jsonPath("$.categoryId", is(electronics.getCategoryId())));
		
	   verify(categoryRepository, VerificationModeFactory.times(1)).save(Mockito.any());
	   reset(categoryRepository);	    
	}
}
