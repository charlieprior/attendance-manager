package edu.duke.ece651.team2.courseManagement.client;

import edu.duke.ece651.team2.server.*;
import edu.duke.ece651.team2.shared.Course;
import edu.duke.ece651.team2.shared.University;

import java.util.List;

public class CourseManagement {
    private static final DAOFactory daoFactory = new DAOFactory();
    private static final CourseDAO courseDAO = new CourseDAO(daoFactory);
    private static final EnrollmentDAO enrollmentDAO = new EnrollmentDAO(daoFactory);
    private static final SectionDAO sectionDAO = new SectionDAO(daoFactory);
    private static final StudentDAO studentDAO = new StudentDAO(daoFactory);

    public University getUniversity() {
        return university;
    }

    private final University university;

    public CourseManagement(University university) {
        this.university = university;
    }

    public List<Course> listCourses() {
        return courseDAO.listByUniversity(university.getId());
    }

    public void addCourse(Course course) {
        courseDAO.create(course);
    }

    public void deleteAllCourses() {
        courseDAO.deleteAll();
    }

    public void removeCourse(Integer id) {
        Course course = courseDAO.get(id);
        courseDAO.remove(course);
    }


}
