package models.account;

import enums.Currency;

public class SavingsAccount extends Account {
    private double interestRate;
    private int withdrawalLimit;
    private int withdrawals;
    public SavingsAccount( String customerId, Currency currency, double interestRate, int withdrawalLimit){
        super(customerId, currency);
        this.interestRate = interestRate;
        this.withdrawalLimit = withdrawalLimit;
        this.withdrawals = 0;
    }
    public double getInterestRate(){
        return interestRate;
    }
    public int getWithdrawalLimit(){
        return withdrawalLimit;
    }
    public int getWithdrawals(){
        return withdrawals;
    }
    public void resetWithdrawals(){
        withdrawals = 0;
    }
    @Override
    public boolean withdraw(double amount) throws Exception {
        Exception IllegalArgumentException = new Exception();
        if (amount < 0)
            throw IllegalArgumentException;
        if (balance >= amount && withdrawals < withdrawalLimit){
            balance-= amount;
            withdrawals += 1;
            return true;
        }
        return false;
    }
    public void addInterest(){
        balance += balance*interestRate;
    }
    @Override
    public String getAccountSummary(){
        return "Savings Account " +
                " IBAN " + getIban() +
                " Balance " + balance +
                " Rate " + interestRate +
                " Withdrawals left " + withdrawals + "/" + withdrawalLimit;
    }
}
