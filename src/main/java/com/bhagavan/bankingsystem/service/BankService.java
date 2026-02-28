package com.bhagavan.bankingsystem.service;

import com.bhagavan.bankingsystem.exception.InsufficientBalanceException;
import com.bhagavan.bankingsystem.model.Account;
import com.bhagavan.bankingsystem.model.Transaction;
import com.bhagavan.bankingsystem.util.IdGenerator;
import java.util.*;

public class BankService {

    private final Map<Long, Account> accounts = new HashMap<>();
    private final List<Transaction> transactions = new ArrayList<>();

    public Account createAccount(String name, double initialBalance) {
        long accountNumber = IdGenerator.nextAccountNumber();
        return createAccount(accountNumber, name, initialBalance);
    }

    public void deposit(long accountNumber, double amount) {
        Account acc = getAccount(accountNumber);
        acc.deposit(amount);
        transactions.add(new Transaction(UUID.randomUUID().toString(), 0, accountNumber, amount, "DEPOSIT"));
    }

    public void withdraw(long accountNumber, double amount) {
        Account acc = getAccount(accountNumber);
        if (acc.getBalance() < amount) {
            throw new InsufficientBalanceException("Insufficient balance. Current: " + acc.getBalance());
        }
        acc.withdraw(amount);
        transactions.add(new Transaction(UUID.randomUUID().toString(), accountNumber, 0, amount, "WITHDRAW"));
    }

    public void transfer(long fromAcc, long toAcc, double amount) {
        if (fromAcc == toAcc) {
            throw new IllegalArgumentException("Cannot transfer to same account");
        }
        Account from = getAccount(fromAcc);
        Account to = getAccount(toAcc);

        if (amount <= 0) {
            throw new IllegalArgumentException("Transfer amount must be > 0");
        }
        if (from.getBalance() < amount) {
            throw new InsufficientBalanceException("Insufficient balance. Current: " + from.getBalance());
        }

        from.withdraw(amount);
        to.deposit(amount);

        transactions.add(new Transaction(UUID.randomUUID().toString(), fromAcc, toAcc, amount, "TRANSFER"));
    }

    public Account getAccount(long accountNumber) {
        Account acc = accounts.get(accountNumber);
        if (acc == null) {
            throw new NoSuchElementException("Account not found: " + accountNumber);
        }
        return acc;
    }

    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }

    public Collection<Account> getAllAccounts() {
        return Collections.unmodifiableCollection(accounts.values());
    }
}