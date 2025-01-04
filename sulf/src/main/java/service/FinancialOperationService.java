package service;

import dao.CategoryBudgetDao;
import dao.FinancialOperationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FinancialOperationService {
    @Autowired
    private CategoryBudgetDao categoryBudgetDao;
    @Autowired
    private FinancialOperationDao financialOperationDao;
}
