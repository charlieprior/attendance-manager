package edu.duke.ece651.team2.courseManagement;

import edu.duke.ece651.team2.shared.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CourseManagementMock implements CourseManagementInterface {
    private final University university;
    private final MainMenuController mainMenuController;

    List<Course> courses = new ArrayList<>();
    List<Professor> professors = new ArrayList<>();
    List<Section> sections = new ArrayList<>();

    public CourseManagementMock(University university) {
        this.university = university;
        this.mainMenuController = new MainMenuController(this);
    }

    public void addProfessor(Professor professor) { professors.add(professor); }

    @Override
    public MainMenuController getMainMenuController() {
        return mainMenuController;
    }

    @Override
    public University getUniversity() {
        return university;
    }

    @Override
    public List<Course> listCourses() {
        return courses;
    }

    @Override
    public void addCourse(Course course) {
        courses.add(course);
    }

    @Override
    public void deleteAllCourses() {

    }

    @Override
    public void removeCourse(Course course) {

    }

    @Override
    public Course getCourse(Integer id) {
        return null;
    }

    @Override
    public void updateCourse(Course course) {

    }

    @Override
    public List<Section> getSections(Course course) {
        return sections.stream()
                .filter(section -> section.getCourseId().equals(course.getCourseID()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean addStudentToSection(Section section, Student student) {
        return false;
    }

    @Override
    public void addStudentToSection(Integer studentId, Section section) {
    }

    @Override
    public Section getSection(Integer sectionId) {
        return null;
    }

    @Override
    public void updateSection(Section section) {

    }

    @Override
    public void removeSection(Section section) {

    }

    @Override
    public void removeStudent(Student student) {

    }

    @Override
    public void unenrollStudent(Student student, Section section) {

    }

    @Override
    public List<Student> getStudentsBySection(Section section) {
        return null;
    }

    @Override
    public List<Professor> listProfessors() {
        return null;
    }

    @Override
    public Professor getProfessor(Integer professorId) {
        return null;
    }

    @Override
    public void addSection(Section section) {
        sections.add(section);
    }

    @Override
    public void addLecture(Lecture lecture) {

    }

    @Override
    public void deleteAllSections() {

    }

    @Override
    public List<Student> getAllStudentsInUniversity() {
        return null;
    }
}
