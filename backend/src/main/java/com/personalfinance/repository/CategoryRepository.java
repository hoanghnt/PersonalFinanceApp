package com.personalfinance.repository;

import com.personalfinance.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("select c from Category c order by c.id asc")
    List<Category> findAll();
    @Query("select c from Category c where c.type = :type order by c.id asc")
    List<Category> findByType(Category.CategoryType type);
    @Query("select c from Category c where c.name = :name order by c.id asc")
    List<Category> findByName(String name);
    boolean existsByName(String name);
    boolean existsByNameAndId(String name, Long id);
}
