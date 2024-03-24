package edu.duke.ece651.team2.attendancemanager;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class StudentTest {
  @Test
  public void test_setter_getter() {
    Student s1 = new Student();
    String test = "Kenan";
    s1.setLegalName(test);
    s1.setStudentID("KC566");
    s1.setEmail("kc566@duke.edu");
    s1.setDisplayName("kc566");
    assertEquals("Kenan", s1.getLegalName());
    assertEquals("KC566", s1.getStudentID());
    assertEquals("kc566@duke.edu", s1.getEmail());
    assertEquals("kc566", s1.getDisplayName());
  }
}
