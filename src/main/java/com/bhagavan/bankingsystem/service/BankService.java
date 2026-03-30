package com.bhagavan.bankingsystem.service;

import com.bhagavan.bankingsystem.model.Account;
import com.bhagavan.bankingsystem.model.Transaction;
import com.bhagavan.bankingsystem.util.IdGenerator;

import java.util.*;

public class BankService {

    private final Map<Long, Account> accounts = new HashMap<>();
    private final List<Transaction> transactions = new ArrayList<>();

    // ================= CREATE ACCOUNT =================

    public Account createAccount(String name, double initialBalance, String pin) {

        if (pin == null || !pin.matches("\\d{4}")) {
            throw new IllegalArgumentException("PIN must be exactly 4 digits");
        }

        long accountNumber = IdGenerator.nextAccountNumber();

        Account acc = new Account(accountNumber, name, initialBalance, pin);
        accounts.put(accountNumber, acc);

        if (initialBalance > 0) {
            transactions.add(new Transaction(
                    UUID.randomUUID().toString(),
                    0,
                    accountNumber,
                    initialBalance,
                    "DEPOSIT"
            ));
        }

        return acc;
    }

    // ================= PIN VERIFICATION =================

    private void verify(long accountNumber, String pin) {
        Account acc = getAccount(accountNumber);
        if (!acc.isPinValid(pin)) {
            throw new SecurityException("Invalid PIN");
        }
    }

    // ================= DEPOSIT =================

    public void deposit(long accountNumber, String pin, double amount) {
        verify(accountNumber, pin);

        Account acc = getAccount(accountNumber);
        acc.deposit(amount);

        transactions.add(new Transaction(
                UUID.randomUUID().toString(),
                0,
                accountNumber,
                amount,
                "DEPOSIT"
        ));
    }

    // ================= WITHDRAW =================

    public void withdraw(long accountNumber, String pin, double amount) {
        verify(accountNumber, pin);

        Account acc = getAccount(accountNumber);
        acc.withdraw(amount);

        transactions.add(new Transaction(
                UUID.randomUUID().toString(),
                accountNumber,
                0,
                amount,
                "WITHDRAW"
        ));
    }

    // ================= TRANSFER =================

    public void transfer(long fromAcc, String pin, long toAcc, double amount) {

        if (fromAcc == toAcc) {
            throw new IllegalArgumentException("Cannot transfer to same account");
        }

        verify(fromAcc, pin);

        Account from = getAccount(fromAcc);
        Account to = getAccount(toAcc);

        if (amount <= 0) {
            throw new IllegalArgumentException("Transfer amount must be > 0");
        }

        from.withdraw(amount);
        to.deposit(amount);

        transactions.add(new Transaction(
                UUID.randomUUID().toString(),
                fromAcc,
                toAcc,
                amount,
                "TRANSFER"
        ));
    }

    // ================= GET ACCOUNT =================

    public Account getAccount(long accountNumber) {
        Account acc = accounts.get(accountNumber);
        if (acc == null) {
            throw new NoSuchElementException("Account not found: " + accountNumber);
        }
        return acc;
    }

    // ================= ACCOUNT TRANSACTIONS =================

    public List<Transaction> getTransactionsForAccount(long accountNumber, String pin) {

        verify(accountNumber, pin);

        List<Transaction> result = new ArrayList<>();

        for (Transaction t : transactions) {
            if (t.getFromAccount() == accountNumber ||
                    t.getToAccount() == accountNumber) {
                result.add(t);
            }
        }

        return result;
    }

    // ================= VIEW ALL =================

    public Collection<Account> getAllAccounts() {
        return Collections.unmodifiableCollection(accounts.values());
    }

    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }
}