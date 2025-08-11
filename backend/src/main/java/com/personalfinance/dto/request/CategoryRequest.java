package com.personalfinance.dto.request;

import com.personalfinance.entity.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Request object for creating or updating a category")
public class CategoryRequest {

    @NotBlank
    @Size(min = 2, max = 50)
    @Schema(description = "Category name", example = "Food & Dining", required = true)
    private String name;

    @Size(max = 255)
    @Schema(description = "Category description", example = "Expenses for meals and dining out")
    private String description;

    @Size(max = 7)
    @Schema(description = "Category color in hex format", example = "#FF5733")
    private String color;

    @Size(max = 50)
    @Schema(description = "Category icon name", example = "restaurant")
    private String icon;

    @NotNull
    @Schema(description = "Category type", example = "EXPENSE", required = true, allowableValues = {"INCOME", "EXPENSE"})
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
