package edu.duke.ece651.team2.courseManagement;

import edu.duke.ece651.team2.shared.Course;
import edu.duke.ece651.team2.shared.Professor;
import edu.duke.ece651.team2.shared.University;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.base.WindowMatchers;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class ModifyCourseControllerTest extends ApplicationTest {
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
    public void testModifyCourse() {
        clickOn("#CourseSelector");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#CourseNameField");
        write("NewCourseName");
        clickOn("#ConfirmButton");

        Set<String> expected = new HashSet<>();
        expected.add("NewCourseName");
        assertEquals(expected, new HashSet<>(model.listCourses().stream().map(Course::getName).collect(Collectors.toSet())));
    }

    @Test
    public void testNullCourse() {
        clickOn("#CourseNameField");
        write("NewCourseName");
        clickOn("#ConfirmButton");
        FxAssert.verifyThat(window("Error"), WindowMatchers.isShowing());
    }

    @Test
    public void testNullName() {
        clickOn("#CourseSelector");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#ConfirmButton");
        FxAssert.verifyThat(window("Error"), WindowMatchers.isShowing());
    }
}