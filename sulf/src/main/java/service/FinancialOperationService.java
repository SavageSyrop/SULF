package service;

import dao.CategoryBudgetDao;
import dao.FinancialOperationDao;
import entity.CategoryBudget;
import entity.FinancialOperation;
import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class FinancialOperationService {
    @Autowired
    private CategoryBudgetDao categoryBudgetDao;
    @Autowired
    private FinancialOperationDao financialOperationDao;


    public FinancialOperation addFinancialOperation(FinancialOperation operation) {
        return financialOperationDao.save(operation);
    }

    public CategoryBudget addBudgetCategory(CategoryBudget categoryBudget) {
        return categoryBudgetDao.save(categoryBudget);
    }

    public CategoryBudget getCategoryByUser(Long id, String categoryName) {
        Optional<CategoryBudget> categoryBudgetOp = categoryBudgetDao.findByOwnerAndCategoryName(id, categoryName);
        if (categoryBudgetOp.isEmpty()) {
            throw new IllegalArgumentException(categoryName + " нет у текущего пользователя");
        }
        return categoryBudgetOp.get();
    }

    public List<FinancialOperation> getAllIncomeOperationsByCurrentUser(User user) {
    }

    public List<FinancialOperation> getAllExpenseOperationsByCurrentUser(User user) {
    }

    public List<FinancialOperation> getAllOperationsByCurrentUser(User user) {
    }

    public List<FinancialOperation> getAllOperationsBySelectedCategory(User user) {
    }

    public Map<String, Float> getAllBudgetOverflows(User user) {
    }
}
