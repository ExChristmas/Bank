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
    private User user;

    Controller() {
        this.view = new View();
        this.databaseActions = new DatabaseActions();
        this.user = null;
    }

    private void createUser(String login, String password, String address, String phone) {
        String hashCodePassword = sha1(password);
        this.databaseActions.insertUser(login, hashCodePassword, address, phone);
    }
/************************************Registration user******************************************************/
    private void registration() {
        String login = view.getLogin();
        while (databaseActions.checkUserLogin(login)) {
            System.out.print("Такой логин уже существует! Придумайте другой.\n");
            login = view.getLogin();
        }
        String phone = view.getPhoneNumber();
        if(databaseActions.checkUserPhone(phone)) {
            System.out.println("Пользователь, с таким номером телефона уже зарегистрирован!");
        } else {
            String address = view.getAddress();
            while (true) {
                String password = view.getPassword();
                String passwordReply = view.getPasswordReply();
                if (password.equals(passwordReply)) {
                    databaseActions.insertUser(login, sha1(password), address, phone);
                    System.out.println("Пользователь создан!");
                    break;
                } else {
                    System.out.println("Пароли не совпадают!");
                }
            }
        }
    }

    /*****************************************Autorization user*************************************************/

    private List<Account> returnAccounts(int idUser) {
        List<Account> accountList = new ArrayList<Account>();
        ArrayList<ArrayList<String>> data = databaseActions.selectAccountByClientId(idUser);
        for (ArrayList<String> datum : data) {
            Account account = new Account(Integer.parseInt(datum.get(0)),
                    new BigDecimal(datum.get(1)), datum.get(2));
            accountList.add(account);
        }
        return accountList;
    }

    private User autorizationCheck(String login, String password) {
        String passwordIntoDB = databaseActions.selectUserPassword(login);
        if(passwordIntoDB.equals("")) {
            System.out.println("Неверный логин!");
            return null;
        } else if(!sha1(password).equals(passwordIntoDB)) {
            System.out.println("Неверный пароль!");
            return null;
        }
        List<String> userList = databaseActions.selectUserByLogin(login);
        User usr = new User(login, password, userList.get(2), userList.get(3));
        if (usr.getAccountList().isEmpty()) {
            usr.setAccountList(returnAccounts(databaseActions.selectUserIdByLogin(login)));
        }
            return usr;
    }

    private User autorization() {
        String login = view.getLogin();
        String password = view.getPassword();
        return autorizationCheck(login, password);
    }

    /**************************************Create account****************************************************/

    private void createAccount(User user, String accCode) {
        int idUser = databaseActions.selectUserIdByLogin(user.getLogin());
        databaseActions.insertAccount(idUser, accCode);
        user.setAddAccountList(new Account(idUser, new BigDecimal(0.0), accCode));
        System.out.println("Счёт" + accCode +"создан!");
    }

    /***************************************Replenishment***************************************************/

    private void replenishment(User user) {
        if (user.getAccountList().isEmpty()) {
            System.out.println("У Вас нет ни одного счёта!");
        } else {
            List<String> accounts = new ArrayList<String>();
            for(Account account : user.getAccountList()) {
                accounts.add(account.toString());
            }
            int numOfAcc = this.view.chooseAnAccount(accounts);
            System.out.println(accounts.get(numOfAcc - 1));
        }
    }

    void action() {
        boolean flMain = true;
        while (flMain) {
            switch (this.view.printMainMenu()) {
                case 0:
                    flMain = false;
                    break;
                case 1:
                    registration();
                    break;
                case 2:
                    this.user = autorization();
                    if (this.user != null) {
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
                                                createAccount(user, "RUB");
                                                System.out.println("Создан счёт в рублях");
                                                flCur = false;
                                                break;
                                            case 2:
                                                createAccount(user, "USD");
                                                System.out.println("Создан счёт в долларах");
                                                flCur = false;
                                                break;
                                            case 3:
                                                createAccount(user, "EUR");
                                                System.out.println("Создан счёт в евро");
                                                flCur = false;
                                                break;
                                            case 4:
                                                createAccount(user, "CYN");
                                                System.out.println("Создан счёт в юанях");
                                                flCur = false;
                                                break;
                                        }
                                    }
                                    break;
                                case 2:
                                    replenishment(this.user);
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