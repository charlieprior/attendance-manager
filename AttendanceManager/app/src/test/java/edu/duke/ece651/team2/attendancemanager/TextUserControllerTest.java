package edu.duke.ece651.team2.attendancemanager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.duke.ece651.team2.attendancemanager.App.AttendanceStatus;

public class TextUserControllerTest {
    private TextUserController controller;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outContent)); // Redirect System.out to capture print statements
    }

    @Test
    public void testReadNewStudents() throws IOException {
        String simulatedInputs = "John Doe\nJohnD\n123456\njohndoe@example.com\n";
        BufferedReader reader = new BufferedReader(new StringReader(simulatedInputs));

        TextUserController controller = new TextUserController(reader, System.out);

        Student student = controller.readNewStudents();
        assertEquals("John Doe", student.getLegalName());
        assertEquals("JohnD", student.getDisplayName());
        assertEquals("123456", student.getStudentID());
        assertEquals("johndoe@example.com", student.getEmail());
        String expectedPrompts = "You are adding new Student, please provide the required info:\n" +
                "Whats the student's legal name:\n" +
                "Whats the student's display name:\n" +
                "Whats the student's UID:\n" +
                "Whats the student's E-Mail:\n";
        assertEquals(expectedPrompts, outContent.toString());
    }

    @Test
    public void testReadStudentStatus() throws IOException {
        Student dummyStudent = new Student("John Doe", "123", "johndoe@example.com", "John");
        String startInput = "p\n";
        String endInput = "l\n";

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(bytes, true);

        TextUserController controllerStart = new TextUserController(new BufferedReader(new StringReader(startInput)),
                output);
        System.out.println(bytes.toString());
        AttendanceStatus statusStart = controllerStart.readStudentStatus(dummyStudent, true);
        assertEquals(AttendanceStatus.present, statusStart);

        TextUserController controllerEnd = new TextUserController(new BufferedReader(new StringReader(endInput)),
                System.out);
        AttendanceStatus statusEnd = controllerEnd.readStudentStatus(dummyStudent, false);
        assertEquals(AttendanceStatus.tardy, statusEnd);

        assertTrue(outContent.toString()
                .contains("You are recording students status, please type the status of the student:"));
    }

    @Test
    public void testReadNewProfessor() throws IOException {
        String input = "Jane Doe\n987654321\njane.doe@example.com\n";
        TextUserController controller = new TextUserController(new BufferedReader(new StringReader(input)), System.out);

        Professor newProfessor = controller.readNewProfessor();

        assertNotNull(newProfessor, "New professor should not be null.");
        assertEquals("Jane Doe", newProfessor.getName());
        assertEquals("987654321", newProfessor.getProfessorID());
        assertEquals("jane.doe@example.com", newProfessor.getEmail());

        String outputText = outContent.toString();
        assertTrue(outputText.contains("What's the professor's legal name:"));
        assertTrue(outputText.contains("What's the professor's UID:"));
        assertTrue(outputText.contains("What's the professor's E-Mail:"));
    }

    @Test
    public void testReadNewCourse() throws IOException {
        String courseId = "CS101";
        String input = "Introduction to Programming\n";
        TextUserController controller = new TextUserController(new BufferedReader(new StringReader(input)), System.out);

        Professor professor;
        ArrayList<Student> students;

        professor = new Professor("Prof. John", "001", "prof.john@example.com");

        students = new ArrayList<>();
        students.add(new Student("Student1", "1001", "student1@example.com", "Stu1"));
        students.add(new Student("Student2", "1002", "student2@example.com", "Stu2"));

        Course newCourse = controller.readNewCourse(courseId, professor, students);
        assertNotNull(newCourse);
        assertEquals(courseId, newCourse.getCourseID());
        assertEquals("Introduction to Programming", newCourse.getName());
        assertEquals("Prof. John", newCourse.getProfessor());
        assertEquals(2, newCourse.numberOfStudents());

        // Optional: Check if prompts are correctly printed
        String outputText = outContent.toString();
        assertTrue(outputText.contains("What's the course's name"));
    }

    @Test
    public void testReadActionValidInput() throws IOException {
        String input = "1\n";
        TextUserController controller = new TextUserController(new BufferedReader(new StringReader(input)), System.out);

        int action = controller.readAction("Choose an action:");
        assertEquals(1, action);
    }

    @Test
    public void testReadActionInvalidThenValidInput() throws IOException {
        String input = "invalid\n2\n";
        TextUserController controller = new TextUserController(new BufferedReader(new StringReader(input)), System.out);

        int action = controller.readAction("Choose an action:");
        assertEquals(2, action);
    }
}