package com.bhagavan.bankingsystem.model;

import java.time.LocalDateTime;

public class Transaction {
    private final String id;
    private final long fromAccount;
    private final long toAccount;
    private final double amount;
    private final String type; // DEPOSIT / WITHDRAW / TRANSFER
    private final LocalDateTime createdAt;

    public Transaction(String id, long fromAccount, long toAccount, double amount, String type) {
        this.id = id;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.type = type;
        this.createdAt = LocalDateTime.now();
    }

    public String getId() { return id; }
    public long getFromAccount() { return fromAccount; }
    public long getToAccount() { return toAccount; }
    public double getAmount() { return amount; }
    public String getType() { return type; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    @Override
    public String toString() {
        return "Transaction{" +
                "id='" + id + '\'' +
                ", fromAccount=" + fromAccount +
                ", toAccount=" + toAccount +
                ", amount=" + amount +
                ", type='" + type + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}