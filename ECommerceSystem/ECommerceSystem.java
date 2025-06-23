package ECommerceSystem;
import ecommerce.*;
import java.util.*; // Import all  classes
import java.util.stream.Collectors;

public class ECommerceSystem {
    private List <Product> products;
    private List<Customer> customers;
    private List<Order> orders;
    private Scanner scanner;

    public ECommerceSystem() {
        this.products = new ArrayList<>();
        this.customers = new ArrayList<>();
        this.orders = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        initializeData(); // Populate some initial data
    }

    // --- Initial Data Setup ---
    private void initializeData() {
        System.out.println("Initializing sample data...");
        // Add sample products
        addProduct(new Product("Laptop Pro", "High-performance laptop", 1200.00, 10));
        addProduct(new Product("Mechanical Keyboard", "RGB backlit, tactile keys", 85.50, 25));
        addProduct(new Product("Wireless Mouse", "Ergonomic design, long battery life", 30.00, 50));
        addProduct(new Product("4K Monitor", "27-inch IPS display", 350.00, 15));
        addProduct(new Product("USB-C Hub", "Multiport adapter", 45.00, 30));

        // Add sample customers
        addCustomer(new Customer("Alice Johnson", "alice@example.com", "123 Main St, Anytown"));
        addCustomer(new Customer("Bob Smith", "bob@example.com", "456 Oak Ave, Somewhere"));
        System.out.println("Sample data initialized.\n");
    }

    // --- Product Management ---
    public void addProduct(Product product) {
        products.add(product);
        System.out.println("Product added: " + product.getName());
    }

    public Product getProductById(String productId) {
        return products.stream()
                       .filter(p -> p.getProductId().equals(productId))
                       .findFirst()
                       .orElse(null);
    }

    public void displayAllProducts() {
        if (products.isEmpty()) {
            System.out.println("No products available.");
            return;
        }
        System.out.println("\n===== All Products =====");
        for (Product product : products) {
            product.displayProductDetails();
        }
        System.out.println("========================\n");
    }

    // --- Customer Management ---
    public void addCustomer(Customer customer) {
        customers.add(customer);
        System.out.println("Customer registered: " + customer.getName());
    }

    public Customer getCustomerById(String customerId) {
        return customers.stream()
                        .filter(c -> c.getCustomerId().equals(customerId))
                        .findFirst()
                        .orElse(null);
    }

    public void displayAllCustomers() {
        if (customers.isEmpty()) {
            System.out.println("No customers registered.");
            return;
        }
        System.out.println("\n===== All Customers =====");
        for (Customer customer : customers) {
            customer.displayCustomerDetails();
        }
        System.out.println("=========================\n");
    }

    // --- Order Management ---
    public void placeOrder(Customer customer, ShoppingCart cart) {
        if (customer == null) {
            System.out.println("Error: No customer selected for placing order.");
            return;
        }
        if (cart.getItems().isEmpty()) {
            System.out.println("Error: Shopping cart is empty. Cannot place an empty order.");
            return;
        }

        Order newOrder = new Order(customer);
        boolean stockProblem = false;

        // Verify stock for all items in the cart before finalizing the order
        for (OrderItem cartItem : cart.getItems()) {
            Product productInStock = getProductById(cartItem.getProduct().getProductId());
            if (productInStock == null || productInStock.getStockQuantity() < cartItem.getQuantity()) {
                System.out.println("ERROR: Insufficient stock for " + cartItem.getProduct().getName() +
                                   ". Available: " + (productInStock != null ? productInStock.getStockQuantity() : "0") +
                                   ", Requested: " + cartItem.getQuantity());
                stockProblem = true;
                break; // Stop processing if any stock issue is found
            }
        }

        if (stockProblem) {
            System.out.println("Order could not be placed due to stock issues.");
            return;
        }

        // If stock is okay, add items to the order and decrease product stock
        for (OrderItem cartItem : cart.getItems()) {
            newOrder.addOrderItem(cartItem);
            // Decrease stock quantity for the actual product
            Product actualProduct = getProductById(cartItem.getProduct().getProductId());
            if (actualProduct != null) {
                actualProduct.setStockQuantity(actualProduct.getStockQuantity() - cartItem.getQuantity());
                System.out.println("Updated stock for " + actualProduct.getName() + " to " + actualProduct.getStockQuantity());
            }
        }

        orders.add(newOrder);
        cart.clearCart(); // Clear the cart after a successful order
        System.out.println("Order placed successfully!");
        newOrder.displayOrderDetails();
    }

