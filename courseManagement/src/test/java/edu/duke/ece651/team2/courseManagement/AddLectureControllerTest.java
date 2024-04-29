package edu.duke.ece651.team2.courseManagement;

import edu.duke.ece651.team2.shared.*;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class AddLectureControllerTest extends ApplicationTest {
    University university = new University("Duke", true);
    CourseManagementMock model;
    Course course;
    Section section;

    @Override
    public void start(Stage stage) {
        model = new CourseManagementMock(university);
        course = new Course("NewCourse", university.getId());
        course.setCourseID(0);
        model.addCourse(course);
        section = new Section(course.getCourseID(), 0, "NewSection");
        section.setSectionID(0);
        model.addSection(section);

        Scene scene = new Scene(new AddSectionController(model), 600, 500);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void testAddLecture() {
        clickOn("#CourseSelector");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#SectionSelector");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#datePicker");
        write("4/20/1969");
        clickOn("#AddButton");

        LocalDate date = LocalDate.of(1969, 4, 20);
        Set<Integer> expectedYear = new HashSet<>();
        expectedYear.add(1969);
        Set<Integer> expectedMonth = new HashSet<>();
        expectedMonth.add(4);
        Set<Integer> expectedDay = new HashSet<>();
        expectedDay.add(20);

        List<Lecture> lectures = model.getLectures();
        assertEquals(expectedYear, new HashSet<>(lectures.stream().map(Lecture::getYear).collect(Collectors.toSet())));
        assertEquals(expectedMonth, new HashSet<>(lectures.stream().map(Lecture::getMonth).collect(Collectors.toSet())));
        assertEquals(expectedDay, new HashSet<>(lectures.stream().map(Lecture::getDay).collect(Collectors.toSet())));
    }

}