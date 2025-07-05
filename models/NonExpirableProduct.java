public class NonExpirableProduct extends Product {
    private boolean shippable;
    private double weight;

    public NonExpirableProduct(String name, double price, int quantity, boolean shippable, double weight) {
        super(name, price, quantity);
        this.shippable = shippable;
        this.weight = weight;
    }

    @Override
    public boolean isExpirable() { return false; }
    @Override
    public boolean isShippable() { return shippable; }
    @Override
    public double getWeight() { return shippable ? weight : 0.0; }
} 