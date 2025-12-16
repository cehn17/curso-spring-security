package com.cehn17.spring.security.persistence.repository;

import com.cehn17.spring.security.persistence.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
