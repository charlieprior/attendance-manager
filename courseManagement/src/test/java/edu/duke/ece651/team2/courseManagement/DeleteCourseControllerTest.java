package edu.duke.ece651.team2.courseManagement;

import edu.duke.ece651.team2.shared.Course;
import edu.duke.ece651.team2.shared.Professor;
import edu.duke.ece651.team2.shared.University;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.base.WindowMatchers;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class DeleteCourseControllerTest extends ApplicationTest {
    University university = new University("Duke", true);
    CourseManagementMock model;
    Professor professor;
    Course course;

    @Override
    public void start(Stage stage) {
        model = new CourseManagementMock(university);
        professor = new Professor("Professor", "test@example.com", university.getId());
        professor.setProfessorID(0);
        model.addProfessor(professor);
        course = new Course("CourseName", university.getId());
        course.setCourseID(0);
        model.addCourse(course);

        Scene scene = new Scene(new ModifyCourseController(model), 600, 500);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void testDeleteCourse() {
        clickOn("#CourseSelector");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#ConfirmButton");

        Window confirmation = window("Confirmation");
        String confirmationButtonId = confirmation.getScene().getRoot().getChildrenUnmodifiable()
                .stream().filter(node -> node instanceof Button)
                .filter(button -> ((Button) button).getText().equals("OK")).findFirst()
                .orElseThrow(RuntimeException::new).getId();
        clickOn(confirmationButtonId);

        List<Course> expected = new ArrayList<>();
        assertEquals(expected, model.listCourses());
    }

    @Test
    public void testNullCourse() {
        clickOn("#ConfirmButton");
        FxAssert.verifyThat(window("Error"), WindowMatchers.isShowing());
    }
}