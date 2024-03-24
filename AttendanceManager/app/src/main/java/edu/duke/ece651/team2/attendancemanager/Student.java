package edu.duke.ece651.team2.attendancemanager;

public class Student{
  String legalName;
  String studentID;
  String email;
  String displayName;

  public Student(){
    this.legalName = "";
    this.studentID = "";
    this.email = "";
    this.displayName = "";
  }

  public Student(String ln, String si, String e, String dn){
    this.legalName = ln;
    this.studentID = si;
    this.email = e;
    this.displayName = dn;
  }

  void setLegalName(String legalName){
    System.out.println("Please enter Student's legal name: ");
    this.legalName = legalName;
  }

  String getLegalName(){return legalName;}

  void setStudentID(String studentID){
    System.out.println("Please enter Student's ID: ");
    this.studentID = studentID;
  }

  String getStudentID(){return studentID;}
  void setEmail(String email){
    System.out.println("Please enter Student's email address: ");
    this.email = email;
  }

  String getEmail(){return email;}
  
  void setDisplayName(String displayName){
    System.out.println("Please enter Student's display name: ");
    this.displayName = displayName;
  }
  String getDisplayName(){return displayName;}

  void addCheck(){
    if(legalName != "" && studentID != "" &&
       email != "" && displayName != ""){
      System.out.println("Student " + legalName + "Successfully Added");
    }
    else{
      System.out.println("Failed to add all fields for the student");
    }
  }
}
