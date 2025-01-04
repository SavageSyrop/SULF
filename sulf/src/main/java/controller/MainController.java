package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.FinancialOperationService;
import service.UserService;

@Component
public class MainController {
    @Autowired
    private UserService userService;
    @Autowired
    private FinancialOperationService financialOperationService;
}
