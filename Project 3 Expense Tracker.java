import java.io.*;
import java.util.*;

class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public boolean authenticate(String password) {
        return this.password.equals(password);
    }
}

class Expense {
    private String date;
    private String category;
    private double amount;

    public Expense(String date, String category, double amount) {
        this.date = date;
        this.category = category;
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Date: " + date + ", Category: " + category + ", Amount: " + amount;
    }
}

class ExpenseTracker {
    private List<User> users = new ArrayList<>();
    private List<Expense> expenses = new ArrayList<>();
    private String currentUser;

    public void register(String username, String password) {
        users.add(new User(username, password));
        System.out.println("User registered successfully!");
    }

    public boolean login(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.authenticate(password)) {
                currentUser = username;
                System.out.println("Login successful!");
                return true;
            }
        }
        System.out.println("Invalid username or password.");
        return false;
    }

    public void addExpense(String date, String category, double amount) {
        expenses.add(new Expense(date, category, amount));
        System.out.println("Expense added successfully.");
    }

    public void listExpenses() {
        System.out.println("Listing all expenses:");
        for (Expense expense : expenses) {
            System.out.println(expense);
        }
    }

    public void categoryWiseSummation() {
        Map<String, Double> categorySum = new HashMap<>();
        for (Expense expense : expenses) {
            categorySum.put(expense.getCategory(), categorySum.getOrDefault(expense.getCategory(), 0.0) + expense.getAmount());
        }
        for (Map.Entry<String, Double> entry : categorySum.entrySet()) {
            System.out.println("Category: " + entry.getKey() + ", Total: " + entry.getValue());
        }
    }

    public void saveExpenses(String filename) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        for (Expense expense : expenses) {
            writer.write(expense.toString() + "\n");
        }
        writer.close();
        System.out.println("Expenses saved to file.");
    }

    public void loadExpenses(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        expenses.clear();
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(", ");
            String date = parts[0].split(": ")[1];
            String category = parts[1].split(": ")[1];
            double amount = Double.parseDouble(parts[2].split(": ")[1]);
            expenses.add(new Expense(date, category, amount));
        }
        reader.close();
        System.out.println("Expenses loaded from file.");
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ExpenseTracker tracker = new ExpenseTracker();

        while (true) {
            System.out.println("Welcome to Expense Tracker");
            System.out.println("1. Register as New User");
            System.out.println("2. Login as Existing User");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice == 1) {
                // Register user
                System.out.println("Register a new user");
                System.out.print("Username: ");
                String username = scanner.nextLine();
                System.out.print("Password: ");
                String password = scanner.nextLine();
                tracker.register(username, password);
            } else if (choice == 2) {
                // Login user
                System.out.println("Login");
                System.out.print("Username: ");
                String username = scanner.nextLine();
                System.out.print("Password: ");
                String password = scanner.nextLine();
                if (!tracker.login(username, password)) {
                    System.out.println("Exiting...");
                    return;
                }

                // Input for expenses
                while (true) {
                    System.out.println("1. Add Expense");
                    System.out.println("2. List Expenses");
                    System.out.println("3. Category-wise Summation");
                    System.out.println("4. Save Expenses to File");
                    System.out.println("5. Load Expenses from File");
                    System.out.println("6. Logout");
                    System.out.print("Choose an option: ");
                    int userChoice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    switch (userChoice) {
                        case 1:
                            // Add expenses
                            System.out.print("Enter Date (YYYY-MM-DD): ");
                            String date = scanner.nextLine();
                            System.out.print("Enter Category: ");
                            String category = scanner.nextLine();
                            System.out.print("Enter Amount: ");
                            double amount = scanner.nextDouble();
                            scanner.nextLine(); // Consume newline
                            tracker.addExpense(date, category, amount);
                            break;
                        case 2:
                            // List expenses
                            tracker.listExpenses();
                            break;
                        case 3:
                            // Category-wise summation
                            tracker.categoryWiseSummation();
                            break;
                        case 4:
                            // Save expenses
                            try {
                                tracker.saveExpenses("expenses.txt");
                            } catch (IOException e) {
                                System.out.println("An error occurred while saving expenses.");
                            }
                            break;
                        case 5:
                            // Load expenses
                            try {
                                tracker.loadExpenses("expenses.txt");
                            } catch (IOException e) {
                                System.out.println("An error occurred while loading expenses.");
                            }
                            break;
                        case 6:
                            // Logout
                            System.out.println("Logging out...");
                            break;
                        default:
                            System.out.println("Invalid option. Please try again.");
                    }

                    if (userChoice == 6) {
                        break; // Exit the expense management menu
                    }
                }
            } else if (choice == 3) {
                System.out.println("Exiting...");
                break;
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }

        scanner.close();
    }
}
