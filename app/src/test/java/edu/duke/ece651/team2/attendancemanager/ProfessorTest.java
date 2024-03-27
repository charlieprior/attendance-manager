package edu.duke.ece651.team2.attendancemanager;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

public class ProfessorTest {
    private Professor professor1;
    private Professor professor2;
    private Course testCourse1;
    private Course testCourse2;

    @BeforeEach
    void setUp() {
        professor1 = new Professor("John Doe", "12345", "AAA@gmail.com");
        professor2 = new Professor("TTT", "11111", "BBB@gmail.com");
        testCourse1 = new Course("1", "CS101", professor1, null,null);
        testCourse2 = new Course("2", "CS301", professor2, null,null);
        // testCourse1 = new Course("CS101", "Intro to Computer Science");
        // testCourse2 = new Course("CS301", "Database System");
    }

    @Test
    public void testGetName() {
        assertEquals("John Doe", professor1.getName());
        assertNotEquals("A", professor1.getName());
        assertNotEquals(professor2.getName(), professor1.getName());
    }

    @Test
    public void testGetEmail() {
        assertEquals("AAA@gmail.com", professor1.getEmail());
        assertNotEquals("A", professor1.getEmail());
        assertNotEquals(professor2.getEmail(), professor1.getEmail());
    }

    @Test
    void testGetProfessorID() {
        assertEquals("12345", professor1.getProfessorID());
        assertNotEquals("11", professor1.getProfessorID());
        assertNotEquals(professor2.getProfessorID(), professor1.getProfessorID());
    }

    @Test
    void testCourses() {
        // Initially, the professor should have no courses
        assertTrue(professor1.getCourses().isEmpty());
        assertEquals(professor1.getCourses(), professor2.getCourses());
        // Add a course and check if it is reflected in the courses list
        professor1.addCourse(testCourse1);
        assertEquals(1, professor1.getCourses().size());
        assertTrue(professor1.getCourses().contains(testCourse1));
        assertNotEquals(professor1.getCourses(), professor2.getCourses());

        // Add another course and check the list size and contents
        professor1.addCourse(testCourse2);
        assertEquals(2, professor1.getCourses().size());
        assertTrue(professor1.getCourses().contains(testCourse2));

        // Remove a course and validate the list again
        professor1.removeCourse(testCourse1);
        assertEquals(1, professor1.getCourses().size());
        assertFalse(professor1.getCourses().contains(testCourse1));
    }
}
