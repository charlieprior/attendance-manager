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

import static org.junit.jupiter.api.Assertions.*;

class DeleteSectionControllerTest extends ApplicationTest {
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

        Scene scene = new Scene(new DeleteSectionController(model), 600, 500);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void testDeleteSection() {
        clickOn("#CourseSelector");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#SectionSelector");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#ConfirmButton");

        FxAssert.verifyThat(window("Confirmation"), WindowMatchers.isShowing());
        // Literally impossible to progress further with TestFX because buttons in dialogs don't get ids
    }

    @Test
    public void testNullSection() {
        clickOn("#ConfirmButton");
        FxAssert.verifyThat(window("Error"), WindowMatchers.isShowing());
    }
}