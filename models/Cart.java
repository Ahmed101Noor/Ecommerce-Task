import java.util.*;

public class Cart {
    private List<CartItem> items = new ArrayList<>();

    public void add(Product product, int quantity) {
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be positive");
        if (quantity > product.getQuantity()) throw new IllegalArgumentException("Not enough stock for " + product.getName());
        for (CartItem item : items) {
            if (item.product == product) {
                if (item.quantity + quantity > product.getQuantity())
                    throw new IllegalArgumentException("Not enough stock for " + product.getName());
                item.quantity += quantity;
                return;
            }
        }
        items.add(new CartItem(product, quantity));
    }

    public List<CartItem> getItems() { return items; }
    public boolean isEmpty() { return items.isEmpty(); }
    public void clear() { items.clear(); }
} 