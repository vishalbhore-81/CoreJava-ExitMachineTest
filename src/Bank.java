import java.io.*;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
class Bank {
    private Map<String, Account> accounts;
    private ScheduledExecutorService backupService;

    public Bank() {
        accounts = new HashMap<>();
        startBackupService();
    }

    public void addAccount(Account account) {
        accounts.put(account.getAccountNumber(), account);
    }

    public void removeAccount(String accountNumber) {
        accounts.remove(accountNumber);
    }

    public Account searchAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }

    public List<Account> viewAccountsSortedBy(String criteria) {
        List<Account> accountList = new ArrayList<>(accounts.values());
        switch (criteria.toLowerCase()) {
            case "number":
                accountList.sort(Comparator.comparing(Account::getAccountNumber));
                break;
            case "name":
                accountList.sort(Comparator.comparing(Account::getName));
                break;
            case "balance":
                accountList.sort(Comparator.comparingDouble(Account::getBalance).reversed());
                break;
            default:
                System.out.println("Invalid sorting criteria.");
        }
        return accountList;
    }

    public void depositInterest() {
        for (Account account : accounts.values()) {
            if (account instanceof SavingsAccount) {
                ((SavingsAccount) account).depositInterest();
            }
        }
    }

    private void startBackupService() {
        backupService = Executors.newSingleThreadScheduledExecutor();
        backupService.scheduleAtFixedRate(this::backupAccounts, 0, 5, TimeUnit.SECONDS);
    }

    private void backupAccounts() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("backup.txt"))) {
            for (Account account : accounts.values()) {
                writer.write("Account: " + account.getAccountNumber() + ", Name: " + account.getName() + ", Balance: " + account.getBalance());
                writer.newLine();
            }
            System.out.println("Backup completed.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}