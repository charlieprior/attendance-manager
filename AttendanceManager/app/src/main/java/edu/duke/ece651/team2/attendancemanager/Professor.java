package edu.duke.ece651.team2.attendancemanager;

import java.util.ArrayList;

public class Professor {
    private String name;
    private String professorID;
    private ArrayList<Course> courses;

    public Professor(String name, String professorID) {
        this.name = name;
        this.professorID = professorID;
        this.courses = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getProfessorID() {
        return professorID;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public boolean removeCourse(Course course) {
        return courses.remove(course);
    }
}
