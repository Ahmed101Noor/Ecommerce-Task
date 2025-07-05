import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProductService {
    private List<Product> products = new ArrayList<>();
    
    public void addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        products.add(product);
    }
    
    public Product findProductByName(String name) {
        return products.stream()
                .filter(p -> p.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }
    
    public List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }
    
    public boolean isProductAvailable(String name, int quantity) {
        Product product = findProductByName(name);
        return product != null && product.getQuantity() >= quantity && !isProductExpired(product);
    }
    
    public boolean isProductExpired(Product product) {
        return product.isExpirable() && product.isExpired();
    }
    
    public void updateProductStock(String name, int quantity) {
        Product product = findProductByName(name);
        if (product != null) {
            product.reduceQuantity(quantity);
        }
    }
    
    public void displayAllProducts() {
        System.out.println("\n=== Available Products ===");
        for (Product product : products) {
            String status = isProductExpired(product) ? " [EXPIRED]" : "";
            System.out.printf("%s - $%.2f (Qty: %d)%s\n", 
                product.getName(), product.getPrice(), product.getQuantity(), status);
        }
        System.out.println();
    }
} 