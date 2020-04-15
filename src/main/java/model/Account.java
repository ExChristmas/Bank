package model;

import java.math.BigDecimal;

public class Account {

    private int id;
    private int id_client;
    private BigDecimal amount;
    private String accCode;

    Account (int id_client, BigDecimal amount, String accCode) {
        this.id = 0;
        this.id_client = id_client;
        this.amount = amount;
        this.accCode = accCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getAccCode() {
        return accCode;
    }

    public void setAccCode(String accCode) {
        this.accCode = accCode;
    }
}