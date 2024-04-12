package edu.duke.ece651.team2.admin;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.ResourceAccessMode;
import org.junit.jupiter.api.parallel.ResourceLock;
import org.junit.jupiter.api.parallel.Resources;

import edu.duke.ece651.team2.server.DAOFactory;
import edu.duke.ece651.team2.server.UniversityDAO;
import edu.duke.ece651.team2.shared.University;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AppTest {

  @Test
  public void testUniversities() throws IOException{
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    UserRegistration userRegistration = new UserRegistration();
    UserRegistrationView userRegistrationView = new UserRegistrationView(System.out, userRegistration, input);
    App app = new App(userRegistrationView);
    app.readUniversities("universities.csv");
    UniversityDAO universityDAO = new UniversityDAO(new DAOFactory());
    List<University> us = universityDAO.list();
    assertEquals(3, us.size());
    assertEquals("Duke University",us.get(0).getName());
    assertTrue(us.get(0).canChangeName());
    assertEquals("UNC",us.get(1).getName());
    assertTrue(us.get(1).canChangeName());
    assertEquals("NC State",us.get(2).getName());
    assertFalse(us.get(2).canChangeName());
    universityDAO.remove(us.get(0));
    universityDAO.remove(us.get(1));
    universityDAO.remove(us.get(2));
    assertThrows(IOException.class, ()->app.readUniversities("test.csv"));
  }

  @Test
  @ResourceLock(value = Resources.SYSTEM_OUT, mode = ResourceAccessMode.READ_WRITE)
  public void testApp() throws IOException, ClassNotFoundException {
    // DAOFactory factory = new DAOFactory();
    // UniversityDAO universityDAO = new UniversityDAO(factory);
    // University u = new University("University 1", true);
    // universityDAO.create(u);
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes, true);
    InputStream input = getClass().getClassLoader().getResourceAsStream("input.txt");
    assertNotNull(input);
    InputStream expectedStream = getClass().getClassLoader().getResourceAsStream("output.txt");
    assertNotNull(expectedStream);
    InputStream oldIn = System.in;
    PrintStream oldOut = System.out;
    try {
      System.setIn(input);
      System.setOut(out);
      App.main(new String[0]);
    } finally {
      System.setIn(oldIn);
      System.setOut(oldOut);
    }
    // universityDAO.remove(u);
    // String expected = new String(expectedStream.readAllBytes());
    // String actual = bytes.toString();
    // assertEquals(expected, actual);
  }
}
