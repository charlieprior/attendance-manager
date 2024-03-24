package edu.duke.ece651.team2.attendancemanager;

//import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

public class Lecture{
  String courseName;
  String lectureID;
  Calendar date;
  ArrayList<Student> Students;
  Professor professor;
  AttendanceSession attendanceSession;

  public Lecture(){
    this.lectureID = new String();
    this.Students = new ArrayList<>();
  }

  public Lecture(String courseName,String lectureID, ArrayList<Student> students,Professor professor){
    this.courseName = courseName;
    this.lectureID = lectureID;
    this.Students = students;
    this.professor = professor;
  }
  
  void setLectureID(String lectureID){
    this.lectureID = lectureID;
  }

  String getLectureID(){
    return lectureID;
  }

  void setDate(Calendar date){
    this.date = date;
  }

  Calendar getDate(){
    return date;
  }

  void setStudents(ArrayList<Student> Students){
    for(Student s : Students){
      this.Students.add(s);
    }
  }

  public Iterable<Student> getStudents(){
    return Students;
  }
  
  public boolean readStatus(){
    Scanner scanner = new Scanner(System.in);
    String ans = scanner.nextLine();
    ans = ans.toLowerCase();
    if(ans =="" || ans=="y" || ans=="yes"){
      return true;
    }
    return false;
  }


  public void attendanceRecord(){
    AttendanceSession newSession = new AttendanceSession();
    this.attendanceSession = newSession;
    for (Student s:Students){
      boolean status = readStatus();
      newSession.recordAttendance(s.getStudentID(),status,lectureID);
    }
  }

  public void endLecture(){
    attendanceSession.endSession();
  }
}
