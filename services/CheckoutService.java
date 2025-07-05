import java.util.ArrayList;
import java.util.List;

public class CheckoutService {
    private static final double SHIPPING_FEE = 30.0;
    private ProductService productService;
    private ShippingService shippingService;
    
    public CheckoutService(ProductService productService, ShippingService shippingService) {
        this.productService = productService;
        this.shippingService = shippingService;
    }
    
    public void checkout(Customer customer, Cart cart) {
        validateCheckout(customer, cart);
        
        double subtotal = calculateSubtotal(cart);
        double shipping = calculateShipping(cart);
        double total = subtotal + shipping;
        
        if (customer.getBalance() < total) {
            throw new InsufficientBalanceException("Insufficient balance. Required: $" + total + ", Available: $" + customer.getBalance());
        }
        
        processOrder(customer, cart, subtotal, shipping, total);
    }
    
    private void validateCheckout(Customer customer, Cart cart) {
        if (cart.isEmpty()) {
            throw new EmptyCartException("Cannot checkout with empty cart");
        }
        
        for (CartItem item : cart.getItems()) {
            Product product = item.product;
            
            if (item.quantity > product.getQuantity()) {
                throw new OutOfStockException("Not enough stock for " + product.getName() + 
                    ". Available: " + product.getQuantity() + ", Requested: " + item.quantity);
            }
            
            if (productService.isProductExpired(product)) {
                throw new ExpiredProductException("Product " + product.getName() + " is expired");
            }
        }
    }
    
    private double calculateSubtotal(Cart cart) {
        return cart.getItems().stream()
                .mapToDouble(item -> item.product.getPrice() * item.quantity)
                .sum();
    }
    
    private double calculateShipping(Cart cart) {
        boolean hasShippableItems = cart.getItems().stream()
                .anyMatch(item -> item.product.isShippable());
        return hasShippableItems ? SHIPPING_FEE : 0.0;
    }
    
    private void processOrder(Customer customer, Cart cart, double subtotal, double shipping, double total) {
        List<Shippable> shippableItems = collectShippableItems(cart);
        
        updateProductStock(cart);
        customer.deduct(total);
        
        if (!shippableItems.isEmpty()) {
            shippingService.ship(shippableItems);
        }
        
        printReceipt(cart, subtotal, shipping, total, customer);
        cart.clear();
    }
    
    private List<Shippable> collectShippableItems(Cart cart) {
        List<Shippable> shippableItems = new ArrayList<>();
        for (CartItem item : cart.getItems()) {
            if (item.product.isShippable()) {
                for (int i = 0; i < item.quantity; i++) {
                    shippableItems.add(new Shippable() {
                        public String getName() { return item.product.getName(); }
                        public double getWeight() { return item.product.getWeight(); }
                    });
                }
            }
        }
        return shippableItems;
    }
    
    private void updateProductStock(Cart cart) {
        for (CartItem item : cart.getItems()) {
            productService.updateProductStock(item.product.getName(), item.quantity);
        }
    }
    
    private void printReceipt(Cart cart, double subtotal, double shipping, double total, Customer customer) {
        System.out.println("\n** Checkout Receipt **");
        for (CartItem item : cart.getItems()) {
            System.out.printf("%dx %s $%.0f\n", 
                item.quantity, item.product.getName(), item.product.getPrice() * item.quantity);
        }
        System.out.println("----------------------");
        System.out.printf("Subtotal %.0f\n", subtotal);
        System.out.printf("Shipping %.0f\n", shipping);
        System.out.printf("Amount %.0f\n", total);
        System.out.printf("Customer balance: %.0f\n", customer.getBalance());
    }
} 