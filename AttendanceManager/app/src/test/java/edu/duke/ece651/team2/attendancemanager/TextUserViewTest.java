package edu.duke.ece651.team2.attendancemanager;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TextUserViewTest {
    @Test
    void testPrintCourse() {
        BufferedReader input = new BufferedReader(new StringReader(""));
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(bytes, true);

        TextUserView view = new TextUserView(output);

        Professor prof = new Professor("John", "123", "john@example.edu");
        Course course1 = new Course("C1", "Course1", prof, 0, new ArrayList<Student>(), input);
        Course course2 = new Course("C2", "Course2", prof, 0, new ArrayList<Student>(), input);

        prof.addCourse(course1);
        prof.addCourse(course2);

        String expected = "===========================================================================\n" +
                "Courses Taught by John\n" +
                "===========================================================================\n" +
                "1. C1 - Course1\n" +
                "2. C2 - Course2\n";
        view.printCourses(prof);
        assertEquals(expected, bytes.toString());
    }
    
      @Test
    void testPrintStudents() {
        BufferedReader input = new BufferedReader(new StringReader(""));
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(bytes, true);

        Professor prof = new Professor("John", "123", "john@example.edu");
        Course course1 = new Course("C1", "Course1", prof, 0, new ArrayList<Student>(), input);
        Student s1 = new Student("Kenan", "kc566", "kc566@duke.edu", "kencolak");
        Student s2 = new Student("Charlie", "cgp", "cgp@duke.edu", "charliep");
        TextUserView view = new TextUserView(output);

        prof.addCourse(course1);

        course1.addStudent(s1);
        course1.addStudent(s2);
        
        String expected = "===========================================================================\n"+
    "Students in Course Course1\n"+
    "===========================================================================\n"+
    "1. Kenan\n"+
    "2. Charlie\n";
        view.printStudents(course1);
        assertEquals(expected, bytes.toString());
    }

    @Test
    void testPrintStudentStatuses() throws IOException {
        BufferedReader input = new BufferedReader(new StringReader(""));
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(bytes, true);

        TextUserView view = new TextUserView(output);

        Professor prof = new Professor("John", "123", "john@example.edu");
        Student student1 = new Student("John Doe", "1", "john.doe@example.edu", "John");
        Student student2 = new Student("Qianyi Jane", "2", "mary.jane@example.edu", "Mary");
        Course course1 = new Course("C1", "Course1", prof, 0, new ArrayList<Student>(), input);
        course1.addStudent(student1);
        course1.addStudent(student2);
        Lecture lecture = course1.startLecture();
        view.printStudentStatus(lecture);

        // TODO: Test the output
    }

}