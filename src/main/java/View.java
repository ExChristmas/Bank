import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

class View {

    private Scanner scanner = new Scanner(System.in);

    int printMainMenu() {
        System.out.println("1)Создать пользователя\n" +
                "2)Авторизация\n" +
                "0)Выход");
        int mainMenu = scanner.nextInt();
        scanner.nextLine();
        return mainMenu;
    }

    int printAuthMenu() {
        System.out.println("1)Создать счёт\n" +
                "2)Пополненить счёт\n" +
                "3)Перевести средства на другой счёт\n" +
                "4)Просмотр истории операций\n" +
                "0)Назад");
        return scanner.nextInt();
    }

    int getCurrency() {
        System.out.println("Выберите валюту:\n" +
                "1)RUB\n" +
                "2)USD\n" +
                "3)EUR\n" +
                "4)CNY\n" +
                "0)Назад");
        return scanner.nextInt();
    }

    String getLogin() {
        System.out.println("Введите логин:");
        return scanner.nextLine();
    }

    String getPassword() {
        System.out.println("Введите пароль:");
        return scanner.nextLine();
    }

    String getPasswordReply() {
        System.out.println("Повторите пароль:");
        return scanner.nextLine();
    }
    String getPhoneNumber() {
        System.out.println("Введите номер телефона:");
        return scanner.nextLine();
    }

    String getAddress() {
        System.out.println("Введите адрес:");
        return scanner.nextLine();
    }

    BigDecimal getTransferAmount() {
        System.out.println("Введите сумму:");
        return scanner.nextBigDecimal();
    }

    int chooseAnAccount(List<String> accounts) {
        System.out.println("Выберите счёт:");
        for (int i = 1; i <= accounts.size(); i++) {
            System.out.println(i + ")" + accounts.get(i - 1));
        }
        int num = scanner.nextInt();
        if (num >= 1 && num <= accounts.size()) {
            return num - 1;
        } else {
            return -1;
        }
    }
}