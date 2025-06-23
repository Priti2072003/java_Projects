package ecommerce;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID; // For generating unique IDs

public class Order {
    private String orderId;
    private Customer customer; // Composition: Order "has-a" Customer
    private List<OrderItem> items; // Composition: Order "has-a" multiple OrderItems
    private double totalAmount;
    private LocalDateTime orderDate;
    private String status; // e.g., "PENDING", "PROCESSING", "SHIPPED", "DELIVERED", "CANCELLED"

    // Constructor
    public Order(Customer customer) {
        this.orderId = UUID.randomUUID().toString().substring(0, 8); // Short unique ID
        this.customer = customer;
        this.items = new ArrayList<>();
        this.orderDate = LocalDateTime.now(); // Set current date/time
        this.status = "PENDING"; // Initial status
        this.totalAmount = 0.0; // Will be calculated when items are added
    }

    // Getters
    public String getOrderId() {
        return orderId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public String getStatus() {
        return status;
    }

    // Setters
    public void setStatus(String status) {
        this.status = status;
    }

    // Method to add an item to the order
    public void addOrderItem(OrderItem item) {
        items.add(item);
        calculateTotalAmount(); // Recalculate total whenever an item is added
    }

    // Method to calculate the total amount of the order
    private void calculateTotalAmount() {
        this.totalAmount = items.stream() .mapToDouble(OrderItem::getTotalItemCost).sum();
    }

    // Method to display order details
    public void displayOrderDetails() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println("\n===== Order Details =====");
        System.out.println("Order ID: " + orderId);
        System.out.println("Customer: " + customer.getName() + " (ID: " + customer.getCustomerId() + ")");
        System.out.println("Order Date: " + orderDate.format(formatter));
        System.out.println("Status: " + status);
        System.out.println("Ordered Items:");
        if (items.isEmpty()) {
            System.out.println("   (No items in this order)");
        } else {
            for (OrderItem item : items) {
                item.displayOrderItemDetails();
            }
        }
        System.out.printf("Total Amount: $", totalAmount);
        System.out.println("=========================\n");
    }

    @Override
    public String toString() {
        return "Order{" +
               "orderId='" + orderId + '\'' +
               ", customer=" + customer.getName() +
               ", totalAmount=" + totalAmount +
               ", orderDate=" + orderDate.format(DateTimeFormatter.ISO_LOCAL_DATE) +
               ", status='" + status + '\'' +
        '}';
}
}
