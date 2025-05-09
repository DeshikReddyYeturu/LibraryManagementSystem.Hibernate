package librarymanagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import librarymanagement.DAO.*;
import librarymanagement.Entity.*;

public class IssueBook extends JFrame implements ActionListener {
    JTextField bookIdField, bookNameField, studentIdField, studentNameField, studentContactField;
    JButton issueButton;

    IssueBook() {
        setTitle("Issue Book");
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

        issueButton = new JButton("Issue Book");
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        issueButton.addActionListener(this);
        panel.add(issueButton, gbc);

        add(panel, BorderLayout.CENTER);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == issueButton) {
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

            Student student = new Student();
            student.setStudentId(studentId);
            student.setStudentName(studentName);
            student.setStudentContact(studentContact);

            StudentDAO studentDAO = new StudentDAO();
            try {
                studentDAO.saveStudent(student);

                IssuedBook issuedBook = new IssuedBook();
                issuedBook.setStudent(student);
                issuedBook.setIssueDate(new Date());
                IssuedBookDAO issuedBookDAO = new IssuedBookDAO();
                issuedBookDAO.saveIssuedBook(issuedBook);

                JOptionPane.showMessageDialog(this, "Book issued successfully!");
                this.dispose();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error issuing book. Check student or book information. Or there was a database error.");
                ex.printStackTrace();
            }
        }
    }
}