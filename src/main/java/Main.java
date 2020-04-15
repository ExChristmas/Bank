import model.DatabaseActions;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        DatabaseActions databaseActions = new DatabaseActions();
        databaseActions.createTables();
        databaseActions.insertUser("Alex", "jgfgbfkjf", "Sovetskaya", "89271626944");
//        DatabaseActions.dropTables();
//        List<String> userData = databaseActions.selectAll("User");
//        for (String row : userData) {
//            System.out.println(row);
//        }
//        System.out.println("*************************");
//        List<String> accountData = databaseActions.selectAll("Account");
//        for (String row : accountData) {
//            System.out.println(row);
//        }
//        System.out.println("*************************");
//        List<String> operationData = databaseActions.selectAll("Operation");
//        for (String row : operationData) {
//            System.out.println(row);
//        }
    }
}
