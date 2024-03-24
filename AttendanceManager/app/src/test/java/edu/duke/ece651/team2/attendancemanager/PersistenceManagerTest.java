package edu.duke.ece651.team2.attendancemanager;

import java.util.Date;

import org.junit.jupiter.api.Test;

public class PersistenceManagerTest {
    private AttendanceRecord record1;
    private Date testDate;
    private String testStudentID;
    private Boolean testStatus;
    private String testLectureID;
    String studentName;

    @Test
    public void testWriteRecordsToCSV(){
        testStudentID = "12345";
        studentName = "A1 B1";
        testStatus = true; // Assume true means present
        testLectureID = "CS101_Lecture1";
        System.out.println(getClass().getClassLoader());
        record1 = new AttendanceRecord(testDate, testStudentID, studentName,testStatus, testLectureID);
        PersistenceManager m = new PersistenceManager();
        AttendanceSession s = new AttendanceSession();
        s.recordAttendance(testStudentID, studentName,testStatus, testLectureID);
        m.writeRecordsToCSV(testLectureID, s);
    }

    @Test
    public void testWriteRecordsToJSON(){
        testStudentID = "12345";
        studentName = "A1 B1";
        testStatus = true; // Assume true means present
        testLectureID = "CS101_Lecture1";
        System.out.println(getClass().getClassLoader());
        record1 = new AttendanceRecord(testDate, testStudentID, studentName,testStatus, testLectureID);
        PersistenceManager m = new PersistenceManager();
        AttendanceSession s = new AttendanceSession();
        s.recordAttendance(testStudentID, studentName,testStatus, testLectureID);
        m.writeRecordsToJSON(testLectureID, "CS101", testLectureID, s);
        m.writeRecordsToCSV(testLectureID, s);
    }
}
