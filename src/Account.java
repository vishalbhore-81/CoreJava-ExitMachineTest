import java.io.*;
import java.util.ArrayList;
import java.util.List;

abstract class Account {
    protected String accountNumber;
    protected String name;
    protected String contact;
    protected String accountType;
    protected double balance;
    protected List<String> transactions;

    public Account(String accountNumber, String name, String contact, String accountType, double balance) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.contact = contact;
        this.accountType = accountType;
        this.balance = balance;
        this.transactions = new ArrayList<>();
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            transactions.add("Deposited: " + amount + ", Balance: " + balance);
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            transactions.add("Withdrawn: " + amount + ", Balance: " + balance);
        }
    }

    public void transfer(double amount, Account recipient) {
        if (amount > 0 && balance >= amount) {
            this.withdraw(amount);
            recipient.deposit(amount);
            transactions.add("Transferred: " + amount + " to " + recipient.accountNumber);
            recipient.transactions.add("Received: " + amount + " from " + this.accountNumber);
        }
    }

    public void viewStatement() {
        transactions.forEach(System.out::println);
    }

    public void exportStatement(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String transaction : transactions) {
                writer.write(transaction);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }
}
