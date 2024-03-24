package edu.duke.ece651.team2.attendancemanager;

import java.util.Date;

/**
 * The AttendanceRecord class represents a record of student attendance for a
 * specific lecture.
 */
public class AttendanceRecord {
    /**
     * The date of the attendance record.
     */
    private Date attendanceDate;

    /**
     * The ID of the student associated with the attendance record.
     */
    private String studentID;

    /**
     * The status of the student's attendance (true if present, false if absent).
     */
    private Boolean status;

    /**
     * The ID of the lecture associated with the attendance record.
     */
    private String lectureID;

    private String studentName;

    /**
     * Constructs a new AttendanceRecord object with the specified attendance date,
     * student ID, status, and lecture ID.
     *
     * @param attendanceDate The date of the attendance record.
     * @param studentID      The ID of the student associated with the attendance
     *                       record.
     * @param status         The status of the student's attendance (true if
     *                       present, false if absent).
     * @param lectureID      The ID of the lecture associated with the attendance
     *                       record.
     */
    public AttendanceRecord(Date attendanceDate, String studentID, String studentName, Boolean status, String lectureID) {
        this.attendanceDate = attendanceDate;
        this.studentID = studentID;
        this.status = status;
        this.lectureID = lectureID;
        this.studentName = studentName;
    }

    /**
     * Returns the date of the attendance record.
     *
     * @return The date of the attendance record.
     */
    public Date getAttendanceDate() {
        return attendanceDate;
    }

    /**
     * Sets the date of the attendance record.
     *
     * @param attendanceDate The date of the attendance record.
     */
    public void setAttendanceDate(Date attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    /**
     * Returns the ID of the student associated with the attendance record.
     *
     * @return The ID of the student associated with the attendance record.
     */
    public String getStudentID() {
        return studentID;
    }

    /**
     * Sets the ID of the student associated with the attendance record.
     *
     * @param studentID The ID of the student associated with the attendance record.
     */
    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    /**
     * Returns the status of the student's attendance.
     *
     * @return The status of the student's attendance (true if present, false if
     *         absent).
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     * Sets the status of the student's attendance.
     *
     * @param status The status of the student's attendance (true if present, false
     *               if absent).
     */
    public void setStatus(Boolean status) {
        this.status = status;
    }

    /**
     * Returns the ID of the lecture associated with the attendance record.
     *
     * @return The ID of the lecture associated with the attendance record.
     */
    public String getLectureID() {
        return lectureID;
    }

    public String getStudentName(){
        return studentName;
    }

    /**
     * Sets the ID of the lecture associated with the attendance record.
     *
     * @param lectureID The ID of the lecture associated with the attendance record.
     */
    public void setLectureID(String lectureID) {
        this.lectureID = lectureID;
    }

}
