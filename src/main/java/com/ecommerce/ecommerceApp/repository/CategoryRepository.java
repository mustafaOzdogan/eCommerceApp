package com.ecommerce.ecommerceApp.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.ecommerceApp.model.Category;

@Repository
@Transactional
public interface CategoryRepository extends JpaRepository<Category, Long> {}
