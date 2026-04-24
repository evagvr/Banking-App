package models;

import enums.AtmOperationType;
import enums.MerchantCategory;
import enums.TransactionStatus;
import models.transaction.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BankStatement {
    private String statementId;
    private String accountIban;
    private String customerId;
    private LocalDate periodStart;
    private LocalDate periodEnd;
    private List<Transaction> transactions;
    private double openingBalance;
    private double closingBalance;

    public BankStatement(String accountIban, String customerId, LocalDate periodStart,
                         LocalDate periodEnd, double openingBalance) {
        this.statementId = UUID.randomUUID().toString();
        this.accountIban = accountIban;
        this.customerId = customerId;
        this.periodStart = periodStart;
        this.periodEnd = periodEnd;
        this.openingBalance = openingBalance;
        this.closingBalance = openingBalance;
        this.transactions = new ArrayList<>();
    }

    public String getStatementId() { return statementId; }
    public String getAccountIban() { return accountIban; }
    public String getCustomerId() { return customerId; }
    public LocalDate getPeriodStart() { return periodStart; }
    public LocalDate getPeriodEnd() { return periodEnd; }
    public List<Transaction> getTransactions() { return transactions; }
    public double getOpeningBalance() { return openingBalance; }
    public double getClosingBalance() { return closingBalance; }
    public void setClosingBalance(double closingBalance) { this.closingBalance = closingBalance; }

    public void addTransaction(Transaction t) {
        transactions.add(t);
    }

    public double getTotalInflows() {
        double total = 0;
        for (Transaction t : transactions) {
            if (t.getStatus() != TransactionStatus.COMPLETED) continue;
            if (t instanceof ATMTransaction atm) {
                if (atm.getAtmOperationType() == AtmOperationType.DEPOSIT) {
                    total += t.getAmount();
                }
            } else if (t instanceof TransferTransaction transfer) {
                if (transfer.getDestinationAccountIban().equals(this.accountIban)) {
                    total += t.getAmount();
                }
            }
        }
        return total;
    }

    public double getTotalOutflows() {
        double total = 0;
        for (Transaction t : transactions) {
            if (t.getStatus() != TransactionStatus.COMPLETED) continue;
            if (t instanceof ATMTransaction atm) {
                if (atm.getAtmOperationType() == AtmOperationType.WITHDRAWAL) {
                    total += t.getAmount();
                }
            } else if (t instanceof PosTransaction) {
                total += t.getAmount();
            } else if (t instanceof OnlineTransaction) {
                total += t.getAmount();
            } else if (t instanceof TransferTransaction transfer) {
                if (transfer.getSourceAccountIban().equals(this.accountIban)) {
                    total += t.getAmount();
                }
            }
        }
        return total;
    }

    public double getNetPosition() {
        return getTotalInflows() - getTotalOutflows();
    }

    public int getTransactionCount() {
        return transactions.size();
    }

    public List<Transaction> getTransactionsByCategory(MerchantCategory category) {
        List<Transaction> result = new ArrayList<>();
        for (Transaction t : transactions) {
            if (t instanceof PosTransaction pos) {
                if (pos.getMerchantCategory() == category) result.add(pos);
            } else if (t instanceof OnlineTransaction online) {
                if (online.getMerchantCategory() == category) result.add(online);
            }
        }
        return result;
    }
}