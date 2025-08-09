package com.personalfinance.dto.request;

import com.personalfinance.entity.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CategoryRequest {

    @NotBlank
    @Size(min = 2, max = 50)
    private String name;

    @Size(max = 255)
    private String description;

    @Size(max = 7)
    private String color;

    @Size(max = 50)
    private String icon;

    @NotNull
    private Category.CategoryType type;

    //Constructors
    public CategoryRequest() {
    }

    //Getters and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Category.CategoryType getType() {
        return type;
    }

    public void setType(Category.CategoryType type) {
        this.type = type;
    }
}
