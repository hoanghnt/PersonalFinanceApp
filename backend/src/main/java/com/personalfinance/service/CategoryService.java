package com.personalfinance.service;

import com.personalfinance.dto.request.CategoryRequest;
import com.personalfinance.dto.response.CategoryResponse;
import com.personalfinance.entity.Category;
import com.personalfinance.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    //Get all categories
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream().map(CategoryResponse::new).collect(Collectors.toList());
    }

    //Get all categories by type
    public List<CategoryResponse> getCategoriesByType(Category.CategoryType type) {
        return categoryRepository.findByType(type).stream().map(CategoryResponse::new).collect(Collectors.toList());
    }

    //Get category by id
    public Optional<CategoryResponse> getCategoryById(Long id) {
        return categoryRepository.findById(id).map(CategoryResponse::new);
    }

    //Create new category
    public CategoryResponse createCategory(CategoryRequest request) {
        if (categoryRepository.existsByName(request.getName())) {
            throw new RuntimeException("Category with name '" + request.getName() + "' already exists.");
        }
        Category category = new Category();
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        category.setColor(request.getColor());
        category.setIcon(request.getIcon());
        category.setType(request.getType());

        Category saveCategory = categoryRepository.save(category);
        return new CategoryResponse(saveCategory);
    }

    //Update category
    public CategoryResponse updateCategory(Long id, CategoryRequest request) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found with id: " + id));

        if (category.getIsDeleted()) {
            throw new RuntimeException("Cannot update deleted category");
        }

        //Check if name already exists (excluding current category)
        if (!category.getName().equals(request.getName()) && categoryRepository.existsByNameAndId(request.getName(), id)) {
            throw new RuntimeException("Category with name '" + request.getName() + "' already exists.");
        }

        category.setName(request.getName());
        category.setDescription(request.getDescription());
        category.setColor(request.getColor());
        category.setIcon(request.getIcon());
        category.setType(request.getType());

        Category updateCategory = categoryRepository.save(category);
        return new CategoryResponse(updateCategory);
    }

    //Delete category
    public void deleteCategory(Long id){
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
        if (category.getIsDeleted()) {
            throw new RuntimeException("Category already deleted");
        }
        category.softDelete();
        categoryRepository.save(category);
    }

    //Search category by name
    public List<CategoryResponse> searchCategory(String name) {
        return categoryRepository.findByName(name)
                .stream()
                .map(CategoryResponse::new)
                .collect(Collectors.toList());
    }
}
