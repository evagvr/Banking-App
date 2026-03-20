package models;

public abstract class Account {
    protected String accountId;
    protected String clientCnp;
    protected double balance;
    protected String currency;

    public Account (String accountId, String clientCnp, String currency){
        this.accountId = accountId;
        this.clientCnp = clientCnp;
        this.balance = 0.0;
        this.currency = currency;
    }
    public String getAccountId(){
        return accountId;
    }

    public String getClientCnp() {
        return clientCnp;
    }

    public double getBalance() {
        return balance;
    }

    public String getCurrency() {
        return currency;
    }
    public void deposit(double amount){
        balance += amount;
    }
    public abstract boolean withdraw(double amount);
}
