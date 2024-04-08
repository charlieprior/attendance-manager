package edu.duke.ece651.team2.courseManagement.client;

import edu.duke.ece651.team2.shared.Course;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

public class CourseManagementController {
    private final CourseManagement model;
    private final PrintStream out;
    private final BufferedReader reader;

    public CourseManagementController(CourseManagement model, PrintStream out, BufferedReader reader) {
        this.model = model;
        this.out = out;
        this.reader = reader;
    }

    public void listCourses() {
        for (Course c : model.listCourses()) {
            String str = c.getCourseID() +
                    ". " +
                    c.getName();
            out.println(str);
        }
    }

    public void addCourse() throws IOException {
        out.println("Please enter the name of the new course:");
        String courseName = reader.readLine();
        Course course = new Course(courseName, model.getUniversity().getId());
        model.addCourse(course);
    }

}
