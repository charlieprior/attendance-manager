package edu.duke.ece651.team2.attendancemanager;

import java.util.Date;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AttendanceSessionTest {
    private AttendanceSession session;
    // private PersistenceManager mockPersistenceManager;

    String studentID1 = "student123";
    String lectureID1 = "lectureABC";
    String studentName1 = "11 22 22";
    String studentName2 = "AA BB CC";
    Boolean initialStatus = false;
    Boolean newStatus = true;

    @BeforeEach
    public void setUp() {
        // Create a mock PersistenceManager using Mockito
        // mockPersistenceManager = mock(PersistenceManager.class);
        // Initialize AttendanceSession with the mock PersistenceManager
        session = new AttendanceSession();

    }

    @Test
    void testRecordAttendanceNewRecord() {
        String studentID = "123";
        String lectureID = "Lecture1";
        String studentName = "A1 B1";
        Boolean status = true;

        // Test adding a new record
        assertTrue(session.recordAttendance(studentID, studentName,status, lectureID));

        // Verify that the record was added
        assertEquals(1, session.getRecords().size());
        AttendanceRecord addedRecord = session.getRecords().get(0);
        assertEquals(studentID, addedRecord.getStudentID());
        assertEquals(lectureID, addedRecord.getLectureID());
        assertEquals(status, addedRecord.getStatus());
        assertEquals(studentName, addedRecord.getStudentName());
    }

    @Test
    void testRecordAttendanceDuplicateRecord() {
        String studentID = "123";
        String lectureID = "Lecture1";
        String studentName = "A1 B1";
        Boolean status = true;

        // Add the initial record
        session.recordAttendance(studentID,studentName, status, lectureID);

        // Attempt to add a duplicate record
        assertFalse(session.recordAttendance(studentID, studentName,status, lectureID));

        // Verify that no duplicate record was added
        assertEquals(1, session.getRecords().size());
    }

    @Test
    void testUpdateAttendanceRecord() {

        // Update the status of the record
        session.recordAttendance(studentID1, studentName1,initialStatus, lectureID1);
        assertTrue(session.updateAttendanceRecord(studentID1, newStatus));

        // Verify the status was updated
        AttendanceRecord updatedRecord = session.getRecords().stream()
                .filter(r -> r.getStudentID().equals(studentID1))
                .findFirst()
                .orElse(null);
        assertNotNull(updatedRecord);
        assertEquals(newStatus, updatedRecord.getStatus());
    }

    @Test
    void testRemoveAttendanceRecord() {
        // Add and Remove the record
        session.recordAttendance(studentID1, studentName1,initialStatus, lectureID1);
        assertTrue(session.removeAttendanceRecord(studentID1));

        // Verify the record was removed
        boolean recordExists = session.getRecords().stream()
                .anyMatch(r -> r.getStudentID().equals(studentID1));
        assertFalse(recordExists);
    }

    @Test
    void testUpdateNonexistentAttendanceRecord() {
        // Try to update a nonexistent record
        assertFalse(session.updateAttendanceRecord("nonexistentID", true));
    }

    @Test
    void testRemoveNonexistentAttendanceRecord() {
        // Try to remove a nonexistent record
        assertFalse(session.removeAttendanceRecord("nonexistentID"));
    }
}
