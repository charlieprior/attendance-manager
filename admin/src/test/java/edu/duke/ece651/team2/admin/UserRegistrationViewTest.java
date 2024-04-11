package edu.duke.ece651.team2.admin;

import edu.duke.ece651.team2.admin.UserRegistration;
import edu.duke.ece651.team2.admin.UserRegistrationView;
import edu.duke.ece651.team2.server.UniversityDAO;
import edu.duke.ece651.team2.shared.University;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

public class UserRegistrationViewTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outContent)); // Redirect System.out to capture print statements
    }

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

        userRegistrationView1.universityDAO.remove(university);
    }

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
        String simulatedInputs2 = "4\n1\n4\n3\n" + studentID + "\n" + "newpassword\n" + "3\n";
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

}
