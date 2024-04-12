package edu.duke.ece651.team2.server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import edu.duke.ece651.team2.shared.Course;
import edu.duke.ece651.team2.shared.University;

public class CourseDAOTest {
    DAOFactory factory = new DAOFactory();
    CourseDAO courseDAO = new CourseDAO(factory);


    @Test
    public void testCreate(){
        // Create a new Course object
        Course course = new Course("Test Course", 1);

        // Insert the course into the database
        courseDAO.create(course);

        // Retrieve the course from the database using its ID
        Course retrievedCourse = courseDAO.get(course.getCourseID());

        // Check if the retrieved course is not null
        assertNotNull(retrievedCourse);

        // Check if the retrieved course matches the original course
        assertEquals(course.getName(), retrievedCourse.getName());
        assertEquals(course.getUniversityId(), retrievedCourse.getUniversityId());
        courseDAO.remove(course);
        assertEquals(0, courseDAO.list().size());
    }

    @Test
    public void testupdate(){
        Course course = new Course("Test Course", 1);

        // Insert the course into the database
        courseDAO.create(course);

        // Update the course's name
        course.setName("Updated Course Name");

        // Update the course in the database
        courseDAO.update(course);

        // Retrieve the updated course from the database using its ID
        Course retrievedCourse = courseDAO.get(course.getCourseID());

        // Check if the retrieved course is not null
        assertNotNull(retrievedCourse);

        // Check if the retrieved course's name matches the updated name
        assertEquals("Updated Course Name", retrievedCourse.getName());
        
        courseDAO.deleteAll();
        assertEquals(0, courseDAO.list().size());
    }
    
}
