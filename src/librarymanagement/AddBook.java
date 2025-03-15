package librarymanagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import librarymanagement.DAO.*;
import librarymanagement.Entity.*;

public class AddBook extends JFrame implements ActionListener {
    JTextField bookIdField, bookNameField, authorField, quantityField;
    JButton addButton;

    public AddBook() {
        setTitle("Add Book");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel bookIdLabel = new JLabel("Book ID:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(bookIdLabel, gbc);

        bookIdField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(bookIdField, gbc);

        JLabel bookNameLabel = new JLabel("Book Name:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(bookNameLabel, gbc);

        bookNameField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(bookNameField, gbc);

        JLabel authorLabel = new JLabel("Author:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(authorLabel, gbc);

        authorField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(authorField, gbc);

        JLabel quantityLabel = new JLabel("Available Quantity:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(quantityLabel, gbc);

        quantityField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(quantityField, gbc);

        addButton = new JButton("Add Book");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        addButton.addActionListener(this);
        panel.add(addButton, gbc);

        add(panel, BorderLayout.CENTER);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            String bookId = bookIdField.getText().trim();
            String bookName = bookNameField.getText().trim();
            String author = authorField.getText().trim();
            String quantityStr = quantityField.getText().trim();

            if (bookId.isEmpty() || bookName.isEmpty() || author.isEmpty() || quantityStr.isEmpty() ||
                bookId.contains(" ") || bookName.contains(" ") || author.contains(" ") || quantityStr.contains(" ")) {
                JOptionPane.showMessageDialog(this, "All fields are required and cannot contain spaces.");
                return;
            }

            try {
                int quantity = Integer.parseInt(quantityStr);

                Book book = new Book();
                book.setBookId(bookId);
                book.setBookName(bookName);
                book.setAuthor(author);
                book.setQuantity(quantity);

                BookDAO bookDAO = new BookDAO();
                bookDAO.saveBook(book);

                JOptionPane.showMessageDialog(this, "Book added successfully!");
                this.dispose();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid quantity. Please enter a valid number.");
            } catch(Exception ex){
                JOptionPane.showMessageDialog(this, "Error adding book. Book ID may already exist or there was a database error.");
                ex.printStackTrace();
            }
        }
    }
}