package models.account;

import enums.Currency;

import java.util.Random;
import java.time.LocalDateTime;

public abstract class Account {
    private String iban;
    private String customerId;
    protected double balance;
    private Currency currency;
    private LocalDateTime openedAt;
    private boolean active;

    public Account ( String customerId, Currency currency){
        this.iban = generateIban();
        this.customerId = customerId;
        this.balance = 0.0;
        this.currency = currency;
        this.openedAt = LocalDateTime.now();
        this.active = true;
    }
    private static String generateIban(){
        String lastDigits = "";
        Random random = new Random();
        for (int i = 0; i < 16; i++)
            lastDigits += random.nextInt(10);
        return "RO" + String.format("%02d", random.nextInt(97)+ 2) + "BSFB" + lastDigits;
    }
    public String getIban(){
        return iban;
    }
    public String getCustomerId() {
        return customerId;
    }
    public LocalDateTime getOpenedAt() {
        return openedAt;
    }
    public boolean isActive() {
        return active;
    }
    public double getBalance() {
        return balance;
    }
    public Currency getCurrency() {
        return currency;
    }
    public void setActive(boolean active){
        this.active = active;
    }
    public void deposit(double amount) throws Exception {
        Exception IllegalArgumentException = new Exception();
        if (amount > 0)
            balance += amount;
        else
            throw IllegalArgumentException;
    }
    public abstract boolean withdraw(double amount) throws Exception;
    public abstract String getAccountSummary();
}
