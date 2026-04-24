package models.account;

import enums.Currency;

public class CurrentAccount extends Account {
    private double monthlyFee;
    private double overdraftLimit;
    public CurrentAccount(String clientId, Currency currency, double overdraftLimit, double monthlyFee){
        super( clientId, currency);
        this.overdraftLimit = overdraftLimit;
        this.monthlyFee = monthlyFee;
    }
    public void applyMonthlyFee(){
        balance -= monthlyFee;
    }
    public boolean isInOverdraft(){
        return balance < 0;
    }
    public double getAvailableBalance(){
        return balance + overdraftLimit;
    }
    @Override
    public boolean withdraw(double amount) throws Exception{
        Exception IllegalArgumentException = new Exception();
        if (balance < 0){
            throw IllegalArgumentException;
        }
        if (balance + overdraftLimit >= amount){
            balance -= amount;
            return true;
        }
        return false;
    }
    @Override
    public String getAccountSummary(){
        return "Current Account " +
                " IBAN " + getIban() +
                " Balance " + balance+
                " Overdraft "+ overdraftLimit +
                " Monthly fee " + monthlyFee;
    }
}
