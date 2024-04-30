package edu.duke.ece651.team2.courseManagement;

import edu.duke.ece651.team2.shared.Course;
import edu.duke.ece651.team2.shared.Section;
import edu.duke.ece651.team2.shared.Student;
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

class EnrollStudentControllerTest extends ApplicationTest {
    University university = new University("Duke", true);
    CourseManagementMock model;
    Course course;
    Section section;

    @Override
    public void start(Stage stage) {
        model = new CourseManagementMock(university);
        course = new Course("CourseName", university.getId());
        course.setCourseID(0);
        model.addCourse(course);
        section = new Section(course.getCourseID(), 0, "SectionName");
        section.setSectionID(0);
        model.addSection(section);

        Scene scene = new Scene(new EnrollStudentController(model), 600, 500);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void testEnrollStudent() {
        clickOn("#CourseSelector");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#SectionSelector");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#StudentSelector");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#EnrollButton");

        Set<String> expected = new HashSet<>();
        expected.add("Name");
        assertEquals(expected, new HashSet<>(model.getStudentsBySection(section).stream().map(Student::getLegalName).collect(Collectors.toSet())));
    }

    @Test
    public void testNullSection() {
        clickOn("#StudentSelector");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#EnrollButton");
        FxAssert.verifyThat(window("Error"), WindowMatchers.isShowing());
    }

    @Test
    public void testNullStudent() {
        clickOn("#CourseSelector");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#SectionSelector");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#EnrollButton");
        FxAssert.verifyThat(window("Error"), WindowMatchers.isShowing());
    }

    @Test
    public void testDuplicate() {
        clickOn("#CourseSelector");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#SectionSelector");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#StudentSelector");
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#EnrollButton");
        FxAssert.verifyThat(window("Error"), WindowMatchers.isShowing());
    }
}