import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.awt.*;
import java.net.URI;
import java.time.Instant;
import java.util.Scanner;

@ComponentScan("ru.sks")
public class Main {

    public static void main(String[] args) {
        System.setProperty("java.awt.headless", "false");
        ConfigurableApplicationContext app = SpringApplication.run(Main.class, args);
        Manager managerImpl = app.getBean(Manager.class);

        Scanner sc = new Scanner(System.in);

        System.out.println("Сервис сокращения ссылок\n" +
                "Для выхода нажмите ctrl+d\n\n" +
                "Для вызова команды введите её номер:\n" +
                "1. Зарегистрироваться (получить UUID)\n" +
                "2. Выйти из аккаунта\n" +
                "3. Авторизоваться (по UUID)\n" +
                "4. Создать короткую ссылку\n" +
                "5. Перейти по короткой ссылке\n" +
                "6. Изменить количество переходов по короткой ссылке\n" +
                "7. Удалить короткую ссылку\n" +
                "8. Изменить время валидности короткой ссылки\n" +
                "9. Получить UUID текущего пользователя\n" +
                "10. Список актуальных коротких ссылок текущего пользователя\n");

        System.out.print("Ожидание ввода команды: ");

        while (sc.hasNextLine()) {
            String command = sc.nextLine();
            try {
                switch (command) {
                    case "1":
                        System.out.println("Ваш UUID: " + managerImpl.register());
                        break;
                    case "2":
                        managerImpl.logout();
                        System.out.println("Вы вышли из пользователя");
                        break;
                    case "3":
                        System.out.print("Введите UUID пользователя: ");
                        String uuid = sc.nextLine();
                        managerImpl.login(uuid);
                        System.out.println("Вы вошли в пользователя");
                        break;
                    case "4":
                        System.out.print("Введите ссылку для сокращения: ");
                        String link = sc.nextLine();

                        System.out.print("Введите максимальное число переходов по ссылке: ");
                        long usagesCount = Long.parseLong(sc.nextLine());

                        long validUntil = inputTimeInUnixSeconds();

                        ShortUrl shortUrl = managerImpl.createUrl(link, usagesCount, validUntil);
                        System.out.println("Создана короткая ссылка: " + shortUrl.getShortUrl());
                        break;
                    case "5":
                        System.out.print("Введите короткую ссылку для перехода по ней: ");
                        String compressedUrl = sc.nextLine();
                        String longUrl = managerImpl.useShortUrl(compressedUrl).getOriginalUrl();
                        Desktop.getDesktop().browse(new URI(longUrl));
                        break;
                    case "6":
                        System.out.print("Введите коротку ссылку для перехода по ней: ");
                        String urlToChangeUsagesCount = sc.nextLine();

                        System.out.print("Введите максимальное число переходов по ссылке: ");
                        long newUsagesCount = Long.parseLong(sc.nextLine());
                        managerImpl.editUrlUsageLimit(urlToChangeUsagesCount, newUsagesCount);
                        System.out.println("Количество переходов обновлено");
                        break;
                    case "7":
                        System.out.print("Введите короткую ссылку, которую хотите удалить: ");
                        String urlToDelete = sc.nextLine();

                        managerImpl.deleteUrlByShortUrl(urlToDelete);
                        System.out.println("Ссылка удалена");
                        break;
                    case "8":
                        System.out.print("Введите короткую ссылку: ");
                        String linkToChangeTime = sc.nextLine();

                        long newValidUntil = inputTimeInUnixSeconds();
                        managerImpl.editUrlTimeLimit(linkToChangeTime, newValidUntil);
                        System.out.println("Данные обновлены");
                        break;
                    case "9":
                        String currentUser = managerImpl.whoAmI();
                        if (currentUser==null) {
                            System.out.println("Вы не авторизованы");
                        } else {
                            System.out.println("Ваш пользоваетель: " + currentUser);
                        }
                        break;
                    case "10":
                        for (ShortUrl entry: managerImpl.getUrlByCreatorUUID()) {
                            System.out.println(entry.getShortUrl());
                        }
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

    public static long inputTimeInUnixSeconds() throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("Выберите в чём ввести время жизни ссылки\nМаксимальный период 1 месяц\n1 - в секундах\n2 - в минутах\n3 - в часах\n4 - в днях");
        String choice = sc.nextLine();

        long validUntil = Instant.now().getEpochSecond();
        long inputValue;

        System.out.print("Введите значение в выбранной величине: ");
        inputValue = Long.parseLong(sc.nextLine());

        switch (choice) {
            case "1":
                validUntil += inputValue;
                break;
            case "2":
                validUntil += inputValue * 60;
                break;
            case "3":
                validUntil += inputValue * 3600;
                break;
            case "4":
                validUntil += inputValue * 86400;
                break;
            default:
                throw new Exception("Неверный выбор формата ввода времени");
        }
        return validUntil;
    }
}
