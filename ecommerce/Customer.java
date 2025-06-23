package ecommerce; 
import java.util.UUID; // For generating unique IDs

public class Customer {
    private String customerId;// for customerId
    private String name;
    private String email;
    private String address; // Simple address string for this project

    // Constructor
    public Customer(String name, String email, String address) {
        this.customerId = UUID.randomUUID().toString().substring(0, 8); // Generate a short unique ID
        this.name = name;
        this.email = email;
        this.address = address;
    }

    // Getters
    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    // Setter for address (customers might update their address)
    public void setAddress(String address) {
        this.address = address;
    }

    // Method to display customer details
    public void displayCustomerDetails() {
        System.out.println("--------------------");
        System.out.println("Customer ID: " + customerId);
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Address: " + address);
        System.out.println("--------------------");
    }

   @Override
    public String toString() {
        return "Customer{" +
               "customerId='" + customerId + '\'' +
               ", name='" + name + '\'' +
               ", email='" + email + '\'' +
        '}';
}
}
