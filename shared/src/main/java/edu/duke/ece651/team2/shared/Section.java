package edu.duke.ece651.team2.shared;

public class Section {
    private int SectionID;
    private Course Course;
    private Professor Instructor;
    private String name;//display the name for this Section(not quite the same as Course. Should be CourseName+Section)

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

    public void getStudents() {
        //DAO
    }

    public void getLectures() {
        //DAO
    }

}
