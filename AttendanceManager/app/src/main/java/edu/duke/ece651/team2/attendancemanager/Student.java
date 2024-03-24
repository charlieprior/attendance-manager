package edu.duke.ece651.team2.attendancemanager;

public class Student{
  String legalName;
  String studentID;
  String email;
  String displayName;

  public Student(){
    this.legalName = new String();
    this.studentID = new String();
    this.email = new String();
    this.displayName = new String();
  }

  public Student(String ln, String si, String e, String dn){
    this.legalName = ln;
    this.studentID = si;
    this.email = e;
    this.displayName = dn;
  }

  void setLegalName(String legalName){
    this.legalName = legalName;
  }

  String getLegalName(){return legalName;}

  void setStudentID(String studentID){
    this.studentID = studentID;
  }

  String getStudentID(){return studentID;}
  void setEmail(String email){
    this.email = email;
  }

  String getEmail(){return email;}
  
  void setDisplayName(String displayName){
    this.displayName = displayName;
  }
  String getDisplayName(){return displayName;}
}
