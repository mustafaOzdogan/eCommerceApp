package com.ecommerce.ecommerceApp.integrationTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.ecommerce.ecommerceApp.model.Category;
import com.ecommerce.ecommerceApp.repository.CategoryRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CategoryRepositoryIntegrationTest 
{
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Test
	public void whenFindById_thenReturnCategory()
	{
		Category electronics = new Category("electronics");
		entityManager.persistAndFlush(electronics);
		
		Category found = categoryRepository.findById(electronics.getCategoryId()).orElse(null);
		
		assertThat(found.getName()).isEqualTo(electronics.getName());
	}
	
	@Test
	public void whenInvalidId_thenReturnNull()
	{
		long invalidId = -99;
		Category found = categoryRepository.findById(invalidId).orElse(null);
		assertThat(found).isNull();
	}
	
	@Test
	public void givenSetOfCtegories_whenFindAll_thenReturnAllCategories()
	{
		Category electronics = new Category("electronics");
		Category cleaning    = new Category("cleaning");
		Category selfCare    = new Category("self care");
		
		entityManager.persist(electronics);
		entityManager.persist(cleaning);
		entityManager.persist(selfCare);
		entityManager.flush();
		
		List<Category> allCategories = categoryRepository.findAll();
		
		assertThat(allCategories).hasSize(3).extracting(Category::getName)
		 	 	 	 	 	     .contains(electronics.getName(), cleaning.getName(), selfCare.getName());
	}
}
