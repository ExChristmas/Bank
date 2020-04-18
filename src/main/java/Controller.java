import model.Account;
import model.DatabaseActions;
import model.Operation;
import model.User;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
        String login = this.view.getLogin();
        while (this.databaseActions.checkUserLogin(login)) {
            System.out.print("Такой логин уже существует! Придумайте другой.\n");
            login = this.view.getLogin();
        }
        String phone = this.view.getPhoneNumber();
        if(this.databaseActions.checkUserPhone(phone)) {
            System.out.println("Пользователь, с таким номером телефона уже зарегистрирован!");
        } else {
            String address = this.view.getAddress();
            while (true) {
                String password = this.view.getPassword();
                String passwordReply = this.view.getPasswordReply();
                if (password.equals(passwordReply)) {
                    this.databaseActions.insertUser(login, sha1(password), address, phone);
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
        ArrayList<ArrayList<String>> data = this.databaseActions.selectAccountByClientId(idUser);
        for (ArrayList<String> datum : data) {
            Account account = new Account(datum.get(0), Integer.parseInt(datum.get(1)),
                    new BigDecimal(datum.get(2)), datum.get(3));
            accountList.add(account);
        }
        return accountList;
    }

    private User autorizationCheck(String login, String password) {
        String passwordIntoDB = this.databaseActions.selectUserPassword(login);
        if(passwordIntoDB.equals("")) {
            System.out.println("Неверный логин!");
            return null;
        } else if(!sha1(password).equals(passwordIntoDB)) {
            System.out.println("Неверный пароль!");
            return null;
        }
        List<String> userList = this.databaseActions.selectUserByLogin(login);
        User usr = new User(login, password, userList.get(2), userList.get(3));
        if (usr.getAccountList().isEmpty()) {
            usr.setAccountList(returnAccounts(this.databaseActions.selectUserIdByLogin(login)));
        }
            return usr;
    }

    private User autorization() {
        String login = this.view.getLogin();
        String password = this.view.getPassword();
        return autorizationCheck(login, password);
    }

    /**************************************Create account****************************************************/

    private void createAccount(User user, String accCode) {
        int idUser = this.databaseActions.selectUserIdByLogin(user.getLogin());
        this.databaseActions.insertAccount(idUser, accCode);
        user.setAddAccountList(new Account("", idUser, new BigDecimal(0.0), accCode));
        System.out.println("Создан счёт в валюте " + accCode + "!");
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
            Account account = user.getAccountList().get(this.view.chooseAnAccount(accounts));
            BigDecimal sum = this.view.getTransferAmount();
            account.replenishAccount(sum);
            this.databaseActions.updateAccountAmmountById(account.getId(),
                    account.getAmount().add(sum)); //округление до 5 знаков
            System.out.println("Счёт пополнен на " + sum);
        }
    }

    /********************************************Money transaction**************************************************/

    private void transction() {
        if (this.user.getAccountList().isEmpty()) {
            System.out.println("У Вас нет ни одного счёта!");
        } else {
            List<String> accounts = new ArrayList<String>();
            for (Account account : this.user.getAccountList()) {
                accounts.add(account.toString());
            }
            Account accountUser = this.user.getAccountList().get(this.view.chooseAnAccount(accounts)); //выбираем счёт с которого переводим
            String numberPhone = this.view.getPhoneNumber(); // запрашиваем номер телефона другого пользователя
            int idUserPhone = this.databaseActions.selectUserIdByPhone(numberPhone); // по номеру телефона узнаём id другого пользователя
            List<String> firstAccountUser = this.databaseActions.selectFirstAccountByClientId(idUserPhone); // вычисляем первый попавшийся счёт другого пользователя
            Account anotherAccountUser = new Account(firstAccountUser.get(0), Integer.parseInt(firstAccountUser.get(1)),
                    new BigDecimal(firstAccountUser.get(2)), firstAccountUser.get(3)); // оборачиваем в объект
            BigDecimal sum = this.view.getTransferAmount(); // запрашиваем сумму для перевода
            if (sum.compareTo(accountUser.getAmount()) < 0) { // если запрашиваемая сумма меньше той, что есть на счёте
                System.out.println("Недостаточно средств на счёте!"); // то выводим ошибку
            } else { // иначе, совершаем перевод
                BigDecimal sumTransf = sum;
                String accCodeAccount = accountUser.getAccCode(); // берём валюты счетов
                String accCodeAtherAccount = anotherAccountUser.getAccCode();
                if (!accCodeAccount.equals(accCodeAtherAccount)) { // если валюты счетов не равны
                    sumTransf = ConverterMoney.convert(sum, accCodeAccount, accCodeAtherAccount); // конвертируем сумму в валюте счёта зачисления
                }
                // сохраняем старые значения счётов
                BigDecimal oldAmountAccountUser = accountUser.getAmount();
                BigDecimal oldAmountAnotherAccountUser = anotherAccountUser.getAmount();
                accountUser.deductFromTheAccount(sum); // вычитаем переведённую сумму из счёта
                anotherAccountUser.replenishAccount(sumTransf); // добавляем переведённую сумму на счёт
                this.databaseActions.updateAccountAmmountById(accountUser.getId(), accountUser.getAmount()); //обновляем значения в базе
                this.databaseActions.updateAccountAmmountById(anotherAccountUser.getId(), anotherAccountUser.getAmount());
                // логируем перевод
                java.sql.Date date = new java.sql.Date( (new java.util.Date()).getTime()); // вычисляем текущую дату
                // добавляем запись для счёта, с которого переводили
                this.databaseActions.insertOperation(date, accCodeAccount, accountUser.getId(),
                        anotherAccountUser.getId(), sum, oldAmountAccountUser, accountUser.getAmount());
                // добавляем запись для счёта на который переводили
                this.databaseActions.insertOperation(date, accCodeAtherAccount, accountUser.getId(),
                        anotherAccountUser.getId(), sumTransf, oldAmountAnotherAccountUser, anotherAccountUser.getAmount());
            }
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
                                                flCur = false;
                                                break;
                                            case 2:
                                                createAccount(user, "USD");
                                                flCur = false;
                                                break;
                                            case 3:
                                                createAccount(user, "EUR");
                                                flCur = false;
                                                break;
                                            case 4:
                                                createAccount(user, "CYN");
                                                flCur = false;
                                                break;
                                        }
                                    }
                                    break;
                                case 2:
                                    replenishment(this.user);
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

    private static String sha1(String message) {
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