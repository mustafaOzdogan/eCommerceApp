package com.ecommerce.ecommerceApp.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.ecommerceApp.model.Product;

@Repository
@Transactional
public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {}
