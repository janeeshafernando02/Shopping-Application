package org.example;
//Import Statement
import java.io.IOException;

public interface ShoppingManager {
    // Add a new product to the shopping management system
    public abstract void addNewProduct();
    // Remove an existing product from the shopping management system
    public abstract void removeProduct();
    // Print information about all products in the shopping management system
    public abstract void printProducts();
    // Save the current state of the shopping management system to a file
    // If displayMessage is true, display a message indicating the save operation result
    public abstract void saveToFile(boolean displayMessage) throws IOException;
    // Load data from a file to restore the state of the shopping management system
    public abstract void loadFromFile() throws IOException;
}

