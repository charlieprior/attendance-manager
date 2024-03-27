package edu.duke.ece651.team2.attendancemanager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



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
        AttendanceStatus statusStart = controllerStart.readStudentStatus(dummyStudent.getDisplayName(), true);
        assertEquals(AttendanceStatus.PRESENT, statusStart);

        TextUserController controllerEnd = new TextUserController(new BufferedReader(new StringReader(endInput)),
                System.out);
        AttendanceStatus statusEnd = controllerEnd.readStudentStatus(dummyStudent.getDisplayName(), false);
        assertEquals(AttendanceStatus.TARDY, statusEnd);

        assertTrue(outContent.toString()
                .contains("You are recording students status, please type the status of the student:"));
    }

    @Test
    public void testReadNewProfessor() throws IOException {
        String input = "987654321\n" + "17778888aaa\n"+ "Jane Doe\njane.doe@example.com\n";
        TextUserController controller = new TextUserController(new BufferedReader(new StringReader(input)), System.out);
        University university = new University("Duke", true);
        ProtectedInfo info = new ProtectedInfo();
        Professor newProfessor = controller.register(info,university);

        assertNotNull(newProfessor, "New professor should not be null.");
        assertEquals("Jane Doe", newProfessor.getName());
        assertEquals("987654321", newProfessor.getProfessorID());
        assertEquals("jane.doe@example.com", newProfessor.getEmail());

        String outputText = outContent.toString();
        assertTrue(outputText.contains("Hi, new Professor, this is " +university.getName()+". What is your id?"));
        assertTrue(outputText.contains("What is your password?"));
        assertTrue(outputText.contains("What's your legal name:"));
        assertTrue(outputText.contains("What's your E-Mail:"));
    }

    @Test
    public void testReadNewCourse() throws IOException {
        String courseId = "CS101";
        String input = "Introduction to Programming\nn\n";
        TextUserController controller = new TextUserController(new BufferedReader(new StringReader(input)), System.out);

        Professor professor;
        ArrayList<Student> students;

        University university = new University("Duke", true);
        professor = new Professor("Prof. John", "001", "prof.john@example.com",university);

        students = new ArrayList<>();
        students.add(new Student("Student1", "1001", "student1@example.com", "Stu1"));
        students.add(new Student("Student2", "1002", "student2@example.com", "Stu2"));


        Course newCourse = controller.readNewCourse(courseId, professor);
        assertNotNull(newCourse);
        assertEquals(courseId, newCourse.getCourseID());
        assertEquals("Introduction to Programming", newCourse.getName());
        assertEquals("Prof. John", newCourse.getProfessor());
        assertEquals(0, newCourse.numberOfStudents());

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

    @Test
    public void testAskHeader() throws IOException {
        String input = "y\n";
        TextUserController controller = new TextUserController(new BufferedReader(new StringReader(input)), System.out);
        assertTrue(controller.askHeader()); // Test when user enters 'y'
    }

    @Test
    public void testReadLines() throws IOException{
        TextUserController controller = new TextUserController(new BufferedReader(new StringReader(",")), System.out);
        String[] exp = new String[5];
        exp[0] = "rohini";
        exp[1] = "rohiniR";
        exp[2] = "rrrrr";
        exp[3] = "23456@stu.edu";
        exp[4] = "3.2";
        String[] test = controller.readLines("rohini,rohiniR,rrrrr,23456@stu.edu,3.2",",");
        for(int i =0;i<test.length;i++){
            assertEquals(exp[i], test[i]);
        }
    }

    @Test
    public void testFileColumns() throws Exception {
        String input = "y\n";
        TextUserController controller = new TextUserController(new BufferedReader(new StringReader(input)), System.out);
        // FileReader filereader = new FileReader("/Users/louiseli/Desktop/Assignments/ECE/ECE651/project/project1/app/import/students.csv"); 
        // BufferedReader breader = new BufferedReader(filereader);// Provide a CSVReader instance
        // String line;
        // ArrayList<String> lines = new ArrayList<>();
        //     while((line = breader.readLine())!=null){
        //         lines.add(line);
        // }
        assertEquals(5, controller.fileColumns("rohini,rohiniR,rrrrr,23456@stu.edu,3.1",",")); // Assuming CSV has 5 columns
    }

    @Test
    public void testReadColumns() throws Exception {
        String input = "1\n3\n4\n2\n";
        TextUserController controller = new TextUserController(new BufferedReader(new StringReader(input)), System.out);
        ArrayList<Integer> ans = new ArrayList<>();
        ans.add(controller.readColumns(5, "1"));
        ans.add(controller.readColumns(5, "3"));
        ans.add(controller.readColumns(5, "4"));
        ans.add(controller.readColumns(5, "2"));
        ArrayList<Integer> expect = new ArrayList<>();
        expect.add(0);
        expect.add(2);
        expect.add(3);
        expect.add(1);
        assertEquals(expect, ans); // Ensure all columns are added to the list
    }

    @Test
    public void testReadStudents() throws Exception {
        String input = ",\n1\n3\n4\n2\n";
        TextUserController controller = new TextUserController(new BufferedReader(new StringReader(input)), System.out);
        FileReader filereader = new FileReader("/home/xl435/project-team-2/app/import/students.csv"); 
        BufferedReader breader = new BufferedReader(filereader);// Provide a CSVReader instance
        String line;
        breader.readLine();
        ArrayList<String> lines = new ArrayList<>();
        while((line = breader.readLine())!=null){
                lines.add(line);
        }
        breader.close();
        System.out.println("testReadStudents");
        System.out.println(lines);
        ArrayList<Student> students =  controller.readStudents(lines); 
        ArrayList<Student> expect = new ArrayList<>();
        Student s1 = new Student("amar","aaaa","12345@stu.edu","amarA");
        Student s2 = new Student("rohini", "rrrrr", "23456@stu.edu", "rohiniR");
        Student s3 = new Student("aman","34567","34567@stu.edu","amanA");
        Student s4 = new Student("rahul","rrr567","45678@stu.edu","rahulR");
        Student s5 = new Student("pratik","ppp","56789@stu.edu","pratikP");
        expect.add(s1);
        expect.add(s2);
        expect.add(s3);
        expect.add(s4);
        expect.add(s5);
        assertEquals(5, students.size()); // Ensure all columns are added to the list
        for(int i =0;i<5;i++){
            assertEquals(expect.get(i).getDisplayName(), students.get(i).getDisplayName());
            assertEquals(expect.get(i).getEmail(), students.get(i).getEmail());
            assertEquals(expect.get(i).getLegalName(), students.get(i).getLegalName());
            assertEquals(expect.get(i).getStudentID(), students.get(i).getStudentID());
        }
    }

    @Test
    public void testReadCSVFiles() throws Exception{
        String input = "/home/xl435/project-team-2/app/import/students.csv\ny\n,\n1\n3\n4\n2\n";
        TextUserController controller = new TextUserController(new BufferedReader(new StringReader(input)), System.out);
        FileReader filereader = new FileReader("/home/xl435/project-team-2/app/import/students.csv"); 
        BufferedReader breader = new BufferedReader(filereader);// Provide a CSVReader instance
        String line;
        breader.readLine();
        ArrayList<String> lines = new ArrayList<>();
        while((line = breader.readLine())!=null){
                lines.add(line);
        }
        breader.close();
        ArrayList<Student> students =  controller.readCSVFiles();
        ArrayList<Student> expect = new ArrayList<>();
        Student s1 = new Student("amar","aaaa","12345@stu.edu","amarA");
        Student s2 = new Student("rohini", "rrrrr", "23456@stu.edu", "rohiniR");
        Student s3 = new Student("aman","34567","34567@stu.edu","amanA");
        Student s4 = new Student("rahul","rrr567","45678@stu.edu","rahulR");
        Student s5 = new Student("pratik","ppp","56789@stu.edu","pratikP");
        expect.add(s1);
        expect.add(s2);
        expect.add(s3);
        expect.add(s4);
        expect.add(s5);
        assertEquals(5, students.size()); // Ensure all columns are added to the list
        for(int i =0;i<5;i++){
            assertEquals(expect.get(i).getDisplayName(), students.get(i).getDisplayName());
            assertEquals(expect.get(i).getEmail(), students.get(i).getEmail());
            assertEquals(expect.get(i).getLegalName(), students.get(i).getLegalName());
            assertEquals(expect.get(i).getStudentID(), students.get(i).getStudentID());
        }
    }

}