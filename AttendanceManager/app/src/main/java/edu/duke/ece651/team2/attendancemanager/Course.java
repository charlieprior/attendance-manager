package edu.duke.ece651.team2.attendancemanager;

import java.util.ArrayList;

public class Course {
    private final String courseID;
    private final String courseName;
    private final Professor professor;
    private int lectureTimes;
    private final ArrayList<Student> students;

    /*
     * constructor
     */
    public Course(String id, String name, Professor pro,ArrayList<Student> students){
        this.courseID = id;
        this.courseName = name;
        this.professor = pro;
        this.lectureTimes = 0;
        this.students = students;
    }

    public Course(String id, String name, Professor pro, int lectureTimes,ArrayList<Student> students){
        this.courseID = id;
        this.courseName = name;
        this.professor = pro;
        this.lectureTimes = lectureTimes;
        this.students = students;
    }

    /*
     * return course name
    */
    public String getName(){
        return courseName;
    }

    public String getCourseID(){
        return courseID;
    }

    public String getProfessor(){
        return professor.getName();
    }

    public Lecture startLecture(){
        lectureTimes+=1;
        String lectureID = courseID+"_"+lectureTimes;
        //AttendanceSession newSession = new AttendanceSession(courseName, lectureID, professor.getName(), students);
        Lecture newLec = new Lecture(courseName,lectureID, students, professor);
        newLec.attendanceRecord(); //function inside Lecture class
        return newLec;
    }

    public void endLecture(Lecture lecture){
        lecture.endLecture();
    }
}