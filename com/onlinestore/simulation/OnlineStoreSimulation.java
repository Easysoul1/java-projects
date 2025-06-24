
// Imporrting necessary classes for the simulation
package com.onlinestore.simulation;

import com.onlinestore.model.Customer;
import com.onlinestore.model.Order;
import com.onlinestore.model.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Main class to simulate the online store purchase flow.
 * This class demonstrates the creation of products, customers,
 * placing orders, and generating receipts, showcasing the use of
 * interfaces, aggregation, and ArrayLists as required.
 */
public class OnlineStoreSimulation {

    public static void main(String[] args) {
        System.out.println("--- Online Store Simulation Started ---");

        // 1. Create Products available in the store
        System.out.println("\n--- Creating Products ---");
        Product laptop = new Product("P001", "Gaming Laptop", 1200.00);
        Product keyboard = new Product("P002", "Mechanical Keyboard", 150.00);
        Product mouse = new Product("P003", "Wireless Mouse", 75.00);
        Product monitor = new Product("P004", "4K Monitor", 450.00);
        Product headphones = new Product("P005", "Noise-Cancelling Headphones", 250.00);
        Product webcam = new Product("P006", "HD Webcam", 80.00);
        Product deskChair = new Product("P007", "Ergonomic Desk Chair", 600.00);

        System.out.println("Available Products:");
        System.out.println(laptop);
        System.out.println(keyboard);
        System.out.println(mouse);
        System.out.println(monitor);
        System.out.println(headphones);
        System.out.println(webcam);
        System.out.println(deskChair);

        // 2. Create Customers
        System.out.println("\n--- Creating Customers ---");
        Customer alice = new Customer("C001", "Alice Smith", "alice.smith@example.com");
        Customer bob = new Customer("C002", "Bob Johnson", "bob.j@example.com");
        Customer charlie = new Customer("C003", "Charlie Brown", "charlie.b@example.com");

        System.out.println(alice);
        System.out.println(bob);
        System.out.println(charlie);

        // 3. Demonstrate Purchase Flow for Alice (Order 1 - Eligible for Discount)
        System.out.println("\n--- Alice's First Order (Eligible for Discount) ---");
        List<Product> aliceCart1 = new ArrayList<>();
        aliceCart1.add(laptop);      // $1200
        aliceCart1.add(monitor);     // $450
        aliceCart1.add(headphones);  // $250
        // Subtotal: $1200 + $450 + $250 = $1900.00 (should get 10% discount)

        System.out.println("Alice's cart for Order 1: " + aliceCart1);
        Order aliceOrder1 = new Order(alice, aliceCart1);
        aliceOrder1.purchase(); // This will also add the order to Alice's list
        aliceOrder1.generateReceipt();

        // 4. Demonstrate Purchase Flow for Bob (Order 1 - Not Eligible for Discount)
        System.out.println("\n--- Bob's First Order (Not Eligible for Discount) ---");
        List<Product> bobCart1 = new ArrayList<>();
        bobCart1.add(keyboard); // $150
        bobCart1.add(mouse);    // $75
        bobCart1.add(webcam);   // $80
        // Subtotal: $150 + $75 + $80 = $305.00 (no discount)

        System.out.println("Bob's cart for Order 1: " + bobCart1);
        Order bobOrder1 = new Order(bob, bobCart1);
        bobOrder1.purchase();
        bobOrder1.generateReceipt();

        // 5. Demonstrate Purchase Flow for Alice (Order 2 - Not Eligible for Discount)
        System.out.println("\n--- Alice's Second Order (Not Eligible for Discount) ---"); 
        List<Product> aliceCart2 = new ArrayList<>();
        aliceCart2.add(keyboard); // $150
        aliceCart2.add(mouse);    // $75
        // Subtotal: $150 + $75 = $225.00 (no discount)

        System.out.println("Alice's cart for Order 2: " + aliceCart2);
        Order aliceOrder2 = new Order(alice, aliceCart2);
        aliceOrder2.purchase();
        aliceOrder2.generateReceipt();

        // 6. Demonstrate Purchase Flow for Charlie (Order 1 - Exactly at discount threshold)
        System.out.println("\n--- Charlie's First Order (Exactly at Discount Threshold) ---");
        List<Product> charlieCart1 = new ArrayList<>();
        charlieCart1.add(deskChair); // $600
        charlieCart1.add(keyboard);  // $150
        charlieCart1.add(monitor);   // $450
        // Subtotal: $600 + $150 + $450 = $1200.00 (should get 10% discount)

        System.out.println("Charlie's cart for Order 1: " + charlieCart1);
        Order charlieOrder1 = new Order(charlie, charlieCart1);
        charlieOrder1.purchase();
        charlieOrder1.generateReceipt();

        // 7. Demonstrate Customer's multiple orders (aggregation in Customer class)
        System.out.println("\n--- Checking Customer's Order History ---");

        System.out.println("\nAlice's total orders: " + alice.getOrders().size());
        for (Order order : alice.getOrders()) {
            System.out.println("  - Order ID: " + order.getOrderId() + ", Final Total: $" + String.format("%.2f", order.getFinalTotalAmount()));
        }

        System.out.println("\nBob's total orders: " + bob.getOrders().size());
        for (Order order : bob.getOrders()) {
            System.out.println("  - Order ID: " + order.getOrderId() + ", Final Total: $" + String.format("%.2f", order.getFinalTotalAmount()));
        }

        System.out.println("\nCharlie's total orders: " + charlie.getOrders().size());
        for (Order order : charlie.getOrders()) {
            System.out.println("  - Order ID: " + order.getOrderId() + ", Final Total: $" + String.format("%.2f", order.getFinalTotalAmount()));
        }

        System.out.println("\n--- Online Store Simulation Finished ---");
    }
}