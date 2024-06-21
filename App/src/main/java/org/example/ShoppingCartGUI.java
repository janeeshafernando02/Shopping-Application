package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.Random;

public class ShoppingCartGUI{
    private final DefaultTableModel model;  // Model for managing data in the JTable
    private final JTable table;  // JTable for displaying product information
    ShoppingCart cart;  // Instance of the ShoppingCart class
    private final JLabel total_price_value;  // JLabel for displaying total price
    private final JLabel final_price_value;  // JLabel for displaying final total price
    private final JLabel discount1_price_value;  // JLabel for displaying discount related to the first buyer
    private final JLabel discount2_price_value;  // JLabel for displaying discount related to the category
    private double total;  // Variable to store the total price
    private double category_discount;  // Variable to store the category discount
    private double first_buyer_discount;  // Variable to store the first buyer discount
    private static final String PREFIX = "PH";  // Prefix for generating purchase history IDs

    //Constructor
    public ShoppingCartGUI(ShoppingCart cart){
        this.cart = cart;
        Font font = new Font("Poppins",Font.PLAIN,12);
        // Create a frame instance of JFrame class
        JFrame frame = new JFrame("Shopping Cart");

        // Set the frame size
        frame.setSize(780, 620);

        // Set the background color of the JFrame
        frame.getContentPane().setBackground(new Color(213, 232, 252, 253));

        // Close the JFrame when a button is clicked
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Set the layout to null for manual positioning
        frame.setLayout(null);

        // Define column names for the JTable
        String[] column_names = {"Product","Quantity","Price"};

        // Create a 2D array to hold data for the JTable rows
        Object[][] rowData = new Object[cart.cartItemsList.size()][3];
        // Populate the rowData array with data from the shopping cart
        updateTable(rowData);

        // Create a DefaultTableModel with the specified column names and row data
        model = new DefaultTableModel(rowData,column_names);
        // Create a JTable with the previously created DefaultTableModel
        table = new JTable(model);


        // Set the bounds for the JTable
        table.setBounds(50, 50, 665, 243);

        // Create a JScrollPane to hold the JTable
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 50, 665, 243);

        // Customize cell renderer to center the data in the cells
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Increase the height of column headers
        table.getTableHeader().setPreferredSize(new Dimension(table.getTableHeader().getWidth(), 80));
        table.getTableHeader().setBackground(new Color(7, 120, 173, 253));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Poppins",Font.BOLD,12));

        // Increase row heights of column headers and content equally
        table.setRowHeight(80); // Adjust the value based on your preference

        // Add the JScrollPane to the JFrame
        frame.add(scrollPane);

        // Make the frame visible
        frame.setVisible(true);

        // Center the frame on the screen
        frame.setLocationRelativeTo(null);

        // Create a label for displaying the total price
        JLabel total_price_label = new JLabel("Total");
        total_price_label.setFont(font);
        total_price_label.setBounds(475,330,120,30);
        frame.add(total_price_label);

        // Create a label to display the actual total price value
        total_price_value = new JLabel();
        total_price_value.setFont(font);
        total_price_value.setBounds(572,330,120,30);

        // Calculate and set the total price value from the shopping cart
        total = cart.calculateTotal(cart);
        String format_total = String.format("%.2f €",total);
        total_price_value.setText(format_total);
        frame.add(total_price_value);

        // Create a label for displaying the first purchase discount information
        JLabel discount1_label = new JLabel("First Purchase Discount (10%)");
        discount1_label.setFont(font);
        discount1_label.setBounds(345,370,200,30);
        frame.add(discount1_label);

        // Create a label to display the first purchase discount value
        discount1_price_value = new JLabel();
        discount1_price_value.setFont(font);
        discount1_price_value.setBounds(565,370,120,30);

        // Check if the user is a first-time buyer and calculate the discount
        boolean first_time_buyer = cart.isFirstTimeBuyer(User.getUserId());
        if (first_time_buyer) {
            first_buyer_discount = total * 0.10;
            String first_buyer_discount_format = String.format("- %.2f €", first_buyer_discount);
            discount1_price_value.setText(first_buyer_discount_format);
        } else {
            discount1_price_value.setText("- 0.00€");
        }

