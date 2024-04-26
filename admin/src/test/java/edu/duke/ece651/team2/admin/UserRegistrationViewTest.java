package edu.duke.ece651.team2.admin;

import static org.mockito.Answers.valueOf;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.duke.ece651.team2.shared.Student;
import edu.duke.ece651.team2.shared.University;

public class UserRegistrationViewTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outContent)); // Redirect System.out to capture print statements
    }

  //@Disabled
    @Test
    public void testAddRemoveStudentAndProfessorViews() throws IOException {
        UserRegistration userRegistration = new UserRegistration();

        // registering student
        String simulatedInputs1 = "Kenan Colak\nkencolak\nkc566@duke.edu\n1\npassword\n";
        BufferedReader reader1 = new BufferedReader(new StringReader(simulatedInputs1));
        UserRegistrationView userRegistrationView1 = new UserRegistrationView(System.out, userRegistration, reader1);
        University university = new University("University 1", true);
        userRegistrationView1.universityDAO.create(university);
        int id = userRegistrationView1.addStudentView();
        String studentID = String.valueOf(id);

        // successfully deleting registered student
        String simulatedInputs2 = "1\n2\n" + studentID + "\n" + "3\n";
        BufferedReader reader2 = new BufferedReader(new StringReader(simulatedInputs2));
        UserRegistrationView userRegistrationView2 = new UserRegistrationView(System.out, userRegistration, reader2);
        userRegistrationView2.menuOptions();

        // registering faculty member
        String simulatedInputs3 = "Charlie Prior\ncharliep@duke.edu\n1\npassword";
        BufferedReader reader3 = new BufferedReader(new StringReader(simulatedInputs3));
        UserRegistrationView userRegistrationView3 = new UserRegistrationView(System.out, userRegistration, reader3);
        int Pid = userRegistrationView3.addProfessorView();
        String professorID = String.valueOf(Pid);

        // successfully deleting faculty member
        String simulatedInputs4 = "2\n2\n" + professorID + "\n" + "3\n";
        BufferedReader reader4 = new BufferedReader(new StringReader(simulatedInputs4));
        UserRegistrationView userRegistrationView4 = new UserRegistrationView(System.out, userRegistration, reader4);
        userRegistrationView4.menuOptions();

        // unsuccessful in deleting faculty member
        String simulatedInputs5 = "4\n2\n4\n2\n1\n3\n";
        BufferedReader reader5 = new BufferedReader(new StringReader(simulatedInputs5));
        UserRegistrationView userRegistrationView5 = new UserRegistrationView(System.out, userRegistration, reader5);
        userRegistrationView5.menuOptions();

        // unsuccessful in deleting student
        String simulatedInputs6 = "4\n1\n4\n2\n1\n3\n";
        BufferedReader reader6 = new BufferedReader(new StringReader(simulatedInputs6));
        UserRegistrationView userRegistrationView6 = new UserRegistrationView(System.out, userRegistration, reader6);
        userRegistrationView6.menuOptions();

        // registering student via menuOptions()
        String simulatedInputs7 = "1\n1\nKenan Colak\nkencolak\nkc566@duke.edu\n1\npassword\n3\n";
        BufferedReader reader7 = new BufferedReader(new StringReader(simulatedInputs7));
        UserRegistrationView userRegistrationView7 = new UserRegistrationView(System.out, userRegistration, reader7);
        userRegistrationView7.menuOptions();

        // registering faculty member via menuOptions()
        String simulatedInputs8 = "2\n1\nCharlie Prior\ncharliep@duke.edu\n1\npassword\n3\n";
        BufferedReader reader8 = new BufferedReader(new StringReader(simulatedInputs8));
        UserRegistrationView userRegistrationView8 = new UserRegistrationView(System.out, userRegistration, reader8);
        userRegistrationView8.menuOptions();

        userRegistrationView1.listUniversities();
        userRegistrationView1.listUniversitiesController();
        userRegistrationView1.universityDAO.remove(university);
    }

  //@Disabled
    @Test
    public void testAddUpdateStudentAndProfessorViews() throws IOException {
        UserRegistration userRegistration = new UserRegistration();
        

        // registering student
        String simulatedInputs1 = "Kenan Colak\nkencolak\nkc566@duke.edu\n1\npassword\n";
        BufferedReader reader1 = new BufferedReader(new StringReader(simulatedInputs1));
        UserRegistrationView userRegistrationView1 = new UserRegistrationView(System.out, userRegistration, reader1);
        int id = userRegistrationView1.addStudentView();
        String studentID = String.valueOf(id);

        University university = new University("University 1", true);
        userRegistrationView1.universityDAO.create(university);

        // successfully updating student password
        String simulatedInputs2 = "4\n1\n4\n3\n" + studentID + "\n" + "newpassword\n" + "newName\n" + "3\n";
        BufferedReader reader2 = new BufferedReader(new StringReader(simulatedInputs2));
        UserRegistrationView userRegistrationView2 = new UserRegistrationView(System.out, userRegistration, reader2);
        userRegistrationView2.menuOptions();

        // registering faculty member
        String simulatedInputs3 = "Charlie Prior\ncharliep@duke.edu\n1\npassword";
        BufferedReader reader3 = new BufferedReader(new StringReader(simulatedInputs3));
        UserRegistrationView userRegistrationView3 = new UserRegistrationView(System.out, userRegistration, reader3);
        int Pid = userRegistrationView3.addProfessorView();
        String professorID = String.valueOf(Pid);

        // successfully updating faculty password
        String simulatedInputs4 = "2\n3\n" + professorID + "\n" + "newpassword2\n" + "3\n";
        BufferedReader reader4 = new BufferedReader(new StringReader(simulatedInputs4));
        UserRegistrationView userRegistrationView4 = new UserRegistrationView(System.out, userRegistration, reader4);
        userRegistrationView4.menuOptions();

        // unsuccessful in updating faculty password
        String simulatedInputs5 = "4\n2\n4\n3\n1\n3\n";
        BufferedReader reader5 = new BufferedReader(new StringReader(simulatedInputs5));
        UserRegistrationView userRegistrationView5 = new UserRegistrationView(System.out, userRegistration, reader5);
        userRegistrationView5.menuOptions();

        // unsuccessful in updating student password
        String simulatedInputs6 = "4\n1\n4\n3\n1\n3\n";
        BufferedReader reader6 = new BufferedReader(new StringReader(simulatedInputs6));
        UserRegistrationView userRegistrationView6 = new UserRegistrationView(System.out, userRegistration, reader6);
        userRegistrationView6.menuOptions();

        userRegistrationView1.universityDAO.remove(university);
    }

  @Test
    public void testControllerMethods() throws IOException {
        UserRegistration userRegistration = new UserRegistration();

        // registering student
        String simulatedInputs = "Kenan Colak\nkencolak\nkc566@duke.edu\n1\npassword\n";
        BufferedReader reader1 = new BufferedReader(new StringReader(simulatedInputs));
        String []simulatedInputs1 = new String[5];
        simulatedInputs1[0]= "Kenan Colak";
        simulatedInputs1[1] = "kencolak";
        simulatedInputs1[2] = "kc566@duke.edu";
        simulatedInputs1[3] = "password";
        UserRegistrationView userRegistrationView1 = new UserRegistrationView(System.out, userRegistration, reader1);
        University university = new University("University 1", true);
        userRegistrationView1.universityDAO.create(university);
        simulatedInputs1[4] = String.valueOf(university.getId());
        userRegistrationView1.addStudentController(simulatedInputs1);

        Student student = new Student("Kenan Colak", "kc566@duke.edu",1,
                                              "kencolak");
        userRegistration.addStudent(student,"password");
        String []simulatedInputs2 = new String[3];
        String []simulatedInputs3 = new String[3];
        String []simulatedInputs4 = new String[3];
        simulatedInputs2[0]= String.valueOf(student.getStudentID());
        simulatedInputs2[1]= "newpassword";
        simulatedInputs2[2] = "newkenan";
        simulatedInputs3[0]= String.valueOf(student.getStudentID());
        simulatedInputs3[1]= "";
        simulatedInputs3[2] = "";
        simulatedInputs4[0]= String.valueOf(2);
        simulatedInputs4[1]= "";
        simulatedInputs4[2] = "";
        userRegistrationView1.updateStudentController(simulatedInputs2);
        userRegistrationView1.updateStudentController(simulatedInputs3);
        userRegistrationView1.updateStudentController(simulatedInputs4);
        userRegistration.removeStudent(student.getStudentID());
        
        userRegistrationView1.listUniversitiesController();
        userRegistrationView1.universityDAO.remove(university);
    }
}
