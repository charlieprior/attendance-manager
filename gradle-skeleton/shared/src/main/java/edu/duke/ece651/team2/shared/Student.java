package edu.duke.ece651.team2.shared;

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
    String studentID;
    /**
     * The email of the student.
     */
    String email;
    /**
     * The display name of the student.
     */
    String displayName;

    /**
     * Constructs a new Student object with default values.
     */
    public Student() {
        this.legalName = "";
        this.studentID = "";
        this.email = "";
        this.displayName = "";
    }

    /**
     * Constructs a new Student object with the specified legal name, student ID,
     * email, and display name.
     *
     * @param ln The legal name of the student.
     * @param si The student ID of the student.
     * @param e  The email of the student.
     * @param dn The display name of the student.
     */
    public Student(String ln, String si, String e, String dn) {
        this.legalName = ln;
        this.studentID = si;
        this.email = e;
        this.displayName = dn;
    }

    /**
     * Get the legal name of the student.
     *
     * @return The legal name of the student.
     */
    String getLegalName() {
        return legalName;
    }

    /**
     * Set the legal name of the student.
     *
     * @param legalName The legal name of the student.
     */
    void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    /**
     * Get the student ID of the student.
     *
     * @return The student ID of the student.
     */
    String getStudentID() {
        return studentID;
    }

    /**
     * Set the student ID of the student.
     *
     * @param studentID The student ID of the student.
     */
    void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    /**
     * Get the email of the student.
     *
     * @return The email of the student.
     */
    String getEmail() {
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
    String getDisplayName() {
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
}
