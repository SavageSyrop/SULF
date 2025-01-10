package org.example.service;

import org.example.dao.CategoryBudgetDao;
import org.example.dao.FinancialOperationDao;
import org.example.entity.CategoryBudget;
import org.example.entity.FinancialOperation;
import org.example.entity.User;
import org.example.enums.FinancialOperationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

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

    public CategoryBudget getCategoryByUser(User user, String categoryName) {
        Optional<CategoryBudget> categoryBudgetOp = categoryBudgetDao.findByOwnerAndCategoryName(user, categoryName);
        if (categoryBudgetOp.isEmpty()) {
            throw new IllegalArgumentException(categoryName + " нет у текущего пользователя");
        }
        return categoryBudgetOp.get();
    }

    public List<FinancialOperation> getAllIncomeOperationsByCurrentUser(User user) {
        return financialOperationDao.findAllByOwnerAndOperationType(user, FinancialOperationType.INCOME);
    }

    public List<FinancialOperation> getAllExpenseOperationsByCurrentUser(User user) {
        return financialOperationDao.findAllByOwnerAndOperationType(user, FinancialOperationType.EXPENSE);
    }

    public List<FinancialOperation> getAllOperationsByCurrentUser(User user) {
        return financialOperationDao.findAllByOwner(user);
    }

    public List<FinancialOperation> getAllOperationsBySelectedCategory(User user, String selectedCategory) {
        return financialOperationDao.findAllByOwnerAndCategoryName(user, selectedCategory);
    }

    public Map<String, Float> getAllBudgetOverflows(User user) {
        List<FinancialOperation> allOps = financialOperationDao.findAllByOwner(user);

        Map<String, Float> mappedBudget = new HashMap<>();
        Map<String, Float> resMap = new HashMap<>();

        Set<CategoryBudget> budgets = new LinkedHashSet<>();
        for (FinancialOperation financialOperation : allOps) {
            if (FinancialOperationType.EXPENSE.equals(financialOperation.getOperationType())) {
                CategoryBudget budget = categoryBudgetDao.findByOwnerAndCategoryName(user, financialOperation.getCategoryName()).orElse(null);
                if (budget != null) {
                    budgets.add(budget);
                    float found = mappedBudget.getOrDefault(financialOperation.getCategoryName(), 0f);
                    found -= financialOperation.getPrice();
                    mappedBudget.put(financialOperation.getCategoryName(), found);
                }
            }
        }

        for (CategoryBudget categoryBudget : budgets) {
            float found = mappedBudget.get(categoryBudget.getCategoryName());
            found += categoryBudget.getBudgetSize();
            if (found < 0) {
                resMap.put(categoryBudget.getCategoryName(), found);
            }
        }
        return resMap;
    }

    public List<CategoryBudget> getAllBudgetsByUser(User owner) {
        return categoryBudgetDao.findAllByOwner(owner);
    }
}
