package services;

import enums.Currency;
import enums.TransactionStatus;
import models.*;
import models.account.Account;
import models.transaction.OnlineTransaction;
import models.transaction.PosTransaction;
import models.transaction.Transaction;
import models.transaction.TransferTransaction;

import java.util.*;

public class BankingAnalyticsService {
    private static BankingAnalyticsService instance = null;
    private final HashMap<String, Customer> clients;
    private final ArrayList<Transaction> transactions;
    private final HashMap<String, Account> accounts;
    private final ExchangeRateService exchangeService;
    public static BankingAnalyticsService getInstance(){
        if (instance == null){
            instance = new BankingAnalyticsService();
        }
        return instance;
    }
    private BankingAnalyticsService(){
        this.clients = new HashMap<>();
        this.transactions = new ArrayList<>();
        this.accounts = new HashMap<>();
        this.exchangeService = ExchangeRateService.getInstance();
    }
    public void registerAccount(Account account) {
        accounts.put(account.getAccountId(), account);
    }
    public void addClient(Customer customer){
        if (clients.containsKey(customer.getUserId())){
            System.out.println("clientul exista deja");
            return;
        }
        clients.put(customer.getUserId(), customer);
    }
    public void processTransaction(Transaction transaction){
        Account sourceAccount = accounts.get(transaction.getAccountId());
        if (sourceAccount == null){
            transaction.setStatus(TransactionStatus.FAILED);
        }
        else{
            if (transaction instanceof DepositTransaction){
                sourceAccount.deposit(transaction.getAmount());
                transaction.setStatus(TransactionStatus.COMPLETED);
            }
            if (transaction instanceof OnlineTransaction || transaction instanceof PosTransaction){
                double total = transaction.getAmount() + transaction.calculateFee();
                boolean success = sourceAccount.withdraw(total);
                if (success){
                    transaction.setStatus(TransactionStatus.COMPLETED);
                }
                else{
                    transaction.setStatus(TransactionStatus.REJECTED_NONSUFFICIENTFUNDS);
                }
            }
            if (transaction instanceof TransferTransaction){
                String targetAccountId = ((TransferTransaction) transaction).getTargetAccountId();
                if (targetAccountId == null){
                    transaction.setStatus(TransactionStatus.FAILED);

                }
                else{
                    double total = transaction.getAmount() + transaction.calculateFee();
                    boolean succes = sourceAccount.withdraw(total);
                    if (!succes){
                        transaction.setStatus(TransactionStatus.REJECTED_NONSUFFICIENTFUNDS);

                    }
                    else {
                        Account destinationAccount = accounts.get(targetAccountId);
                        if (destinationAccount != null){
                            Currency initialCurrency = sourceAccount.getCurrency();
                            Currency finalCurrency = destinationAccount.getCurrency();
                            double totalAmount = exchangeService.convert(transaction.getAmount(), initialCurrency, finalCurrency);
                            destinationAccount.deposit(totalAmount);
                            transaction.setStatus(TransactionStatus.COMPLETED);
                            // TODO cazul external transfer + eroare cu restituire de fonduri
                        }
                    }
                }
            }
        }
        transactions.add(transaction);
    }
    public double getClientNetWorth(String userId){
        Customer customer = clients.get(userId);
        if (customer == null){
            return 0;
        }
        double total = 0.0;
        for (Account a: accounts.values()){
            if (a.getClientId().equals(userId)){
                Currency accCurency = a.getCurrency();
                double accBalance = exchangeService.convert(a.getBalance(), accCurency, Currency.RON);
                total += accBalance;
            }
        }
        return total;
    }
    public List<Transaction> getSpendingAnomalies(String accountId){
        List<Transaction> accountTransactions = new ArrayList<>();
        double sum = 0.0;
        for (Transaction t : transactions){
            if (t.getAccountId().equals(accountId) && t.getStatus() == TransactionStatus.COMPLETED){
                if (t instanceof DepositTransaction)
                    continue;
                accountTransactions.add(t);
                sum += t.getAmount();
            }
        }
        if (accountTransactions.size() < 10)
            return new ArrayList<>();
        double mean = sum / accountTransactions.size();
        double variance =0.0;
        for (Transaction t : accountTransactions){
            variance += Math.pow(t.getAmount()- mean, 2);
        }
        variance = variance / accountTransactions.size();
        double stdDeviation = Math.sqrt(variance);
        List<Transaction> anomalies = new ArrayList<>();
        for (Transaction t : accountTransactions){
            double zScore = Math.abs((t.getAmount() - mean) / stdDeviation);
           if (zScore > 3.0){
               anomalies.add(t);
           }
        }
        return anomalies;
    }
    public List<Transaction> getAllTransactionsSortedByDate(){
        List<Transaction> copie = transactions;
        copie.sort(Comparator.comparing(Transaction::getAmount).reversed());
        return copie;
    }
    public List<Transaction> getSuspiciousOnlineTransactions(double amountTreshold){
        List<Transaction> suspiciousTransactions = new ArrayList<>();
        for( Transaction t : transactions){
            if(t instanceof OnlineTransaction && t.getAmount()> amountTreshold){
                suspiciousTransactions.add(t);
            }
        }
        return suspiciousTransactions;
    }
    public double getTotalSpendingsAccountCategory(String targetAccountId, String targetCategory){
        double total = 0.0;
        for (Transaction t: transactions){
            if (t.getAccountId().equals(targetAccountId) && t.getCategory().equals(targetCategory)){
                total += t.getAmount();
            }
        }
        return total;
    }
}
