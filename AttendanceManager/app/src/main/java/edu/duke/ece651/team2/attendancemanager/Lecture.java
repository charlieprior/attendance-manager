package edu.duke.ece651.team2.attendancemanager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
//import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import edu.duke.ece651.team2.attendancemanager.App.AttendanceStatus;

public class Lecture{
  String courseName;
  String lectureID;
  Calendar date;
  ArrayList<Student> Students;
  Professor professor;

  public AttendanceSession getAttendanceSession() {
    return attendanceSession;
  }

  AttendanceSession attendanceSession;
  private BufferedReader inputReader;
  

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

  public Lecture(String courseName,String lectureID, ArrayList<Student> students,Professor professor,BufferedReader reader){
    this.courseName = courseName;
    this.lectureID = lectureID;
    this.Students = students;
    this.professor = professor;
    this.inputReader = reader;
  }
  
  void setLectureID(String lectureID){
    this.lectureID = lectureID;
  }

  String getLectureID(){
    return lectureID;
  }

  String getCourseName(){
    return courseName;
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
  
  public void setInputReader(BufferedReader inputReader){
    this.inputReader = inputReader;
  }

  public void attendanceRecord(ArrayList<AttendanceStatus> status){
    AttendanceSession newSession = new AttendanceSession();
    this.attendanceSession = newSession;
    for(int i =0;i<Students.size();i++){
      newSession.recordAttendance(Students.get(i).getStudentID(),Students.get(i).getDisplayName(),status.get(i),lectureID);
    }
  }

  public ArrayList<String> getLateStudentsID(){
    return attendanceSession.lateStudentsID();
  }

  public ArrayList<String> getLateStudentsName(){
    return attendanceSession.lateStudentsName();
  }

  public void endLecture(ArrayList<String> lateStudentsID,ArrayList<AttendanceStatus> status) throws IOException{
    for(int i =0;i<lateStudentsID.size();i++){
      if(status.get(i)==AttendanceStatus.tardy){
        attendanceSession.updateAttendanceRecord(lateStudentsID.get(i), AttendanceStatus.tardy);
      }
    }
    PersistenceManager export = new PersistenceManager();
    //export.writeRecordsToCSV(courseName+" "+lectureID,attendanceSession);
    export.writeRecordsToCSV(courseName+" "+lectureID, attendanceSession);
    export.writeRecordsToJSON(courseName+" "+lectureID, courseName, lectureID, attendanceSession);
    attendanceSession.endSession();
  }

}
