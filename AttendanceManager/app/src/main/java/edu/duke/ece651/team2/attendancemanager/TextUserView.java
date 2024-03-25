package edu.duke.ece651.team2.attendancemanager;

import java.io.PrintStream;

public class TextUserView {
  final PrintStream out;

  public TextUserView(PrintStream out){
    this.out = out;
  }

  void printAllStudents(Lecture l){
    /*code to print out students*/
  }
  
  void studentAddCheck(Student s){
    if(s.legalName != "" && s.studentID != "" &&
       s.email != "" && s.displayName != ""){
      out.println("Student " + s.legalName +
                         "Successfully Added");
    }
    else{
      out.println("Failed to add all fields for the student");
    }
  }
}
