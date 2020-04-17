package model;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseActions {

    private static final String DB_URL = "jdbc:h2:/c:/JavaPrj/SQLDemo/db/stockExchange";
    private static final String DB_Driver = "org.h2.Driver";

    private static Connection connection;
    private static Statement statement;

    public DatabaseActions() {
        try {
            Class.forName(DB_Driver); //Проверяем наличие JDBC драйвера для работы с БД
            connection = DriverManager.getConnection(DB_URL); //соединение с БД
            statement = connection.createStatement();
            System.out.println("Соединение с СУБД выполнено.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace(); // обработка ошибки  Class.forName
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateAccountAmmountById(String id, BigDecimal amount) {
        try {
            statement.executeUpdate("UPDATE Account SET " +
                    "amount = " + amount +
                    " WHERE id = '" + id + "'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ArrayList<String>> selectAccountByClientId(int client_id){ // возврат записей из таблицы со счетами по foreign key клиента
        try {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM Account WHERE client_id = " + client_id);
            ResultSet rs = query.executeQuery();
            ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
            while (rs.next()) {
                ArrayList<String> acc = new ArrayList<String>();
                acc.add(rs.getString(1));
                acc.add(rs.getString(2));
                acc.add(rs.getString(3));
                acc.add(rs.getString(4));
                data.add(acc);
            }
            return data;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean checkUserLogin(String login) { // проверка есть в базе пользователь с заданным логином
        try {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM User WHERE login = '" + login + "'");
            ResultSet rs = query.executeQuery();
            boolean fl = rs.next();
            rs.close();
            query.close();
            return fl;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean checkUserPhone(String phone) { // проверка, есть ли в базе пользователь с заданным номером телефона
        try {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM User WHERE phone = '" + phone + "'");
            ResultSet rs = query.executeQuery();
            boolean fl = rs.next();
            rs.close();
            query.close();
            return fl;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String selectUserPassword(String login) { // возврат записи из таблицы User по логину или номеру телефона
        try {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM User WHERE login = '" + login + "'");
            ResultSet rs = query.executeQuery();
            if (!rs.next()) {
                query = connection.prepareStatement("SELECT * FROM User WHERE phone = '" + login + "'");
                rs = query.executeQuery();
                rs.next();
                String password = rs.getString(3);
                rs.close();
                query.close();
                return password;
            } else {
                String password = rs.getString(3);
                rs.close();
                query.close();
                return password;
            }
        } catch (SQLException e) {
            return "";
        }
    }

    public int selectUserIdByLogin(String login) { // возврат id пользователя по логину
        try {
            PreparedStatement query = connection.prepareStatement("SELECT id FROM User WHERE login = '" + login + "'");
            ResultSet rs = query.executeQuery();
            rs.next();
            int id = rs.getInt(1);
            rs.close();
            query.close();
            return id;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ошибка SQL !");
            return -1;
        }
    }

    public List<String> selectUserByLogin(String login) { // возврат пользователя по логину
        try {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM User WHERE login = '" + login + "'");
            ResultSet rs = query.executeQuery();
            List<String> data = new ArrayList<String>();
            rs.next();
            data.add(rs.getString(2));
            data.add(rs.getString(3));
            data.add(rs.getString(4));
            data.add(rs.getString(5));
            rs.close();
            query.close();
            return data;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /************************************************************************************************************/
    public List<String> selectAllUsers() {
        try {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM User");
            ResultSet rs = query.executeQuery();
            List<String> data = new ArrayList<String>();
            while (rs.next()) {
                data.add(String.format("%s, %s, %s, %s, %s", rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5)));
            }
            rs.close();
            query.close();
            return data;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<String> selectAllAccounts() {
        try {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM Account");
            ResultSet rs = query.executeQuery();
            List<String> data = new ArrayList<String>();
            while (rs.next()) {
                data.add(String.format("%s, %s, %s, %s", rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4)));
            }
            rs.close();
            query.close();
            return data;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<String> selectAllOperations() {
        try {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM Operation");
            ResultSet rs = query.executeQuery();
            List<String> data = new ArrayList<String>();
            while (rs.next()) {
                data.add(String.format("%s, %s, %s, %s, %s, %s, %s, %s", rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8)));
            }
            rs.close();
            query.close();
            return data;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void createTables() {
        try {
            statement.execute("CREATE TABLE User (" +
                    "id int IDENTITY(1,1)," +
                    "login varchar(255)," +
                    "password varchar(255)," +
                    "address varchar(255)," +
                    "phone varchar(20)," +
                    "PRIMARY KEY (id))");
            statement.execute("CREATE TABLE Account (" +
                    "id uuid default random_uuid()," +
                    "client_id int," +
                    "amount decimal(20, 5)," +
                    "acc_code ENUM('RUB', 'USD', 'EUR', 'CNY')," +
                    "PRIMARY KEY (id)," +
                    "FOREIGN KEY (client_id) REFERENCES User(id)" +
                    ")");
            statement.execute("CREATE TABLE Operation (" +
                    "id int IDENTITY(1,1)," +
                    "date_of_operation date," +
                    "acc_code ENUM('RUB', 'USD', 'EUR', 'CNY')," +
                    "account_transferred int," +
                    "account_transferred_to int," +
                    "transfer_amount decimal," +
                    "amount_of_funds_to_transfer decimal," +
                    "amount_of_funds_after_transfer decimal," +
                    "PRIMARY KEY (id)," +
                    "FOREIGN KEY (account_transferred) REFERENCES Account(id)," +
                    "FOREIGN KEY (account_transferred_to) REFERENCES Account(id)" +
                    ")");
        } catch (SQLException e) {
            e.printStackTrace(); // обработка ошибок  DriverManager.getConnection
            System.out.println("Ошибка SQL !");
        }
    }

    public static void dropTables() {
        try {
            statement.executeUpdate("DROP TABLE Operation");
            statement.executeUpdate("DROP TABLE Account");
            statement.executeUpdate("DROP TABLE User");
        } catch (SQLException e) {
            e.printStackTrace(); // обработка ошибок  DriverManager.getConnection
            System.out.println("Ошибка SQL !");
        }
    }

    public void insertUser(String login, String password, String address, String phone) { // занесение пользователя в базу
        try {
            statement.executeUpdate("INSERT INTO User " +
                    "(login, password, address, phone) VALUES ('" +
                    login + "', '" +
                    password + "', '" +
                    address + "', '" +
                    phone +
                    "')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertAccount(int client_id, String acc_code) { // занесение счёта в базу
        try {
            statement.executeUpdate("INSERT INTO Account " +
                    "(client_id, amount, acc_code) VALUES (" +
                    client_id + ", " +
                    new BigDecimal(0.0) + ", '" +
                    acc_code +
                    "')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertOperation(Date date_of_operation, String acc_code, int account_transferred,
                                int account_transferred_to, BigDecimal transfer_amount,
                                BigDecimal amount_of_funds_to_transfer, BigDecimal amount_of_funds_after_transfer) { // занесение операции в базу
        try {
            statement.executeUpdate("INSERT INTO Operation " +
                    "(date_of_operation, acc_code, account_transferred," +
                    "account_transferred_to, transfer_amount, amount_of_funds_to_transfer," +
                    "amount_of_funds_after_transfer)" +
                    " VALUES ('" +
                    date_of_operation + "', '" +
                    acc_code + "', " +
                    account_transferred + ", " +
                    account_transferred_to + ", " +
                    transfer_amount + ", " +
                    amount_of_funds_to_transfer + ", " +
                    amount_of_funds_after_transfer +
                    ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}