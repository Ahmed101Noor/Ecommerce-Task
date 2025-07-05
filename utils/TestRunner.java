/*only this test file  created by AI agent to test multiple scenarios for the e-commerce system */  
import java.time.LocalDate;

public class TestRunner {
    private ProductService productService;
    private CustomerService customerService;
    private CartService cartService;
    private CheckoutService checkoutService;
    private ShippingService shippingService;
    
    public TestRunner() {
        this.productService = new ProductService();
        this.customerService = new CustomerService();
        this.shippingService = new ShippingService();
        this.cartService = new CartService(productService);
        this.checkoutService = new CheckoutService(productService, shippingService);
        setupData();
    }
    
    private void setupData() {
        Product cheese = new ExpirableProduct("Cheese", 100, 5, LocalDate.now().plusDays(2), true, 0.4);
        Product biscuits = new ExpirableProduct("Biscuits", 150, 2, LocalDate.now().plusDays(1), true, 0.7);
        Product tv = new NonExpirableProduct("TV", 5000, 3, true, 10.0);
        Product scratchCard = new NonExpirableProduct("Mobile scratch card", 50, 10, false, 0.0);
        Product expiredMilk = new ExpirableProduct("Milk", 80, 3, LocalDate.now().minusDays(1), true, 1.0);
        
        productService.addProduct(cheese);
        productService.addProduct(biscuits);
        productService.addProduct(tv);
        productService.addProduct(scratchCard);
        productService.addProduct(expiredMilk);
        
        Customer ali = new Customer("Ali", 1000);
        Customer sarah = new Customer("Sarah", 200);
        Customer john = new Customer("John", 50);
        
        customerService.addCustomer(ali);
        customerService.addCustomer(sarah);
        customerService.addCustomer(john);
    }
    
    public void runAllTests() {
        System.out.println("=== E-COMMERCE SYSTEM TEST SUITE ===\n");
        
        test1_DisplayInitialData();
        test2_SuccessfulCheckout();
        test3_EmptyCartError();
        test4_InsufficientBalanceError();
        test5_OutOfStockError();
        test6_ExpiredProductError();
        test7_AddToCartValidation();
        test8_MultipleCustomers();
        test9_AddBalance();
        test10_ComplexOrder();
        
        System.out.println("=== ALL TESTS COMPLETED ===");
    }
    
    private void test1_DisplayInitialData() {
        System.out.println("Test 1: Display Initial Data");
        System.out.println("============================");
        customerService.displayCustomerInfo(customerService.findCustomerByName("Ali"));
        productService.displayAllProducts();
    }
    
    private void test2_SuccessfulCheckout() {
        System.out.println("Test 2: Successful Checkout (Original Example)");
        System.out.println("=============================================");
        
        Customer customer = customerService.findCustomerByName("Ali");
        Cart cart = new Cart();
        
        try {
            cartService.addToCart(cart, "Cheese", 2);
            cartService.addToCart(cart, "Biscuits", 1);
            cartService.addToCart(cart, "Mobile scratch card", 1);
            
            cartService.displayCart(cart);
            checkoutService.checkout(customer, cart);
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    private void test3_EmptyCartError() {
        System.out.println("\nTest 3: Empty Cart Error");
        System.out.println("=======================");
        
        Customer customer = customerService.findCustomerByName("Ali");
        Cart cart = new Cart();
        
        try {
            checkoutService.checkout(customer, cart);
        } catch (EmptyCartException e) {
            System.out.println("Expected Error: " + e.getMessage());
        }
    }
    
    private void test4_InsufficientBalanceError() {
        System.out.println("\nTest 4: Insufficient Balance Error");
        System.out.println("=================================");
        
        Customer customer = customerService.findCustomerByName("John");
        Cart cart = new Cart();
        
        try {
            cartService.addToCart(cart, "TV", 1);
            cartService.displayCart(cart);
            checkoutService.checkout(customer, cart);
        } catch (InsufficientBalanceException e) {
            System.out.println("Expected Error: " + e.getMessage());
        }
    }
    
    private void test5_OutOfStockError() {
        System.out.println("\nTest 5: Out of Stock Error");
        System.out.println("==========================");
        
        Customer customer = customerService.findCustomerByName("Ali");
        Cart cart = new Cart();
        
        try {
            cartService.addToCart(cart, "Biscuits", 5);
        } catch (IllegalArgumentException e) {
            System.out.println("Expected Error: " + e.getMessage());
        }
    }
    
    private void test6_ExpiredProductError() {
        System.out.println("\nTest 6: Expired Product Error");
        System.out.println("=============================");
        
        Customer customer = customerService.findCustomerByName("Ali");
        Cart cart = new Cart();
        
        try {
            cartService.addToCart(cart, "Milk", 1);
        } catch (IllegalArgumentException e) {
            System.out.println("Expected Error: " + e.getMessage());
        }
    }
    
    private void test7_AddToCartValidation() {
        System.out.println("\nTest 7: Add to Cart Validation");
        System.out.println("==============================");
        
        Cart cart = new Cart();
        
        try {
            cartService.addToCart(cart, "NonExistentProduct", 1);
        } catch (IllegalArgumentException e) {
            System.out.println("Expected Error: " + e.getMessage());
        }
        
        try {
            cartService.addToCart(cart, "Cheese", -1);
        } catch (IllegalArgumentException e) {
            System.out.println("Expected Error: " + e.getMessage());
        }
    }
    
    private void test8_MultipleCustomers() {
        System.out.println("\nTest 8: Multiple Customers");
        System.out.println("==========================");
        
        Customer sarah = customerService.findCustomerByName("Sarah");
        Cart cart = new Cart();
        
        try {
            cartService.addToCart(cart, "Cheese", 1);
            cartService.addToCart(cart, "Mobile scratch card", 2);
            
            cartService.displayCart(cart);
            checkoutService.checkout(sarah, cart);
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    private void test9_AddBalance() {
        System.out.println("\nTest 9: Add Balance");
        System.out.println("===================");
        
        Customer john = customerService.findCustomerByName("John");
        customerService.displayCustomerInfo(john);
        customerService.addBalance(john, 500);
        customerService.displayCustomerInfo(john);
    }
    
    private void test10_ComplexOrder() {
        System.out.println("\nTest 10: Complex Order");
        System.out.println("======================");
        
        Customer ali = customerService.findCustomerByName("Ali");
        Cart cart = new Cart();
        
        try {
            cartService.addToCart(cart, "TV", 1);
            cartService.addToCart(cart, "Cheese", 1);
            cartService.addToCart(cart, "Mobile scratch card", 3);
            
            cartService.displayCart(cart);
            checkoutService.checkout(ali, cart);
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        TestRunner runner = new TestRunner();
        runner.runAllTests();
    }
} 