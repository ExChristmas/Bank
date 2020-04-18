package model;

import java.math.BigDecimal;
import java.sql.Date;

public class Operation {

    private Date dateOfOperation;
    private Currecy currency;
    private int accountTransferred;
    private int accountTransferredTo;
    private BigDecimal transferAmount;
    private BigDecimal amountOfFundsToTransfer;
    private BigDecimal amountOfFundsAfterTransfer;

    Operation (Date dateOfOperation, Currecy currency, int accountTransferred, int accountTransferredTo,
               BigDecimal transferAmount, BigDecimal amountOfFundsToTransfer, BigDecimal amountOfFundsAfterTransfer) {
        this.dateOfOperation = dateOfOperation;
        this.currency = currency;
        this.accountTransferred = accountTransferred;
        this.accountTransferredTo = accountTransferredTo;
        this.transferAmount = transferAmount;
        this.amountOfFundsToTransfer = amountOfFundsToTransfer;
        this.amountOfFundsAfterTransfer = amountOfFundsAfterTransfer;
    }

    public Date getDateOfOperation() {
        return dateOfOperation;
    }

    public void setDateOfOperation(Date dateOfOperation) {
        this.dateOfOperation = dateOfOperation;
    }

    public Currecy getCurrency() {
        return currency;
    }

    public void setCurrency(Currecy currency) {
        this.currency = currency;
    }

    public int getAccountTransferred() {
        return accountTransferred;
    }

    public void setAccountTransferred(int accountTransferred) {
        this.accountTransferred = accountTransferred;
    }

    public int getAccountTransferredTo() {
        return accountTransferredTo;
    }

    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
    }

    public void setAccountTransferredTo(int accountTransferredTo) {
        this.accountTransferredTo = accountTransferredTo;
    }

    public BigDecimal getAmountOfFundsToTransfer() {
        return amountOfFundsToTransfer;
    }

    public void setAmountOfFundsToTransfer(BigDecimal amountOfFundsToTransfer) {
        this.amountOfFundsToTransfer = amountOfFundsToTransfer;
    }

    public BigDecimal getAmountOfFundsAfterTransfer() {
        return amountOfFundsAfterTransfer;
    }

    public void setAmountOfFundsAfterTransfer(BigDecimal amountOfFundsAfterTransfer) {
        this.amountOfFundsAfterTransfer = amountOfFundsAfterTransfer;
    }
}

enum Currecy { RUS, USD, EUR, CNY }