package edu.duke.ece651.team2.server;

import edu.duke.ece651.team2.shared.*;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentDAOTest {
     DAOFactory factory = new DAOFactory();
     StudentDAO studentDAO = new StudentDAO(factory);

     @Test
     void testCreateRemoveUpdate() {
         Student test = new Student("John Smith", "test@example.com", 0, "John");
         studentDAO.create(test);
         assertNotNull(test.getStudentID());
         assertThrows(IllegalArgumentException.class, () -> studentDAO.create(test));

         test.setLegalName("Mary Jane");
         studentDAO.update(test);

         studentDAO.remove(test);
         assertNull(test.getStudentID());
         assertThrows(IllegalArgumentException.class, () -> studentDAO.remove(test));
         assertThrows(IllegalArgumentException.class, () -> studentDAO.update(test));
     }

     @Test
     void testList() {
         List<Student> students = studentDAO.list();
         for(Student student : students) {
             System.out.println(student.getDisplayName());
         }
     }

     @Test
     void testGet() {
         Student test = new Student("John Smith", "test@example.com", 0, "John");
         studentDAO.create(test);
         Student got = studentDAO.get(test.getStudentID());
         assertEquals(test, got);
     }

    //  @Test
    //  void testGetUniversityID(){
    //     Integer id = studentDAO.getUniversityID(1);
    //     assertEquals(1, id);
    //  }
}