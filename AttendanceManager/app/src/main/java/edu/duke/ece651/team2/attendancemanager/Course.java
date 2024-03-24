package edu.duke.ece651.team2.attendancemanager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;


public class Course {
    private final String courseID;
    private final String courseName;
    private final Professor professor;
    private int lectureTimes;
    private final ArrayList<Student> students;
    private final BufferedReader inputReader;
    // private final PrintStream out;

    /*
     * constructor
     */
    public Course(String id, String name, Professor pro,ArrayList<Student> students,BufferedReader inputReader){
        this.courseID = id;
        this.courseName = name;
        this.professor = pro;
        this.lectureTimes = 0;
        this.students = students;
        this.inputReader = inputReader;
    }

    public Course(String id, String name, Professor pro, int lectureTimes,ArrayList<Student> students,BufferedReader inputReader){
        this.courseID = id;
        this.courseName = name;
        this.professor = pro;
        this.lectureTimes = lectureTimes;
        this.students = students;
        this.inputReader = inputReader;
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

    public int getLectureTimes(){
        return lectureTimes;
    }

    public int numberOfStudents(){
        return students.size();
    }

    public void addStudent(Student s){
        students.add(s);
    }

    public void addStudents(ArrayList<Student> stu){
        for(Student s:stu){
            students.add(s);
        }
    }

    public Lecture startLecture() throws IOException{
        lectureTimes+=1;
        String lectureID = courseID+"_"+lectureTimes;
        //AttendanceSession newSession = new AttendanceSession(courseName, lectureID, professor.getName(), students);
        Lecture newLec = new Lecture(courseName,lectureID, students, professor,inputReader);
        newLec.attendanceRecord(); //function inside Lecture class
        return newLec;
    }

    public void endLecture(Lecture lecture) throws IOException{
        lecture.endLecture();
    }
}