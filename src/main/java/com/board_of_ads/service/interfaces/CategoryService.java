package com.board_of_ads.service.interfaces;

import com.board_of_ads.models.Category;
import com.board_of_ads.models.dto.CategoryDto;

import java.util.Optional;
import java.util.Set;

public interface CategoryService {

    Optional<Category> getCategoryByName(String name);

    Category saveCategory(Category category);

    Set<CategoryDto> findAllCategory();

    Optional<CategoryDto> getCategoryDtoById(Long id);

    Category updateCategory(CategoryDto category);

    void deleteCategory(Long id);

    Category createCategory(CategoryDto category);
}
