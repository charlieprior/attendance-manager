package edu.duke.ece651.team2.attendancemanager;

import java.util.Date;

public class AttendanceRecord {
    private Date attendanceDate;
    private String studentID;
    private Boolean status;
    private String lectureID;

    public AttendanceRecord(Date attendanceDate, String studentID, Boolean status, String lectureID) {
        this.attendanceDate = attendanceDate;
        this.studentID = studentID;
        this.status = status;
        this.lectureID = lectureID;
    }

    public Date getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(Date attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getLectureID() {
        return lectureID;
    }

    public void setLectureID(String lectureID) {
        this.lectureID = lectureID;
    }
}
