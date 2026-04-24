package models;

import enums.TransactionStatus;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Loan {
    private String loanId;
    private String customerId;
    private String accountIban;
    private double loanAmount;
    private double interestRate;
    private double monthlyInstalment;
    private LocalDate startDate;
    private LocalDate endDate;
    private int totalInstalments;
    private int instalmentsPaid;
    private TransactionStatus status;

    public Loan(String customerId, String accountIban, double loanAmount, double interestRate, LocalDate startDate, LocalDate endDate){
        this.loanId = java.util.UUID.randomUUID().toString();
        this.customerId = customerId;
        this.accountIban = accountIban;
        this.loanAmount = loanAmount;
        this.interestRate = interestRate;
        this.totalInstalments = (int) ChronoUnit.MONTHS.between(startDate, endDate);
        this.startDate = startDate;
        this.endDate = endDate;
        this.instalmentsPaid = 0;
        this.monthlyInstalment = calculateMonthlyInstalment();
        this.status = TransactionStatus.PENDING;
    }
    public double calculateMonthlyInstalment(){
        double monthlyRate = interestRate / 12;
        return (loanAmount * monthlyRate) / (1 - Math.pow((1 + monthlyRate), -totalInstalments));
    }
    public void setStatus(TransactionStatus status){
        this.status = status;
    }
    public String getLoanId() {
        return loanId;
    }
    public String getCustomerId() {
        return customerId;
    }
    public String getAccountIban() {
        return accountIban;
    }

    public double getLoanAmount() {
        return loanAmount;
    }
    public double getInterestRate() {
        return interestRate;
    }
    public double getMonthlyInstalment() {
        return monthlyInstalment;
    }
    public LocalDate getStartDate() {
        return startDate;
    }
    public LocalDate getEndDate() {
        return endDate;
    }
    public int getTotalInstalments() {
        return totalInstalments;
    }
    public int getInstalmentsPaid() {
        return instalmentsPaid;
    }
    public TransactionStatus getStatus() {
        return status;
    }
    public double getRemainingDept(){
        return loanAmount - (instalmentsPaid * monthlyInstalment);
    }
    public boolean isFullyRepaid(){
        return instalmentsPaid >= totalInstalments;
    }
    public void payInstalment(){
        if (!isFullyRepaid()){
            instalmentsPaid += 1;
            if (isFullyRepaid()){
                setStatus(TransactionStatus.COMPLETED);
            }
        }
    }
}
