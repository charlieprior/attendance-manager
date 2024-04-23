package edu.duke.ece651.team2.courseManagement;

import edu.duke.ece651.team2.server.*;
import edu.duke.ece651.team2.shared.Course;
import edu.duke.ece651.team2.shared.Professor;
import edu.duke.ece651.team2.shared.Section;
import edu.duke.ece651.team2.shared.University;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class CourseManagementControllerTest {

    @Test
    void testGetSection() throws IOException {
        String inputData = "A\n0";
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        BufferedReader input = new BufferedReader(new StringReader(inputData));
        PrintStream output = new PrintStream(bytes, true);

        University university = new University("Duke", true);
        UniversityDAO universityDAO = new UniversityDAO(new DAOFactory());
        universityDAO.create(university);
        CourseManagement model = new CourseManagement(university);
        CourseManagementTextController controller = new CourseManagementTextController(model, output, input);

        model.deleteAllCourses();
        model.deleteAllSections();

        Course course = new Course("ECE651", university.getId());
        model.addCourse(course);

        String prompt = "Please select a section (blank to exit):";
        controller.getSection(prompt, course);

        Section section = new Section(course.getCourseID(), 1, "Section 1");
        model.addSection(section);
        Integer sectionId = section.getSectionID();
        controller.getSection(prompt, course);
        controller.getSection(prompt, course);

        assertEquals("Please select a section (blank to exit):\n" +
                "No sections in course!\n" +
                "Please select a section (blank to exit):\n" +
                sectionId + ". Section 1\n" +
                "Please select a section (blank to exit):\n" +
                sectionId + ". Section 1\n", bytes.toString());
    }

    @Test
    void testGetCourse() throws IOException {
        String inputData = "A\n0";
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        BufferedReader input = new BufferedReader(new StringReader(inputData));
        PrintStream output = new PrintStream(bytes, true);

        University university = new University("Duke", true);
        UniversityDAO universityDAO = new UniversityDAO(new DAOFactory());
        universityDAO.create(university);
        CourseManagement model = new CourseManagement(university);
        CourseManagementTextController controller = new CourseManagementTextController(model, output, input);

        model.deleteAllCourses();
        String prompt = "Please select a course (blank to exit):";
        controller.getCourse(prompt);

        Course course = new Course("ECE651", university.getId());
        model.addCourse(course);
        Integer courseId = course.getCourseID();

        controller.getCourse(prompt);
        controller.getCourse(prompt);

        assertEquals("Please select a course (blank to exit):\n" +
                "No courses in database!\n" +
                "Please select a course (blank to exit):\n" +
                courseId + ". ECE651\n" +
                "Please select a course (blank to exit):\n" +
                courseId + ". ECE651\n", bytes.toString());
    }

    @Test
    void testAddListCourse() throws IOException {
        String inputData = "\nECE651";
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        BufferedReader input = new BufferedReader(new StringReader(inputData));
        PrintStream output = new PrintStream(bytes, true);

        University university = new University("Duke", true);
        UniversityDAO universityDAO = new UniversityDAO(new DAOFactory());
        universityDAO.create(university);
        CourseManagement model = new CourseManagement(university);
        CourseManagementTextController controller = new CourseManagementTextController(model, output, input);

        model.deleteAllCourses();
        model.deleteAllSections();

        controller.addCourse(); // blank input will exit
        Integer courseId = controller.addCourse(); // ECE651
        controller.listCourses();

        assertEquals("Please enter the name of the new course (blank to exit):\n" +      
        "Please enter the name of the new course (blank to exit):\n" +
        "Course added successfully\n"+
                courseId + ". ECE651\n", bytes.toString());
    }

    @Test
    void testRemoveCourse() throws IOException {
        University university = new University("Duke", true);
        UniversityDAO universityDAO = new UniversityDAO(new DAOFactory());
        universityDAO.create(university);

        Course course = new Course("ECE651", university.getId());
        CourseDAO courseDAO = new CourseDAO(new DAOFactory());
        courseDAO.create(course);
        Integer courseId = course.getCourseID();

        String inputData = courseId + "\nN\n" + courseId + "\nY\n";
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        BufferedReader input = new BufferedReader(new StringReader(inputData));
        PrintStream output = new PrintStream(bytes, true);

        CourseManagement model = new CourseManagement(university);
        CourseManagementTextController controller = new CourseManagementTextController(model, output, input);

        controller.removeCourse(); // no
        controller.removeCourse(); // yes

        assertEquals("Please select the course you would like to delete (blank to exit):\n" +
                courseId + ". ECE651\n" +
                "Are you sure you want to delete course ECE651? Y for yes\n" +
                "Deletion cancelled\n" +
                "Please select the course you would like to delete (blank to exit):\n" +
                courseId + ". ECE651\n" +
                "Are you sure you want to delete course ECE651? Y for yes\n" +
                "Course deleted successfully\n", bytes.toString());

    }

    @Test
    void testAddListSection() throws IOException {
        CourseDAO courseDAO = new CourseDAO(new DAOFactory());
        SectionDAO sectionDAO = new SectionDAO(new DAOFactory());
        UniversityDAO universityDAO = new UniversityDAO(new DAOFactory());
        ProfessorDAO professorDAO = new ProfessorDAO(new DAOFactory());

        University university = new University("Duke", true);
        universityDAO.create(university);

        Professor professor = new Professor("Charlie Prior", "charlie.prior@duke.edu", university.getId());
        professorDAO.create(professor);
        Integer professorId = professor.getProfessorID();

        Course course = new Course("ECE651", university.getId());
        courseDAO.create(course);
        Integer courseId = course.getCourseID();

        String inputData = "0\n" +  courseId + "\n\n" + courseId + "\n" + "Section 1\n" + "0\n" + courseId + "\n" + "Section 1\n" + professorId + "\n";
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        BufferedReader input = new BufferedReader(new StringReader(inputData));
        PrintStream output = new PrintStream(bytes, true);

        CourseManagement model = new CourseManagement(university);
        CourseManagementTextController controller = new CourseManagementTextController(model, output, input);

        controller.addSection(); // invalid course
        controller.addSection(); // empty section name
        controller.addSection(); // invalid professor

        Integer sectionId = controller.addSection();
        controller.listSections(course);
        assertEquals("Please select the course you would like to add a section to (blank to exit):\n" +
                courseId + ". ECE651\n" +
                "Please select the course you would like to add a section to (blank to exit):\n" +
                courseId + ". ECE651\n" +
                "Please enter the name of the new section (blank to exit):\n" +
                "Please select the course you would like to add a section to (blank to exit):\n" +
                courseId + ". ECE651\n" +
                "Please enter the name of the new section (blank to exit):\n" +
                "Please select the professor for the section (blank to exit):\n" +
                professorId + ". Charlie Prior\n" +
                "Please select the course you would like to add a section to (blank to exit):\n" +
                courseId + ". ECE651\n" +
                "Please enter the name of the new section (blank to exit):\n" +
                "Please select the professor for the section (blank to exit):\n" +
                professorId + ". Charlie Prior\n" +
                "Section added successfully\n" +
                sectionId + ". Section 1\n", bytes.toString());
    }
}