package librarymanagement;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import librarymanagement.DAO.*;
import librarymanagement.Entity.*;
public class ViewStudents extends JFrame {
    JTable studentsTable;
    ViewStudents() {
        setTitle("View Students");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        String[] columnNames = {"Student ID", "Student Name", "Student Contact"};
        Object[][] StudentData = {};

        
        StudentDAO studentDAO = new StudentDAO();
        List<Student> students = studentDAO.getAllStudents();

        
        StudentData = new Object[students.size()][3];
        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            StudentData[i][0] = student.getStudentId();
            StudentData[i][1] = student.getStudentName();
            StudentData[i][2] = student.getStudentContact();
        }

        studentsTable = new JTable(StudentData, columnNames);
        JScrollPane scrollPane = new JScrollPane(studentsTable);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }
}