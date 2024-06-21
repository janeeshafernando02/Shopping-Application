package org.example;
//Import Statements
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginGUI{
    // GUI components
    private JTextField username_field;
    private JPasswordField password_field;
    private JButton login_button;
    private JButton register_button;
    private JLabel username_validation;
    private JLabel password_validation;
    private JFrame frame1;
    public String username;

    // Constructor to initialize the Login GUI
    public LoginGUI(){
        initComponents();
    }

    // Method to initialize and set up GUI components
    private void initComponents(){
        // Define fonts for different components
        Font font = new Font("Poppins", Font.PLAIN, 12);
        Font button_font = new Font("Poppins", Font.BOLD, 12);
        Font validation_font = new Font("Poppins",Font.PLAIN,10);

        //Create a frame instance of JFrame class
        frame1 = new JFrame("Login");
        //Set the frame size
        frame1.setSize(880, 525);
        //Close the JFrame when a button is clicked
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a JPanel with a custom background color
        JPanel panel = new JPanel();
        panel.setBounds(440, 0, 420, 530);
        panel.setBackground(new Color(228, 240, 255, 253));
        panel.setLayout(null);
        //Add the panel to the frame
        frame1.setContentPane(panel);

        //Removing the default layout by pass the null argument to the setLayout method
        frame1.setLayout(null);
        // Make the frame visible
        frame1.setVisible(true);
        //center the form
        frame1.setLocationRelativeTo(null);

        // Create a panel for displaying an image
        JPanel panel2 = new JPanel();
        // Specify the size of the JPanel
        panel2.setBounds(0, 0, 450, 530);
        // Load an image from the specified path
        ImageIcon image = new ImageIcon("C:\\Users\\Janisha Fernando\\OneDrive\\Desktop\\OOP Coursework\\OnlineShoppingApplication\\src\\main\\java\\org\\example\\Login Image.jpg");
        // Create a JLabel to display the loaded image
        JLabel image_icon = new JLabel(image);
        //Specify the location of the image
        image_icon.setBounds(0, 0, 564, 490);
        // Add the image label to the panel
        panel2.add(image_icon);
        // Add the panel with the image to the main frame
        frame1.add(panel2);

        //Title Label
        JLabel title = new JLabel("Login");
        // Offset the shadow
        title.setBounds(620, 70, 100, 50);
        Font title_font = new Font("Poppins", Font.BOLD, 32);
        title.setFont(title_font);
        // Set custom font color for the JButton
        title.setForeground(new Color(7, 109, 157, 253));
        // add title to the panel
        panel.add(title);

        //Username Label
        JLabel username_label = new JLabel("User Name");
        username_label.setBounds(540, 150, 110, 30);
        username_label.setFont(font);
        panel.add(username_label);

        //Username TextField
        username_field = new JTextField();
        username_field.setFont(font);
        username_field.setForeground(new Color(0,0,0));
        username_field.setBounds(540, 180, 250, 25);
        panel.add(username_field);


        // Add a FocusListener to change the border color when focused
        username_field.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                username_fieldFocusGained(evt);

            }
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                username_fieldFocusLost(evt);
            }
        });

        //Username validation message
        username_validation = new JLabel("*Username required");
        username_validation.setBounds(540, 205, 250, 25);
        username_validation.setFont(validation_font);
        username_validation.setForeground(new Color(169, 24, 24));
        panel.add(username_validation);
        username_validation.setVisible(false);

        //Password Label
        JLabel password_label = new JLabel("Password");
        password_label.setBounds(540, 230, 110, 30);
        password_label.setFont(font);
        panel.add(password_label);

        //Password TextField
        password_field = new JPasswordField();
        password_field.setBounds(540, 260, 250, 25);
        password_field.setForeground(new Color(0,0,0));

        panel.add(password_field);

        // Add FocusListener to change the border color when focused
        password_field.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt){
                password_fieldFocusGained(evt);
            }
            @Override
            public void focusLost(java.awt.event.FocusEvent evt){
                password_fieldFocusLost(evt);
            }
        });


        // Validation message for password
        password_validation = new JLabel("*Password required");
        password_validation.setBounds(540, 285, 250, 25);
        password_validation.setFont(validation_font);
        password_validation.setForeground(new Color(169, 24, 24));
        panel.add(password_validation);
        password_validation.setVisible(false);

        //Login Button
        login_button = new JButton("Login");
        login_button.setBounds(620, 330, 90, 25);
        // Set font for the JButton
        login_button.setFont(button_font);
        // Set custom font color for the JButton
        login_button.setForeground(new Color(255, 255, 255));
        // Set a colour to the button
        login_button.setBackground(new Color(7, 120, 173, 253));
        panel.add(login_button);

        // Add mouse listeners for button hover effects
        login_button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                login_buttonMouseEntered(evt);
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                login_buttonMouseExited(evt);
            }
        });

        // Add action listener for login button
        login_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                login_buttonActionPerformed(evt);
            }
        });

        // Create an account label
        JLabel create_account = new JLabel("Don't have an account?");
        create_account.setFont(font);
        create_account.setBounds(605,365,300,40);
        panel.add(create_account);

        // Register Button
        register_button = new JButton("Create an Account");
        register_button.setFont(button_font);
        register_button.setBackground(new Color(228, 240, 255, 253));
        register_button.setBounds(580, 400, 170, 30);
        register_button.setForeground(new Color(0,152,204));
        register_button.setBorderPainted(false);
        panel.add( register_button);

        // Add mouse listeners for button hover effects
        register_button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                register_buttonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                register_buttonMouseExited(evt);
            }
        });

        // Add action listener for register button
        register_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                register_buttonActionPerformed(evt);
            }
        });

    }

    // Focus listener for the username field when it gains focus
    private void username_fieldFocusGained(FocusEvent evt) {
        username_field.setBorder(new LineBorder(new Color(47, 163, 246, 253)));
        username_validation.setVisible(false);
    }

    // Focus listener for the username field when it loses focus
    private void username_fieldFocusLost(FocusEvent evt) {
        username_field.setBorder(new LineBorder(Color.BLACK));
    }


    // Focus listener for the password field when it gains focus
    private void password_fieldFocusGained(FocusEvent evt) {
        password_field.setBorder(new LineBorder(new Color(47, 163, 246, 253)));
        password_validation.setVisible(false);
    }

    // Focus listener for the password field when it loses focus
    private void password_fieldFocusLost(FocusEvent evt){
        password_field.setBorder(new LineBorder(Color.BLACK));
    }

    // Mouse listener for the login button when mouse enters
    private void login_buttonMouseEntered(MouseEvent evt) {
        // Set a custom background color when the login button is hovered over
        login_button.setBackground(new Color(47, 163, 246, 253));
    }

    // Mouse listener for the login button when mouse exits
    private void login_buttonMouseExited(MouseEvent evt) {
        // Reset the background color when the login button is no longer hovered over
        login_button.setBackground(new Color(7, 120, 173, 253));
    }

    // Action listener for the login button
    private void login_buttonActionPerformed(ActionEvent evt) {
        // Get the entered username and password from the text fields
        username = username_field.getText();
        String password = password_field.getText();


        // Check if either or both fields are empty
        if (username.isEmpty() && password.isEmpty()) {
            // Show an error message if both fields are empty
            username_validation.setVisible(true);
            password_validation.setVisible(true);
            username_field.setBorder(new LineBorder(new Color(153, 0, 0)));
            password_field.setBorder(new LineBorder(new Color(153, 0, 0)));
        } else {
            //Check if the username field is empty
            if (username.isEmpty()) {
                username_validation.setVisible(true);
                username_field.setBorder(new LineBorder(new Color(153, 0, 0)));

            }
            //Check if the password field is empty
            else if (password.isEmpty()) {
                password_validation.setVisible(true);
                password_field.setBorder(new LineBorder(new Color(153, 0, 0)));
            } else {
                try {
                    // Establish a database connection
                    Connection connection = DatabaseHandler.getConnection();
                    // Prepare and execute a SQL query to check username and password
                    PreparedStatement statement = connection.prepareStatement("Select customer_id,username,password from customer where username=? and password=?");

                    // Set the first parameter in the prepared statement to the entered username
                    statement.setString(1, username);
                    // Set the second parameter in the prepared statement to the entered password
                    statement.setString(2, password);

                    // Execute the query and retrieve the result set from the database
                    ResultSet result_set = statement.executeQuery();

                    // Check if the query returned a result
                    if (result_set.next()) {
                        User.setUserId(result_set.getString("customer_id"));
                        // If the credentials are valid, open the FlightDetailsGUI and close the current window
                        WestminsterShoppingCenterGUI gui = new WestminsterShoppingCenterGUI();

                        // Close the login GUI
                        JFrame loginFrame = (JFrame) SwingUtilities.getWindowAncestor(login_button);
                        loginFrame.dispose();

                    } else {
                        // Show an error message for invalid credentials
                        JOptionPane.showMessageDialog(login_button, "Invalid Username or Password");
                        username_field.setText("");
                        password_field.setText("");
                    }

                    // Close the database connection using the DatabaseHandler
                    DatabaseHandler.closeConnection(connection);
                } catch (SQLException ex) {
                    // Log any SQLException that may occur
                    System.out.println(ex.getMessage());
                }

            }
        }
    }

    // Mouse listener for the register button when mouse enters
    private void register_buttonMouseEntered(MouseEvent evt) {
        //Set a custom text color when the register button is hovered over
        register_button.setForeground(new Color(128, 0, 128));
    }

    // Mouse listener for the register button when mouse exits
    private void register_buttonMouseExited(MouseEvent evt) {
        //Reset the text color when the register button is no longer hovered over
        register_button.setForeground(new Color(0,152,204));
    }

    // Action listener for the register button
    private void register_buttonActionPerformed(ActionEvent evt) {
        // Create a new instance of RegisterGUI to open the registration window
        RegisterGUI gui = new RegisterGUI();
        JFrame loginFrame = (JFrame) SwingUtilities.getWindowAncestor(register_button);
        loginFrame.dispose();

    }



}

