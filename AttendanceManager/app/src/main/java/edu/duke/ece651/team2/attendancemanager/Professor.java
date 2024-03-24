package edu.duke.ece651.team2.attendancemanager;

import java.util.ArrayList;

/**
 * The Professor class represents a professor with a name, professor ID, and a
 * list of courses they teach.
 */
public class Professor {
    /**
     * The name of the professor.
     */
    private String name;

    /**
     * The unique ID of the professor.
     */
    private String professorID;

    /**
     * The list of courses taught by the professor.
     */
    private ArrayList<Course> courses;

    /**
     * The email address of the professor.
     */
    private String email;

    /**
     * Constructs a new Professor object with the specified name and professor ID.
     *
     * @param name        The name of the professor.
     * @param professorID The unique ID of the professor.
     */
    public Professor(String name, String professorID, String email) {
        this.name = name;
        this.professorID = professorID;
        this.courses = new ArrayList<>();
        this.email = email;
    }

    /**
     * Returns the name of the professor.
     *
     * @return The name of the professor.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the email address of the professor.
     *
     * @return The email address of the professor.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Returns the professor ID of the professor.
     *
     * @return The professor ID of the professor.
     */
    public String getProfessorID() {
        return professorID;
    }

    /**
     * Returns the list of courses taught by the professor.
     *
     * @return The list of courses taught by the professor.
     */
    public ArrayList<Course> getCourses() {
        return courses;
    }

    /**
     * Adds a course to the list of courses taught by the professor.
     *
     * @param course The course to be added.
     */
    public void addCourse(Course course) {
        courses.add(course);
    }

    /**
     * Removes a course from the list of courses taught by the professor.
     *
     * @param course The course to be removed.
     * @return true if the course was successfully removed, false otherwise.
     */
    public boolean removeCourse(Course course) {
        return courses.remove(course);
    }
}
