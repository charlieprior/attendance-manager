package edu.duke.ece651.team2.attendancemanager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Calendar;

import org.junit.jupiter.api.Test;

public class LectureTest {

  protected ArrayList<Student> initailSetting(){
    Student s1 = new Student
      ("Kenan", "kc566", "kc566@duke.edu", "kencolak");
    Student s2 = new Student
      ("Charlie", "cgp26", "cgp26@duke.edu", "charliep");
    ArrayList<Student> stu1 = new ArrayList<>();
    stu1.add(s1);
    stu1.add(s2);
    return stu1;
  }

  @Test
  public void test_LectureGetterSetter() {
    Lecture l1 = new Lecture();
    l1.setLectureID("L1");
    Calendar c1 = Calendar.getInstance();
    l1.setDate(c1);
    assertEquals("L1",l1.getLectureID());
    assertEquals(c1,l1.getDate());
    ArrayList<Student> stu1 = initailSetting();
    l1.addStudents(stu1);
    assertEquals(stu1,l1.getStudents());
  }

  BufferedReader provideInput(String data) {
    BufferedReader input = new BufferedReader(new StringReader(data));
    return input;
  }


  @Test
  public void  testAttendanceRecord() throws IOException{
    Lecture l1 = new Lecture();
    l1.addStudents(initailSetting());
  }

  @Test
  public void testEndLecture(){

  }
}
