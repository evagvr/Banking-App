package services;

import models.account.Account;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

public class AccountService {
    private HashMap<String, Account> accountsByIban;
    private HashMap<String, List<Account>> accountsByCustomer;
    private TreeMap<Double, List<Account>> accountsByBalance;
    private static AccountService instance = null;

    private AccountService() {
        this.accountsByIban = new HashMap<>();
        this.accountsByCustomer = new HashMap<>();
        this.accountsByBalance = new TreeMap<>();
    }

    public static AccountService getInstance() {
        if (instance == null) {
            instance = new AccountService();
        }
        return instance;
    }
    public void addAccount(Account account) {
        accountsByIban.put(account.getIban(), account);
        accountsByCustomer
                .computeIfAbsent(account.getCustomerId(), k -> new ArrayList<>())
                .add(account);
        accountsByBalance
                .computeIfAbsent(account.getBalance(), k -> new ArrayList<>())
                .add(account);
    }

    public Account getAccountByIban(String iban) {
        return accountsByIban.get(iban);
    }

    public List<Account> getAccountsByCustomer(String customerId) {
        return accountsByCustomer.getOrDefault(customerId, new ArrayList<>());
    }

    public TreeMap<Double, List<Account>> getRankedAccountsByBalance() {
        return accountsByBalance;
    }
    public void removeAccount(String iban) {
        Account account = getAccountByIban(iban);
        if (account != null) {
            accountsByIban.remove(iban);
            List<Account> balanceGroup = accountsByBalance.get(account.getBalance());
            if (balanceGroup != null) {
                balanceGroup.remove(account);
                if (balanceGroup.isEmpty()) {
                    accountsByBalance.remove(account.getBalance());
                }
            }
            List<Account> customerAccounts = accountsByCustomer.get(account.getCustomerId());
            if (customerAccounts != null) {
                customerAccounts.remove(account);
            }
        }
    }

    public double getTotalBalanceForCustomer(String customerId) {
        List<Account> accounts = getAccountsByCustomer(customerId);
        double total = 0;
        for (Account account : accounts) {
            total += account.getBalance();
        }
        return total;
    }
    public List<Account> getAllAccounts() {
        return new ArrayList<>(accountsByIban.values());
    }

    public int getAccountCount() {
        return accountsByIban.size();
    }

}
