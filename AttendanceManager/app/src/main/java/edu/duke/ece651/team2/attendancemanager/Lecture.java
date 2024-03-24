package edu.duke.ece651.team2.attendancemanager;

//import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Lecture{
  String lectureID;
  Calendar date;
  ArrayList<Student> Students;

  public Lecture(){
    this.lectureID = new String();
    this.Students = new ArrayList<>();
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
  //void attendanceRecord(AttendanceSession);
}
