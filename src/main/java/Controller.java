import model.Account;
import model.DatabaseActions;
import model.Operation;
import model.User;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

class Controller {

    private View view;
    private DatabaseActions databaseActions;
    private List<User> users;
    private List<Account> accounts;
    private List<Operation> operations;

    Controller() {
        this.view = new View();
        this.databaseActions = new DatabaseActions();
        users = new ArrayList<User>();
        accounts = new ArrayList<Account>();
        operations = new ArrayList<Operation>();
    }

    private User createUser(String login, String password, String address, String phone) {
        String hashCodePassword = sha1(password);
        this.databaseActions.insertUser(login, hashCodePassword, address, phone);
        return new User(login, hashCodePassword, address, phone);
    }

    private User registration() {
        String login = view.getLogin();
        while (databaseActions.checkUserLogin(login)) {
            System.out.print("Такой логин уже существует! Придумайте другой.\n");
            login = view.getLogin();
        }
        String phone = view.getPhoneNumber();
        if(databaseActions.checkUserPhone(phone)) {
            System.out.println("Пользователь, с таким номером телефона уже зарегистрирован!");
            return null;
        } else {
            String address = view.getAddress();
            while (true) {
                String password = view.getPassword();
                String passwordReply = view.getPasswordReply();
                if (password.equals(passwordReply)) {
                    System.out.println("Пользователь создан!");
                    return createUser(login, password, address, phone);
                } else {
                    System.out.println("Пароли не совпадают!");
                }
            }
        }
    }

    private boolean autorizationCheck(String login, String password) {
        String passwordIntoDB = databaseActions.selectUserPassword(login);
        if(passwordIntoDB.equals("")) {
            System.out.println("Неверный логин!");
            return false;
        } else if(!sha1(password).equals(passwordIntoDB)) {
            System.out.println("Неверный пароль!");
            return false;
        }
        return true;
    }

    private boolean autorisation() {
        if ()
    }

    void action() {

        boolean flMain = true;

        while (flMain) {
            switch (view.printMainMenu()) {
                case 0:
                    flMain = false;
                    break;
                case 1:
                    users.add(registration());
                    break;
                case 2:

                    boolean flAuth = true;
                    while (flAuth) {
                        switch (view.printAuthMenu()) {
                            case 0:
                                flAuth = false;
                                break;
                            case 1:

                                boolean flCur = true;
                                while (flCur) {
                                    switch (view.getCurrency()) {
                                        case 0:
                                            flCur = false;
                                            break;
                                        case 1:
                                            System.out.println("Создан счёт в рублях");
                                            flCur = false;
                                            break;
                                        case 2:
                                            System.out.println("Создан счёт в долларах");
                                            flCur = false;
                                            break;
                                        case 3:
                                            System.out.println("Создан счёт в евро");
                                            flCur = false;
                                            break;
                                        case 4:
                                            System.out.println("Создан счёт в юани");
                                            flCur = false;
                                            break;
                                    }
                                }
                                break;
                            case 2:
                                /*............................*/
                                BigDecimal sum = view.getTransferAmount();
                                System.out.println("Счёт пополнен на " + sum);
                                break;
                            case 3:
                                String phoneNumber = view.getPhoneNumber();
                                System.out.println("Выполнен перевод по номеру телефона: " + phoneNumber);
                                break;
                            case 4:
                                System.out.println("Вывод из таблицы Operation");
                                break;
                        }
                    }
            }
        }
    }

    public static String sha1(String message) {
        byte[] bytes = new byte[0];
        try {
            MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
            bytes = sha1.digest(message.getBytes());
        } catch (Exception ignore) { }
        StringBuilder stringBuilder = new StringBuilder();
        for(byte b : bytes) {
            stringBuilder.append(String.format("%02X", b));
        }
        return stringBuilder.toString();
    }
}