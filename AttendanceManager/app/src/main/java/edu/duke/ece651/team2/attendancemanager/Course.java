package edu.duke.ece651.team2.attendancemanager;

import java.util.ArrayList;

public class Course {
    private final String categ;
    private final String courseID;
    private final String courseName;
    private final String professor;
    private final ArrayList<Lecture> lectures = new ArrayList<>();
    private final AttendanceSession attendance;
    private final int lectureID;

    /*
     * constructor
     */
    public Course(String categ, String id, String name, String pro, AttendanceSession attendance){
        this.categ = categ;
        this.courseID = id;
        this.courseName = name;
        this.professor = pro;
        this.attendance = attendance;
        this.lectureID = 0;
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

    public String getCateg(){
        return categ;
    }

    public String toString(){
        return categ+courseID;
    }

    public String getProfessor(){
        return professor;
    }

    public void addLecture(Lecture lec){
        lectures.add(lec);
    }

    public void startLecture(){
        lectureID+=1;
        Lecture newLec = new Lecture();//constructor, losing parameters
        newLec.attendanceRecord(attendance); //function inside Lecture class
        addLecture(newLec);
    }
}