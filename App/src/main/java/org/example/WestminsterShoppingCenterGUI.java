package org.example;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class WestminsterShoppingCenterGUI {
    private final ArrayList<Product> lowQuantityProducts; // Represents a list of products with low quantity.
    private final JComboBox<String> category_selection; //Represents a combo box for selecting product categories.
    private final String[] category; // Represents an array of category names.
    private final JTable table; //Represents a table for displaying product information.
    private final DefaultTableModel tableModel; //Represents the default table model for the product table.
    private final JButton addToCartButton; //Represents a button for adding selected products to the shopping cart.
    private final JLabel product_details_label; //Represents a label for displaying product details.
    private final JLabel product_id_label; //Represents a label for displaying the product ID.
    private final JLabel category_label; //Represents a label for displaying the product category.
    private final JLabel product_name_label; //Represents a label for displaying the product name.
    private final JLabel size_label; //Represents a label for displaying the size of the product.
    private final JLabel brand_name_label; //Represents a label for displaying the brand name of the product.
    private final JLabel color_label; //Represents a label for displaying the color of the product.
    private final JLabel warranty_label; //Represents a label for displaying the warranty information of the product.
    private final JLabel item_label; //Represents a label for displaying the available quantity of the product.

    User user; //Represents a user associated with the shopping manager.
    WestminsterShoppingManager manager; // Represents the WestminsterShoppingManager instance managing the shopping experience.
    ShoppingCart cart = new ShoppingCart(); // Represents the shopping cart where selected products are added.

    public WestminsterShoppingCenterGUI() {
        //Create a frame instance of JFrame class
        JFrame frame1 = new JFrame("Westminster Shopping Center");
        //Set the frame size
        frame1.setSize(780, 680);
        // Set the background color of the JFrame
        frame1.getContentPane().setBackground(new Color(213, 232, 252, 253));
        //Close the JFrame when a button is clicked
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Removing the default layout by pass the null argument to the setLayout method
        frame1.setLayout(null);
        // Make the frame visible
        frame1.setVisible(true);
        frame1.setLocationRelativeTo(null);

        Font font = new Font("Poppins", Font.PLAIN, 12);

        JLabel selection_label = new JLabel("Select Product Category");
        selection_label.setFont(font);
        selection_label.setBounds(80, 30, 130, 30);
        frame1.add(selection_label);

        category = new String[]{"All", "Electronics", "Clothing"};
        category_selection = new JComboBox<>(category);
        category_selection.setFont(font);
        category_selection.setBackground(new Color(239, 244, 250, 253));

        // Create a custom renderer to align the text
        DefaultListCellRenderer renderer = new DefaultListCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        category_selection.setRenderer(renderer);

        //category_selection.setBackground(new Color(202, 227, 253, 253));
        category_selection.setBounds(250, 35, 160, 25);
        frame1.add(category_selection);

        JButton shopping_cart_btn = new JButton("Shopping Cart");
        shopping_cart_btn.setFont(font);
        shopping_cart_btn.setBounds(600, 35, 120, 28);
        shopping_cart_btn.setForeground(Color.WHITE);
        shopping_cart_btn.setBackground(new Color(7, 120, 173, 253));
        frame1.add(shopping_cart_btn);

        // Add a MouseListener to change the background color when hovered
        shopping_cart_btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Set the background color when hovered
                shopping_cart_btn.setBackground(new Color(10, 145, 211, 253));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Set the background color when not hovered
                shopping_cart_btn.setBackground(new Color(7, 120, 173, 253));
            }
        });

        shopping_cart_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ShoppingCartGUI cartGUI = new ShoppingCartGUI(cart);

            }
        });

        //Reference - https://www.youtube.com/watch?v=ccUdvsj4L0U
        // Define column names
        manager = new WestminsterShoppingManager();
        manager.loadFromFile();
        lowQuantityProducts = new ArrayList<>();
        // Iterate through the productList to find products with quantity less than 3
        for (Product product : manager.productList) {
            if (product.getNumberOfItems()< 3) {
                lowQuantityProducts.add(product);
            }
        }

        String[] column_names = {"Product ID", "Name", "Category", "Price(€)", "Info"};
        Object[][] rowData = new Object[manager.productList.size()][5];
        getDataFromProductList(rowData);
        tableModel = new DefaultTableModel(rowData, column_names);
        table = new JTable(tableModel);

        // Custom renderer for row colors
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                Product product = manager.productList.get(row);

                if (lowQuantityProducts.contains(product)) {
                    comp.setBackground(new Color(255, 0, 0)); // Red background for low quantity products
                } else {
                    comp.setBackground(new Color(255, 255, 255)); // Default background for other products
                }

                return comp;
            }
        });

        table.setAutoCreateRowSorter(true); // sorting of the rows on a particular column
        Font header = new Font("Poppins", Font.BOLD, 12);
        table.getTableHeader().setBackground(new Color(7, 120, 173, 253));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(header);

        table.setRowHeight(32);
        TableColumn column;
        for (int i = 0; i < 5; i++) {
            column = table.getColumnModel().getColumn(i);
            if (i == 4) {
                column.setPreferredWidth(180);
            } else {
                column.setPreferredWidth(80);
            }
        }

        // Center-align renderer for table cells
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);


        // Apply center renderer to each column
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane pane = new JScrollPane(table);
        pane.setBounds(50, 160, 650, 180);
        frame1.add(pane);

        // Add listener for category selection
        category_selection.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterTable();
            }
        });

        JSeparator separator = new JSeparator(JSeparator.HORIZONTAL);
        separator.setBounds(0, 370, 800, 20); // Adjust the bounds as needed
        frame1.add(separator);

        addToCartButton = new JButton("Add to Shopping Cart");
        addToCartButton.setFont(new Font("Poppins", Font.PLAIN, 12));
        addToCartButton.setBounds(300, 600, 150, 30);
        addToCartButton.setForeground(Color.WHITE);
        addToCartButton.setBackground(new Color(7, 120, 173, 253));
        addToCartButton.setVisible(false);
        frame1.add(addToCartButton);

        product_details_label = new JLabel("Selected Products - Details");
        product_details_label.setFont(new Font("Poppins", Font.BOLD, 12));
        product_details_label.setBounds(50, 390, 200, 30);
        product_details_label.setVisible(false);
        frame1.add(product_details_label);

        product_id_label = new JLabel();
        product_id_label.setFont(font);
        product_id_label.setBounds(50, 420, 100, 30);
        product_id_label.setVisible(false);
        frame1.add(product_id_label);

        category_label = new JLabel();
        category_label.setFont(font);
        category_label.setBounds(50, 450, 150, 30);
        category_label.setVisible(false);
        frame1.add(category_label);

        product_name_label = new JLabel();
        product_name_label.setFont(font);
        product_name_label.setBounds(50, 480, 200, 30);
        product_name_label.setVisible(false);
        frame1.add(product_name_label);

        size_label = new JLabel();
        size_label.setFont(font);
        size_label.setBounds(50, 510, 100, 30);
        size_label.setVisible(false);
        frame1.add(size_label);

        brand_name_label = new JLabel();
        brand_name_label.setFont(font);
        brand_name_label.setBounds(50, 510, 200, 30);
        brand_name_label.setVisible(false);
        frame1.add(brand_name_label);

        color_label = new JLabel();
        color_label.setFont(font);
        color_label.setBounds(50, 540, 200, 30);
        color_label.setVisible(false);
        frame1.add(color_label);

        warranty_label = new JLabel();
        warranty_label.setFont(font);
        warranty_label.setBounds(50, 540, 200, 30);
        warranty_label.setVisible(false);
        frame1.add(warranty_label);

        item_label = new JLabel();
        item_label.setFont(font);
        item_label.setBounds(50, 570, 200, 30);
        item_label.setVisible(false);
        frame1.add(item_label);


        // Add a ListSelectionListener to detect row selection changes
        ListSelectionModel selectionModel = table.getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = table.getSelectedRow();

                    if (selectedRow != -1) {
                        // Row is selected, update the product details labels
                        updateProductDetails(selectedRow);

                        // Show the "Add to Shopping ItemCart" button
                        addToCartButton.setVisible(true);

                    } else {
                        // No row is selected, hide the product details labels and the button
                        hideProductDetails();
                    }
                }
            }
        });

        addToCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selected_row = table.getSelectedRow();

                // Prompt the user to enter the quantity
                String inputQuantity = JOptionPane.showInputDialog(frame1, "Enter quantity:","Quantity",JOptionPane.QUESTION_MESSAGE);

                int quantity_from_list = 0;

                if(selected_row != -1){
                    String pro_id = table.getValueAt(selected_row,0).toString();
                    quantity_from_list = manager.getQuantity(pro_id);
                }

                // Check if the user entered a valid quantity
                if (inputQuantity != null && !inputQuantity.trim().isEmpty()) {
                    if (!inputQuantity.matches("\\d+")) {
                        JOptionPane.showMessageDialog(frame1, "Invalid quantity. Please enter a valid number.", "Invalid Quantity", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    int quantity = Integer.parseInt(inputQuantity);
                    // Check if the quantity input contains letters or special characters
                    if(quantity > quantity_from_list){
                        // Display a message indicating that the entered quantity exceeds the available quantity
                        String message = "Quantity entered exceeds available stock.";
                        JOptionPane.showMessageDialog(frame1, message, "Invalid Quantity", JOptionPane.ERROR_MESSAGE);
                        return; // Exit the actionPerformed method
                    }

                    // Check if the quantity is greater than 0
                    if (quantity > 0) {
                        String category = table.getValueAt(selected_row,2).toString();

                        String productId = table.getValueAt(selected_row, 0).toString();
                        String productName = table.getValueAt(selected_row, 1).toString();

                        String str_price = table.getValueAt(selected_row, 3).toString();
                        double price = Double.parseDouble(str_price);

                        String additionalInfo = table.getValueAt(selected_row, 4).toString();

                        if(category.equals("Electronics")){
                            // Split the string using the comma as a delimiter
                            String[] parts = additionalInfo.split(", ");
                            // Extract brand name and warranty information
                            String brandName = parts[0];  // The first part before the comma
                            String warrantyInfo = parts[1];  // The second part after the comma
                            // Extract numerical warranty value (assuming it's always followed by a space and then 'weeks warranty')
                            int warrantyValue = Integer.parseInt(warrantyInfo.split(" ")[0]);

                            Electronic electronic =  new Electronic(productId,productName,quantity,price,brandName,warrantyValue);
                            cart.addProductsToCart(electronic);

                        }
                        else if(category.equals("Clothing")){
                            String[] part = additionalInfo.split(", ");
                            String size = part[0];
                            String color = part[1];
                            Clothing clothing =  new Clothing(productId,productName,quantity,price,size,color);
                            cart.addProductsToCart(clothing);
                        }

                        JOptionPane.showMessageDialog(frame1,"Item is successfully added to the Cart","Success",JOptionPane.INFORMATION_MESSAGE);
                    }else{
                        JOptionPane.showMessageDialog(frame1,"Quantity cannot be less than zero.","Error",JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

    }

    private void updateProductDetails(int selectedRow) {
        // Retrieve information from the selected row
        String productId = table.getValueAt(selectedRow, 0).toString();
        String productName = table.getValueAt(selectedRow, 1).toString();
        String category = table.getValueAt(selectedRow, 2).toString();
        String details = table.getValueAt(selectedRow, 4).toString();

        // Split the details using the comma as a separator
        String[] detailParts = details.split(", ");

        // Update the product details labels
        product_details_label.setVisible(true);
        product_id_label.setText("Product Id: " + productId);
        product_id_label.setVisible(true);
        category_label.setText("Category: " + category);
        category_label.setVisible(true);
        product_name_label.setText("Name: " + productName);
        product_name_label.setVisible(true);


        // Determine the category and update additional labels accordingly
        if ("Electronics".equals(category) && detailParts.length == 2) {
            // Electronics category
            String brandName = detailParts[0];
            String warrantyPeriod = detailParts[1].replaceAll("\\D", ""); // Extract only digits

            size_label.setVisible(false);
            brand_name_label.setText("Brand Name: " + brandName);
            brand_name_label.setVisible(true);
            color_label.setVisible(false);
            warranty_label.setText("Warranty Period: " + warrantyPeriod + " weeks");
            warranty_label.setVisible(true);
        } else if ("Clothing".equals(category) && detailParts.length == 2) {
            // Clothing category
            String size = detailParts[0];
            String color = detailParts[1];

            brand_name_label.setVisible(false);
            size_label.setText("Size: " + size);
            size_label.setVisible(true);
            color_label.setText("Color: " + color);
            color_label.setVisible(true);
            warranty_label.setVisible(false);
        }

        int quantity = manager.getQuantity(productId);
        item_label.setText("Items Available: " + quantity);
        item_label.setVisible(true);

    }

   private void hideProductDetails() {
        // Hide the product details labels and the "Add to Shopping ItemCart" button
        product_details_label.setVisible(false);
        product_id_label.setVisible(false);
        category_label.setVisible(false);
        product_name_label.setVisible(false);
        size_label.setVisible(false);
        brand_name_label.setVisible(false);
        color_label.setVisible(false);
        warranty_label.setVisible(false);
        item_label.setVisible(false);
        addToCartButton.setVisible(false);
    }

    public void getDataFromProductList(Object[][] rowData) {
        int rowIndex = 0;

        for (Product product : manager.productList) {
            Object[] row = new Object[5];

            // Common information for all products
            row[0] = product.getProductID();
            row[1] = product.getProductName();
            double price = product.getPrice();
            row[3] = String.format("%.2f", price);

            // Specific information based on product type
            if (product instanceof Electronic electronicProduct) {
                row[2] = "Electronics";
                row[4] = electronicProduct.getBrandName() + ", " + electronicProduct.getWarrantyPeriod() + " weeks warranty";
            } else if (product instanceof Clothing clothingProduct) {
                row[2] = "Clothing";
                row[4] = clothingProduct.getSize() + ", " + clothingProduct.getColor();
            }

            rowData[rowIndex++] = row;

        }

    }

    private void filterTable() {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);

        int selectedCategoryIndex = category_selection.getSelectedIndex();
        if (selectedCategoryIndex == 0) {
            sorter.setRowFilter(null); // Show all rows
        } else {
            String selectedCategory = category[selectedCategoryIndex];
            sorter.setRowFilter(RowFilter.regexFilter(selectedCategory, 2)); // Filter based on category column
        }
    }




}

