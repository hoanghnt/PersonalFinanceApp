package com.personalfinance.repository;

import com.personalfinance.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByIsDeletedFalse();
    List<Category> findByTypeAndIsDeletedFalse(Category.CategoryType type);
    List<Category> findByNameContainingIgnoreCaseAndIsDeletedFalse(String name);
    boolean existsByNameAndIsDeletedFalse(String name);
    boolean existsByNameAndIsDeletedFalseAndIdNot(String name, Long id);


}
