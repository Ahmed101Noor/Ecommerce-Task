import java.time.*;
import java.util.*;

public class ECommerceSystem {
    private static final double SHIPPING_FEE = 30.0;

    public static void checkout(Customer customer, Cart cart) {
        if (cart.isEmpty()) {
            System.out.println("Error: Cart is empty");
            return;
        }
        double subtotal = 0.0;
        double shipping = 0.0;
        List<Shippable> shippables = new ArrayList<>();
        for (CartItem item : cart.getItems()) {
            Product p = item.product;
            if (item.quantity > p.getQuantity()) {
                System.out.println("Error: Not enough stock for " + p.getName());
                return;
            }
            if (p.isExpirable() && p.isExpired()) {
                System.out.println("Error: Product " + p.getName() + " is expired");
                return;
            }
            subtotal += p.getPrice() * item.quantity;
            if (p.isShippable()) {
                for (int i = 0; i < item.quantity; i++) {
                    shippables.add(new Shippable() {
                        public String getName() { return p.getName(); }
                        public double getWeight() { return p.getWeight(); }
                    });
                }
            }
        }
        if (!shippables.isEmpty()) shipping = SHIPPING_FEE;
        double total = subtotal + shipping;
        if (customer.getBalance() < total) {
            System.out.println("Error: Insufficient balance");
            return;
        }
        // Reduce product quantities
        for (CartItem item : cart.getItems()) {
            item.product.reduceQuantity(item.quantity);
        }
        // Ship items
        ShippingService.ship(shippables);
        // Print receipt
        System.out.println("** Checkout receipt **");
        for (CartItem item : cart.getItems()) {
            System.out.printf("%dx %s %.0f\n", item.quantity, item.product.getName(), item.product.getPrice() * item.quantity);
        }
        System.out.println("----------------------");
        System.out.printf("Subtotal %.0f\n", subtotal);
        System.out.printf("Shipping %.0f\n", shipping);
        System.out.printf("Amount %.0f\n", total);
        customer.deduct(total);
        System.out.printf("Customer balance: %.0f\n", customer.getBalance());
        cart.clear();
    }

    public static void main(String[] args) {
        System.out.println("=== E-COMMERCE SYSTEM DEMO ===\n");
        
        ProductService productService = new ProductService();
        CustomerService customerService = new CustomerService();
        ShippingService shippingService = new ShippingService();
        CartService cartService = new CartService(productService);
        CheckoutService checkoutService = new CheckoutService(productService, shippingService);
        
        setupDemoData(productService, customerService);
        
        Customer ali = customerService.findCustomerByName("Ali");
        customerService.displayCustomerInfo(ali);
        productService.displayAllProducts();
        
        Cart cart = new Cart();
        
        try {
            cartService.addToCart(cart, "Cheese", 2);
            cartService.addToCart(cart, "Biscuits", 1);
            cartService.addToCart(cart, "Mobile scratch card", 1);
            
            cartService.displayCart(cart);
            checkoutService.checkout(ali, cart);
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    private static void setupDemoData(ProductService productService, CustomerService customerService) {
        Product cheese = new ExpirableProduct("Cheese", 100, 5, LocalDate.now().plusDays(2), true, 0.4);
        Product biscuits = new ExpirableProduct("Biscuits", 150, 2, LocalDate.now().plusDays(1), true, 0.7);
        Product tv = new NonExpirableProduct("TV", 5000, 3, true, 10.0);
        Product scratchCard = new NonExpirableProduct("Mobile scratch card", 50, 10, false, 0.0);
        
        productService.addProduct(cheese);
        productService.addProduct(biscuits);
        productService.addProduct(tv);
        productService.addProduct(scratchCard);
        
        Customer ali = new Customer("Ali", 1000);
        customerService.addCustomer(ali);
    }
}
