package edu.duke.ece651.team2.shared;

import java.util.ArrayList;

public class Section {
    private Integer courseId;
    private Integer instructorId;
    private String name;//display the name for this Section(not quite the same as Course. Should be CourseName+Section)
    private Integer SectionID;
    private ArrayList<Student> students;
    private ArrayList<Lecture> lectures;

    public Section(){}

    public Section(Integer courseId, String name){
        this.courseId = courseId;
        this.name = name;//how to set name, courseName + len(Section) in Course?
    }

    public Integer getSectionID() {
        return SectionID;
    }

    public void setSectionID(Integer sectionID) {
        SectionID = sectionID;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public Integer getInstructorId() {
        return instructorId;
    }

    public void setInstructorID(Integer insInteger){
        this.instructorId = insInteger;
    }

    public String getName() {
        return name;
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
