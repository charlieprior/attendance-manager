package edu.duke.ece651.team2.attendancemanager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The AttendanceSession class represents a session for recording student
 * attendance.
 */
public class AttendanceSession {
    /**
     * The list of attendance records for the session.
     */
    private List<AttendanceRecord> records = new ArrayList<>();
    private final String courseName;
    private final String lectureID;

    /**
     * Constructs a new AttendanceSession object.
     */
    public AttendanceSession() {
        this.records = new ArrayList<>();
        this.courseName = "courseName";
        this.lectureID = "lectureID";
    }

    public AttendanceSession(String courseName,String lectureID) {
        this.records = new ArrayList<>();
        this.courseName = courseName;
        this.lectureID = lectureID;

    }

    /**
     * Returns the list of attendance records for the session.
     *
     * @return The list of attendance records for the session.
     */
    public List<AttendanceRecord> getRecords() {
        return records;
    }

    public String getCourseName(){
        return courseName;
    }

    public String getlectureID(){
        return lectureID;
    }

    /**
     * Records attendance for a student in the session.
     *
     * @param studentID The ID of the student.
     * @param status    The status of the student's attendance (true if present,
     *                  false if absent).
     * @param lectureID The ID of the lecture.
     * @return true if the attendance was successfully recorded, false if a record
     *         for the same student and lecture already exists.
     */
    public boolean recordAttendance(String studentID, Boolean status, String lectureID) {
        // Check if a record for the same student and lecture already exists
        for (AttendanceRecord record : records) {
            if (record.getStudentID().equals(studentID) && record.getLectureID().equals(lectureID)) {
                System.out.println("An attendance record for this student and lecture already exists.");
                return false;
            }
        }

        // If no existing record is found, create a new one
        AttendanceRecord newRecord = new AttendanceRecord(new Date(), studentID, status, lectureID);
        records.add(newRecord);
        return true;
    }

    /**
     * Updates the attendance record for a student in the session.
     *
     * @param studentID The ID of the student.
     * @param newStatus The new status of the student's attendance (true if present,
     *                  false if absent).
     * @return true if the attendance record was successfully updated, false if the
     *         record was not found.
     */
    public boolean updateAttendanceRecord(String studentID, Boolean newStatus) {
        for (AttendanceRecord record : records) {
            if (record.getStudentID().equals(studentID)) {
                record.setStatus(newStatus);
                return true; // Record found and status updated
            }
        }
        return false; // Record not found
    }

    /**
     * Removes the attendance record for a student from the session.
     *
     * @param studentID The ID of the student.
     * @return true if the attendance record was successfully removed, false if the
     *         record was not found.
     */
    public boolean removeAttendanceRecord(String studentID) {
        return records.removeIf(record -> record.getStudentID().equals(studentID));
        // removeIf returns true if any elements were removed
    }

    /**
     * Ends the attendance session by clearing all records and call function to
     * write to CSV.
     */
    public void endSession() {
        records.clear();
    }
}
