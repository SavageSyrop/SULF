package org.example.dao;

import org.example.entity.CategoryBudget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryBudgetDao extends JpaRepository<CategoryBudget, Long> {
    Optional<CategoryBudget> findByOwnerAndCategoryName(Long owner, String categoryName);
}
