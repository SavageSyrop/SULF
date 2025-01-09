package org.example.service;

import org.example.dao.CategoryBudgetDao;
import org.example.dao.FinancialOperationDao;
import org.example.entity.CategoryBudget;
import org.example.entity.FinancialOperation;
import org.example.entity.User;
import org.example.enums.FinancialOperationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
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
        return financialOperationDao.findAllByOwnerAndOperationType(user.getId(), FinancialOperationType.INCOME.name());
    }

    public List<FinancialOperation> getAllExpenseOperationsByCurrentUser(User user) {
        return financialOperationDao.findAllByOwnerAndOperationType(user.getId(), FinancialOperationType.EXPENSE.name());
    }

    public List<FinancialOperation> getAllOperationsByCurrentUser(User user) {
        return financialOperationDao.findAllByOwner(user.getId());
    }

    public List<FinancialOperation> getAllOperationsBySelectedCategory(User user, String selectedCategory) {
        return financialOperationDao.findAllByOwnerAndCategoryName(user.getId(), selectedCategory);
    }

    public Map<String, Float> getAllBudgetOverflows(User user) {
        List<FinancialOperation> allOps = financialOperationDao.findAllByOwner(user.getId());

        Map<String, Float> mappedBudget = new HashMap<>();
        for (FinancialOperation financialOperation: allOps) {
            if (financialOperation.getOperationType().equals(FinancialOperationType.INCOME)) {
                float currentVal = mappedBudget.getOrDefault(financialOperation.getCategoryName(), 0f);
                currentVal+= financialOperation.getPrice();
                mappedBudget.put(financialOperation.getCategoryName(), currentVal);
            } else {
                float currentVal = mappedBudget.getOrDefault(financialOperation.getCategoryName(), 0f);
                currentVal-= financialOperation.getPrice();
                mappedBudget.put(financialOperation.getCategoryName(), currentVal);
            }
        }
        return mappedBudget;
    }
}