    public Order getOrderById(String orderId) {
        return orders.stream()
                     .filter(o -> o.getOrderId().equals(orderId))
                     .findFirst()
                     .orElse(null);
    }

    public void displayAllOrders() {
        if (orders.isEmpty()) {
            System.out.println("No orders placed yet.");
            return;
        }
        System.out.println("\n===== All Orders =====");
        for (Order order : orders) {
            order.displayOrderDetails();
        }
        System.out.println("========================\n");
    }

    public void updateOrderStatus(String orderId, String newStatus) {
        Order order = getOrderById(orderId);
        if (order != null) {
            order.setStatus(newStatus);
            System.out.println("Order " + orderId + " status updated to " + newStatus);
        } else {
            System.out.println("Order with ID " + orderId + " not found.");
        }
    }

    public void displayOrdersByCustomer(String customerId) {
        Customer customer = getCustomerById(customerId);
        if (customer == null) {
            System.out.println("Customer with ID " + customerId + " not found.");
            return;
        }
        List<Order> customerOrders = orders.stream()
                                           .filter(o -> o.getCustomer().getCustomerId().equals(customerId))
                                           .collect(Collectors.toList());

        if (customerOrders.isEmpty()) {
            System.out.println("No orders found for customer " + customer.getName() + " (ID: " + customerId + ").");
            return;
        }
        System.out.println("\n===== Orders for Customer: " + customer.getName() + " =====");
        for (Order order : customerOrders) {
            order.displayOrderDetails();
        }
        System.out.println("=========================================\n");
    }


    // --- User Interface (Console Menus) ---

    public void mainMenu() {
        int choice;
        do {
            System.out.println("\n===== E-commerce System Main Menu =====");
            System.out.println("1. Product Management");
            System.out.println("2. Customer Management");
            System.out.println("3. Order Management");
            System.out.println("4. Shopping (Customer View)");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
                switch(choice) {
                    case 1: productMenu(); break;
                    case 2: customerMenu(); break;
                    case 3: orderMenu(); break;
                    case 4: shoppingMenu(); break;
                    case 0: System.out.println("Exiting E-commerce System. Goodbye!"); break;
                    default: System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                choice = -1; // Reset choice to keep loop running
            }
        } while (choice != 0);
    }

    private void productMenu() {
        int choice;
        do {
            System.out.println("\n===== Product Management =====");
            System.out.println("1. Add New Product");
            System.out.println("2. View All Products");
            System.out.println("3. View Product by ID");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
                switch(choice) {
                    case 1:
                        System.out.print("Enter product name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter product description: ");
                        String desc = scanner.nextLine();
                        System.out.print("Enter product price: ");
                        double price = Double.parseDouble(scanner.nextLine());
                        System.out.print("Enter stock quantity: ");
                        int stock = Integer.parseInt(scanner.nextLine());
                        addProduct(new Product(name, desc, price, stock));
                        break;
                    case 2:
                        displayAllProducts();
                        break;
                    case 3:
                        System.out.print("Enter Product ID: ");
                        String prodId = scanner.nextLine();
                        Product p = getProductById(prodId);
                        if (p != null) {
                            p.displayProductDetails();
                        } else {
                            System.out.println("Product not found.");
                        }
                        break;
                    case 0: break;
                    default: System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number or valid price/stock.");
                choice = -1;
            }
        } while (choice != 0);
    }

