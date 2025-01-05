package service;

import dao.CategoryBudgetDao;
import dao.FinancialOperationDao;
import entity.CategoryBudget;
import entity.FinancialOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
}
