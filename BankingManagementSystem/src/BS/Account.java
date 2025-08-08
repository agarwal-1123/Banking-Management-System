package BS;

public class Account {
    private long accountNumber; // System-generated
    private int customerId;     // FK from Customer
    private String accountType="savings";
    private double balance;

    public Account() {}

    public Account(long accountNumber, int customerId, double balance) {
        this.accountNumber = accountNumber;
        this.customerId = customerId;
       
        this.balance = balance;
    }

    // Getters and Setters
    public long getAccountNumber() { return accountNumber; }
    public void setAccountNumber(long accountNumber) { this.accountNumber = accountNumber; }

    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }

    public String getAccountType() { return accountType; }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }
}