    private void customerMenu() {
        int choice;
        do {
            System.out.println("\n===== Customer Management =====");
            System.out.println("1. Register New Customer");
            System.out.println("2. View All Customers");
            System.out.println("3. View Customer by ID");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        System.out.print("Enter customer name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter customer email: ");
                        String email = scanner.nextLine();
                        System.out.print("Enter customer address: ");
                        String address = scanner.nextLine();
                        addCustomer(new Customer(name, email, address));
                        break;
                    case 2:
                        displayAllCustomers();
                        break;
                    case 3:
                        System.out.print("Enter Customer ID: ");
                        String custId = scanner.nextLine();
                        Customer c = getCustomerById(custId);
                        if (c != null) {
                            c.displayCustomerDetails();
                        } else {
                            System.out.println("Customer not found.");
                        }
                        break;
                    case 0: break;
                    default: System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                choice = -1;
            }
        } while (choice != 0);
    }

    private void orderMenu() {
        int choice;
        do {
            System.out.println("\n===== Order Management =====");
            System.out.println("1. View All Orders");
            System.out.println("2. View Order by ID");
            System.out.println("3. Update Order Status");
            System.out.println("4. View Orders by Customer ID");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        displayAllOrders();
                        break;
                    case 2:
                        System.out.print("Enter Order ID: ");
                        String orderId = scanner.nextLine();
                        Order o = getOrderById(orderId);
                        if (o != null) {
                            o.displayOrderDetails();
                        } else {
                            System.out.println("Order not found.");
                        }
                        break;
                    case 3:
                        System.out.print("Enter Order ID to update: ");
                        String updateOrderId = scanner.nextLine();
                        System.out.print("Enter new status (e.g., PENDING, PROCESSING, SHIPPED, DELIVERED, CANCELLED): ");
                        String newStatus = scanner.nextLine().toUpperCase();
                        updateOrderStatus(updateOrderId, newStatus);
                        break;
                    case 4:
                        System.out.print("Enter Customer ID to view orders: ");
                        String custIdForOrders = scanner.nextLine();
                        displayOrdersByCustomer(custIdForOrders);
                        break;
                    case 0: break;
                    default: System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                choice = -1;
            }
        } while (choice != 0);
    }

    private void shoppingMenu() {
        System.out.print("Enter Customer ID to start shopping: ");
        String customerId = scanner.nextLine();
        Customer currentCustomer = getCustomerById(customerId);

        if (currentCustomer == null) {
            System.out.println("Customer not found. Please register first or enter a valid ID.");
            return;
        }

        ShoppingCart cart = new ShoppingCart(currentCustomer);
        int choice;
        do {
            System.out.println("\n===== Shopping for " + currentCustomer.getName() + " =====");
            System.out.println("1. Browse Products");
            System.out.println("2. Add Product to Cart");
            System.out.println("3. View Cart");
            System.out.println("4. Remove Product from Cart");
            System.out.println("5. Place Order");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        displayAllProducts();
                        break;
                    case 2:
                        System.out.print("Enter Product ID to add: ");
                        String prodId = scanner.nextLine();
                        Product p = getProductById(prodId);
                        if (p != null) {
                            System.out.print("Enter quantity: ");
                            int qty = Integer.parseInt(scanner.nextLine());
                            cart.addItem(p, qty);
                        } else {
                            System.out.println("Product not found.");
                        }
                        break;
                    case 3:
                        cart.viewCart();
                        break;
                    case 4:
                        System.out.print("Enter Product ID to remove from cart: ");
                        String removeProdId = scanner.nextLine();
                        cart.removeItem(removeProdId);
                        break;
                    case 5:
                        placeOrder(currentCustomer, cart);
                        break;
                    case 0:
                        if (!cart.getItems().isEmpty()) {
                            System.out.println("Warning: Your cart is not empty. It will be cleared upon exiting shopping menu.");
                            cart.clearCart();
                        }
                        break;
                    default: System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                choice = -1;
            }
        } while(choice!= 0);
}
}

