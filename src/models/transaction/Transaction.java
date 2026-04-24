package models.transaction;


import enums.Currency;
import enums.TransactionStatus;


import java.time.LocalDateTime;

public abstract class Transaction {
    private TransactionStatus status;
    private String transactionId;
    private String iban;
    private double amount;
    private Currency currency;
    private LocalDateTime timestamp;
    private String description;
    private boolean isForeign;
    private Double exchangeRateApplied;

    public Transaction(String iban, double amount, Currency currency,String description) {
        this.status = TransactionStatus.PENDING;
        this.transactionId = java.util.UUID.randomUUID().toString();
        this.iban = iban;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
        this.currency = currency;
        this.description = description;
        this.isForeign = false;
        this.exchangeRateApplied = null;
    }
    public String getTransactionId() {
        return transactionId;
    }
    public String getIban() {
        return iban;
    }
    public double getAmount() {
        return amount;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public TransactionStatus getStatus() {
        return status;
    }
    public void complete() {
        status = TransactionStatus.COMPLETED;
    }
    public void fail(){
        status = TransactionStatus.FAILED;
    }
    public void reverse(){
        if (status == TransactionStatus.COMPLETED){
            status = TransactionStatus.REVERSED;
        }
        // else throw exception
    }
    public void setIsForeign(boolean isForeign){
        this.isForeign = isForeign;
    }
    public void setExchangeRateApplied(Double rate){
        exchangeRateApplied = rate;
    }

}
