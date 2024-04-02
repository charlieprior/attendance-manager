package edu.duke.ece651.team2.attendancemanager;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class CourseTest {

    String courseID1 = "0";
    String courseName1 = "ECE 651";
    University university = new University("Duke", true);
    Professor professor1 = new Professor("Prof A", "ABCDEF", "abc@duke.edu", university);
    Student s1 = new Student("Xinyi Li", "1282080", "xl435@duke.edu", "Louise Li");
    Student s2 = new Student("Some one", "98765", "so@duke.edu", "Someone");
    Student s3 = new Student("Some one Else", "34567", "soe@duke.edu", "Someoneelse");
    ArrayList<Student> slst1 = new ArrayList<>();
    String courseID2 = "3";
    String courseName2 = "CS 123";
    Professor professor2 = new Professor("Prof CS123", "HIJKL", "cs123@duke.edu", university);
    int lectureTimes2 = 2;

  /*BufferedReader provideInput(String data) {
        BufferedReader input = new BufferedReader(new StringReader(data));
        return input;
    }*/

    @Test
    public void testConstructors() {
        slst1.add(s1);
        Course course1 = new Course(courseID1, courseName1, professor1, slst1, null);
        assertEquals(course1.getCourseID(), "0");
        assertEquals(course1.getName(), "ECE 651");
        assertEquals(course1.getLectureTimes(), 0);
        assertEquals(course1.getProfessor(), "Prof A");
        assertEquals(course1.numberOfStudents(), 1);
        slst1.add(s2);
        Course course2 = new Course(courseID2, courseName2, professor2, lectureTimes2, slst1, null);
        assertEquals(course2.getCourseID(), "3");
        assertEquals(course2.getName(), "CS 123");
        assertEquals(course2.getLectureTimes(), 2);
        assertEquals(course2.getProfessor(), "Prof CS123");
        assertEquals(course2.numberOfStudents(), 2);
        Course course3 = new Course(courseID2, courseName2, professor2, slst1);
        assertEquals(course3.getCourseID(), "3");
        assertEquals(course3.getName(), "CS 123");
        assertEquals(course3.getLectureTimes(), 0);
        assertEquals(course3.getProfessor(), "Prof CS123");
        assertEquals(course3.numberOfStudents(), 2);
    }

    @Test
    public void testaddStudents() {
        ArrayList<Student> slst2 = new ArrayList<>();
        Course course1 = new Course(courseID1, courseName1, professor1, slst1);
        assertEquals(course1.numberOfStudents(), 0);
        course1.addStudent(s1);
        assertEquals(course1.numberOfStudents(), 1);
        slst2.add(s2);
        slst2.add(s3);
        course1.addStudents(slst2);
        assertEquals(course1.numberOfStudents(), 3);
        course1.getStudentsDisplayName();
        course1.changeStudentDisplayName("98765", "test");
        course1.changeStudentDisplayName("9876", "test");
        course1.dropStudents("98765");
        course1.dropStudents("9876");
    }

    @Test
    public void testStartLecture() throws IOException {
        slst1.add(s1);
        slst1.add(s2);
        slst1.add(s3);

        //Course course1 = new Course(courseID2, courseName2, professor2, lectureTimes2,slst1,null,provideInput("y\nN\ny\n"));
        Course course1 = new Course(courseID2, courseName2, professor2, slst1);
        ArrayList<AttendanceStatus> status = new ArrayList<>();
        status.add(AttendanceStatus.ABSENT);
        status.add(AttendanceStatus.TARDY);
        status.add(AttendanceStatus.PRESENT);
        Lecture newLec = course1.startLecture(status);
        assertEquals(newLec.getLectureID(), "3_1");
        assertEquals(course1.getLectureTimes(), 1);
        assertEquals(newLec.getCourseName(), "CS 123");
        assertEquals(newLec.getStudents(), slst1);
        Lecture newLec2 = course1.startLecture(status);
        assertEquals("3_2", newLec2.getLectureID());
        assertEquals(course1.getLectureTimes(), 2);
        assertEquals(newLec2.getCourseName(), "CS 123");
        assertEquals(newLec2.getStudents(), slst1);
        //course1.getLatestLecture();
    }
}
