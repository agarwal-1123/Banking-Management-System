package BS;

import java.time.LocalDateTime;

public class Transaction {
    private int transactionId;
    private long accountNumber;
    private String type; // deposit / withdraw
    private double amount;
    private LocalDateTime timestamp;

    public Transaction(int transactionId, long accountNumber, String type, double amount, LocalDateTime timestamp) {
        this.transactionId = transactionId;
        this.accountNumber = accountNumber;
        this.type = type;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public int getTransactionId() { return transactionId; }
    public long getAccountNumber() { return accountNumber; }
    public String getType() { return type; }
    public double getAmount() { return amount; }
    public LocalDateTime getTimestamp() { return timestamp; }
}
