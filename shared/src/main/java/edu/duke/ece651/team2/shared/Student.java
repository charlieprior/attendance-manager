package edu.duke.ece651.team2.shared;

import java.util.ArrayList;
/**
 * The Student class represents a student.
 */
public class Student {
    /**
     * The legal name of the student.
     */
    String legalName;
    /**
     * The student ID of the student.
     */
    int studentID;
    /**
     * The email of the student.
     */
    String email;
    /**
     * The display name of the student.
     */
    String displayName;

    ArrayList<Section> sections;

    /**
     * Constructs a new Student object with the specified legal name, student ID,
     * email, and display name.
     *
     * @param legalName   The legal name of the student.
     * @param id          The student ID of the student.
     * @param email       The email of the student.
     * @param displayName The display name of the student.
     */
    public Student(String legalName,String email, String displayName) {
        this.legalName = legalName;
        this.email = email;
        this.displayName = displayName;
    }

    /**
     * Get the legal name of the student.
     *
     * @return The legal name of the student.
     */
    public String getLegalName() {
        return legalName;
    }

    /**
     * Set the legal name of the student.
     *
     * @param legalName The legal name of the student.
     */
    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    /**
     * Get the student ID of the student.
     *
     * @return The student ID of the student.
     */
    public int getStudentID() {
        return studentID;
    }

    /**
     * Set the student ID of the student.
     *
     * @param studentID The student ID of the student.
     */
    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    /**
     * Get the email of the student.
     *
     * @return The email of the student.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set the email of the student.
     *
     * @param email The email of the student.
     */
    void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get the display name of the student.
     *
     * @return The display name of the student.
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Set the display name of the student.
     *
     * @param displayName The display name of the student.
     */
    void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    void addSection(Section... sec){
        for(Section s:sec){
            sections.add(s);
        }
    }

}
