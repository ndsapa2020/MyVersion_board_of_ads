package com.board_of_ads.repository;

import com.board_of_ads.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findCategoryByName(String categoryName);

    Category findCategoryById(Long id);

    @Query("from Category c where c.category.id = :id")
    List<Category> findCategoriesByCategory(Long id);
}
