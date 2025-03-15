package librarymanagement;

import javax.swing.*;
import java.awt.event.*;
import java.util.Date;
import librarymanagement.DAO.*;
import librarymanagement.Entity.*;
import java.awt.*;

public class ReturnBook extends JFrame implements ActionListener {
    JTextField bookIdField, bookNameField, studentIdField, studentNameField, studentContactField;
    JButton returnButton;
    private StudentList studentList;

    ReturnBook(StudentList studentList) {
        this.studentList = studentList;
        setTitle("Return Book");
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

        JLabel studentIdLabel = new JLabel("Student ID:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(studentIdLabel, gbc);

        studentIdField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(studentIdField, gbc);

        JLabel studentNameLabel = new JLabel("Student Name:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(studentNameLabel, gbc);

        studentNameField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(studentNameField, gbc);

        JLabel studentContactLabel = new JLabel("Student Contact:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(studentContactLabel, gbc);

        studentContactField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(studentContactField, gbc);

        returnButton = new JButton("Return Book");
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        returnButton.addActionListener(this);
        panel.add(returnButton, gbc);

        add(panel, BorderLayout.CENTER);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == returnButton) {
            String bookId = bookIdField.getText().trim();
            String bookName = bookNameField.getText().trim();
            String studentId = studentIdField.getText().trim();
            String studentName = studentNameField.getText().trim();
            String studentContact = studentContactField.getText().trim();

            if (bookId.isEmpty() || bookName.isEmpty() || studentId.isEmpty() || studentName.isEmpty() || studentContact.isEmpty() ||
                bookId.contains(" ") || bookName.contains(" ") || studentId.contains(" ") || studentName.contains(" ") || studentContact.contains(" ")) {
                JOptionPane.showMessageDialog(this, "All fields are required and cannot contain spaces.");
                return; 
            }

            try {
                BookDAO bookDAO = new BookDAO();
                Book book = bookDAO.getBookById(bookId);

                StudentDAO studentDAO = new StudentDAO();
                Student student = studentDAO.getStudentById(studentId);

                IssuedBookDAO issuedBookDAO = new IssuedBookDAO();
                IssuedBook issuedBook = issuedBookDAO.getIssuedBook(bookId, studentId);

                if (book != null && student != null && issuedBook != null) {
                    issuedBook.setReturnDate(new Date());
                    issuedBookDAO.updateIssuedBook(issuedBook);

                    book.setQuantity(book.getQuantity() + 1);
                    bookDAO.updateBook(book);

                    JOptionPane.showMessageDialog(this, "Book returned successfully!");
                    studentList.populateStudentTable();

                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Book return failed. Book ID or Student ID may be incorrect.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "An error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}