package edu.duke.ece651.team2.courseManagement;

import edu.duke.ece651.team2.shared.Course;
import edu.duke.ece651.team2.shared.Professor;
import edu.duke.ece651.team2.shared.Section;
import edu.duke.ece651.team2.shared.University;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.base.WindowMatchers;
import org.testfx.util.WaitForAsyncUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class AddSectionControllerTest extends ApplicationTest {
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
        course = new Course("NewCourse", university.getId());
        course.setCourseID(0);
        model.addCourse(course);

        Scene scene = new Scene(new AddSectionController(model), 600, 500);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void testAddSection() {
            clickOn("#CourseSelector");
            type(KeyCode.DOWN);
            type(KeyCode.ENTER);
            clickOn("#InstructorSelector");
            type(KeyCode.DOWN);
            type(KeyCode.ENTER);
            clickOn("#SectionNameField");
            write("SectionName");
            clickOn("#ConfirmButton");

        Set<String> expected = new HashSet<>();
        expected.add("SectionName");
        assertEquals(expected, new HashSet<>(model.getSections(course).stream().map(Section::getName).collect(Collectors.toSet())));
    }

    @Test
    public void testCourseNull() {
        clickOn("#SectionNameField");
        write("SectionName");
        clickOn("#ConfirmButton");
        FxAssert.verifyThat(window("Error"), WindowMatchers.isShowing());
    }

    @Test
    public void testNameEmpty() {
        clickOn("#CourseSelector");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#ConfirmButton");
        FxAssert.verifyThat(window("Error"), WindowMatchers.isShowing());
    }

    @Test
    public void testNullProfessor() {
        clickOn("#CourseSelector");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#SectionNameField");
        write("SectionName");
        clickOn("#ConfirmButton");

        Set<String> expected = new HashSet<>();
        expected.add("SectionName");
        assertEquals(expected, new HashSet<>(model.getSections(course).stream().map(Section::getName).collect(Collectors.toSet())));
    }

}
