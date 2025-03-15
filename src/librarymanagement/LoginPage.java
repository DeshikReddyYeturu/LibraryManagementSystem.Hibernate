package librarymanagement;

import librarymanagement.DAO.*;
import librarymanagement.Entity.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginPage extends JFrame implements ActionListener {
    JTextField usernameField;
    JPasswordField passwordField;
    JButton loginButton, signupButton;

    public LoginPage() {
        setTitle("Library Management System - Login");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel titleLabel = new JLabel("Library Management System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(titleLabel, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel usernameLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(usernameLabel, gbc);

        usernameField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(passwordField, gbc);

        loginButton = new JButton("Login");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        loginButton.addActionListener(this);
        panel.add(loginButton, gbc);

        signupButton = new JButton("Signup");
        gbc.gridx = 1;
        gbc.gridy = 2;
        signupButton.addActionListener(this);
        panel.add(signupButton, gbc);

        add(panel, BorderLayout.CENTER);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
           
            if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Username and Password cannot be empty.");
                return;
            }

            AdminDAO adminDAO = new AdminDAO();
            Admin admin = adminDAO.getAdminByUsernameAndPassword(username, password);

            if (admin != null) {
                JOptionPane.showMessageDialog(this, "Login successful!");
                new MainMenu();
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Username or Password");
            }
        } else if (e.getSource() == signupButton) {
            new SignUpPage();
            this.dispose();
        }
    }

    public static void main(String[] args) {
        new LoginPage();
    }
}