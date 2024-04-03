package edu.duke.ece651.team2.shared;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * The Lecture class represents a lecture for a course.
 */
public class Lecture {
    /**
     * The name of the course.
     */
    String courseName;
    /**
     * The ID of the lecture.
     */
    String lectureID;
    /**
     * The date of the lecture.
     */
    Calendar date;
    /**
     * The list of students in the lecture.
     */
    ArrayList<Student> students;
    /**
     * The Professor teaching the lecture.
     */
    Professor professor;
    /**
     * The AttendanceSession of the lecture.
     */
    AttendanceSession attendanceSession;

    /**
     * Constructs a new Lecture object with default values;
     */
    public Lecture() {
        this.lectureID = "";
        this.students = new ArrayList<>();
    }

    /**
     * Constructs a new Lecture object with the specified course name, lecture ID,
     * students and professor.
     *
     * @param courseName The name of the course.
     * @param lectureID  The ID of the lecture.
     * @param students   The list of students in the lecture.
     * @param professor  The Professor teaching the lecture.
     */
    public Lecture(String courseName, String lectureID, ArrayList<Student> students, Professor professor) {
        this.courseName = courseName;
        this.lectureID = lectureID;
        this.students = students;
        this.professor = professor;
    }

    /**
     * Get the AttendanceSession of the lecture.
     *
     * @return The AttendanceSession of the lecture.
     */
    public AttendanceSession getAttendanceSession() {
        return attendanceSession;
    }

    /**
     * Returns the ID of the lecture.
     *
     * @return The ID of the lecture.
     */
    String getLectureID() {
        return lectureID;
    }

    /**
     * Sets the ID of the lecture.
     *
     * @param lectureID The ID of the lecture.
     */
    void setLectureID(String lectureID) {
        this.lectureID = lectureID;
    }

    /**
     * Gets the name of the course.
     *
     * @return The name of the course.
     */
    String getCourseName() {
        return courseName;
    }

    /**
     * Returns the date of the lecture.
     *
     * @return The date of the lecture.
     */
    Calendar getDate() {
        return date;
    }

    /**
     * Sets the date of the lecture.
     *
     * @param date The date of the lecture.
     */
    void setDate(Calendar date) {
        this.date = date;
    }

    /**
     * Adds students to the lecture.
     *
     * @param students The list of students to add.
     */
    void addStudents(ArrayList<Student> students) {
        this.students.addAll(students);
    }

    /**
     * Returns the list of students in the lecture.
     *
     * @return The list of students in the lecture.
     */
    public Iterable<Student> getStudents() {
        return students;
    }

    /**
     * Records attendance for the students in the lecture.
     *
     * @param status The list of attendance statuses for the students.
     */
    public void recordAttendance(ArrayList<AttendanceStatus> status) {
        AttendanceSession newSession = new AttendanceSession();
        this.attendanceSession = newSession;
        for (int i = 0; i < students.size(); i++) {
            newSession.recordAttendance(students.get(i).getStudentID(), students.get(i).getDisplayName(), status.get(i),
                    lectureID);
        }
    }

    /**
     * Gets the list of student IDs who are late.
     *
     * @return The list of student IDs who are late.
     */
    public ArrayList<String> getLateStudentsID() {
        return attendanceSession.lateStudentsID();
    }

    /**
     * Gets the list of student names who are late.
     *
     * @return The list of student names who are late.
     */
    public ArrayList<String> getLateStudentsName() {
        return attendanceSession.lateStudentsName();
    }

    /**
     * Ends the lecture and updates the attendance records.
     *
     * @param lateStudentsID The list of student IDs who are late.
     * @param status         The list of attendance statuses for the students.
     * @throws IOException We will not handle this exception.
     */
    public void endLecture(ArrayList<String> lateStudentsID, ArrayList<AttendanceStatus> status) throws IOException {
        for (int i = 0; i < lateStudentsID.size(); i++) {
            if (status.get(i) == AttendanceStatus.TARDY) {
                attendanceSession.updateAttendanceRecord(lateStudentsID.get(i), AttendanceStatus.TARDY);
            }
        }
        PersistenceManager export = new PersistenceManager();
        // export.writeRecordsToCSV(courseName+" "+lectureID,attendanceSession);
        export.writeRecordsToCSV(courseName + " " + lectureID, attendanceSession);
        export.writeRecordsToJSON(courseName + " " + lectureID, courseName, lectureID, attendanceSession);
        // attendanceSession.endSession();
    }

    /**
     * Ends the lecture and updates the attendance records.
     */
    public void endLecture() {
        PersistenceManager export = new PersistenceManager();
        export.writeRecordsToCSV(courseName + " " + lectureID, attendanceSession);
        // email-notify
    }

    /**
     * Checks if the student is in the lecture.
     *
     * @param id The ID of the student.
     * @return true if the student is in the lecture, false otherwise.
     */
    public boolean currentStudent(String id) {
        for (Student s : students) {
            if (s.getStudentID().equals(id)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Finds the email of the student with the specified ID.
     *
     * @param id The ID of the student.
     * @return The email of the student, or null if the student is not in the
     *         lecture.
     */
    public String findEmailThroughID(String id) {
        if (currentStudent(id)) {
            for (Student s : students) {
                if (s.getStudentID().equals(id)) {
                    return s.getEmail();
                }
            }
        }
        return null;
    }

    /**
     * Finds the student with the specified ID.
     *
     * @param id The ID of the student.
     * @return The student with the specified ID, or null if the student is not in
     *         the lecture.
     */
    public Student findStudentThroughID(String id) {
        for (Student s : students) {
            if (s.getStudentID().equals(id)) {
                return s;
            }
        }
        return null;
    }

    /**
     * Updates the attendance record for a student.
     *
     * @param lateStudentsID The ID of the student.
     * @param eventManager   The EventManager to notify of the change.
     * @return true if the attendance record was successfully updated, false
     *         otherwise.
     * @throws GeneralSecurityException We will not handle this exception.
     * @throws IOException              We will not handle this exception.
     */
    public boolean updateForOneStudent(String lateStudentsID, EventManager eventManager)
            throws GeneralSecurityException, IOException {
        if (currentStudent(lateStudentsID)) {
            boolean res = attendanceSession.updateAttendanceRecord(lateStudentsID, AttendanceStatus.TARDY);
            if (res) {
                PersistenceManager export = new PersistenceManager();
                export.writeRecordsToCSV(courseName + " " + lectureID + " updated", attendanceSession);

                // Send Email
                Student student = findStudentThroughID(lateStudentsID);
                AttendanceRecord record = attendanceSession.getAttendanceRecord(lateStudentsID);
                eventManager.notifyAttendanceChanged(student, record);
                return true;
            }
        }
        return false;
    }

}
