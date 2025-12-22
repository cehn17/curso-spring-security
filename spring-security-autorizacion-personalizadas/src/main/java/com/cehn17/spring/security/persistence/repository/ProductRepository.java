package com.cehn17.spring.security.persistence.repository;

import com.cehn17.spring.security.persistence.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
