package edu.duke.ece651.team2.courseManagement;

import edu.duke.ece651.team2.shared.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.time.LocalDate;
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
     *
     * @param model  the model for the course management app
     * @param out    the output stream
     * @param reader the input stream
     */
    public CourseManagementController(CourseManagement model, PrintStream out, BufferedReader reader) {
        this.model = model;
        this.out = out;
        this.reader = reader;
    }

    /**
     * Returns whether the controller should exit
     *
     * @return true if the controller should exit, false otherwise
     */
    public boolean isShouldExit() {
        return shouldExit;
    }

    /**
     * Lists all courses in the database
     *
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

    public int listProfessors() {
        List<Professor> professors = model.listProfessors();
        for (Professor p : professors) {
            String str = p.getProfessorID() +
                    ". " +
                    p.getName();
            out.println(str);
        }

        return professors.size();
    }


    /**
     * Prompts the user to select a course
     *
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

    private Section getSection(String prompt, Course course) throws IOException {
        out.println(prompt);
        if (listSections(course) == 0) {
            out.println("No sections in course!");
            return null;
        }
        try {
            Integer sectionId = Integer.parseInt(reader.readLine());
            return model.getSection(sectionId);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Professor getProfessor(String prompt) throws IOException {
        out.println(prompt);
        if (listProfessors() == 0) {
            out.println("No professors in database!");
            return null;
        }
        try {
            Integer professorId = Integer.parseInt(reader.readLine());
            return model.getProfessor(professorId);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Lists all sections for a given course
     *
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
     *
     * @return the course ID of the new course
     * @throws IOException if there is an error reading input
     */
    public Integer addCourse() throws IOException {
        out.println("Please enter the name of the new course (blank to exit):");
        String courseName = reader.readLine();
        if (!courseName.isEmpty()) {
            Course course = new Course(courseName, model.getUniversity().getId());
            model.addCourse(course);
            out.println("Course added successfully");
            return course.getCourseID();
        }

        return null;
    }

    public Integer addSection() throws IOException {
        Course course = getCourse("Please select the course you would like to add a section to (blank to exit):");
        if (course == null) {
            return null;
        }

        out.println("Please enter the name of the new section (blank to exit):");
        String sectionName = reader.readLine();

        Professor professor = getProfessor("Please select the professor for the section (blank to exit):");
        if (professor == null) {
            return null;
        }

        if (!sectionName.isEmpty()) {
            Section section = new Section(course.getCourseID(), professor.getProfessorID(), sectionName);
            model.addSection(section);
            out.println("Section added successfully");
            return section.getSectionID();
        }

        return null;
    }

    /**
     * Prompts the user to remove a course
     *
     * @throws IOException if there is an error reading input
     */
    public void removeCourse() throws IOException {
        Course course = getCourse("Please select the course you would like to delete (blank to exit):");
        if (course != null) {
            out.println("Are you sure you want to delete course " + course.getName() + "? Y for yes");
            String confirm = reader.readLine();
            if (!confirm.equals("Y")) {
                out.println("Deletion cancelled");
                return;
            }

            model.removeCourse(course);
            out.println("Course deleted successfully");
        }
    }

    public void removeSection() throws IOException {
        Course course = getCourse("Please select the course whose section you would like to delete (blank to exit):");
        if (course == null) {
            return;
        }

        Section section = getSection("Please select the section you would like to delete (blank to exit):", course);
        if (section != null) {
            out.println("Are you sure you want to delete section " + section.getName() + "? Y for yes");
            String confirm = reader.readLine();
            if (!confirm.equals("Y")) {
                out.println("Deletion cancelled");
                return;
            }

            model.removeSection(section);
            out.println("Section deleted successfully");
        }
    }

    /**
     * Prompts the user to update a course
     *
     * @throws IOException if there is an error reading input
     */
    public void updateCourse() throws IOException {
        Course course = getCourse("Please select the course you would like to update (blank to exit):");
        if (course != null) {
            out.println("Please enter the new name for the course");
            String newName = reader.readLine();
            course.setCourseName(newName);
            model.updateCourse(course);
            out.println("Course updated successfully");
        }
    }

    public void updateSection() throws IOException {
        Course course = getCourse("Please select the course whose section you would like to update (blank to exit):");
        if (course == null) {
            return;
        }

        Section section = getSection("Please select the section you would like to update (blank to exit):", course);
        if (section != null) {
            out.println("Please enter the new name for the section");
            String newName = reader.readLine();
            section.setName(newName);
            model.updateSection(section);
            out.println("Section updated successfully");
        }
    }

    /**
     * Prompts the user to choose an option and performs the corresponding action
     *
     * @throws IOException if there is an error reading input
     */
    public void chooseOption() throws IOException {
        out.println("Please select an option: \n" +
                "1. Update an existing course\n" +
                "2. Delete an existing course\n" +
                "3. Add a new course\n" +
                "4. Update an existing section\n" +
                "5. Delete an existing section\n" +
                "6. Add a new section\n" +
                "7. Add students to a section\n" +
                "8. Add a lecture to a section\n" +
                "9. Exit");
        try {
            int option = Integer.parseInt(reader.readLine());
            if (option < 1 || option > 9) {
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
                    updateSection();
                    break;
                case 5:
                    removeSection();
                    break;
                case 6:
                    addSection();
                    break;
                case 7:
                    addStudents();
                    break;
                case 8:
                    addLecture();
                    break;
                case 9:
                    shouldExit = true;
                    break;
            }

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private void addLecture() throws IOException {
        Course course = getCourse("Please select the course you would like to add a lecture to (blank to exit):");
        if (course == null) {
            return;
        }

        Section section = getSection("Please select the section you would like to add a lecture to (blank to exit):", course);
        if (section == null) {
            return;
        }

        out.println("Please enter the date of the lecture (YYYY-MM-DD):");
        String dateString = reader.readLine();
        String[] dateParts = dateString.split("-");
        if (dateParts.length != 3) {
            out.println("Invalid date format");
            return;
        }
        try {
            LocalDate date = LocalDate.of(Integer.parseInt(dateParts[0]), Integer.parseInt(dateParts[1]), Integer.parseInt(dateParts[2]));
            Lecture lecture = new Lecture(section.getSectionID(), date);
            model.addLecture(lecture);
            out.println("Lecture added successfully");
        } catch (NumberFormatException e) {
            out.println("Invalid date format");
        }
    }

    /**
     * Prints a message indicating that the user has made an invalid selection
     */
    public void invalidSelection() {
        out.println("Please enter a valid selection!");
    }

    public void addStudents() throws IOException {
        Course course = getCourse("Please select the course you would like to add students to (blank to exit):");
        if (course == null) {
            return;
        }

        Section section = getSection("Please select the section you would like to add students to (blank to exit):", course);
        if (section == null) {
            return;
        }

        out.println("Would you like to load students from a CSV file? Y for yes");
        if (reader.readLine().equals("Y")) {
            List<Student> students = readStudentsFromCSV();
            for (Student student : students) {
                model.addStudentToSection(student, section);
            }
            out.println("Students added successfully");
        } else {
            model.addStudentToSection(readStudent(), section);
            out.println("Student added successfully");
        }
    }

    public List<Student> readStudentsFromCSV() throws IOException {
        CSVLoader csvLoader = new CSVLoader();
        out.println("Please enter the name of the file to load:");
        String filename = reader.readLine();
        out.println("Does the file have a header? Y for yes");
        boolean hasHeader = reader.readLine().equals("Y");
        out.println("What is the delimiter?");
        String delimiter = reader.readLine();
        while (true) {
            try {
                out.println("What column is the legal name in? (starting from 0)");
                int legalNameIndex = Integer.parseInt(reader.readLine());
                out.println("What column is the email in? (starting from 0)");
                int emailIndex = Integer.parseInt(reader.readLine());
                out.println("What column is the display name in? (starting from 0)");
                int displayNameIndex = Integer.parseInt(reader.readLine());

                try {
                    List<String> lines = csvLoader.getLines(filename, hasHeader);
                    return csvLoader.getStudents(lines, ",", legalNameIndex, emailIndex, displayNameIndex, model.getUniversity().getId());
                } catch (IOException e) {
                    out.println("Error reading file");
                }

            } catch (NumberFormatException e) {
                out.println("Invalid column number");
            }
        }
    }

    Student readStudent() throws IOException {
        out.println("Please enter the legal name of the student:");
        String legalName = reader.readLine();
        out.println("Please enter the email of the student:");
        String email = reader.readLine();
        out.println("Please enter the display name of the student:");
        String displayName = reader.readLine();
        return new Student(legalName, email, model.getUniversity().getId(), displayName);
    }
}
