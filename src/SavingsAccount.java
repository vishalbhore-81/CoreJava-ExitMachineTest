class SavingsAccount extends Account {
    private double interestRate;

    public SavingsAccount(String accountNumber, String name, String contact, String accountType, double balance, double interestRate) {
        super(accountNumber, name, contact, accountType, balance);
        this.interestRate = interestRate;
    }

    public void depositInterest() {
        double interest = balance * interestRate;
        deposit(interest);
        transactions.add("Interest deposited: " + interest + ", Balance: " + balance);
    }
}