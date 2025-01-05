package dao;

import entity.CategoryBudget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryBudgetDao extends JpaRepository<CategoryBudget, Long> {
}
