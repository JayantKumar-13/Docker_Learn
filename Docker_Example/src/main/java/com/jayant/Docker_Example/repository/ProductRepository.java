package com.jayant.Docker_Example.repository;

import com.jayant.Docker_Example.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
