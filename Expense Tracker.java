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
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    private String category;
    private double amount;

    public Expense(String date, String category, double amount) {
        this.date = date;
        this.category = category;
        this.amount = amount;
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

        // Register user
        System.out.println("Register a new user");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        tracker.register(username, password);

        // Login user
        System.out.println("Login");
        System.out.print("Username: ");
        username = scanner.nextLine();
        System.out.print("Password: ");
        password = scanner.nextLine();
        if (!tracker.login(username, password)) {
            System.out.println("Exiting...");
            return;
        }

        // Add expenses
        tracker.addExpense("2024-10-12", "Food", 20.5);
        tracker.addExpense("2024-10-13", "Transport", 15.0);

        // List expenses
        tracker.listExpenses();

        // Category-wise summation
        tracker.categoryWiseSummation();

        // Save and load from file
        try {
            tracker.saveExpenses("expenses.txt");
            tracker.loadExpenses("expenses.txt");
        } catch (IOException e) {
            System.out.println("An error occurred while saving/loading expenses.");
        }
    }
}
