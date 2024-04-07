package edu.duke.ece651.team2.shared;


/**
 * The AttendanceRecord class represents a record of student attendance for a
 * specific lecture.
 */
public class AttendanceRecord {
    /**
     * The ID of the student associated with the attendance record.
     */
    private final Student student;
    /**
     * The status of the student's attendance (true if present, false if absent).
     */
    private AttendanceStatus status;
    /**
     * The ID of the lecture associated with the attendance record.
     */
    private final Lecture lecture;

    private final Section section;

    private int recordID;

    /**
     * Constructs a new AttendanceRecord object with the specified attendance date,
     * student ID, status, and lecture ID.
     *
     * @param attendanceDate The date of the attendance record.
     * @param studentID      The ID of the student associated with the attendance
     *                       record.
     * @param studentName    The name of the student associated with the attendance
     * @param status         The status of the student's attendance (true if
     *                       present, false if absent).
     * @param lectureID      The ID of the lecture associated with the attendance
     *                       record.
     */
    public AttendanceRecord(Student student,AttendanceStatus status,Lecture lec,Section sec) {
        this.student = student;
        this.status = status;
        this.lecture = lec;
        this.section = sec;
    }

    public void setRecordID(int id){
        this.recordID = id;
    }

    public Student getStudent() {
        return student;
    }

    public AttendanceStatus getStatus() {
        return status;
    }

    public void setStatus(AttendanceStatus status) {
        this.status = status;
    }

    public Lecture getLecture() {
        return lecture;
    }

    public Section getSection() {
        return section;
    }

    public int getRecordID() {
        return recordID;
    }



}
