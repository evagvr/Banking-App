package models;

public class SavingsAccount extends Account{
    private double interestRate;
    public SavingsAccount(String accountId, String clientCnp, String currency,double interestRate){
        super(accountId, clientCnp, currency);
        this.interestRate = interestRate;
    }

    @Override
    public boolean withdraw(double amount) {
        if (balance >= amount){
            balance-= amount;
            return true;
        }
        return false;
    }
    public void addInterest(){
        balance += balance*interestRate;
    }
}
