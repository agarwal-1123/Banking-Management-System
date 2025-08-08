package BS;
import java.sql.*;
import java.util.Random;
import java.util.Scanner;

public class Customer {
    private long accountNumber;
    private String name;
    private String email;
    private String phone;
    private String dob;
    private String password;
    private String pin;
    private double balance;

    Scanner sc = new Scanner(System.in);

    public void register() {
        System.out.println("\n🔰 Customer Registration 🔰");

        // Name
        while (true) {
            System.out.print("Enter your name: ");
            this.name = sc.nextLine().trim();
            if (name.matches("[a-zA-Z ]{2,}")) break;
            System.out.println("❌ Invalid name. Only letters and spaces allowed.");
        }

        // Email
        while (true) {
            System.out.print("Enter your email: ");
            this.email = sc.nextLine().trim();
            if (email.matches("^[\\w.-]+@[\\w.-]+\\.\\w{2,}$")) break;
            System.out.println("❌ Invalid email format.");
        }

        // Phone
        while (true) {
            System.out.print("Enter 10-digit phone number: ");
            this.phone = sc.nextLine();
            if (phone.matches("\\d{10}")) break;
            System.out.println("❌ Invalid phone number. Must be 10 digits.");
        }

        // DOB
        while (true) {
            System.out.print("Enter date of birth (yyyy-mm-dd): ");
            this.dob = sc.nextLine();
            if (dob.matches("\\d{4}-\\d{2}-\\d{2}")) break;
            System.out.println("❌ Invalid DOB format. Use yyyy-mm-dd.");
        }

        // Password
        while (true) {
            System.out.print("Create a strong password (min 8 chars, upper, lower, digit, special): ");
            this.password = sc.nextLine();
            if (isStrongPassword(password)) break;
            System.out.println("❌ Weak password! Try again.");
        }

        // PIN
        while (true) {
            System.out.print("Set 4-digit transaction PIN: ");
            this.pin = sc.nextLine();
            if (pin.matches("\\d{4}")) break;
            System.out.println("❌ PIN must be exactly 4 digits.");
        }

        // Account Number
        this.accountNumber = generateAccountNumber();
        if (this.accountNumber == -1) {
            System.out.println("❌ Failed to generate account number.");
            return;
        }

        // Insert into DB
        try (Connection con = DBConnection.getConnection()) {
            String sql = "INSERT INTO customers(account_no, name, email, phone, dob, password, pin, balance) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setLong(1, this.accountNumber);
            pst.setString(2, this.name);
            pst.setString(3, this.email);
            pst.setString(4, this.phone);
            pst.setString(5, this.dob);
            pst.setString(6, this.password);
            pst.setString(7, this.pin);
            pst.setDouble(8, 0.0);

            int rows = pst.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ Registration successful!");
                System.out.println("🎉 Your 11-digit Account Number is: " + this.accountNumber);
            } else {
                System.out.println("❌ Registration failed.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void login() {
        System.out.println("\n🔐 Customer Login 🔐");
        System.out.print("Enter your Account Number: ");
        long accNo = Long.parseLong(sc.nextLine());
        
        System.out.print("Enter your Password: ");
        String enteredPassword = sc.nextLine();

        try (Connection con = DBConnection.getConnection()) {
            String query = "SELECT * FROM customers WHERE account_no = ? AND password = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setLong(1, accNo);
            pst.setString(2, enteredPassword);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                // Load customer data
                this.accountNumber = rs.getLong("account_no");
                this.name = rs.getString("name");
                this.email = rs.getString("email");
                this.phone = rs.getString("phone");
                this.dob = rs.getString("dob");
                this.password = rs.getString("password");
                this.pin = rs.getString("pin");
                this.balance = rs.getDouble("balance");

                System.out.println("✅ Login successful! Welcome, " + this.name);
                customerMenu();
            } else {
                System.out.println("❌ Invalid account number or password.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void customerMenu() {
        int choice = 0;

        while (choice != 6) {
            System.out.println("\n===== Customer Menu =====");
            System.out.println("1. View Account Details");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Check Balance");
            System.out.println("5. View Last 5 Transactions");
            System.out.println("6. Logout");
            System.out.print("Enter your choice: ");
            choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1 -> viewAccountDetails();
                case 2 -> deposit();
                case 3 -> withdraw();
                case 4 -> System.out.println("💰 Current Balance: ₹" + this.balance);
                case 5 -> viewTransactionHistory();
                case 6 -> System.out.println("👋 Logged out successfully.");
                default -> System.out.println("❌ Invalid choice.");
            }
        }
    }

    private void viewAccountDetails() {
        System.out.println("\n--- Account Details ---");
        System.out.println("Account No: " + accountNumber);
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Phone: " + phone);
        System.out.println("DOB: " + dob);
        System.out.println("Balance: ₹" + balance);

        System.out.print("\nDo you want to update Email or Phone? (yes/no): ");
        String ans = sc.nextLine();
        if (ans.equalsIgnoreCase("yes")) updateEmailOrPhone();
    }

    private void updateEmailOrPhone() {
        System.out.print("Update Email (leave blank to skip): ");
        String newEmail = sc.nextLine().trim();

        System.out.print("Update Phone (leave blank to skip): ");
        String newPhone = sc.nextLine().trim();

        try (Connection con = DBConnection.getConnection()) {
            if (!newEmail.isEmpty()) {
                PreparedStatement pst = con.prepareStatement("UPDATE customers SET email = ? WHERE account_no = ?");
                pst.setString(1, newEmail);
                pst.setLong(2, accountNumber);
                pst.executeUpdate();
                this.email = newEmail;
                System.out.println("✅ Email updated.");
            }

            if (!newPhone.isEmpty()) {
                PreparedStatement pst = con.prepareStatement("UPDATE customers SET phone = ? WHERE account_no = ?");
                pst.setString(1, newPhone);
                pst.setLong(2, accountNumber);
                pst.executeUpdate();
                this.phone = newPhone;
                System.out.println("✅ Phone updated.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deposit() {
        System.out.print("Enter amount to deposit: ");
        double amount = Double.parseDouble(sc.nextLine());

        if (amount <= 0) {
            System.out.println("❌ Invalid amount.");
            return;
        }

        this.balance += amount;
        updateBalanceInDB();
        recordTransaction("Deposit", amount);
        System.out.println("✅ ₹" + amount + " deposited successfully.");
    }

    private void withdraw() {
        System.out.print("Enter amount to withdraw: ");
        double amount = Double.parseDouble(sc.nextLine());

        if (amount <= 0 || amount > this.balance) {
            System.out.println("❌ Invalid or Insufficient balance.");
            return;
        }

        this.balance -= amount;
        updateBalanceInDB();
        recordTransaction("Withdraw", amount);
        System.out.println("✅ ₹" + amount + " withdrawn successfully.");
    }

    private void viewTransactionHistory() {
        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT * FROM transactions WHERE account_no = ? ORDER BY timestamp DESC LIMIT 5";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setLong(1, accountNumber);
            ResultSet rs = pst.executeQuery();

            System.out.println("\n📜 Last 5 Transactions:");
            while (rs.next()) {
                System.out.println(rs.getTimestamp("timestamp") + " - " + rs.getString("type") + ": ₹" + rs.getDouble("amount"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void recordTransaction(String type, double amount) {
        try (Connection con = DBConnection.getConnection()) {
            String sql = "INSERT INTO transactions(account_no, type, amount, timestamp) VALUES (?, ?, ?, NOW())";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setLong(1, accountNumber);
            pst.setString(2, type);
            pst.setDouble(3, amount);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateBalanceInDB() {
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement pst = con.prepareStatement("UPDATE customers SET balance = ? WHERE account_no = ?");
            pst.setDouble(1, this.balance);
            pst.setLong(2, this.accountNumber);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean isStrongPassword(String password) {
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$");
    }

    private long generateAccountNumber() {
        Random rand = new Random();
        long accNo;

        try (Connection con = DBConnection.getConnection()) {
            while (true) {
                accNo = 10000000000L + (long)(rand.nextDouble() * 89999999999L);
                String query = "SELECT account_no FROM customers WHERE account_no = ?";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setLong(1, accNo);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }

        return accNo;
    }

    public long getAccountNumber() { return accountNumber; }
    public String getName() { return name; }
    public double getBalance() { return balance; }
}


