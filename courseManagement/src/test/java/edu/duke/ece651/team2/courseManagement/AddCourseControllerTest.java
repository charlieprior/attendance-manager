package edu.duke.ece651.team2.courseManagement;

import edu.duke.ece651.team2.shared.Course;
import edu.duke.ece651.team2.shared.University;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.*;

class AddCourseControllerTest extends ApplicationTest {
    University university = new University("Duke", true);
    @Override
    public void start(Stage stage) {
        CourseManagement model = new CourseManagement(university);

        Scene scene = new Scene(new AddCourseController(model), 600, 500);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void testAddCourse() {
        CourseManagement model = new CourseManagement(university);
        Platform.runLater(() -> {
            rightClickOn("#CourseNameField");
            write("CourseName");
            rightClickOn("#ConfirmButton");
        });
        WaitForAsyncUtils.waitForFxEvents();
        for(Course c : model.listCourses()) {
            System.out.println(c.getName());
        }
    }
}