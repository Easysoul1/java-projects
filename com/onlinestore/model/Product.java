package com.onlinestore.model;

/**
 * Represents a product available for sale in the online store.
 * This class encapsulates product details such as ID, name, and price.
 */
public class Product {
    private String id;
    private String name;
    private double price;

    /**
     * Constructs a new Product instance.
     *
     * @param id    The unique identifier for the product.
     * @param name  The name of the product.
     * @param price The price of the product.
     */
    public Product(String id, String name, double price) {
        // Basic validation for constructor parameters
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Product ID cannot be null or empty.");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be null or empty.");
        }
        if (price <= 0) {
            throw new IllegalArgumentException("Product price must be positive.");
        }

        this.id = id;
        this.name = name;
        this.price = price;
    }

    /**
     * Returns the unique identifier of the product.
     * @return The product ID.
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the name of the product.
     * @return The product name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the price of the product.
     * @return The product price.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Provides a string representation of the Product object,
     * useful for logging and debugging.
     * @return A formatted string containing product details.
     */
    @Override
    public String toString() {
        return "Product{" +
               "id='" + id + '\'' +
               ", name='" + name + '\'' +
               ", price=" + String.format("%.2f", price) + // Format price to 2 decimal places
               '}';
    }
}
