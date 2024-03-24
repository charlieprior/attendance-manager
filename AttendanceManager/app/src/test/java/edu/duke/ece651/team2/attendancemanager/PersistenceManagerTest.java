package edu.duke.ece651.team2.attendancemanager;

import java.util.Date;

import org.junit.jupiter.api.Test;

public class PersistenceManagerTest {
    private AttendanceRecord record1;
    private Date testDate;
    private String testStudentID;
    private Boolean testStatus;
    private String testLectureID;

    @Test
    public void testWriteRecordsToCSV(){
        testStudentID = "12345";
        testStatus = true; // Assume true means present
        testLectureID = "CS101_Lecture1";
        record1 = new AttendanceRecord(testDate, testStudentID, testStatus, testLectureID);
        PersistenceManager m = new PersistenceManager();
        AttendanceSession s = new AttendanceSession();
        s.recordAttendance(testStudentID, testStatus, testLectureID);
        m.writeRecordsToCSV(testLectureID, s);
    }
}
