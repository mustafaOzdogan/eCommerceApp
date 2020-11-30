package com.ecommerce.ecommerceApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.ecommerceApp.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

}
