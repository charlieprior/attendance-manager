package edu.duke.ece651.team2.server;

import edu.duke.ece651.team2.shared.*;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentDAOTest {
    DAOFactory factory = new DAOFactory();
    StudentDAO studentDAO = new StudentDAO(factory);

    @Test
    void testCreateRemove() {
        // TODO How do we get 100% coverage of exceptions?
        Student test = new Student("John Smith", "test@example.com", "John");
        studentDAO.create(test);
        assertNotNull(test.getStudentID());
        assertThrows(IllegalArgumentException.class, () -> studentDAO.create(test));
        studentDAO.remove(test);
        assertNull(test.getStudentID());
        assertThrows(IllegalArgumentException.class, () -> studentDAO.remove(test));
    }

    @Test
    void testList() {
        List<Student> students = studentDAO.list();
        for(Student student : students) {
            System.out.println(student.getDisplayName());
        }
    }
}