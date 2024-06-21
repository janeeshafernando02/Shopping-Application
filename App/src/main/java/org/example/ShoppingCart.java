package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class ShoppingCart {

    // ArrayList to store products in the shopping cart
    public ArrayList<Product> cartItemsList = new ArrayList<>();

    //Adds the provided product to the shopping cart.
    public void addProductsToCart(Product product) {
        // Add the provided product to the cart
        cartItemsList.add(product);
    }

    //Calculates the total price of the products in the shopping cart.
    public double calculateTotal(ShoppingCart cart) {
        double totalPrice = 0;

        for (Product item : cart.cartItemsList) {
            int quantity = item.getNumberOfItems();
            double itemPrice = item.getPrice() * quantity;
            totalPrice += itemPrice;
        }

        return totalPrice;
    }

    //Method  to apply a category discount to the total price based on the number of products in specific categories
    public double categoryDiscount(ShoppingCart cart) {
        int electronicCount = 0;
        int clothingCount = 0;

        // Count the number of products in each category
        for (Product product : cart.cartItemsList) {
            if (product instanceof Electronic) {
                electronicCount++;
            } else if (product instanceof Clothing) {
                clothingCount++;
            }
        }

        // Check if the category count is greater than or equal to 3 for either category
        if (electronicCount >= 3 || clothingCount >= 3) {
            double discountPercentage = 0.20;
            return discountPercentage * cart.calculateTotal(cart);
        } else {
            // No category discount
            return 0.00;
        }

    }

    //Method to check if a user is a first-time buyer based on their user ID.
    public boolean isFirstTimeBuyer(String user_id) {
        try (BufferedReader reader = new BufferedReader(new FileReader("PurchaseHistory.txt"))) {
            String line;
            // Read each line in the purchase history file
            while ((line = reader.readLine()) != null) {
                // Split the line to extract the user_id
                String[] parts = line.split(", ");
                for (String part : parts) {
                    if (part.trim().startsWith("User ID:")) {
                        String purchaseUserId = part.substring("User ID:".length()).trim();
                        // Check if the user_id matches
                        if (user_id.equals(purchaseUserId)) {
                            // User found in purchase history, not a first-time buyer
                            return false;
                        }
                    }
                }
            }
        } catch (Exception e) {
            //System.out.println(e.getMessage());
        }
        // User not found in purchase history, considered a first-time buyer
        return true;
    }


}