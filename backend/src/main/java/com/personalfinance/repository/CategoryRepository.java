package com.personalfinance.repository;

import com.personalfinance.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAll();
    List<Category> findByType(Category.CategoryType type);
    List<Category> findByNameContainingIgnoreCase(String name);
    boolean existsByName(String name);
    boolean existsByName(String name, Long id);


}
