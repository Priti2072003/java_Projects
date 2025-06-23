package ecommerce;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional; // For cleaner way to find items

public class ShoppingCart {
    private Customer customer; // The customer who owns this cart
    private List<OrderItem> items; // Items currently in the cart

    public ShoppingCart(Customer customer) {
        this.customer = customer;
        this.items = new ArrayList<>();
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    // Add product to cart, handles existing items by increasing quantity
    public void addItem(Product product, int quantity) {
        if (product == null || quantity <= 0) {
            System.out.println("Invalid product or quantity.");
            return;
        }
        if (product.getStockQuantity()< quantity) {
            System.out.println("Not enough stock for " + product.getName() + ". Available: " + product.getStockQuantity());
            return;
        }

        // Check if the product already exists in the cart
        Optional<OrderItem> existingItem = items.stream()
                                               .filter(oi -> oi.getProduct().getProductId().equals(product.getProductId()))
                                               .findFirst();

        if (existingItem.isPresent()) {
            // Update quantity of existing item
            OrderItem itemToUpdate = existingItem.get();
            if (product.getStockQuantity() < itemToUpdate.getQuantity() + quantity) {
                 System.out.println("Adding " + quantity + " to cart would exceed available stock for " + product.getName() + ".");
                 System.out.println("Current in cart: " + itemToUpdate.getQuantity() + ", Available: " + product.getStockQuantity());
                 return;
            }
            // For simplicity, we just create a new OrderItem and replace it,
            // or you could add a setter for quantity in OrderItem if it's allowed.
            // A more robust way would be to modify the existing OrderItem's quantity
            // if OrderItem was mutable for quantity.
            int newQuantity = itemToUpdate.getQuantity() + quantity;
            items.remove(itemToUpdate); // Remove old item
            items.add(new OrderItem(product, newQuantity)); // Add new item with updated quantity
            System.out.println("Updated " + product.getName() + " quantity in cart to " + newQuantity);
        } else {
            // Add new item
            items.add(new OrderItem(product, quantity));
            System.out.println("Added " + quantity + " x " + product.getName() + " to cart.");
        }
    }

    // Remove item from cart
    public void removeItem(String productId) {
        boolean removed = items.removeIf(item -> item.getProduct().getProductId().equals(productId));
        if (removed) {
            System.out.println("Product with ID " + productId + " removed from cart.");
        } else {
            System.out.println("Product with ID " + productId + " not found in cart.");
        }
    }

    // View current items in the cart
    public void viewCart() {
        System.out.println("\n===== " + customer.getName() + "'s Shopping Cart =====");
        if (items.isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }
        double total = 0;
        for (OrderItem item : items) {
            item.displayOrderItemDetails();
            total += item.getTotalItemCost();
        }
        System.out.printf("Cart Total: $", total);
        System.out.println("====================================\n");
    }

    // Calculate total amount in the cart
    public double calculateCartTotal() {
        return items.stream()
                    .mapToDouble(OrderItem::getTotalItemCost)
                    .sum();
    }

    // Clear all items from the cart after placing an order
    public void clearCart() {
        items.clear();
        System.out.println("Shopping cart cleared.");
}
}
