package BS;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        System.out.println("====================================");
        System.out.println("🟢 Welcome to Console Bank System 🟢");
        System.out.println("====================================");

        while (running) {
            System.out.println("\n🔐 Please select user type:");
            System.out.println("1. Customer Login");
            System.out.println("2. New Customer Registration");
            System.out.println("3. Admin Login");
            System.out.println("4. Exit");

            System.out.print("Enter your choice: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    Customer customerLogin = new Customer();
                    customerLogin.login();
                    break;

                case "2":
                    Customer newCustomer = new Customer();
                    newCustomer.register();
                    break;

                case "3":
                    Admin admin = new Admin();
                    admin.login();
                    break;

                case "4":
                    running = false;
                    System.out.println("👋 Thank you for using our Bank System!");
                    break;

                default:
                    System.out.println("❌ Invalid choice. Please try again.");
            }
        }

        sc.close();
    }
}

