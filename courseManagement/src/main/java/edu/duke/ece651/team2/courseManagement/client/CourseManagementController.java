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

    public void removeCourse() throws IOException {
        out.println("Please select the course you would like to delete:");
        listCourses();
        Integer courseId = Integer.parseInt(reader.readLine());
        Course course = model.getCourse(courseId);
        out.println("Are you sure you want to delete course " + course.getName() + "? Y for yes");
        String confirm = reader.readLine();
        if(!confirm.equals("Y")) {
            return;
        }

        model.removeCourse(course);
    }

    public void updateCourse() throws IOException {
        out.println("Please select the course you would like to update:");
        listCourses();
        Integer courseId = Integer.parseInt(reader.readLine());
        Course course = model.getCourse(courseId);
        out.println("Please enter the new name for the course");
        String newName = reader.readLine();
        course.setCourseName(newName);
        model.updateCourse(course);
    }

}
