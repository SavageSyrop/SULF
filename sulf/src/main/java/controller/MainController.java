package controller;

import entity.CategoryBudget;
import entity.FinancialOperation;
import entity.User;
import enums.FinancialOperationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import service.FinancialOperationService;
import service.UserService;

import java.util.List;
import java.util.Map;

@Component
public class MainController {
    @Autowired
    private UserService userService;
    @Autowired
    private FinancialOperationService financialOperationService;

    public void register(String login, String password) {
        userService.register(login, password);
    }

    public void login(String login, String password) {
        userService.login(login, password);
    }

    @PreAuthorize("isAuthenticated()")
    public void logout() {
        userService.logout();
    }

    @PreAuthorize("isAuthenticated()")
    public void addExpense(String category, float price) {
        FinancialOperation operation = new FinancialOperation();
        operation.setOperationType(FinancialOperationType.EXPENSE);
        operation.setPrice(price);
        operation.setCategoryName(category);
        operation.setOwner(userService.getCurrentUser());
        financialOperationService.addFinancialOperation(operation);
    }

    @PreAuthorize("isAuthenticated()")
    public void addIncome(String category, float price) {
        FinancialOperation operation = new FinancialOperation();
        operation.setOperationType(FinancialOperationType.INCOME);
        operation.setPrice(price);
        operation.setCategoryName(category);
        operation.setOwner(userService.getCurrentUser());
        financialOperationService.addFinancialOperation(operation);
    }

    @PreAuthorize("isAuthenticated()")
    public void addBudgetCategory(String category, float price) {
        CategoryBudget categoryBudget = new CategoryBudget();
        categoryBudget.setCategoryName(category);
        categoryBudget.setBudgetSize(price);
        categoryBudget.setOwner(userService.getCurrentUser());
        financialOperationService.addBudgetCategory(categoryBudget);
    }

    @PreAuthorize("isAuthenticated()")
    public void editBudgetCategory(String category, float price) {
        User user = userService.getCurrentUser();
        CategoryBudget categoryBudget = financialOperationService.getCategoryByUser(user.getId(), category);
        categoryBudget.setBudgetSize(price);
        financialOperationService.addBudgetCategory(categoryBudget);

    }

    @PreAuthorize("isAuthenticated()")
    public List<FinancialOperation> getAllIncomeOperationsByCurrentUser() {
        User user = userService.getCurrentUser();

        return financialOperationService.getAllIncomeOperationsByCurrentUser(user);
    }

    @PreAuthorize("isAuthenticated()")
    public List<FinancialOperation> getAllExpenseOperationsByCurrentUser() {
        User user = userService.getCurrentUser();

        return financialOperationService.getAllExpenseOperationsByCurrentUser(user);
    }

    @PreAuthorize("isAuthenticated()")
    public List<FinancialOperation> getAllOperationsByCurrentUser() {
        User user = userService.getCurrentUser();

        return financialOperationService.getAllOperationsByCurrentUser(user);
    }

    @PreAuthorize("isAuthenticated()")
    public List<FinancialOperation> getAllOperationsBySelectedCategory(String selectedCategory) {
        User user = userService.getCurrentUser();

        return financialOperationService.getAllOperationsBySelectedCategory(user, selectedCategory);
    }

    @PreAuthorize("isAuthenticated()")
    public Map<String, Float> getAllBudgetOverflows() {
        User user = userService.getCurrentUser();

        return financialOperationService.getAllBudgetOverflows(user);
    }

    @PreAuthorize("isAuthenticated()")
    @Transactional
    public void transferMoney(String userLogin, Float amount) {
        FinancialOperation operationEpense = new FinancialOperation();
        operationEpense.setOperationType(FinancialOperationType.EXPENSE);
        operationEpense.setPrice(amount);
        operationEpense.setCategoryName("Перевод");
        operationEpense.setOwner(userService.getCurrentUser());
        financialOperationService.addFinancialOperation(operationEpense);

        FinancialOperation operationIncome = new FinancialOperation();
        operationIncome.setOperationType(FinancialOperationType.INCOME);
        operationIncome.setPrice(amount);
        operationIncome.setCategoryName("Перевод");
        operationIncome.setOwner(userService.getUserByLogin(userLogin));
        financialOperationService.addFinancialOperation(operationIncome);
    }
}
