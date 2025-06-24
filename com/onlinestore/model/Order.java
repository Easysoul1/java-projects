package com.onlinestore.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Represents an order placed by a customer, containing a list of products.
 * Implements the Purchasable interface to signify that an order itself can be purchased.
 * This class demonstrates aggregation (Customer and Product lists).
 */
public class Order implements Purchasable {
    private String orderId;
    private Customer customer; // Aggregation: An order belongs to a customer
    private List<Product> products; // Aggregation: An order contains products
    private LocalDateTime orderDate;
    private double subTotalAmount; // Total before discount
    private double finalTotalAmount; // Total after discount
    private boolean isPurchased; // Status flag for the order
    private double discountApplied; // Amount of discount applied

    // Constants for bonus discount logic
    private static final double DISCOUNT_THRESHOLD = 1000.0; // Amount above which discount applies
    private static final double DISCOUNT_PERCENTAGE = 0.10; // 10% discount

    /**
     * Constructs a new Order.
     *
     * @param customer The customer placing the order. Cannot be null.
     * @param products The list of products in the order. Cannot be null or empty.
     * @throws IllegalArgumentException if customer is null or products list is null/empty.
     */
    public Order(Customer customer, List<Product> products) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null for an order.");
        }
        if (products == null || products.isEmpty()) {
            throw new IllegalArgumentException("Products list cannot be null or empty for an order.");
        }

        this.orderId = UUID.randomUUID().toString(); // Generate a unique order ID
        this.customer = customer;
        // Create a defensive copy of the products list to prevent external modification
        this.products = new ArrayList<>(products);
        this.orderDate = LocalDateTime.now();
        this.isPurchased = false; // Order is pending by default
        this.discountApplied = 0.0;
        calculateTotals(); // Calculate initial totals and apply discount if applicable
    }

    // --- Getters for Order properties ---

    public String getOrderId() {
        return orderId;
    }

    public Customer getCustomer() {
        return customer;
    }

    /**
     * Returns an unmodifiable list of products in this order.
     * This prevents external modification of the order's product list after creation.
     * @return An unmodifiable list of products.
     */
    public List<Product> getProducts() {
        return Collections.unmodifiableList(products);
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public double getSubTotalAmount() {
        return subTotalAmount;
    }

    public double getFinalTotalAmount() {
        return finalTotalAmount;
    }

    public boolean isPurchased() {
        return isPurchased;
    }

    public double getDiscountApplied() {
        return discountApplied;
    }

    /**
     * Recalculates the sub-total amount based on the products in the order.
     * This method should be called internally whenever the product list changes (though not exposed publicly).
     * It also triggers the discount application.
     */
    private void calculateTotals() {
        this.subTotalAmount = products.stream()
                                    .mapToDouble(Product::getPrice)
                                    .sum();
        // Initially, final total is the subtotal
        this.finalTotalAmount = this.subTotalAmount;
        applyDiscount(); // Apply discount after calculating sub-total
    }

    /**
     * Applies a bonus discount if the sub-total amount exceeds the defined threshold.
     * This is a bonus implementation.
     */
    private void applyDiscount() {
        if (subTotalAmount > DISCOUNT_THRESHOLD) {
            this.discountApplied = subTotalAmount * DISCOUNT_PERCENTAGE;
            this.finalTotalAmount = subTotalAmount - discountApplied;
            System.out.printf("  [INFO] Discount of $%.2f (%.0f%%) applied for order %s (Subtotal: $%.2f).%n",
                              discountApplied, DISCOUNT_PERCENTAGE * 100, orderId, subTotalAmount);
        } else {
            this.discountApplied = 0.0; // No discount applied
            this.finalTotalAmount = subTotalAmount; // Final total remains subtotal
        }
    }

    /**
     * Implements the purchase method from the Purchasable interface.
     * Simulates the completion of the purchase process for this order.
     * Marks the order as purchased and adds it to the customer's order history.
     */
    @Override
    public void purchase() {
        if (!isPurchased) {
            System.out.println("\n--- Initiating purchase for Order ID: " + orderId + " ---");
            // In a real system, this would involve complex logic like:
            // 1. Payment gateway integration
            // 2. Inventory deduction for each product
            // 3. Updating order status in a database
            // 4. Sending confirmation emails to customer and store
            // 5. Logging the transaction

            this.isPurchased = true; // Mark order as purchased
            customer.addOrder(this); // Add this order to the customer's list of orders (aggregation)
            System.out.println("Purchase successful for Order ID: " + orderId + " by " + customer.getName() + ".");
        } else {
            System.out.println("Order ID: " + orderId + " has already been purchased. No action taken.");
        }
    }

    /**
     * Generates and prints a detailed receipt for the order.
     * This method provides a clear summary of the order details, products, and total amounts.
     */
    public void generateReceipt() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        System.out.println("\n========================================");
        System.out.println("            ORDER RECEIPT");
        System.out.println("========================================");
        System.out.println("Order ID: " + orderId);
        System.out.println("Order Date: " + orderDate.format(formatter));
        System.out.println("Customer: " + customer.getName() + " (ID: " + customer.getId() + ")");
        System.out.println("Email: " + customer.getEmail());
        System.out.println("----------------------------------------");
        System.out.println("Products:");
        if (products.isEmpty()) {
            System.out.println("  No products in this order.");
        } else {
            for (Product product : products) {
                System.out.printf("  - %s (ID: %s) @ $%.2f%n", product.getName(), product.getId(), product.getPrice());
            }
        }
        System.out.println("----------------------------------------");
        System.out.printf("Subtotal: $%.2f%n", subTotalAmount);
        if (discountApplied > 0) {
            System.out.printf("Discount (%.0f%%): -$%.2f%n", DISCOUNT_PERCENTAGE * 100, discountApplied);
        }
        System.out.printf("Total Amount: $%.2f%n", finalTotalAmount);
        System.out.println("Status: " + (isPurchased ? "PURCHASED" : "PENDING"));
        System.out.println("========================================\n");
    }
}
