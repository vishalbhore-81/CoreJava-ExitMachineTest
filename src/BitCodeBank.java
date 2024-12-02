import java.sql.*;
import java.util.*;

public class BitCodeBank {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/bank";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "bitcode@123";

    private Connection connection;

    public BitCodeBank() {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Add a new account
    public void addAccount(String name, String contact, String accountType, double balance, double interestRate) {
        try {
            String sql = "INSERT INTO accounts (name, contact, account_type, balance, interest_rate) VALUES ()";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, contact);
            stmt.setString(3, accountType);
            stmt.setDouble(4, balance);
            stmt.setDouble(5, interestRate);
            stmt.executeUpdate();
            System.out.println("Account added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Deposit money
    public void deposit(int accountNumber, double amount) {
        try {
            String sql = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setDouble(1, amount);
            stmt.setInt(2, accountNumber);
            stmt.executeUpdate();

            System.out.println("Deposit successful.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Withdraw money
    public void withdraw(int accountNumber, double amount) {
        try {
            if (getBalance(accountNumber) < amount) {
                System.out.println("Insufficient funds.");
                return;
            }

            String sql = "UPDATE accounts SET balance = balance - ? WHERE account_number = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setDouble(1, amount);
            stmt.setInt(2, accountNumber);
            stmt.executeUpdate();

            System.out.println("Withdrawal successful.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Transfer money
    public void transfer(int senderAccount, int recipientAccount, double amount) {
        if (getBalance(senderAccount) < amount) {
            System.out.println("Insufficient funds.");
            return;
        }

        withdraw(senderAccount, amount);
        deposit(recipientAccount, amount);

    }

    // View account balance
    public double getBalance(int accountNumber) {
        try {
            String sql = "SELECT balance FROM accounts WHERE account_number = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, accountNumber);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("balance");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public static void main(String[] args) {
        BitCodeBank bank = new BitCodeBank();

        // Add accounts
        bank.addAccount("Vishal", "1234567890", "Student Savings", 5000, 0.02);
        bank.addAccount("Ajinkya", "0987654321", "General Current", 10000, 0);

        // Perform operations
        bank.deposit(1, 2000);
        bank.withdraw(1, 1000);
        bank.transfer(1, 2, 1500);

    }
}
