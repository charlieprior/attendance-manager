package edu.duke.ece651.team2.courseManagement;

import edu.duke.ece651.team2.shared.Course;
import edu.duke.ece651.team2.shared.University;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.stage.Stage;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class AddCourseControllerTest extends ApplicationTest {
    University university = new University("Duke", true);
    CourseManagementInterface model;
    @Override
    public void start(Stage stage) {
        model = new CourseManagementMock(university);

        Scene scene = new Scene(new AddCourseController(model), 600, 500);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void testAddCourse() {
        Platform.runLater(() -> {
            clickOn("#CourseNameField");
            write("CourseName");
            clickOn("#ConfirmButton");
        });
        WaitForAsyncUtils.waitForFxEvents();

        Set<String> expected = new HashSet<>();
        expected.add("CourseName");
        assertEquals(expected, new HashSet<>(model.listCourses().stream().map(Course::getName).collect(Collectors.toSet())));
    }

    @Test
    public void testEmptyName() {
        Platform.runLater(() -> {
            clickOn("#ConfirmButton");

            interact(() -> {
                ButtonBar buttonBar = lookup(".button-bar").query();
                Button okButton = (Button) buttonBar.getButtons().stream()
                        .filter(button -> button instanceof Button && "OK".equals(((Button) button).getText()))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("OK button not found"));
                clickOn(okButton);
            });
        });
    }
}