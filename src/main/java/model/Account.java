package model;

import java.math.BigDecimal;

public class Account {

    private String id;
    private int id_client;
    private BigDecimal amount;
    private String accCode;

    public Account (String id, int id_client, BigDecimal amount, String accCode) {
        this.id = id;
        this.id_client = id_client;
        this.amount = amount;
        this.accCode = accCode;
    }

    @Override
    public String toString() {
        return this.amount + " | " + this.accCode;
    }

    public String getId() {
        return this.id;
    }

    public void replenishAccount(BigDecimal sum) {
        this.amount = this.amount.add(sum);
    }

    public void deductFromTheAccount(BigDecimal sum) {
        this.amount = this.amount.subtract(sum);
    }

    public void setId(String id) {
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