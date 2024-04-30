package edu.duke.ece651.team2.courseManagement;

import edu.duke.ece651.team2.shared.*;
import javafx.util.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class CourseManagementMock implements CourseManagementInterface {
    private final University university;
    private final MainMenuController mainMenuController;

    List<Course> courses = new ArrayList<>();
    List<Professor> professors = new ArrayList<>();
    List<Section> sections = new ArrayList<>();
    List<Lecture> lectures = new ArrayList<>();
    List<Pair<Section, Student>> enrollments = new ArrayList<>();

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
        courses.clear();
    }

    @Override
    public void removeCourse(Course course) {
        courses.remove(course);
    }

    @Override
    public Course getCourse(Integer id) {
        return null;
    }

    @Override
    public void updateCourse(Course course) {
        // nothing to do, this is purely to update in the DAO
    }

    @Override
    public List<Section> getSections(Course course) {
        return sections.stream()
                .filter(section -> section.getCourseId().equals(course.getCourseID()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean addStudentToSection(Section section, Student student) {
        enrollments.add(new Pair<>(section, student));
        return true;
    }

    @Override
    public void addStudentToSection(Integer studentId, Section section) {
        // unused
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
        List<Student> ret = new ArrayList<>();
        for(Pair<Section, Student> p : enrollments) {
            if(p.getKey().equals(section)) {
                ret.add(p.getValue());
            }
        }
        return ret;
    }

    @Override
    public List<Professor> listProfessors() {
        return professors;
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
        lectures.add(lecture);
    }

    public List<Lecture> getLectures() {
        return lectures;
    }

    @Override
    public void deleteAllSections() {

    }

    @Override
    public List<Student> getAllStudentsInUniversity() {
        return Collections.singletonList(new Student("Name", "email", university.getId(), "displayName"));
    }
}
