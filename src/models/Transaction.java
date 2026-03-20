package models;

import java.time.LocalDateTime;

public abstract class Transaction {
    protected String transactionId;
    protected String accountId;
    protected double amount;
    protected LocalDateTime timestamp;
    protected String category;

    public Transaction(String accountId, double amount, String category) {
        this.transactionId = java.util.UUID.randomUUID().toString();
        this.accountId = accountId;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
        this.category = category;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getAccountId() {
        return accountId;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getCategory() {
        return category;
    }
    public abstract double calculateFee();
}
