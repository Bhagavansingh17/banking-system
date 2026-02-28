package com.bhagavan.bankingsystem;

import com.bhagavan.bankingsystem.service.BankService;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        BankService bankService = new BankService();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n==== Banking System ====");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. View All Accounts");
            System.out.println("6. View Transactions");
            System.out.println("0. Exit");
            System.out.print("Choose: ");

            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            try {
                switch (choice) {
                    case 1 -> {
                        System.out.print("Holder Name: ");
                        String name = sc.nextLine();

                        System.out.print("Initial Balance: ");
                        double bal = sc.nextDouble();

                        var acc = bankService.createAccount(name, bal);
                        System.out.println("✅ Account created! Account Number: " + acc.getAccountNumber());
                    }
                    case 2 -> {
                        System.out.print("Account Number: ");
                        long accNo = sc.nextLong();
                        System.out.print("Amount: ");
                        double amt = sc.nextDouble();

                        bankService.deposit(accNo, amt);
                        System.out.println("✅ Deposited!");
                    }
                    case 3 -> {
                        System.out.print("Account Number: ");
                        long accNo = sc.nextLong();
                        System.out.print("Amount: ");
                        double amt = sc.nextDouble();

                        bankService.withdraw(accNo, amt);
                        System.out.println("✅ Withdraw success!");
                    }
                    case 4 -> {
                        System.out.print("From Account: ");
                        long from = sc.nextLong();
                        System.out.print("To Account: ");
                        long to = sc.nextLong();
                        System.out.print("Amount: ");
                        double amt = sc.nextDouble();

                        bankService.transfer(from, to, amt);
                        System.out.println("✅ Transfer success!");
                    }
                    case 5 -> bankService.getAllAccounts().forEach(System.out::println);
                    case 6 -> bankService.getTransactions().forEach(System.out::println);
                    case 0 -> {
                        System.out.println("Bye 👋");
                        return;
                    }
                    default -> System.out.println("Invalid choice!");
                }
            } catch (Exception e) {
                System.out.println("❌ Error: " + e.getMessage());
            }
        }
    }
}