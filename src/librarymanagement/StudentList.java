package librarymanagement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import librarymanagement.DAO.IssuedBookDAO;
import librarymanagement.Entity.*;

public class StudentList extends JFrame {

    JTable studentListTable;

    StudentList() {
        setTitle("Student List");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        String[] columnNames = {"Student ID", "Student Name", "Book ID", "Book Name", "Issue Date", "Return Date"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        try {
            IssuedBookDAO issuedBookDAO = new IssuedBookDAO();
            List<IssuedBook> issuedBooks = issuedBookDAO.getAllIssuedBooksWithStudentAndBook();

            if (issuedBooks != null) {
                for (IssuedBook issuedBook : issuedBooks) {
                    Student student = issuedBook.getStudent();
                    if (student != null && issuedBook.getBook() != null) {
                        model.addRow(new Object[]{
                                student.getStudentId(),
                                student.getStudentName(),
                                issuedBook.getBook().getBookId(),
                                issuedBook.getBook().getBookName(),
                                issuedBook.getIssueDate(),
                                issuedBook.getReturnDate() != null ? issuedBook.getReturnDate() : "Yet to be returned"
                        });
                    } else {
                        System.err.println("IssuedBook with ID " + issuedBook.getIssueId() + " has a null Book or Student.");
                    }
                }
                studentListTable = new JTable(model);
                JScrollPane scrollPane = new JScrollPane(studentListTable);
                add(scrollPane, BorderLayout.CENTER);
            } else {
                JOptionPane.showMessageDialog(this, "Error retrieving student list.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        setVisible(true);
    }

    public void populateStudentTable() {
        DefaultTableModel model = (DefaultTableModel) studentListTable.getModel();
        model.setRowCount(0);

        try {
            IssuedBookDAO issuedBookDAO = new IssuedBookDAO();
            List<IssuedBook> issuedBooks = issuedBookDAO.getAllIssuedBooksWithStudentAndBook();

            if (issuedBooks != null) {
                for (IssuedBook issuedBook : issuedBooks) {
                    Student student = issuedBook.getStudent();
                    if (student != null && issuedBook.getBook() != null) {
                        model.addRow(new Object[]{
                                student.getStudentId(),
                                student.getStudentName(),
                                issuedBook.getBook().getBookId(),
                                issuedBook.getBook().getBookName(),
                                issuedBook.getIssueDate(),
                                issuedBook.getReturnDate() != null ? issuedBook.getReturnDate() : "Yet to be returned"
                        });
                    } else {
                        System.err.println("IssuedBook with ID " + issuedBook.getIssueId() + " has a null Book or Student.");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Error retrieving student list.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new StudentList();
    }
}