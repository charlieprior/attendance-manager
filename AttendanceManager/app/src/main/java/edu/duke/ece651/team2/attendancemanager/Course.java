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

    /**
     * This constructor will create the Course object
     * 
     * @param id is the Course's ID
     * @param name is the Course's name
     * @param pro is the Professor information
     * @param students is the ArrayList<Student> of the Course
     * @param inputReader is the BufferedReader to be read
    */
    public Course(String id, String name, Professor pro,ArrayList<Student> students,BufferedReader inputReader){
        this.courseID = id;
        this.courseName = name;
        this.professor = pro;
        this.lectureTimes = 0;
        this.students = students;
        this.inputReader = inputReader;
    }

    /**
     * This constructor will create the Course object
     * 
     * @param id is the Course's ID
     * @param name is the Course's name
     * @param pro is the Professor information
     * @param lectureTimes is the times of the Course's Lectures
     * @param students is the ArrayList<Student> of the Course
     * @param inputReader is the BufferedReader to be read
    */
    public Course(String id, String name, Professor pro, int lectureTimes,ArrayList<Student> students,BufferedReader inputReader){
        this.courseID = id;
        this.courseName = name;
        this.professor = pro;
        this.lectureTimes = lectureTimes;
        this.students = students;
        this.inputReader = inputReader;
    }

    /**
     * @return course name
    */
    public String getName(){
        return courseName;
    }

    /**
     * @return courseID
    */
    public String getCourseID(){
        return courseID;
    }

    /**
     * @return professor's name
    */
    public String getProfessor(){
        return professor.getName();
    }

    /**
     * @return lecture's times of this course
    */
    public int getLectureTimes(){
        return lectureTimes;
    }

    /**
     * @return the number of students in this course
    */
    public int numberOfStudents(){
        return students.size();
    }

  public String getStudentName(int i){
    return students.get(i).getLegalName();
  }

    /**
     * @param s is the student to be added to the Course
    */
    public void addStudent(Student s){
        students.add(s);
    }

    /**
     * @param stu is the list of students to be added to this Course
    */
    public void addStudents(ArrayList<Student> stu){
        for(Student s:stu){
            students.add(s);
        }
    }

    //or we can add a function to return students and let the textuser give the status 
    /**
     * This function will start a new lecture
     * 
     * @throws IOException if anything happens while recording students' status
     * @return the new lecture 
    */
    public Lecture startLecture() throws IOException{
        lectureTimes+=1;
        String lectureID = courseID+"_"+lectureTimes;
        //AttendanceSession newSession = new AttendanceSession(courseName, lectureID, professor.getName(), students);
        Lecture newLec = new Lecture(courseName,lectureID, students, professor,inputReader);
        newLec.attendanceRecord(); //function inside Lecture class
        return newLec;
    }

    /**
     * This function will end the lecture
     * 
     * @throws IOException if anything happens while recording the students' status at the end of the class
    */
    public void endLecture(Lecture lecture) throws IOException{
        lecture.endLecture();
    }
}