        frame.add(discount1_price_value);

        // Create a label for displaying the category discount information
        JLabel discount2_label = new JLabel("Three items in same Category Discount (20%)");
        discount2_label.setFont(font);
        discount2_label.setBounds(265,410,250,30);
        frame.add(discount2_label);

        // Create a label to display the category discount value
        discount2_price_value = new JLabel();
        discount2_price_value.setFont(font);
        discount2_price_value.setBounds(565,410,120,30);

        // Check if there is a category discount and set the value accordingly
        category_discount = cart.categoryDiscount(cart);
        if(category_discount == 0){
            discount2_price_value.setText("- 0.00€");
        }
        else {
            String discount_format_total = String.format("- %.2f €", category_discount);
            discount2_price_value.setText(discount_format_total);
        }
        frame.add(discount2_price_value);

        // Create a label for displaying the final total price
        JLabel final_price = new JLabel("Final Total");
        final_price.setFont(new Font("Poppins",Font.BOLD,12));
        final_price.setBounds(450,450,100,30);
        frame.add(final_price);

        // Calculate and set the final total price value
        final_price_value = new JLabel();
        final_price_value.setFont(new Font("Poppins",Font.BOLD,12));
        final_price_value.setBounds(572,450,120,30);
        String final_price_format = String.format("%.2f €", calculateFinalPrice());
        final_price_value.setText(final_price_format);
        frame.add(final_price_value);

        // Create a button for the checkout process
        JButton checkout = new JButton("Checkout");
        checkout.setFont(font);
        checkout.setBounds(550, 510, 120, 28);
        checkout.setForeground(Color.WHITE);
        checkout.setBackground(new Color(3, 173, 77, 253));
        frame.add(checkout);

        // Create a button for removing a product from the shopping cart
        JButton remove_product = new JButton("Remove Product");
        remove_product.setFont(font);
        remove_product.setBackground(new Color(148, 5, 78, 253));
        remove_product.setForeground(Color.WHITE);
        remove_product.setBounds(50,310,130,28);
        frame.add(remove_product);

