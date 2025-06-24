package com.onlinestore.model;

/**
 * Interface defining the contract for any item that can be purchased.
 * This provides a common behavior for different purchasable entities.
 */
public interface Purchasable {
    /**
     * Simulates the act of purchasing the item.
     * In a real-world scenario, this method would typically trigger
     * payment processing, inventory updates, order status changes, etc.
     */
    void purchase();
}
