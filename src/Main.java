import java.util.List;

public class Main {
    public static void main(String[] args) {
        Bank bank = new Bank();


        bank.addAccount(new SavingsAccount("1", "vishal", "1234567890", "Student Savings", 5000, 0.02));
        bank.addAccount(new CurrentAccount("2", "ajinkya", "0987654321", "General Current", 10000));


        Account vishal = bank.searchAccount("1");
        Account ajinkya = bank.searchAccount("2");

        vishal.deposit(2000);
        vishal.withdraw(500);
        vishal.transfer(1000, ajinkya);

        // View and export statement
        vishal.viewStatement();
        vishal.exportStatement("vishal_statement.txt");

        // View sorted accounts
        List<Account> sortedAccounts = bank.viewAccountsSortedBy("balance");
        sortedAccounts.forEach(acc -> System.out.println(acc.getName() + ": " + acc.getBalance()));


        bank.depositInterest();


        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


