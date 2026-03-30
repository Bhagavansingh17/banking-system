package com.bhagavan.bankingsystem.util;

import java.util.concurrent.atomic.AtomicLong;

public class IdGenerator {
    private static final AtomicLong ACCOUNT_SEQ = new AtomicLong(1000000000L); // starts from 1,000,000,000

    private IdGenerator() {}

    public static long nextAccountNumber() {
        return ACCOUNT_SEQ.incrementAndGet();
    }
}