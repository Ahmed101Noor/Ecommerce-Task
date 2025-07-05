# 🛒 E-commerce System

A robust Java-based e-commerce system that demonstrates object-oriented programming principles, design patterns, and comprehensive error handling. This system supports both expirable and non-expirable products, shopping cart management, checkout processes, and shipping services.

## ✨ Features

### 🏪 Product Management
- **Dual Product Types**: Support for both expirable and non-expirable products
- **Inventory Tracking**: Real-time stock management with quantity validation
- **Expiry Date Management**: Automatic detection of expired products
- **Shipping Classification**: Products can be marked as shippable or non-shippable
- **Weight Tracking**: Support for shipping weight calculations

### 🛍️ Shopping Experience
- **Shopping Cart**: Add, remove, and manage cart items
- **Real-time Validation**: Stock availability and expiry date checks
- **Price Calculation**: Automatic subtotal, shipping, and total calculations
- **Cart Display**: Detailed cart contents with itemized pricing

### 💳 Checkout System
- **Balance Validation**: Ensures customer has sufficient funds
- **Stock Deduction**: Automatic inventory updates after successful purchase
- **Shipping Integration**: Automatic shipping service for shippable items
- **Receipt Generation**: Detailed purchase receipts with breakdown
- **Exception Handling**: Comprehensive error handling for various scenarios

### 👥 Customer Management
- **Customer Profiles**: Name and balance management
- **Balance Operations**: Add funds and deduct purchases
- **Customer Information Display**: Detailed customer status

## 🏗️ Architecture

### Project Structure
```
Ecommerce-Task/
├── ECommerceSystem.java          # Main system class with demo
├── models/                       # Data models
│   ├── Product.java             # Abstract base product class
│   ├── ExpirableProduct.java    # Products with expiry dates
│   ├── NonExpirableProduct.java # Products without expiry dates
│   ├── Customer.java            # Customer entity
│   ├── Cart.java                # Shopping cart
│   ├── CartItem.java            # Individual cart items
│   └── Shippable.java           # Shipping interface
├── services/                     # Business logic services
│   ├── ProductService.java      # Product management
│   ├── CustomerService.java     # Customer operations
│   ├── CartService.java         # Cart operations
│   ├── CheckoutService.java     # Checkout process
│   └── ShippingService.java     # Shipping operations
├── exceptions/                   # Custom exceptions
│   ├── EmptyCartException.java
│   ├── ExpiredProductException.java
│   ├── InsufficientBalanceException.java
│   └── OutOfStockException.java
└── utils/
    └── TestRunner.java          # Comprehensive test suite
```

### Design Patterns Used
- **Strategy Pattern**: Different product types with varying behaviors
- **Service Layer Pattern**: Separation of business logic
- **Exception Handling Pattern**: Custom exceptions for specific scenarios
- **Factory Pattern**: Product creation and management

## 🚀 Getting Started

### Prerequisites
- Java 8 or higher
- Git

### Installation
1. Clone the repository:
```bash
git clone https://github.com/yourusername/ecommerce-system.git
cd ecommerce-system
```

2. Compile the Java files:
```bash
javac *.java models/*.java services/*.java exceptions/*.java utils/*.java
```

3. Run the main demo:
```bash
java ECommerceSystem
```

4. Run the comprehensive test suite:
```bash
java utils.TestRunner
```

## 📖 Usage Examples

### Basic Shopping Flow
```java
// Create services
ProductService productService = new ProductService();
CustomerService customerService = new CustomerService();
CartService cartService = new CartService(productService);

// Add products
Product cheese = new ExpirableProduct("Cheese", 100, 5, LocalDate.now().plusDays(2), true, 0.4);
productService.addProduct(cheese);

// Create customer
Customer customer = new Customer("Ali", 1000);
customerService.addCustomer(customer);

// Shopping cart operations
Cart cart = new Cart();
cartService.addToCart(cart, "Cheese", 2);
cartService.displayCart(cart);

// Checkout
CheckoutService checkoutService = new CheckoutService(productService, shippingService);
checkoutService.checkout(customer, cart);
```

### Product Types

#### Expirable Products
```java
// Food items with expiry dates
Product cheese = new ExpirableProduct(
    "Cheese",           // name
    100,                // price
    5,                  // quantity
    LocalDate.now().plusDays(2),  // expiry date
    true,               // shippable
    0.4                 // weight
);
```

#### Non-Expirable Products
```java
// Electronics or digital products
Product tv = new NonExpirableProduct(
    "TV",               // name
    5000,               // price
    3,                  // quantity
    true,               // shippable
    10.0                // weight
);
```

## 🧪 Testing

The system includes a comprehensive test suite (`TestRunner.java`) that covers:

1. **Display Initial Data**: Shows customer and product information
2. **Successful Checkout**: Complete purchase flow
3. **Empty Cart Error**: Validation for empty cart checkout
4. **Insufficient Balance Error**: Customer balance validation
5. **Out of Stock Error**: Inventory validation
6. **Expired Product Error**: Expiry date validation
7. **Add to Cart Validation**: Input validation
8. **Multiple Customers**: Multi-customer scenarios
9. **Add Balance**: Customer balance operations
10. **Complex Orders**: Large order processing

Run the test suite:
```bash
java utils.TestRunner
```

## 🔧 Customization

### Adding New Product Types
Extend the `Product` class to create new product categories:

```java
public class DigitalProduct extends Product {
    private String downloadUrl;
    
    public DigitalProduct(String name, double price, int quantity, String downloadUrl) {
        super(name, price, quantity);
        this.downloadUrl = downloadUrl;
    }
    
    @Override
    public boolean isExpirable() { return false; }
    @Override
    public boolean isShippable() { return false; }
}
```

### Custom Shipping Logic
Implement custom shipping calculations by extending `ShippingService`:

```java
public class CustomShippingService extends ShippingService {
    @Override
    public double calculateShipping(List<Shippable> items) {
        // Custom shipping logic
        return items.size() * 15.0; // $15 per item
    }
}
```

## 🛡️ Error Handling

The system implements comprehensive error handling with custom exceptions:

- **EmptyCartException**: Thrown when attempting to checkout with an empty cart
- **ExpiredProductException**: Thrown when adding expired products to cart
- **InsufficientBalanceException**: Thrown when customer balance is insufficient
- **OutOfStockException**: Thrown when requested quantity exceeds available stock

## 📊 System Requirements

- **Memory**: Minimum 256MB RAM
- **Storage**: 10MB free space
- **Java Version**: 8 or higher
- **Operating System**: Cross-platform (Windows, macOS, Linux)

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👨‍💻 Author

**Your Name**
- GitHub: [@yourusername](https://github.com/yourusername)
- LinkedIn: [Your LinkedIn](https://linkedin.com/in/yourprofile)

## 🙏 Acknowledgments

- Java Collections Framework for data structures
- Java Time API for date handling
- Object-oriented programming principles
- Design patterns implementation

---

⭐ **Star this repository if you find it helpful!** 