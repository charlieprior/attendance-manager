package edu.duke.ece651.team2.courseManagement.client;

import edu.duke.ece651.team2.shared.University;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class CourseManagementControllerTest {

    @Test
    void testCreateList() throws IOException {
        String inputData = "ECE651";
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        BufferedReader input = new BufferedReader(new StringReader(inputData));
        PrintStream output = new PrintStream(bytes, true);

        University university = new University("Duke", true);
        CourseManagement model = new CourseManagement(university);
        CourseManagementController controller = new CourseManagementController(model, output, input);

        model.deleteAllCourses();
        controller.addCourse();
        controller.listCourses();
        assertEquals("", bytes.toString());
    }
}