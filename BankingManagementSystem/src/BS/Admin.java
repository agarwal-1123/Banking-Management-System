package BS;
import java.sql.*;
import java.util.Scanner;

public class Admin {
    private final String adminUsername = "admin";
    private final String adminPassword = "Admin@123";
    Scanner sc = new Scanner(System.in);

    public void login() {
        System.out.println("\n🔐 Admin Login 🔐");
        System.out.print("Enter username: ");
        String username = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();

        if (username.equals(adminUsername) && password.equals(adminPassword)) {
            System.out.println("✅ Welcome, Admin!");
            adminMenu();
        } else {
            System.out.println("❌ Invalid credentials.");
        }
    }

    private void adminMenu() {
        int choice = -1;

        while (choice != 4) {
            System.out.println("\n===== Admin Menu =====");
            System.out.println("1. View All Customers");
            System.out.println("2. View All Transactions");
            System.out.println("3. Delete Customer");
            System.out.println("4. Logout");
            System.out.print("Enter your choice: ");
            choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1 -> viewAllCustomers();
                case 2 -> viewAllTransactions();
                case 3 -> deleteCustomer();
                case 4 -> System.out.println("👋 Admin logged out.");
                default -> System.out.println("❌ Invalid choice.");
            }
        }
    }

    private void viewAllCustomers() {
        System.out.println("\n--- All Registered Customers ---");
        try (Connection con = DBConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM customers")) {

            while (rs.next()) {
                System.out.println("Account No: " + rs.getLong("account_no"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Email: " + rs.getString("email"));
                System.out.println("Phone: " + rs.getString("phone"));
                System.out.println("DOB: " + rs.getString("dob"));
                System.out.println("Balance: ₹" + rs.getDouble("balance"));
                System.out.println("-----------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void viewAllTransactions() {
        System.out.println("\n📄 Transaction History (All Customers)");
        try (Connection con = DBConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM transactions ORDER BY timestamp DESC")) {

            while (rs.next()) {
                System.out.println("Account No: " + rs.getLong("account_no"));
                System.out.println("Type: " + rs.getString("type"));
                System.out.println("Amount: ₹" + rs.getDouble("amount"));
                System.out.println("Time: " + rs.getTimestamp("timestamp"));
                System.out.println("---------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteCustomer() {
        System.out.print("Enter Account Number to delete: ");
        long acc = Long.parseLong(sc.nextLine());

        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement del = con.prepareStatement("DELETE FROM customers WHERE account_no = ?");
            del.setLong(1, acc);
            int rows = del.executeUpdate();

            if (rows > 0) {
                System.out.println("✅ Customer deleted.");
            } else {
                System.out.println("❌ Account not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
