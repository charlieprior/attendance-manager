package edu.duke.ece651.team2.attendancemanager;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.StringReader;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TextUserViewTest {
    @Test
    void testPrintCourse() {
        BufferedReader input = new BufferedReader(new StringReader(""));
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(bytes, true);

        Professor prof = new Professor("John", "123", "john@example.edu");
        Course course1 = new Course("C1", "Course1", prof, 0, new ArrayList<Student>(), input);
        Course course2 = new Course("C2", "Course2", prof, 0, new ArrayList<Student>(), input);
        TextUserView view = new TextUserView(output);

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

}