package edu.duke.ece651.team2.shared;

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
    private Integer courseID;

    public Integer getUniversityId() {
        return universityId;
    }

    private Integer universityId;

    private List<Section> sections;


    /**
     * This constructor will create the Course object.
     *
     * @param name is the Course's name.
     */
    public Course(String name, Integer courseID) {
        this.courseName = name;
        this.courseID = courseID;
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
    public Integer getCourseID() {
        return courseID;
    }

    public void addSection(Section s){
        this.sections.add(s);
    }

    public void setCourseID(Integer courseID) {
        this.courseID = courseID;
    }

    public void setSections(List<Section> sec){
        sections = sec;
    }

    public List<Section> getSections(){
        return sections;
    }

}
