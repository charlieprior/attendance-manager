package edu.duke.ece651.team2.attendancemanager;

import java.util.Calendar;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AttendanceRecordTest {
    private AttendanceRecord record1;
    private Date testDate;
    private String testStudentID;
    private Boolean testStatus;
    private String testLectureID;

    @BeforeEach
    void setUp() {
        testDate = new Date();
        testStudentID = "12345";
        testStatus = true; // Assume true means present
        testLectureID = "CS101_Lecture1";
        record1 = new AttendanceRecord(testDate, testStudentID, testStatus, testLectureID);
    }

    @Test
    void testGetAttendanceDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2024);
        calendar.set(Calendar.MONTH, Calendar.MARCH); // Calendar.MONTH is 0-based (January is 0)
        calendar.set(Calendar.DAY_OF_MONTH, 23);
        Date newTestDate = calendar.getTime();
        assertEquals(testDate, record1.getAttendanceDate());
        assertNotEquals(newTestDate, record1.getAttendanceDate());
    }

    @Test
    void testSetAttendanceDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2024);
        calendar.set(Calendar.MONTH, Calendar.MARCH); // Calendar.MONTH is 0-based (January is 0)
        calendar.set(Calendar.DAY_OF_MONTH, 23);
        Date newTestDate = calendar.getTime();

        record1.setAttendanceDate(newTestDate);
        assertEquals(newTestDate, record1.getAttendanceDate());
        assertNotEquals(testDate, record1.getAttendanceDate());
    }

    @Test
    void testGetStudentID() {
        assertEquals(testStudentID, record1.getStudentID());
        assertNotEquals("111", record1.getStudentID());
    }

    @Test
    void testSetStudentID() {
        String newTestStudentID = "67890";
        record1.setStudentID(newTestStudentID);
        assertEquals(newTestStudentID, record1.getStudentID());
        assertNotEquals(testStudentID, record1.getStudentID());
    }

    @Test
    void testGetStatus() {
        assertEquals(testStatus, record1.getStatus());
        assertTrue(record1.getStatus());
    }

    @Test
    void testSetStatus() {
        Boolean newTestStatus = false;
        record1.setStatus(newTestStatus);
        assertEquals(newTestStatus, record1.getStatus());
        assertFalse(record1.getStatus());
    }

    @Test
    void testGetLectureID() {
        assertEquals(testLectureID, record1.getLectureID());
        assertNotEquals("111", record1.getLectureID());
    }

    @Test
    void testSetLectureID() {
        String newTestLectureID = "CS101_Lecture2";
        record1.setLectureID(newTestLectureID);
        assertEquals(newTestLectureID, record1.getLectureID());
        assertNotEquals(testLectureID, record1.getLectureID());
    }
}
