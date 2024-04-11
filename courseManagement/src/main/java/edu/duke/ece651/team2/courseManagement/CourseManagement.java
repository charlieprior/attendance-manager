package edu.duke.ece651.team2.courseManagement;

import edu.duke.ece651.team2.server.*;
import edu.duke.ece651.team2.shared.*;

import java.util.List;

public class CourseManagement {
    private static final DAOFactory daoFactory = new DAOFactory();
    private static final CourseDAO courseDAO = new CourseDAO(daoFactory);
    private static final EnrollmentDAO enrollmentDAO = new EnrollmentDAO(daoFactory);
    private static final SectionDAO sectionDAO = new SectionDAO(daoFactory);
    private static final StudentDAO studentDAO = new StudentDAO(daoFactory);
    private static final ProfessorDAO professorDAO = new ProfessorDAO(daoFactory);
    private static final LectureDAO lectureDAO = new LectureDAO(daoFactory);
    private final University university;

    public CourseManagement(University university) {
        this.university = university;
    }

    public University getUniversity() {
        return university;
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

    public void removeCourse(Course course) {
        courseDAO.remove(course);
    }

    public Course getCourse(Integer id) {
        return courseDAO.get(id);
    }

    public void updateCourse(Course course) {
        courseDAO.update(course);
    }

    public List<Section> getSections(Course course) {
        return sectionDAO.listSectionsFromCourse(course.getCourseID());
    }


    public void addStudentToSection(Student student, Section section) {
        studentDAO.create(student);
        Enrollment enrollment = new Enrollment(student.getStudentID(), section.getSectionID(), true);
        enrollmentDAO.create(enrollment);
    }

    public Section getSection(Integer sectionId) {
        return sectionDAO.get(sectionId);
    }

    public void updateSection(Section section) {
        sectionDAO.update(section);
    }

    public void removeSection(Section section) {
        sectionDAO.remove(section);
    }

    public List<Professor> listProfessors() {
        return professorDAO.listByUniversity(university.getId());
    }

    public Professor getProfessor(Integer professorId) {
        return professorDAO.get(professorId);
    }

    public void addSection(Section section) {
        sectionDAO.create(section);
    }

    public void addLecture(Lecture lecture) {
        lectureDAO.create(lecture);
    }
}
