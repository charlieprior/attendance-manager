package edu.duke.ece651.team2.courseManagement;

import edu.duke.ece651.team2.shared.Course;
import edu.duke.ece651.team2.shared.Section;
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

class ModifySectionControllerTest extends ApplicationTest {
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

        Scene scene = new Scene(new ModifySectionController(model), 600, 500);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void testModifySection() {
        clickOn("#CourseSelector");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#SectionSelector");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#SectionNameField");
        write("NewSectionName");
        clickOn("#ConfirmButton");

        Set<String> expected = new HashSet<>();
        expected.add("NewSectionName");
        assertEquals(expected, new HashSet<>(model.getSections(course).stream().map(Section::getName).collect(Collectors.toSet())));
    }

    @Test
    public void testNullSection() {
        clickOn("#SectionNameField");
        write("NewSectionName");
        clickOn("#ConfirmButton");
        FxAssert.verifyThat(window("Error"), WindowMatchers.isShowing());
    }

    @Test
    public void testNullName() {
        clickOn("#CourseSelector");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#SectionSelector");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#ConfirmButton");
        FxAssert.verifyThat(window("Error"), WindowMatchers.isShowing());
    }

}