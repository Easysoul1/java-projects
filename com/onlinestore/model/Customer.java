package com.onlinestore.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a customer in the online store system.
 * This class manages customer details and aggregates a list of orders placed by the customer.
 */
public class Customer {
    private String id;
    private String name;
    private String email;
    private List<Order> orders; // Aggregation: A customer can have multiple orders

    /**
     * Constructs a new Customer instance.
     *
     * @param id    The unique identifier for the customer.
     * @param name  The name of the customer.
     * @param email The email address of the customer.
     */
    public Customer(String id, String name, String email) {
        // Basic validation for constructor parameters
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer ID cannot be null or empty.");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer name cannot be null or empty.");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer email cannot be null or empty.");
        }

        this.id = id;
        this.name = name;
        this.email = email;
        this.orders = new ArrayList<>(); // Initialize the list of orders
    }

    /**
     * Returns the unique identifier of the customer.
     * @return The customer ID.
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the name of the customer.
     * @return The customer name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the email address of the customer.
     * @return The customer email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Returns an unmodifiable list of orders placed by this customer.
     * This prevents external modification of the customer's order history.
     * @return A list of orders.
     */
    public List<Order> getOrders() {
        return Collections.unmodifiableList(orders);
    }

    /**
     * Adds an order to the customer's list of placed orders.
     * This method is typically called by the Order class upon successful purchase.
     * @param order The order to add.
     */
    public void addOrder(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Cannot add a null order to customer's order list.");
        }
        this.orders.add(order);
    }

    /**
     * Provides a string representation of the Customer object,
     * useful for logging and debugging.
     * @return A formatted string containing customer details.
     */
    @Override
    public String toString() {
        return "Customer{" +
               "id='" + id + '\'' +
               ", name='" + name + '\'' +
               ", email='" + email + '\'' +
               '}';
    }
}
