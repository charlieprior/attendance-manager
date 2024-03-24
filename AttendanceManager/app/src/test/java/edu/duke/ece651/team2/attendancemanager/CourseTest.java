package edu.duke.ece651.team2.attendancemanager;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class CourseTest {
    
    String courseID1 = "0";
    String courseName1 = "ECE 651";
    Professor professor1 = new Professor("Prof A", "ABCDEF", "abc@duke.edu");
    Student s1 = new Student("Xinyi Li", "1282080", "xl435@duke.edu", "Louise Li");
    Student s2 = new Student("Some one","98765","so@duke.edu","Someone");
    Student s3 = new Student("Some one Else","34567","soe@duke.edu","Someoneelse");
    ArrayList<Student> slst1 = new ArrayList<>();
    String courseID2 = "3";
    String courseName2 = "CS 123";
    Professor professor2 = new Professor("Prof CS123", "HIJKL", "cs123@duke.edu");
    int lectureTimes2 = 2;

    BufferedReader provideInput(String data) {
        BufferedReader input = new BufferedReader(new StringReader(data));
        return input;
    }

    @Test
    public void testConstructors(){
        slst1.add(s1);
        Course course1 = new Course(courseID1, courseName1, professor1, slst1,null);
        assertEquals(course1.getCourseID(), "0");
        assertEquals(course1.getName(), "ECE 651");
        assertEquals(course1.getLectureTimes(), 0);
        assertEquals(course1.getProfessor(), "Prof A");
        assertEquals(course1.numberOfStudents(), 1);
        slst1.add(s2);
        Course course2 = new Course(courseID2, courseName2, professor2, lectureTimes2,slst1,null);
        assertEquals(course2.getCourseID(), "3");
        assertEquals(course2.getName(), "CS 123");
        assertEquals(course2.getLectureTimes(), 2);
        assertEquals(course2.getProfessor(), "Prof CS123");
        assertEquals(course2.numberOfStudents(), 2);
    }

    @Test
    public void testaddStudents(){
        ArrayList<Student> slst2 = new ArrayList<>();
        Course course1 = new Course(courseID1, courseName1, professor1, slst1,null);
        assertEquals(course1.numberOfStudents(), 0);
        course1.addStudent(s1);
        assertEquals(course1.numberOfStudents(), 1);
        slst2.add(s2);
        slst2.add(s3);
        course1.addStudents(slst2);
        assertEquals(course1.numberOfStudents(), 3);
    }

    @Test
    public void testStartLecture() throws IOException{
        slst1.add(s1);
        slst1.add(s2);
        slst1.add(s3);

        Course course1 = new Course(courseID2, courseName2, professor2, lectureTimes2,slst1,provideInput("y\nN\ny\n"));
        Lecture newLec = course1.startLecture();
        assertEquals(newLec.getLectureID(), "3_3");
        assertEquals(course1.getLectureTimes(), 3);
        assertEquals(newLec.getCourseName(), "CS 123");
        assertEquals(newLec.getStudents(), slst1);
    }

    @Test
    public void testEndLecture() throws IOException{
        slst1.add(s1);
        slst1.add(s2);
        slst1.add(s3);

        Course course1 = new Course(courseID2, courseName2, professor2, lectureTimes2,slst1,provideInput("y\nN\ny\nn\n"));
        Lecture newLec = course1.startLecture();
        course1.endLecture(newLec);
    }
}
