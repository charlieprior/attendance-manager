package edu.duke.ece651.team2.courseManagement;

import edu.duke.ece651.team2.shared.Course;
import edu.duke.ece651.team2.shared.Section;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

/**
 * The controller for the course management app
 */
public class CourseManagementController {
    /**
     * The model for the course management app
     */
    private final CourseManagement model;
    /**
     * The output stream
     */
    private final PrintStream out;
    /**
     * The input stream
     */
    private final BufferedReader reader;
    /**
     * Whether the controller should exit
     */
    private boolean shouldExit = false;

    /**
     * Creates a new course management controller
     * @param model the model for the course management app
     * @param out the output stream
     * @param reader the input stream
     */
    public CourseManagementController(CourseManagement model, PrintStream out, BufferedReader reader) {
        this.model = model;
        this.out = out;
        this.reader = reader;
    }

    /**
     * Returns whether the controller should exit
     * @return true if the controller should exit, false otherwise
     */
    public boolean isShouldExit() {
        return shouldExit;
    }

    /**
     * Lists all courses in the database
     * @return the number of courses in the database
     */
    public int listCourses() {
        List<Course> courses = model.listCourses();
        for (Course c : courses) {
            String str = c.getCourseID() +
                    ". " +
                    c.getName();
            out.println(str);
        }

        return courses.size();
    }


    /**
     * Prompts the user to select a course
     * @param prompt the prompt to display to the user
     * @return the course selected by the user, or null if no course is selected
     * @throws IOException if there is an error reading input
     */
    private Course getCourse(String prompt) throws IOException {
        out.println(prompt);
        if (listCourses() == 0) {
            out.println("No courses in database!");
            return null;
        }
        try {
            Integer courseId = Integer.parseInt(reader.readLine());
            return model.getCourse(courseId);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Lists all sections for a given course
     * @param c the course to list sections for
     * @return the number of sections in the course
     */
    public int listSections(Course c) {
        List<Section> sections = model.getSections(c);
        for (Section s : sections) {
            out.println(s.getSectionID() +
                    ". " +
                    s.getName());
        }

        return sections.size();
    }

    /**
     * Prompts the user to add a new course
     * @return the course ID of the new course
     * @throws IOException if there is an error reading input
     */
    public Integer addCourse() throws IOException {
        out.println("Please enter the name of the new course (blank to exit):");
        String courseName = reader.readLine();
        if (!courseName.isEmpty()) {
            Course course = new Course(courseName, model.getUniversity().getId());
            model.addCourse(course);
            return course.getCourseID();
        }

        return null;
    }

    /**
     * Prompts the user to remove a course
     * @throws IOException if there is an error reading input
     */
    public void removeCourse() throws IOException {
        Course course = getCourse("Please select the course you would like to delete (blank to exit):");
        if (course != null) {
            out.println("Are you sure you want to delete course " + course.getName() + "? Y for yes");
            String confirm = reader.readLine();
            if (!confirm.equals("Y")) {
                return;
            }

            model.removeCourse(course);
        }
    }

    /**
     * Prompts the user to update a course
     * @throws IOException if there is an error reading input
     */
    public void updateCourse() throws IOException {
        Course course = getCourse("Please select the course you would like to update (blank to exit):");
        if (course != null) {
            out.println("Please enter the new name for the course");
            String newName = reader.readLine();
            course.setCourseName(newName);
            model.updateCourse(course);
        }
    }

    /**
     * Prompts the user to choose an option and performs the corresponding action
     * @throws IOException if there is an error reading input
     */
    public void chooseOption() throws IOException {
        out.println("Please select an option: \n" +
                "1. Update an existing course\n" +
                "2. Delete an existing course\n" +
                "3. Add a new course\n" +
                "4. Exit");
        try {
            int option = Integer.parseInt(reader.readLine());
            if (option < 1 || option > 4) {
                throw new IllegalArgumentException();
            }

            switch (option) {
                case 1:
                    updateCourse();
                    break;
                case 2:
                    removeCourse();
                    break;
                case 3:
                    addCourse();
                    break;
                case 4:
                    shouldExit = true;
                    break;
            }

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Prints a message indicating that the user has made an invalid selection
     */
    public void invalidSelection() {
        out.println("Please enter a valid selection!");
    }
}
