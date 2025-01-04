package dao;

import entity.CategoryBudget;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryBudgetDao extends JpaRepository<CategoryBudget, Long> {
}
