package org.example;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class WestminsterShoppingManager implements ShoppingManager{
    private static Scanner scan; //Scanner object for user input.
    public ArrayList<Product> productList; //ArrayList to store products in the shopping manager.
    private static int total_products; //Total count of products in the shopping manager.
    private Electronic electronic_item; //Electronic item used during product addition.
    private Clothing clothing_item; //Clothing item used during product addition.

    //Constructor for WestminsterShoppingManager initializes the product list.
    public WestminsterShoppingManager(){
        this.productList = new ArrayList<>(50);
    }

    //The main method for the WestminsterShoppingManager class, handling user interactions.
    public static void main(String[] args){
        // Initialize the scanner and WestminsterShoppingManager
        scan = new Scanner(System.in);
        WestminsterShoppingManager manager = new WestminsterShoppingManager();
        // Load data from the file
        manager.loadFromFile();

        // Main menu loop
        while (true) {
            // Display menu options
            System.out.println("""
                    ----  Menu  ----
                    1 - Add Product
                    2 - Remove Product
                    3 - Print Products
                    4 - Save Records to the File
                    5 - Open the GUI
                    6 - Exit
                    """);

            System.out.print("Enter option: ");
            String input = scan.nextLine().trim();

            // Extract choice from input
            int spaceIndex = input.indexOf(" ");
            String choice;

            if (spaceIndex != -1) {
                choice = input.substring(spaceIndex-1,spaceIndex+1);
            } else {
                choice = input;
            }

            // Check if the choice is a valid number
            if (choice.matches("\\d+")) {
                int option = Integer.parseInt(choice);

                // Check if the number is within the range of 1 to 6
                if (option >= 1 && option <= 6) {
                    // Handle user choice
                    switch (choice) {
                        case "1":
                            manager.addNewProduct();
                            break;
                        case "2":
                            manager.removeProduct();
                            break;
                        case "3":
                            manager.printProducts();
                            break;
                        case "4":
                            manager.saveToFile(true);
                            break;
                        case "5":
                            LoginGUI gui = new LoginGUI();
                            break;
                        case "6":
                            System.out.println("Program Ends...");
                            return;
                    }
                } else {
                    System.out.println("Invalid input! Please enter a number between 1 and 6.\n");
                }
            } else {
                System.out.println("Invalid input! Please enter a valid number.\n");
            }

        }
    }

    @Override
    public void addNewProduct(){
        // Check if the maximum limit of 50 products has been reached
        if(productList.size() > 50){
            System.out.println("Maximum limit of 50 products reached.Cannot add more products\n");
        }
        else {
            System.out.println("""
                    --- Product Types ---
                    1 - Electronics Items
                    2 - Clothing Items
                    """);

            int pro_type;

            // Get user input for the product type
            while (true) {
                System.out.print("Enter the product type: ");
                String input = scan.nextLine();

                //Input Validation
                try {
                    pro_type = Integer.parseInt(input);

                    if (pro_type == 1 || pro_type == 2) {
                        break;
                    } else {
                        System.out.println("Invalid input! Enter either 1 or 2.\n");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input! Please enter a number.\n");

                }

            }

            String product_id;
            while (true) {
                System.out.print("Enter the product ID: ");
                product_id = scan.nextLine();
                // Check if the product ID is alphanumeric and starts with a letter
                if (isValidProductId(product_id)) {
                    boolean isDuplicate = false;

                    for (Product product : productList) {
                        if (product.getProductID().equals(product_id)) {
                            isDuplicate = true;
                            System.out.println("Product id " + product_id + " is already exist. Cannot add duplicate products.\n");
                            break;
                        }
                    }

                    if (!isDuplicate) {
                        break;
                    }
                } else {
                    System.out.println("Invalid input! Please enter a valid product id.\n");

                }
            }

            // Capitalize the first letter of the product_id
            product_id = product_id.substring(0, 1).toUpperCase() + product_id.substring(1);

            String product_name;
            while (true) {
                System.out.print("Enter the product name: ");
                if (scan.hasNext("[a-zA-Z]+")) {
                    product_name = scan.nextLine();
                    break;
                } else {
                    System.out.println("Invalid input! Enter a valid product name.\n");
                    scan.next();
                }
                scan.nextLine();
            }

            //Capitalize the first letter of the product name
            product_name = product_name.substring(0, 1).toUpperCase() + product_name.substring(1);

            int quantity;
            while (true) {
                System.out.print("Enter the product quantity: ");

                try {
                    String input = scan.nextLine();

                    // Check if the input contains only digits
                    if (input.matches("\\d+")) {
                        quantity = Integer.parseInt(input);

                        if (quantity < 0) {
                            System.out.println("Invalid input! Enter a valid non-negative quantity.\n");
                        } else {
                            break;
                        }
                    } else {
                        System.out.println("Invalid input! Enter a valid number as the quantity.\n");
                    }

                } catch (InputMismatchException e) {
                    System.out.println("Invalid input! Enter a valid number as the quantity.\n");
                    scan.nextLine(); // Consume the invalid input
                }
            }

            double price;
            while (true) {
                System.out.print("Enter the price: ");

                try {
                    String input = scan.nextLine();

                    // Check if the input is a valid double
                    price = Double.parseDouble(input);

                    if (price < 0) {
                        System.out.println("Invalid input! Enter a valid non-negative price.\n");
                    } else {
                        break;
                    }

                } catch (NumberFormatException e) {
                    System.out.println("Invalid Input! Enter a valid price for the product.\n");
                }
            }

            switch (pro_type) {
                case 1:
                    String brand_name;
                    while (true) {
                        System.out.print("Enter the brand name: ");
                        if (scan.hasNext("[a-zA-Z]+")) {
                            brand_name = scan.nextLine();
                            break; // Valid input, exit the loop
                        } else {
                            System.out.println("Invalid input! Enter a valid brand name (letters only).\n");
                            scan.next();
                        }
                        scan.nextLine();
                    }

                    brand_name = brand_name.substring(0,1).toUpperCase() + brand_name.substring(1);

                    int warranty;
                    while (true) {
                        System.out.print("Enter the warranty period: ");

                        try {
                            String input = scan.nextLine();

                            // Check if the input is a valid integer
                            warranty = Integer.parseInt(input);

                            // Add condition to check if warranty is less than or equal to 0
                            if (warranty <= 0) {
                                System.out.println("Invalid input! Warranty period must be greater than 0.\n");
                            } else {
                                break; // Exit the loop if a valid warranty period is entered
                            }

                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input! Enter a valid number as the warranty period.\n");
                        }
                    }

                    electronic_item = new Electronic(product_id, product_name, quantity, price, brand_name, warranty);
                    productList.add(electronic_item);
                    System.out.println("Electronics item is added to the system successfully!!\n");
                    break;

                case 2:
                    String[] sizes = {"XS", "S", "M", "L", "XL", "XXL"};
                    System.out.println();
                    System.out.println("""
                            --- Size Chart ---
                            XS  - Extra small
                            S   - Small
                            M   - Medium
                            L   - Large
                            XL  - Excel
                            XXL - Double Excel
                            """);
                    String size;
                    while (true) {
                        System.out.print("Enter the size: ");

                        if (scan.hasNextLine()) {
                            size = scan.nextLine();

                            // Check if the entered size is in the array
                            if (Arrays.asList(sizes).contains(size.toUpperCase())) {
                                size = size.toUpperCase();
                                break; // Valid input, exit the loop
                            } else {
                                System.out.println("Invalid input! Enter a valid size.\n");
                                scan.nextLine(); // Consume the entire line to avoid issues
                            }
                        } else {
                            System.out.println("Invalid input! Enter a valid size.\n");
                            scan.next(); // Consume the invalid input
                        }

                    }

                    String color;
                    while (true) {
                        System.out.print("Enter the color: ");
                        color = scan.nextLine();
                        // Check if the input contains only letters or letters and spaces
                        if (color.matches("[a-zA-Z]+") || color.matches("[a-zA-Z]+\\s[a-zA-Z]+")) {
                            // If the input contains two words (e.g., light blue), capitalize the first letter of each word
                            String[] words = color.split("\\s");
                            StringBuilder capitalizedColor = new StringBuilder();

                            for (String word : words) {
                                capitalizedColor.append(word.substring(0, 1).toUpperCase()).append(word.substring(1)).append(" ");
                            }
                            color = capitalizedColor.toString().trim();
                            break; // Valid input, exit the loop
                        } else {
                            System.out.println("Invalid input! Enter a valid color name.\n");
                        }
                    }

                    Clothing clothing_item = new Clothing(product_id, product_name, quantity, price, size, color);
                    productList.add(clothing_item);
                    System.out.println("Clothing item is added to the system successfully!!\n");
                    break;
                default:
                    System.out.println("Invalid input! Enter either 1 or 2\n");
            }
        }
    }

    // Method to checks if the given product ID is valid.
    private static boolean isValidProductId(String productId) {
        // Check if the product ID has a length of 5 and starts with a letter
        if (productId.length() == 5 && Character.isLetter(productId.charAt(0))) {
            try {
                // Parse the numeric part of the product ID
                int digits = Integer.parseInt(productId.substring(1));
                // Check if the numeric part is non-negative
                return digits >= 0;
            } catch (NumberFormatException e) {
                // Parsing failed
            }
        }
        // Return false if the conditions are not met
        return false;
    }

    //Removes a product from the product list based on the provided product ID.
    @Override
    public void removeProduct(){
        // Infinite loop to handle repeated attempts
        while(true) {
            // Check if the product list is empty
            if (productList.isEmpty()) {
                System.out.println("No products are available.\n");
                return;
            }

            // Prompt the user to enter the product ID for removal
            System.out.print("Enter the product ID: ");
            String pro_id = scan.nextLine();

            // Check if the entered product ID is valid
            if (isValidProductId(pro_id)) {
                boolean found = false;
                for (int i = 0; i < productList.size(); i++) {
                    Product product = productList.get(i);

                    // Iterate through the product list to find and remove the product
                    if (product.getProductID().equals(pro_id)) {
                        // Remove the product from the list
                        productList.remove(i);
                        String product_type = product.getType();
                        System.out.println(product_type + " product is removed successfully.\n");
                        found = true;
                        break;
                    }

                }

                // If the product with the given ID is not found
                if (!found) {
                    System.out.println("Product with product ID " + pro_id + " is not available\n");
                    return;
                }

                // Save the updated product list to the file
                saveToFile(false);
                System.out.println("There are currently " + productList.size() + " products available in the system.\n");
                return;

            } else {
                // Prompt the user to enter a valid product ID
                System.out.println("Enter a valid product id.\n");
            }
        }
    }

    //Prints the available products in the system.
    @Override
    public void printProducts() {
        // Check if the product list is empty
        if (productList.isEmpty()) {
            System.out.println("No products are available in the system.\n");
        } else {
            // Sort the ArrayList based on product IDs
            productList.sort(Comparator.comparing(Product::getProductID));

            // Iterate through the sorted product list and print each product
            for (Product product : productList) {
                System.out.println(product);
            }
        }
        System.out.println("\n");
    }

    //Saves the product records to a file.
    public void saveToFile(boolean displayMessage){
        try {
            // Create a new File object for the product records file
            File product_records = new File("Product Records.txt");
            // Create a FileWriter to write to the product_records file
            FileWriter writer = new FileWriter(product_records);

            // Iterate through the productList and write each product's string representation to the file
            for (Product product : productList) {
                writer.write(product.toString());
                writer.write("\n");
            }
            // Close the FileWriter after writing
            writer.close();
            // Display a success message if specified
            if (displayMessage) {
                System.out.println("Products are saved to the file successfully.\n");
            }
        }catch (IOException e){
            // Print the exception message if an IOException occurs during the file writing process
            System.out.println(e.getMessage());
            // Print an error message indicating a failure in saving products to the file
            System.err.println("Error saving products to the file.\n");
        }
    }
   // Loads product records from a file into the productList.
    public void loadFromFile() {
        try {
            // Create a new File object for the product records file
            File read_file = new File("Product Records.txt");
            // Create a Scanner to read from the product_records file
            Scanner myReader = new Scanner(read_file);

            // Iterate through each line in the file
            while (myReader.hasNextLine()) {
                // Read the current line from the file
                String data = myReader.nextLine();
                // Split the line into parts based on the separator ", "
                String[] parts = data.split(", ");

                // Check if there are parts in the line
                if (parts.length > 0) {
                    // Extract the product type from the first part
                    String type = parts[0].split(": ")[1];

                    // Check if the product is of type "Electronics"
                    if (type.equals("Electronics")) {
                        // Extract attributes for Electronics product
                        String pro_id = parts[1].split(": ")[1];
                        String pro_name = parts[2].split(": ")[1];
                        int quantity = Integer.parseInt(parts[3].split(": ")[1]);
                        total_products = quantity + total_products;
                        double price = Double.parseDouble(parts[4].split(": ")[1].replaceAll("[^\\d.]", ""));
                        String brand_name = parts[5].split(": ")[1];
                        int warranty_period = Integer.parseInt(parts[6].split(": ")[1]);

                        // Create an Electronic object and add it to the productList
                        Electronic electronic = new Electronic(pro_id, pro_name, quantity, price, brand_name, warranty_period);
                        productList.add(electronic);

                    } else if (type.equals("Clothing")) {
                        // Extract attributes for Clothing product
                        String pro_id = parts[1].split(": ")[1];
                        String pro_name = parts[2].split(": ")[1];
                        int quantity = Integer.parseInt(parts[3].split(": ")[1]);
                        total_products = quantity + total_products;
                        double price = Double.parseDouble(parts[4].split(": ")[1].replaceAll("[^\\d.]", ""));
                        String size = parts[5].split(": ")[1];
                        String color = parts[6].split(": ")[1];

                        // Create a Clothing object and add it to the productList
                        clothing_item = new Clothing(pro_id, pro_name, quantity, price, size, color);
                        productList.add(clothing_item);

                    }
                }
            }
            // Close the Scanner after reading from the file
            myReader.close();

        } catch (FileNotFoundException e) {
            //System.out.println(e.getMessage());
        }
    }

    //Method to get the quantity of a product with the specified product ID.
    public int getQuantity(String productId) {
        // Iterate through each product in the productList
        for (Product product : productList) {
            // Check if the current product has the specified product ID
            if (product.getProductID().equals(productId)) {
                // Return the quantity of the product
                return product.getNumberOfItems();
            }
        }
        return 0; // Return a default value if the product is not found
    }


}





