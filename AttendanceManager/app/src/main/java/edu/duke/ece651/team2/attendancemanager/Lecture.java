package edu.duke.ece651.team2.attendancemanager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
//import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import edu.duke.ece651.team2.attendancemanager.App.AttendanceStatus;

public class Lecture {
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

  public Lecture() {
    this.lectureID = new String();
    this.Students = new ArrayList<>();
  }

  public Lecture(String courseName, String lectureID, ArrayList<Student> students, Professor professor,
      BufferedReader reader) {
    this.courseName = courseName;
    this.lectureID = lectureID;
    this.Students = students;
    this.professor = professor;
    this.inputReader = reader;
  }

  void setLectureID(String lectureID) {
    this.lectureID = lectureID;
  }

  String getLectureID() {
    return lectureID;
  }

  String getCourseName() {
    return courseName;
  }

  void setDate(Calendar date) {
    this.date = date;
  }

  Calendar getDate() {
    return date;
  }

  void setStudents(ArrayList<Student> Students) {
    for (Student s : Students) {
      this.Students.add(s);
    }
  }

  public Iterable<Student> getStudents() {
    return Students;
  }

  public void setInputReader(BufferedReader inputReader) {
    this.inputReader = inputReader;
  }

  public AttendanceStatus readStatus(String s) throws IOException {
    System.out.println(s);
    String ans = inputReader.readLine();
    if (ans == null) {
      return AttendanceStatus.present;
    }
    ans = ans.toLowerCase();
    if (ans.equals("y") || ans.equals("yes")) {
      return AttendanceStatus.present;
    }
    return AttendanceStatus.absent;
  }

  public void attendanceRecord() throws IOException {
    AttendanceSession newSession = new AttendanceSession();
    this.attendanceSession = newSession;
    for (Student s : Students) {
      AttendanceStatus status = readStatus(s.getDisplayName());
      newSession.recordAttendance(s.getStudentID(), s.getDisplayName(), status, lectureID);
    }
  }

  public void endLecture() throws IOException {
    ArrayList<String> lateStudentsID = attendanceSession.lateStudentsID();
    ArrayList<String> lateStudentsName = attendanceSession.lateStudentsName();
    assert (lateStudentsID.size() == lateStudentsName.size());
    for (int i = 0; i < lateStudentsID.size(); i++) {
      AttendanceStatus status = readStatus(lateStudentsName.get(i));
      if (status == AttendanceStatus.absent) {
        attendanceSession.updateAttendanceRecord(lateStudentsID.get(i), AttendanceStatus.tardy);
      }
    }
    PersistenceManager export = new PersistenceManager();
    // export.writeRecordsToCSV(courseName+" "+lectureID,attendanceSession);
    export.writeRecordsToJSON(courseName + " " + lectureID, courseName, lectureID, attendanceSession);
    attendanceSession.endSession();
  }

}
