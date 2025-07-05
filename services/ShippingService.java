import java.util.*;

public class ShippingService {
    public static void ship(List<Shippable> items) {
        if (items.isEmpty()) return;
        System.out.println("** Shipment notice **");
        double totalWeight = 0.0;
        Map<String, Integer> nameCount = new LinkedHashMap<>();
        Map<String, Double> nameWeight = new HashMap<>();
        for (Shippable item : items) {
            nameCount.put(item.getName(), nameCount.getOrDefault(item.getName(), 0) + 1);
            nameWeight.put(item.getName(), item.getWeight());
            totalWeight += item.getWeight();
        }
        for (String name : nameCount.keySet()) {
            int count = nameCount.get(name);
            double weight = nameWeight.get(name);
            System.out.printf("%dx %s %.0fg\n", count, name, weight * 1000);
        }
        System.out.printf("Total package weight %.1fkg\n", totalWeight);
    }
} 