        // Add mouse event listeners to change the background color when hovered
        remove_product.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Set the background color when hovered
                remove_product.setBackground(new Color(183, 7, 93, 253));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Set the background color when not hovered
                remove_product.setBackground(new Color(148, 5, 78, 253));
            }
        });

        // Add an ActionListener to the "Remove Product" button
        remove_product.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    // Remove the row from the table model
                    model.removeRow(selectedRow);
                    // Get the product from the selected row
                    Product removedProduct = cart.cartItemsList.get(selectedRow);
                    // Remove the product from the cart
                    cart.cartItemsList.remove(removedProduct);
                    updateTotalAndDiscounts();
                    JOptionPane.showMessageDialog(frame, "Product removed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a product to remove.","Error",JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Add a MouseListener to change the background color when hovered
        checkout.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Set the background color when hovered
                checkout.setBackground(new Color(  6, 222, 99, 250));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Set the background color when not hovered
                checkout.setBackground(new Color(3, 173, 77, 253));
            }
        });

        // Add an ActionListener to the "Checkout" button
        checkout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user_id = User.getUserId();

                try (PrintWriter writer = new PrintWriter(new FileWriter("PurchaseHistory.txt", true))) {
                    // Iterate through the items in the cart
                    for (Product item : cart.cartItemsList) {
                        writePurchaseDetailsToFile(user_id, item, writer);
                    }
                    // Clear the cart items and update the table
                    cart.cartItemsList.clear();
                    model.setRowCount(0);
                    updateTotalAndDiscounts();
                    JOptionPane.showMessageDialog(frame, "Your Payment was successful", "Success", JOptionPane.INFORMATION_MESSAGE);


                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                    // Handle the exception, e.g., show an error message to the user
                    JOptionPane.showMessageDialog(frame, "Error during checkout. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    //Method to save purchase details to the file
    private void writePurchaseDetailsToFile(String user_id, Product item, PrintWriter writer) {
        // Generate a unique purchase ID
        String purchase_id = generatePurchaseHistoryID();
        // Extract relevant information from the product
        String product_id = item.getProductID();
        String product_name = item.getProductName();
        double price = item.getPrice();
        int quantity = item.number_of_items;

        // Format the details and write to the file
        String purchaseDetails = "Purchase ID: " + purchase_id + ", Product ID: " + product_id + ", ProductName: "
                + product_name + ", Total Price: " + String.format("%.2f €", price * quantity) + ", Quantity: " + quantity + ", User ID: " + user_id;

        // Write the formatted purchase details to the file
        writer.println(purchaseDetails);
    }

    private void updateTotalAndDiscounts() {
        // Update the total price value
        double total = cart.calculateTotal(cart);
        String format_total = String.format("%.2f €", total);
        total_price_value.setText(format_total);

        boolean first_time_buyer= cart.isFirstTimeBuyer(User.getUserId());
        if (first_time_buyer) {
            first_buyer_discount = total * 0.10;
            String first_buyer_discount_format = String.format("- %.2f €", first_buyer_discount);
            discount1_price_value.setText(first_buyer_discount_format);
        } else {
            discount1_price_value.setText("- 0.00€");
        }

        // Update the category discount value
        double category_discount = cart.categoryDiscount(cart);
        String discount_format_total = String.format("- %.2f €", category_discount);
        discount2_price_value.setText(discount_format_total);

        // Update the final total value
        double finalTotal = total - category_discount - first_buyer_discount;
        String format_final_total = String.format("%.2f €", finalTotal);
        final_price_value.setText(format_final_total);
    }

    //Method to update the table
    private void updateTable(Object[][] row_data) {
        int rowIndex = 0;

        for (Product item : cart.cartItemsList) {
            Object[] row = new Object[3];

            if (item instanceof Electronic) {
                populateRowData(row, item);
            } else if (item instanceof Clothing) {
                populateRowData(row, item);
            }

            row_data[rowIndex++] = row;
        }
    }

    //Method to populates the row data array with product details.
    private void populateRowData(Object[] row, Product product) {
        String pro_id = product.getProductID();
        String pro_name = product.getProductName();
        int quantity = product.getNumberOfItems();
        double total_price = product.getPrice() * quantity;

        String formattedPrice = String.format("%.2f €",total_price);

        if (product instanceof Electronic electronic_product) {
            String brand = ((Electronic) product).getBrandName();
            int warranty = ((Electronic) product).getWarrantyPeriod();
            String str_warranty = String.valueOf(warranty);
            String product_details = pro_id + "/ " + pro_name + "/ " + brand + ", " + str_warranty + " weeks";
            row[0] = formatProductDetails(product_details);
        } else if (product instanceof Clothing clothing_product) {
            String size = clothing_product.getSize();
            String color = clothing_product.getColor();
            String product_details = pro_id + "/ " + pro_name + "/ " + size + ", " + color;
            row[0] = formatProductDetails(product_details);
        }

        row[1] = quantity;
        row[2] = formattedPrice;
    }

    //Method to format product details for HTML rendering in the table.
    private String formatProductDetails(String productDetails) {
        String[] words = productDetails.split("/ ");

        // Join the words with "<br>" to add a line break after each word
        String formattedDetails = String.join("<br>", words);
        // Format the product details using HTML tags
        return "<html><body><div style='text-align: center;'>" + formattedDetails + "</div></body></html>";
    }


    //Method to get the final price
    public double calculateFinalPrice(){
        return total - category_discount - first_buyer_discount;
    }

    //Method to generate a purchase history id
    private String generatePurchaseHistoryID() {
        // Generating a random number
        Random random = new Random();
        int randomNumber = random.nextInt(10000); // You can adjust the range as needed

        // Creating the purchase history ID by combining the prefix, product ID, and random number
        return PREFIX + randomNumber;
    }

}
