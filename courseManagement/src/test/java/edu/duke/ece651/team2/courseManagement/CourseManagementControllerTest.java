package edu.duke.ece651.team2.courseManagement;

import edu.duke.ece651.team2.courseManagement.CourseManagement;
import edu.duke.ece651.team2.courseManagement.CourseManagementController;
import edu.duke.ece651.team2.server.DAOFactory;
import edu.duke.ece651.team2.server.UniversityDAO;
import edu.duke.ece651.team2.shared.University;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class CourseManagementControllerTest {

    @Test
    void testCreateList() throws IOException {
        String inputData = "\nECE651";
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        BufferedReader input = new BufferedReader(new StringReader(inputData));
        PrintStream output = new PrintStream(bytes, true);

        University university = new University("Duke", true);
        UniversityDAO universityDAO = new UniversityDAO(new DAOFactory());
        universityDAO.create(university);
        CourseManagement model = new CourseManagement(university);
        CourseManagementController controller = new CourseManagementController(model, output, input);

        model.deleteAllCourses();
        controller.addCourse();
        Integer id = controller.addCourse();
        controller.listCourses();
        assertEquals("Please enter the name of the new course (blank to exit):\n" +
                "Please enter the name of the new course (blank to exit):\n" +
                id + ". ECE651\n", bytes.toString());
    }
}