import model.DatabaseActions;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Controller controller = new Controller();
//        controller.action();
        DatabaseActions databaseActions = new DatabaseActions();
//        databaseActions.createTables();
//        databaseActions.insertUser("Alex", "jgfgbfkjf", "Sovetskaya", "89271626944");
//        databaseActions.insertAccount(1, "RUB");
//        databaseActions.insertOperation(new Date(120, 0,1), "USD", 1, 2, new BigDecimal(200),
//                new BigDecimal(300), new BigDecimal(500));
//        databaseActions.deleteFromAccount();
//        DatabaseActions.dropTables();
        List<String> userData = databaseActions.selectAllUsers();
        for (String row : userData) {
            System.out.println(row);
        }
//        System.out.println("*************************");
//        List<String> accountData = databaseActions.selectAllAccounts();
//        for (String row : accountData) {
//            System.out.println(row);
//        }
//        System.out.println("*************************");
//        List<String> operationData = databaseActions.selectAllOperations();
//        for (String row : operationData) {
//            System.out.println(row);
//        }
        System.out.println(databaseActions.selectUserPassword("AlexMa"));
        System.out.println(databaseActions.selectUserPassword("8927126944"));
    }
}
