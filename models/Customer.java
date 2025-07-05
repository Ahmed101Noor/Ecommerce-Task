public class Customer {
    private String name;
    private double balance;
    public Customer(String name, double balance) {
        this.name = name;
        this.balance = balance;
    }
    public String getName() { return name; }
    public double getBalance() { return balance; }
    public void deduct(double amount) { this.balance -= amount; }
    public void addBalance(double amount) { this.balance += amount; }
} 