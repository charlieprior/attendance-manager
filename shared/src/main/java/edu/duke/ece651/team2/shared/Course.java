package edu.duke.ece651.team2.shared;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The Course class represents a course that students can take.
 */
public class Course {
    /**
     * The name of the Course.
     */
    private final String courseName;
    /**
     * The ID of the Course.
     */
    private int courseID;

    private List<Section> sections;


    /**
     * This constructor will create the Course object.
     *
     * @param name     is the Course's name.
     */
    public Course(String name) {
        this.courseName = name;
    }


    /**
     * @return course name
     */
    public String getName() {
        return courseName;
    }

    /**
     * @return courseID
     */
    public int getCourseID() {
        return courseID;
    }

    public void addSection(Section s){
        this.sections.add(s);
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public void setSections(List<Section> sec){
        sections = sec;
    }

    public List<Section> getSections(){
        return sections;
    }

}
