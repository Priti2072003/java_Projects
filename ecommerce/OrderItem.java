package ecommerce;
// OrderItem represents a specific product and its quantity within an order.
public class OrderItem {
    private Product product; // Composition: OrderItem "has-a" Product
    private int quantity;
    private double itemPriceAtTimeOfOrder; // Store price to reflect historical data

    public OrderItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        // Capture the price at the moment the order item is created
        this.itemPriceAtTimeOfOrder = product.getPrice();
    }

    // Getters
    public Product getProduct(){
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getItemPriceAtTimeOfOrder() {
        return itemPriceAtTimeOfOrder;
    }

    // Calculate total cost for this specific order item
    public double getTotalItemCost() {
        return quantity * itemPriceAtTimeOfOrder;
    }

    public void displayOrderItemDetails() {
        System.out.printf("   - %-30s | Qty: %-3d | Unit Price: $%-8.2f | Subtotal: $%.2f%n",
                          product.getName(), quantity, itemPriceAtTimeOfOrder, getTotalItemCost());
    }

    @Override
    public String toString() {
        return "OrderItem{" +
               "product=" + product.getName() +
               ", quantity=" + quantity +
               ", itemPriceAtTimeOfOrder=" + itemPriceAtTimeOfOrder +
'}';
}
}
