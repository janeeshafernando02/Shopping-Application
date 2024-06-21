package org.example;
// Import necessary packages and classes
import javax.swing.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.SQLIntegrityConstraintViolationException;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

// Declare private instance variables for GUI components
public class RegisterGUI{
    private JTextField name_field;
    private JTextField username_field;
    private JTextField email_field;
    private JTextField contact_number_field;
    private JPasswordField password_field;
    private JPasswordField confirm_password_field;
    private JButton submit;
    private JButton login;
    private JLabel name_validation;
    private JLabel username_validation;
    private JLabel email_validation;
    private JLabel contact_validation;
    private JLabel password_validation;
    private JLabel confirm_password_validation;
    private JFrame frame1;
    User user;


    // Constructor for the RegisterGUI class
    public RegisterGUI(){
        // Initialize and set up the components
        initComponents();
    }

    // Method to initialize GUI components
    private void initComponents(){
        // Set up fonts for the GUI
        Font font = new Font("Poppins", Font.PLAIN, 12);
        Font button_font = new Font("Poppins", Font.BOLD, 12);
        Font validation_font = new Font("Poppins",Font.PLAIN,10);

        //Create a frame instance of JFrame class
        frame1 = new JFrame("Register");
        //Set the frame size
        frame1.setSize(1100, 580);
        //Close the JFrame when a button is clicked
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a JPanel with a custom background color
        JPanel panel = new JPanel();
        panel.setBounds(500, 0, 420, 560);
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

        // Create another JPanel for the image
        JPanel panel2 = new JPanel();
        panel2.setBounds(0, -5, 500, 560);
        ImageIcon image = new ImageIcon("C:\\Users\\Janisha Fernando\\OneDrive\\Desktop\\OOP Coursework\\OnlineShoppingApplication\\src\\main\\java\\org\\example\\Register Image.jpg");
        JLabel image_icon = new JLabel(image);
        image_icon.setBounds(0, 0, 500, 560);
        panel2.add(image_icon);
        frame1.add(panel2);


        // Create and set up JLabel for the title
        JLabel title = new JLabel("Registration");
        title.setBounds(710, 40, 200, 50);
        Font title_font = new Font("Poppins", Font.BOLD, 32);
        title.setFont(title_font);
        // Set custom font color for the JButton
        title.setForeground(new Color(7, 109, 157, 253));
        panel.add(title);

        // Create JLabel for name label
        JLabel name_label = new JLabel("Name");
        name_label.setFont(font);
        name_label.setBounds(570,130,100,30);
        panel.add(name_label);

        //Create JLabel for name text field
        name_field = new JTextField();
        name_field.setFont(font);
        name_field.setBounds(570,160,200,25);
        name_field.setForeground(new Color(0,0,0));
        panel.add(name_field);

        //Create JLabel for validation message
        name_validation = new JLabel("* Name is required");
        name_validation.setBounds(570, 185, 250, 25);
        name_validation.setFont(validation_font);
        name_validation.setForeground(new Color(169, 24, 24));
        panel.add(name_validation);
        name_validation.setVisible(false);

        // Add focus listeners to the name field
        name_field.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        name_field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                name_fieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                name_fieldFocusLost(evt);
            }
        });

        //Create JLabel for username
        JLabel user_name = new JLabel("Username");
        user_name.setBounds(830,130,100,30);
        user_name.setForeground(new Color(0,0,0));
        user_name.setFont(font);
        panel.add(user_name);

        //Create JTextField for username text field
        username_field = new JTextField();
        username_field.setFont(font);
        username_field.setBounds(830,160,200,25);
        username_field.setForeground(new Color(0,0,0));
        panel.add(username_field);

        //Validation message for username
        username_validation = new JLabel("* Username is required");
        username_validation.setBounds(830, 185, 250, 25);
        username_validation.setFont(validation_font);
        username_validation.setForeground(new Color(169, 24, 24));
        panel.add(username_validation);
        username_validation.setVisible(false);


        // Add focus listeners to the username field
        username_field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                username_fieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                username_fieldFocusLost(evt);
            }
        });

        //Create JLabel for email address
        JLabel email = new JLabel("Email");
        email.setBounds(570,230,230,30);
        email.setForeground(new Color(0,0,0));
        email.setFont(font);
        panel.add(email);

        //Create JTextField for email address text field
        email_field = new JTextField();
        email_field.setBounds(570,260,200,25);
        email_field.setFont(font);
        email_field.setForeground(new Color(0,0,0));
        panel.add(email_field);

        //Email address validation message
        email_validation = new JLabel("* Email is required");
        email_validation.setBounds(570, 285, 250, 25);
        email_validation.setFont(validation_font);
        email_validation.setForeground(new Color(169, 24, 24));
        panel.add(email_validation);
        email_validation.setVisible(false);

        // Add focus listeners to the email field
        email_field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                email_fieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                email_fieldFocusLost(evt);
            }
        });

        //Create JLabel for contact number
        JLabel contact_number_label = new JLabel("Contact Number");
        contact_number_label.setFont(font);
        contact_number_label.setForeground(new Color(0,0,0));
        contact_number_label.setBounds(830,230,200,25);
        panel.add(contact_number_label);

        //Create contact number text field
        contact_number_field = new JTextField();
        contact_number_field.setFont(font);
        contact_number_field.setBounds(830,260,200,25);
        contact_number_field.setForeground(new Color(0,0,0));
        panel.add(contact_number_field);

        //Contact number validation message
        contact_validation = new JLabel("* Contact number is required");
        contact_validation.setBounds(830, 285, 250, 25);
        contact_validation.setFont(validation_font);
        contact_validation.setForeground(new Color(169, 24, 24));
        panel.add(contact_validation);
        contact_validation.setVisible(false);

        //Focus listener for contact number field
        contact_number_field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                contact_number_fieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                contact_number_fieldFocusLost(evt);
            }
        });

        //Create JLabel for password
        JLabel password_label = new JLabel("Password");
        password_label.setBounds(570,330,200,25);
        password_label.setFont(font);
        panel.add(password_label);

        //Create password text field
        password_field = new JPasswordField();
        password_field.setFont(font);
        password_field.setForeground(new Color(0,0,0));
        password_field.setBounds(570,360,200,25);
        password_field.setDoubleBuffered(true);
        password_field.setEchoChar('\u0000');
        panel.add(password_field);

        //Password validation message
        password_validation = new JLabel("* Password is required");
        password_validation .setBounds(570, 385, 250, 25);
        password_validation .setFont(validation_font);
        password_validation.setForeground(new Color(169, 24, 24));
        panel.add(password_validation);
        password_validation.setVisible(false);

        // Add focus listeners to the password field
        password_field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                password_fieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                password_fieldFocusLost(evt);
            }
        });


        //Create JLabel for confirm password
        JLabel confirm_password_label = new JLabel("Confirm Password");
        confirm_password_label.setFont(font);
        confirm_password_label.setBounds(830,330,200,25);
        panel.add(confirm_password_label);

        //Create password confirm password text field
        confirm_password_field = new JPasswordField();
        confirm_password_field.setBounds(830,360,200,25);
        confirm_password_field.setFont(font);
        panel.add(confirm_password_field);

        // Confirm Password validation message
        confirm_password_validation = new JLabel("* Confirm password is required");
        confirm_password_validation .setBounds(830, 385, 250, 25);
        confirm_password_validation .setFont(validation_font);
        confirm_password_validation.setForeground(new Color(169, 24, 24));
        panel.add(confirm_password_validation);
        confirm_password_validation.setVisible(false);

        // Set the echo character of the confirm_password_field to '\u0000'
        // This ensures that the text in the confirm_password_field is displayed in plain text, not as masked characters
        confirm_password_field.setEchoChar('\u0000');

        // Add focus listeners to the confirmation password field
        confirm_password_field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                confirm_password_fieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                confirm_password_fieldFocusLost(evt);
            }
        });

        // Create JButton for submission
        submit = new JButton("Submit");
        submit.setFont(button_font);
        submit.setForeground(new Color(255, 255, 255));
        // Set a colour to the button
        submit.setBackground(new Color(7, 120, 173, 253));
        submit.setBounds(745,430,90,25);
        panel.add(submit);

        // Add mouse listeners to the submit button
        submit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                submitMouseEntered(evt);
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                submitMouseExited(evt);
            }
        });

        //Add action listener to submit
        submit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) { submitActionPerformed(evt);
            }
        });

        // Create JButton for going back to login
        login = new JButton("Back to Login");
        login.setFont(button_font);
        login.setBackground(new Color(228, 240, 255, 253));
        login.setBounds(690, 470, 200, 30);
        login.setForeground(new Color(0,152,204));
        login.setBorderPainted(false);
        panel.add(login);

        // Add mouse listeners login button
        login.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                loginMouseExited(evt);
            }
        });

        //Add action listener to the login button
        login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) { loginActionPerformed(evt);
            }
        });
    }

    // Method to handle mouse entering the submit button
    private void submitMouseEntered(MouseEvent evt) {
        // Set a custom background color when the login button is hovered over
        submit.setBackground(new Color(47, 163, 246, 253));
    }

    // Method to handle mouse exiting to submit
    private void submitMouseExited(MouseEvent evt) {
        // Reset the background color when the login button is no longer hovered over
        submit.setBackground(new Color(7, 120, 173, 253));
    }

    // Set custom text color when the login button is hovered over
    private void loginMouseEntered(MouseEvent evt) {
        //Set a custom text color when the register button is hovered over
        login.setForeground(new Color(128, 0, 128));
    }

    // Reset text color when the login button is no longer hovered over
    private void loginMouseExited(MouseEvent evt) {
        //Reset the text color when the register button is no longer hovered over
        login.setForeground(new Color(0,152,204));
    }

    private void loginActionPerformed(ActionEvent evt) {
        // Create a new instance of RegisterGUI to open the registration window
        LoginGUI gui = new LoginGUI();
        JFrame registerFrame = (JFrame) SwingUtilities.getWindowAncestor(login);
        registerFrame.dispose();

    }

    // Set border color and hide validation message when the name field gains focus
    private void name_fieldFocusGained(FocusEvent evt) {
        name_field.setBorder(new LineBorder(new Color(47,163,246,253)));
        name_validation.setVisible(false);
    }

    // Reset border color when the name field loses focus
    private void name_fieldFocusLost(FocusEvent evt) {
        // Change the border color to indicate focus
        name_field.setBorder(new LineBorder(Color.BLACK));
    }

    // Set border color and hide validation message when the password field gains focus
    private void password_fieldFocusGained(FocusEvent evt) {
        password_field.setBorder(new LineBorder(new Color(47,163,246,253)));
        password_validation.setVisible(false);
    }

    // Reset border color when the password field loses focus
    private void password_fieldFocusLost(FocusEvent evt) {
        password_field.setBorder(new LineBorder(Color.BLACK));

    }

    // Set border color and hide validation message when the email field gains focus
    private void email_fieldFocusGained(FocusEvent evt) {
        email_field.setBorder(new LineBorder(new Color(47,163,246,253)));
        email_validation.setVisible(false);
    }

    // Reset border color when the email field loses focus
    private void email_fieldFocusLost(FocusEvent evt) {
        email_field.setBorder(new LineBorder(Color.BLACK));
    }

    // Set border color and hide validation message when the username field gains focus
    private void username_fieldFocusGained(FocusEvent evt) {
        username_field.setBorder(new LineBorder(new Color(47,163,246,253)));
        username_validation.setVisible(false);
    }

    // Reset border color when the username field loses focus
    private void username_fieldFocusLost(FocusEvent evt) {
        username_field.setBorder(new LineBorder(Color.BLACK));
    }

    // Set border color and hide validation message when the contact number field gains focus
    private void contact_number_fieldFocusGained(FocusEvent evt) {
        contact_number_field.setBorder(new LineBorder(new Color(47,163,246,253)));
        contact_validation.setVisible(false);
    }

    // Reset border color when the contact number field loses focus
    private void contact_number_fieldFocusLost(FocusEvent evt) {
        contact_number_field.setBorder(new LineBorder(Color.BLACK));
    }

    // Set border color and hide validation message when the confirm password field gains focus
    private void confirm_password_fieldFocusGained(FocusEvent evt) {
        confirm_password_field.setBorder(new LineBorder(new Color(47,163,246,253)));
        confirm_password_validation.setVisible(false);
    }

    // Reset border color when the confirm password field loses focu
    private void confirm_password_fieldFocusLost(FocusEvent evt) {
       confirm_password_field.setBorder(new LineBorder(Color.BLACK));
    }

    // Generate a random customer ID combining an uppercase letter and a 4-digit number
    private String generateCustomerId() {
        // Generate an uppercase letter
        char randomLetter = (char) ('A' + (Math.random() * 26));
        // Generate a 4-digit random number
        int randomNumber = (int) (Math.random() * 9000) + 1000;
        // Combine the letter and number
        return String.format("%c%04d", randomLetter,randomNumber);
    }

    // Handle the action when the submit button is clicked
    private void submitActionPerformed(ActionEvent evt) {
        // Get user inputs from the JTextFields
        String name =  name_field.getText();
        String username = username_field.getText();
        String email = email_field.getText();
        String phoneNumber = contact_number_field.getText();
        int length = phoneNumber.length();
        String password = password_field.getText();
        String confirmPassword = confirm_password_field.getText();

        // Validate the user inputs
        if (name.isEmpty()) {
            name_validation.setVisible(true);
            name_field.setBorder(new LineBorder(new Color(153,0,0)));
        }else if (name.matches(".*[0-9!@#$%^&*()_+{}|:\"<>?~`].*")) {
            JOptionPane.showMessageDialog(submit, "Invalid name", "Error", JOptionPane.ERROR_MESSAGE);
        }

        if (username.isEmpty()) {
            username_validation.setVisible(true);
            username_field.setBorder(new LineBorder(new Color(153,0,0)));
        }

        if (email.isEmpty()) {
            email_validation.setVisible(true);
            email_field.setBorder(new LineBorder(new Color(153,0,0)));
        }else if(!email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")){
            JOptionPane.showMessageDialog(submit, "Enter a valid email address","Error",JOptionPane.ERROR_MESSAGE);
        }

        if (phoneNumber.isEmpty()) {
            contact_validation.setVisible(true);
            contact_number_field.setBorder(new LineBorder(new Color(153,0,0)));
        }else if(length != 10 || !phoneNumber.matches("\\d+")){
            JOptionPane.showMessageDialog(submit, "Enter a valid 10-digit phone number","Error",JOptionPane.ERROR_MESSAGE);
        }

        if (password.isEmpty()) {
            password_validation.setVisible(true);
            password_field.setBorder(new LineBorder(new Color(153,0,0)));
        }else if(password.length() < 8){
            JOptionPane.showMessageDialog(submit, "Password must be at least 8 characters long","Error",JOptionPane.ERROR_MESSAGE);
        }

        if (confirmPassword.isEmpty()) {
            confirm_password_validation.setVisible(true);
            confirm_password_field.setBorder(new LineBorder(new Color(153,0,0)));
        }else if (!confirmPassword.equals(password)) {
            JOptionPane.showMessageDialog(submit, "Passwords do not match","Error",JOptionPane.ERROR_MESSAGE);
        }
        else{
            try {
                // Get a connection from the DatabaseHandler
                Connection connection = DatabaseHandler.getConnection();

                // Generate a customer ID
                String customerId = generateCustomerId();

                String query = "INSERT INTO customer VALUES('" + customerId + "','" + name + "','" + username + "', '" + email + "','" + phoneNumber + "','" + password + "','" + confirmPassword + "')";
                Statement statement = connection.createStatement();

                // Execute the query to insert the new customer
                statement.executeUpdate(query);

                // Display a success message
                JOptionPane.showMessageDialog(submit,"Your account is successfully created");
                User.setUserId(customerId);

                // Close the database connection using the DatabaseHandler
                DatabaseHandler.closeConnection(connection);

                WestminsterShoppingCenterGUI gui = new WestminsterShoppingCenterGUI();
                JFrame registerFrame = (JFrame) SwingUtilities.getWindowAncestor(submit);
                registerFrame.dispose();


            }catch (SQLIntegrityConstraintViolationException ex) {
                // Handle the case where the username is already taken
                JOptionPane.showMessageDialog(submit, "Account already exist");
            }catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}

