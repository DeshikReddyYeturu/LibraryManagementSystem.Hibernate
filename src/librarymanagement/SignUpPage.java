package librarymanagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import librarymanagement.DAO.*;
import librarymanagement.Entity.*;

public class SignUpPage extends JFrame implements ActionListener {

    JTextField usernameField;
    JPasswordField passwordField;
    JButton signupButton;

    public SignUpPage() {
        setTitle("Sign Up");
        setSize(400, 200); 
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

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

        signupButton = new JButton("Sign Up");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2; 
        gbc.anchor = GridBagConstraints.CENTER; 
        signupButton.addActionListener(this);
        panel.add(signupButton, gbc);

        add(panel, BorderLayout.CENTER);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String username = usernameField.getText().trim();
        char[] passwordChars = passwordField.getPassword();
        String password = new String(passwordChars).trim();

        if (username.isEmpty() || passwordChars == null || passwordChars.length == 0 || username.contains(" ") || password.contains(" ")) {
            JOptionPane.showMessageDialog(this, "Username and Password cannot be empty or contain spaces.");
            return;
        }

        Admin admin = new Admin();
        admin.setUsername(username);
        admin.setPassword(password);

        AdminDAO adminDAO = new AdminDAO();
        try {
            adminDAO.saveAdmin(admin);
            JOptionPane.showMessageDialog(this, "Sign up successful!");
            this.dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Sign up failed. Username might already exist or there was a database error.");
            ex.printStackTrace();
        }
    }
}