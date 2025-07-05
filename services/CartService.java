import java.util.List;

public class CartService {
    private ProductService productService;
    
    public CartService(ProductService productService) {
        this.productService = productService;
    }
    
    public void addToCart(Cart cart, String productName, int quantity) {
        if (cart == null) {
            throw new IllegalArgumentException("Cart cannot be null");
        }
        
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        
        Product product = productService.findProductByName(productName);
        if (product == null) {
            throw new IllegalArgumentException("Product '" + productName + "' not found");
        }
        
        if (productService.isProductExpired(product)) {
            throw new IllegalArgumentException("Product '" + productName + "' is expired");
        }
        
        if (product.getQuantity() < quantity) {
            throw new IllegalArgumentException("Not enough stock for " + productName + 
                ". Available: " + product.getQuantity() + ", Requested: " + quantity);
        }
        
        cart.add(product, quantity);
        System.out.println("Added " + quantity + "x " + productName + " to cart");
    }
    
    public void removeFromCart(Cart cart, String productName) {
        List<CartItem> items = cart.getItems();
        items.removeIf(item -> item.product.getName().equalsIgnoreCase(productName));
        System.out.println("Removed " + productName + " from cart");
    }
    
    public void displayCart(Cart cart) {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty");
            return;
        }
        
        System.out.println("\n=== Shopping Cart ===");
        double total = 0;
        for (CartItem item : cart.getItems()) {
            double itemTotal = item.product.getPrice() * item.quantity;
            total += itemTotal;
            System.out.printf("%dx %s - $%.2f each = $%.2f\n", 
                item.quantity, item.product.getName(), item.product.getPrice(), itemTotal);
        }
        System.out.printf("Cart Total: $%.2f\n", total);
        System.out.println();
    }
    
    public double calculateCartTotal(Cart cart) {
        return cart.getItems().stream()
                .mapToDouble(item -> item.product.getPrice() * item.quantity)
                .sum();
    }
} 