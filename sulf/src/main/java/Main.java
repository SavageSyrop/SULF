import controller.MainController;
import entity.FinancialOperation;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static enums.FinancialOperationType.EXPENSE;

@ComponentScan("")
public class Main {

    public static void main(String[] args) {
        ConfigurableApplicationContext app = SpringApplication.run(Main.class, args);
        MainController mainController = app.getBean(MainController.class);

        Scanner sc = new Scanner(System.in);

        System.out.println("Сервис управления личными финансами\n" +
                "Для выхода нажмите ctrl+d\n\n" +
                "Для вызова команды введите её номер:\n" +
                "1. Зарегистрироваться\n" +
                "2. Выйти из аккаунта\n" +
                "3. Авторизоваться\n" +
                "4. Добавить расходы\n" +
                "5. Добавить доходы\n" +
                "6. Установить бюджет под категорию\n" +
                "7. Показать все операции дохода\n" +
                "8. Показать все операции расхода\n" +
                "9. Показать данные по всем категориям\n" +
                "10. Показать данные по конкретной категории\n" +
                "11. Изменить бюджет по категории\n" +
                "12. Узнать по каким категориям превышен бюджет\n" +
                "13. Перевести средства на другой кошелёк\n");

        System.out.print("Ожидание ввода команды: ");

        while (sc.hasNextLine()) {
            String command = sc.nextLine();
            try {
                switch (command) {
                    case "1":
                        System.out.print("Введите логин: ");
                        String login = sc.nextLine();
                        System.out.print("Введите пароль: ");
                        String password = sc.nextLine();
                        mainController.register(login, password);
                        System.out.println("Вы зарегистрировались, теперь можете авторизоваться");
                        break;
                    case "2":
                        mainController.logout();
                        System.out.println("Вы вышли из пользователя");
                        break;
                    case "3":
                        System.out.print("Введите логин пользователя: ");
                        String loginA = sc.nextLine();
                        System.out.print("Введите пароль пользователя: ");
                        String passwordA = sc.nextLine();
                        mainController.login(loginA, passwordA);
                        System.out.println("Вы вошли в пользователя");
                        break;
                    case "4":
                        System.out.print("Введите данные в формате 'Категория:Количество денег': ");
                        String dataR = sc.nextLine();
                        String[] dataSplit = dataR.replace(" ", "").split(":");
                        mainController.addExpense(dataSplit[0], Float.parseFloat(dataSplit[1]));
                        System.out.println("Данные добавлены");
                        break;
                    case "5":
                        System.out.print("Введите данные в формате 'Категория:Количество денег': ");
                        String dataD = sc.nextLine();
                        String[] dataSplitD = dataD.replace(" ", "").split(":");
                        mainController.addIncome(dataSplitD[0], Float.parseFloat(dataSplitD[1]));
                        System.out.println("Данные добавлены");
                        break;
                    case "6":
                        System.out.print("Введите данные в формате 'Категория:Размер бюджета': ");
                        String dataB = sc.nextLine();
                        String[] dataSplitB = dataB.replace(" ", "").split(":");
                        mainController.addBudgetCategory(dataSplitB[0], Float.parseFloat(dataSplitB[1]));
                        System.out.println("Данные добавлены");
                        break;
                    case "7":
                        List<FinancialOperation> list = mainController.getAllIncomeOperationsByCurrentUser();
                        System.out.println("<---------------------------->");
                        float total = 0;
                        for (FinancialOperation operation : list) {
                            total += operation.getPrice();
                            System.out.println(operation.getCategoryName() + ": " + operation.getPrice());
                        }
                        System.out.println("Сумма по всем операциям: " + total);
                        System.out.println("<---------------------------->");
                        break;
                    case "8":
                        List<FinancialOperation> listE = mainController.getAllExpenseOperationsByCurrentUser();
                        System.out.println("<---------------------------->");
                        float totalE = 0;
                        for (FinancialOperation operation : listE) {
                            totalE += operation.getPrice();
                            System.out.println(operation.getCategoryName() + ": " + operation.getPrice());
                        }
                        System.out.println("Сумма по всем операциям: " + totalE);
                        System.out.println("<---------------------------->");
                        break;
                    case "9":
                        List<FinancialOperation> listA = mainController.getAllOperationsByCurrentUser();
                        System.out.println("<---------------------------->");
                        float totalA = 0;
                        for (FinancialOperation operation : listA) {
                            if (operation.getOperationType().equals(EXPENSE)) {
                                totalA -= operation.getPrice();
                            } else {
                                totalA += operation.getPrice();
                            }
                            System.out.println(operation.getCategoryName() + ": " + operation.getPrice());
                        }
                        System.out.println("Итог бюджета: " + totalA);
                        System.out.println("<---------------------------->");
                        break;
                    case "10":
                        System.out.print("Введите название категории: ");
                        List<FinancialOperation> listSC = mainController.getAllOperationsBySelectedCategory();
                        System.out.println("<---------------------------->");
                        float totalSC = 0;
                        for (FinancialOperation operation : listSC) {
                            totalSC += operation.getPrice();
                            System.out.println(operation.getCategoryName() + ": " + operation.getPrice());
                        }
                        System.out.println("Сумма по всем операциям: " + totalSC);
                        System.out.println("<---------------------------->");
                        break;
                    case "11":
                        System.out.print("Введите данные в формате 'Категория:Размер бюджета'");
                        String dataNB = sc.nextLine();
                        String[] dataSplitNB = dataNB.replace(" ", "").split(":");
                        mainController.editBudgetCategory(dataSplitNB[0], Float.parseFloat(dataSplitNB[1]));
                        System.out.println("Категория отредактирована");
                        break;
                    case "12":
                        Map<String, Float> budgetOverflows = mainController.getAllBudgetOverflows();
                        System.out.println("<---------------------------->");
                        float totalBO = 0;
                        for (Map.Entry<String, Float> entry : budgetOverflows.entrySet()) {
                            totalBO += entry.getValue();
                            System.out.println(entry.getKey() + ": " + entry.getValue());
                        }
                        System.out.println("Итог превышения бюджета: " + totalBO);
                        System.out.println("<---------------------------->");
                        break;
                    case "13":
                        System.out.print("Укажите логин пользователя, которому будет производиться перевод: ");
                        String userLogin = sc.nextLine();
                        System.out.print("Укажите размер перевода: ");
                        Float amount = Float.parseFloat(sc.nextLine());
                        mainController.transferMoney(userLogin, amount);
                        System.out.println("Перевод успешен");
                        break;
                    default:
                        System.out.println("Неверный номер команды");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            System.out.print("Ожидание ввода команды: ");
        }
    }
}
