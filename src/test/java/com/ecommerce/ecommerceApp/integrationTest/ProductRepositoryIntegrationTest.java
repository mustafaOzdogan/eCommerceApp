package com.ecommerce.ecommerceApp.integrationTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.ecommerce.ecommerceApp.model.Product;
import com.ecommerce.ecommerceApp.repository.ProductRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ProductRepositoryIntegrationTest 
{
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private ProductRepository productRepository;
	
	// test cases
	@Test
	public void whenFindByName_thenReturnProduct() 
	{
	    Product laptop = new Product("laptop");
	    entityManager.persistAndFlush(laptop);

	    Product found = productRepository.findByName(laptop.getName());
	    assertThat(found.getName()).isEqualTo(laptop.getName());
	}	
	
	@Test
    public void whenInvalidName_thenReturnNull() 
	{
		Product fromDb = productRepository.findByName("invalidProductName");
        assertThat(fromDb).isNull();
    }
	
	@Test
	public void whenFindById_thenReturnProduct()
	{
		Product laptop = new Product("laptop");
		entityManager.persistAndFlush(laptop);
		
		Product found = productRepository.findById(laptop.getProductId()).orElse(null);
		assertThat(found.getName()).isEqualTo(laptop.getName());
	}
	
	@Test
	public void whenInvalidId_thenReturnNull()
	{
		long invalidProductId = -9;
		Product fromDb = productRepository.findById(invalidProductId).orElse(null);
		assertThat(fromDb).isNull();	
	}
	
	@Test 
	public void givenSetOfProducts_whenFindAll_thenReturnAllProducts()
	{
		Product laptop = new Product("laptop");
		Product tv 	   = new Product("tv");
		Product tea	   = new Product("tea");
		
		entityManager.persist(laptop);
		entityManager.persist(tv);
		entityManager.persist(tea);
		entityManager.flush();
		
		List<Product> allProducts = productRepository.findAll();
		
		assertThat(allProducts).hasSize(3).extracting(Product::getName)
							   .contains(laptop.getName(), tv.getName(), tea.getName());
	}	
}
