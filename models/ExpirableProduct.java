import java.time.*;

public class ExpirableProduct extends Product {
    private LocalDate expiryDate;
    private double weight;
    private boolean shippable;

    public ExpirableProduct(String name, double price, int quantity, LocalDate expiryDate, boolean shippable, double weight) {
        super(name, price, quantity);
        this.expiryDate = expiryDate;
        this.shippable = shippable;
        this.weight = weight;
    }

    @Override
    public boolean isExpirable() { return true; }
    @Override
    public boolean isShippable() { return shippable; }
    @Override
    public boolean isExpired() { return expiryDate.isBefore(LocalDate.now()); }
    @Override
    public double getWeight() { return shippable ? weight : 0.0; }
} 