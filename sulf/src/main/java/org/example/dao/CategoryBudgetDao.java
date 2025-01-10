package org.example.dao;

import org.example.entity.CategoryBudget;
import org.example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryBudgetDao extends JpaRepository<CategoryBudget, Long> {
    Optional<CategoryBudget> findByOwnerAndCategoryName(User owner, String categoryName);

    List<CategoryBudget> findAllByOwner(User owner);
}
