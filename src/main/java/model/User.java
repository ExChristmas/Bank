package model;

import java.util.ArrayList;
import java.util.List;

public class User {

    private int id;
    private String login;
    private String password;
    private String address;
    private String phone;
    private List<Account> accountList;

    public User (String login, String password, String address, String phone) {
        this.id = 0;
        this.login = login;
        this.password = password;
        this.address = address;
        this.phone = phone;
        this.accountList = new ArrayList<Account>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Account> getAccountList() {
        return this.accountList;
    }

    public void setAccountList(List<Account> accountList) {
        this.accountList = accountList;
    }

    public void setAddAccountList(Account account) {
        this.accountList.add(account);
    }
}