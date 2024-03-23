package edu.duke.ece651.team2.attendancemanager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AttendanceSession {
    private List<AttendanceRecord> records = new ArrayList<>();
    // private PersistenceManager persistenceManager;

    public AttendanceSession() {
        this.records = new ArrayList<>();
        // this.persistenceManager = persistenceManager;
    }

    public List<AttendanceRecord> getRecords() {
        return records;
    }

    // Add AttendanceRecord into records
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

    // Look for the AttendanceRecord with the specified studentID and Update its
    // status.
    public boolean updateAttendanceRecord(String studentID, Boolean newStatus) {
        for (AttendanceRecord record : records) {
            if (record.getStudentID().equals(studentID)) {
                record.setStatus(newStatus);
                return true; // Record found and status updated
            }
        }
        return false; // Record not found
    }

    // Remove the AttendanceRecord with the specified studentID from the records
    // list
    public boolean removeAttendanceRecord(String studentID) {
        return records.removeIf(record -> record.getStudentID().equals(studentID));
        // removeIf returns true if any elements were removed
    }

    public void endSession() {
        // persistenceManager.save()
        records.clear();
    }
}
