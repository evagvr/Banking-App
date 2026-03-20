package models;

public class CheckingAccount extends Account{
    private double overdraftLimit;
    public CheckingAccount(String accountId, String clientId, String currency, double overdraftLimit){
        super(accountId, clientId, currency);
        this.overdraftLimit = overdraftLimit;
    }
    @Override
    public boolean withdraw(double amount) {
        if (balance + overdraftLimit >= amount){
            balance -= amount;
            return true;
        }
        return false;
    }
}
