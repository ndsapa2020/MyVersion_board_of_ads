package com.board_of_ads.service.impl;

import com.board_of_ads.models.Category;
import com.board_of_ads.models.dto.CategoryDto;
import com.board_of_ads.repository.CategoryRepository;
import com.board_of_ads.service.interfaces.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    @Override
    public Optional<Category> getCategoryByName(String name) {
        return Optional.ofNullable(categoryRepository.findCategoryByName(name));
    }

    @Override
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Set<CategoryDto> findAllCategory() {
        Set<CategoryDto> category = new LinkedHashSet<>();
        categoryRepository.findAll().stream()
                .sorted(Comparator.comparing(Category::getId))
                .forEach(cat -> {
                    if (cat.getCategory() == null) {
                        category.add(new CategoryDto(cat.getId(), cat.getName(), null,cat.getLayer()));
                        collectChild(cat, category);
                    }
        });
        return category;
    }

    private void collectChild(Category categoryWithChildren, Set<CategoryDto> collect) {
        categoryRepository.findCategoriesByCategory(categoryWithChildren.getId())
                .forEach(cat -> {
                    collect.add(new CategoryDto(cat.getId(), cat.getName(), cat.getCategory().getName(),cat.getLayer()));
                    collectChild(cat, collect);
                });
    }

    @Override
    public Optional<CategoryDto> getCategoryDtoById(Long id) {
        var category = categoryRepository.findCategoryById(id);
        var categoryDto = new CategoryDto(
                category.getId(),
                category.getName(),
                category.getCategory() == null ? null : category.getCategory().getName(),
                category.getLayer() == 0 ? 0 : category.getLayer());
        return Optional.of(categoryDto);
    }

    @Override
    public Category updateCategory(CategoryDto categoryDto) {
        if (categoryDto.getParentName().equals("")) {
            return saveCategory(new Category(categoryDto.getId(), categoryDto.getName(), null));
        }
        var category = getCategoryByName(categoryDto.getParentName());
        return saveCategory(new Category(categoryDto.getId(), categoryDto.getName(), category.get()));
    }

    @Override
    public void deleteCategory(Long id) {
        var children = categoryRepository.findCategoriesByCategory(id);
        children.forEach(child -> {
            child.setCategory(null);
        });
        categoryRepository.deleteById(id);
    }

    @Override
    public Category createCategory(CategoryDto category) {
        if (category.getParentName().equals("")) {
            return categoryRepository.save(new Category(category.getName(), null));
        }
        var categoryParentFromDB = categoryRepository.findCategoryByName(category.getParentName());
        return categoryRepository.save(new Category(category.getName(), categoryParentFromDB));
    }
}