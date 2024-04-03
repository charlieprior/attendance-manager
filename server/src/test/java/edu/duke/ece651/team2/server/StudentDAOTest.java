package edu.duke.ece651.team2.server;

import edu.duke.ece651.team2.shared.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class StudentDAOTest {
    DAOFactory factory = new DAOFactory();
    StudentDAO studentDAO = new StudentDAO(factory);

    @Test
    void testCreate() {
        // TODO How do we get 100% coverage here?
        Student test = new Student("John Smith", "0", "test@example.com", "John");
        studentDAO.create(test);
        assertNotNull(test.getStudentID());
    }
}