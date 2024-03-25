package edu.duke.ece651.team2.attendancemanager;

import java.io.PrintStream;

public class TextUserView {
    final PrintStream out;

    public TextUserView(PrintStream out) {
        this.out = out;
    }

    void printHeader(String header) {
        out.println("=".repeat(75));
        out.println(header);
        out.println("=".repeat(75));
    }

    void printAllStudents(Lecture l) {
        /*code to print out students*/
    }

    void printCourses(Professor professor) {
        printHeader("Courses Taught by " + professor.getName());
        for (int i = 0; i < professor.getCourses().size(); i++) {
            String sb = (i + 1) + ". " +
                    professor.getCourses().get(i).getCourseID() +
                    " - " +
                    professor.getCourses().get(i).getName();
            out.println(sb);
        }
    }

    void printStudentStatus(Lecture lecture) {
        printHeader("Student Statuses");
        AttendanceSession session = lecture.getAttendanceSession();
        for (AttendanceRecord record : session.getRecords()) {
            out.println(record.getStudentName() + " - " + record.getStatus());
        }
    }

    void studentAddCheck(Student s) {
        if (s.legalName != "" && s.studentID != "" &&
                s.email != "" && s.displayName != "") {
            out.println("Student " + s.legalName +
                    "Successfully Added");
        } else {
            out.println("Failed to add all fields for the student");
        }
    }
}
