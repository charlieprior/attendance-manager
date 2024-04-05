package edu.duke.ece651.team2.shared;

import java.util.ArrayList;

public class Section {
    private int SectionID;
    private Course Course;
    private Professor Instructor;
    private String name;//display the name for this Section(not quite the same as Course. Should be CourseName+Section)
    private ArrayList<Student> students;
    private ArrayList<Lecture> lectures;

    public Section(Course course, Professor professor, String name){
        this.Course = course;
        this.Instructor = professor;
        this.name = name;//how to set name, courseName + len(Section) in Course?
        course.addSection(this);
    }

    public int getSectionID() {
        return SectionID;
    }

    public void setSectionID(int sectionID) {
        SectionID = sectionID;
    }

    public Course getCourse() {
        return Course;
    }

    public Professor getInstructor() {
        return Instructor;
    }

    public String getName() {
        return name;
    }

    public void setCourse(Course course) {
        Course = course;
    }

    public void setInstructor(Professor instructor) {
        Instructor = instructor;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> s) {
        this.students = s;
    }

    public ArrayList<Lecture> getLectures() {
        return lectures;
    }

    public void setLecture(ArrayList<Lecture> l) {
        this.lectures = l;
    }

}
