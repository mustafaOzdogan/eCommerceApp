package com.ecommerce.ecommerceApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.ecommerceApp.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {}	

