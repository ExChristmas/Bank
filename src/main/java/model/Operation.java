package model;

import java.math.BigDecimal;
import java.sql.Date;

public class Operation {

    private Date dateOfOperation;
    private String amount ;
    private String accountTransferred;
    private String accountTransferredTo;
    private BigDecimal transferAmount;
    private BigDecimal amountOfFundsToTransfer;
    private BigDecimal amountOfFundsAfterTransfer;

    public Operation (Date dateOfOperation, String amount, String accountTransferred, String accountTransferredTo,
               BigDecimal transferAmount, BigDecimal amountOfFundsToTransfer, BigDecimal amountOfFundsAfterTransfer) {
        this.dateOfOperation = dateOfOperation;
        this.amount = amount;
        this.accountTransferred = accountTransferred;
        this.accountTransferredTo = accountTransferredTo;
        this.transferAmount = transferAmount;
        this.amountOfFundsToTransfer = amountOfFundsToTransfer;
        this.amountOfFundsAfterTransfer = amountOfFundsAfterTransfer;
    }

    @Override
    public String toString() {
        return this.dateOfOperation + " " + this.amount + " " + this.accountTransferred + " "  +
                this.accountTransferredTo + " " + this.transferAmount + " " + this.amountOfFundsToTransfer +
                this.amountOfFundsAfterTransfer;
    }

    public Date getDateOfOperation() {
        return dateOfOperation;
    }

    public void setDateOfOperation(Date dateOfOperation) {
        this.dateOfOperation = dateOfOperation;
    }

    public String getCurrency() {
        return amount;
    }

    public void setCurrency(String amount) {
        this.amount = amount;
    }

    public String getAccountTransferred() {
        return accountTransferred;
    }

    public void setAccountTransferred(String  accountTransferred) {
        this.accountTransferred = accountTransferred;
    }

    public String getAccountTransferredTo() {
        return accountTransferredTo;
    }

    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
    }

    public void setAccountTransferredTo(String accountTransferredTo) {
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