import java.util.ArrayList;
import java.util.List;

public class CustomerService {
    private List<Customer> customers = new ArrayList<>();
    
    public void addCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }
        customers.add(customer);
    }
    
    public Customer findCustomerByName(String name) {
        return customers.stream()
                .filter(c -> c.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }
    
    public void displayCustomerInfo(Customer customer) {
        if (customer == null) {
            System.out.println("Customer not found");
            return;
        }
        
        System.out.println("\n=== Customer Information ===");
        System.out.println("Name: " + customer.getName());
        System.out.printf("Balance: $%.2f\n", customer.getBalance());
        System.out.println();
    }
    
    public void addBalance(Customer customer, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        customer.addBalance(amount);
        System.out.printf("Added $%.2f to %s's balance\n", amount, customer.getName());
    }
    
    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customers);
    }
} 