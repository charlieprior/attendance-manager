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
    private final Integer universityId;
    /**
     * The ID of the Course.
     */
    private Integer courseID;
    private List<Section> sections;

    private Integer universityId;

    private List<Section> sections;


    /**
     * This constructor will create the Course object.
     *
     * @param name is the Course's name.
     */
    public Course(String name, Integer universityId) {
        this.courseName = name;
        this.universityId = universityId;
    }

    public Integer getUniversityId() {
        return universityId;
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

    public void setCourseID(Integer courseID) {
        this.courseID = courseID;
    }

    public void addSection(Section s) {
        this.sections.add(s);
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sec) {
        sections = sec;
    }

}
