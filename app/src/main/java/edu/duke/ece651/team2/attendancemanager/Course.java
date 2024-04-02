package edu.duke.ece651.team2.attendancemanager;

import java.util.ArrayList;
import java.util.List;


/**
 * The Course class represents a course that students can take.
 */
public class Course {
    /**
     * The ID of the Course.
     */
    private final String courseID;
    /**
     * The name of the Course.
     */
    private final String courseName;
    /**
     * The Professor teaching the Course.
     */
    private final Professor professor;
    /**
     * The list of students taking the Course.
     */
    private final ArrayList<Student> students;
    /**
     * The list of Lectures for the Course.
     */
    private final ArrayList<Lecture> lectures;
    /**
     * The number of times the Course has been lectured.
     */
    private int lectureTimes;

    /**
     * This constructor will create the Course object.
     *
     * @param id       is the Course's ID.
     * @param name     is the Course's name.
     * @param pro      is the Professor teaching the course.
     * @param students the students taking the course.
     */
    public Course(String id, String name, Professor pro, ArrayList<Student> students) {
        this.courseID = id;
        this.courseName = name;
        this.professor = pro;
        this.lectureTimes = 0;
        this.students = students;
        this.lectures = new ArrayList<>();
    }

    /**
     * This constructor will create the Course object.
     *
     * @param id       is the Course's ID.
     * @param name     is the Course's name.
     * @param pro      is the Professor information.
     * @param students the students taking the course.
     * @param lectures the lectures for the course.
     */
    public Course(String id, String name, Professor pro, ArrayList<Student> students, ArrayList<Lecture> lectures) {
        this.courseID = id;
        this.courseName = name;
        this.professor = pro;
        this.lectureTimes = 0;
        this.students = students;
        this.lectures = lectures;
    }

    /**
     * This constructor will create the Course object
     *
     * @param id           is the Course's ID
     * @param name         is the Course's name
     * @param pro          is the Professor information
     * @param lectureTimes is the times of the Course's Lectures
     * @param students     the students attending the course.
     * @param lectures     the lectures for the course.
     */
    public Course(String id, String name, Professor pro, int lectureTimes, ArrayList<Student> students, ArrayList<Lecture> lectures) {
        this.courseID = id;
        this.courseName = name;
        this.professor = pro;
        this.lectureTimes = lectureTimes;
        this.lectures = lectures;
        this.students = students;
    }

    /**
     * @return course name
     */
    public String getName() {
        return courseName;
    }

    /**
     * @return courseID
     */
    public String getCourseID() {
        return courseID;
    }

    /**
     * @return professor's name
     */
    public String getProfessor() {
        return professor.getName();
    }

    /**
     * @return lecture's times of this course
     */
    public int getLectureTimes() {
        return lectureTimes;
    }

    /**
     * @return the number of lectures in this course
     */
    public int getLectureSize() {
        return lectures.size();
    }

    /**
     * @return the number of students in this course
     */
    public int numberOfStudents() {
        return students.size();
    }

    /**
     * @return the list of students in this course
     */
    public ArrayList<Student> getStudents() {
        return students;
    }

    /**
     * @return the list of students' display names in this course
     */
    public ArrayList<String> getStudentsDisplayName() {
        ArrayList<String> dn = new ArrayList<>();
        for (Student s : students) {
            dn.add(s.getDisplayName());
        }
        return dn;
    }

    /**
     * Get the student's name at index i
     *
     * @param i is the index of the student
     * @return the name of the student at index i
     */
    public String getStudentName(int i) {
        return students.get(i).getLegalName();
    }

    /**
     * @return the number of lectures in this course
     */
    public int numberOfLectures() {
        return lectures.size();
    }

    /**
     * @param i is the index of the lecture
     * @return the lecture at index i
     */
    public String getLectureName(int i) {
        return lectures.get(i).getLectureID();
    }

    /**
     * @return the latest lecture
     */
    public Lecture getLatestLecture() {
        return lectures.get(lectures.size() - 1);
    }

    /**
     * @param s is the student to be added to the Course
     */
    public void addStudent(Student s) {
        students.add(s);
    }

    /**
     * @param stu is the list of students to be added to this Course
     */
    public void addStudents(ArrayList<Student> stu) {
        for (Student s : stu) {
            students.add(s);
        }
    }

    /**
     * Drop the student with the given ID.
     *
     * @param id is the student's ID.
     * @return true if the student is dropped successfully, false otherwise.
     */
    public boolean dropStudents(String id) {
        for (Student s : students) {
            if (s.getStudentID().equals(id)) {
                students.remove(s);
                return true;
            }
        }
        return false;
    }

    /**
     * Change the student's display name.
     *
     * @param id is the student's ID.
     * @param dn is the new display name.
     * @return true if the display name is changed successfully, false otherwise.
     */
    public boolean changeStudentDisplayName(String id, String dn) {
        for (Student s : students) {
            if (s.getStudentID().equals(id)) {
                s.setDisplayName(dn);
                return true;
            }
        }
        return false;
    }

    /**
     * This function will start a new lecture
     *
     * @param status is the list of students' status at the beginning of the class.
     * @return the new lecture
     */
    public Lecture startLecture(ArrayList<AttendanceStatus> status) {
        lectureTimes += 1;
        String lectureID = courseID + "_" + lectureTimes;
        Lecture newLec = new Lecture(courseName, lectureID, students, professor);
        newLec.recordAttendance(status);
        return newLec;
    }

    /**
     * This function will end the lecture
     *
     * @param lecture is the lecture to be ended
     */
    public void endLecture(Lecture lecture) {
        lecture.endLecture();
        lectures.add(lecture);
    }

    /**
     * @param idx the index of the lecture.
     * @return the attendance records of the lecture at index idx.
     */
    public List<AttendanceRecord> getLectureRecords(int idx) {
        return lectures.get(idx).getAttendanceSession().getRecords();
    }

    /**
     * Generates all attendance records for all lectures in this course.
     */
    public void generateWholeReportTillNow() {
        List<AttendanceRecord> all = new ArrayList<>();
        for (int i = 0; i < lectureTimes; i++) {
            all.addAll(lectures.get(i).getAttendanceSession().getRecords());
        }
        //may notify!!!!
    }
}
