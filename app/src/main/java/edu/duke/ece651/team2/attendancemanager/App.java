/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package edu.duke.ece651.team2.attendancemanager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

/**
 * The main class for the Attendance Manager application.
 */
public class App {
    /**
     * The Professor using the application.
     */
    private final Professor professor;
    /**
     * The controller for the application.
     */
    private final TextUserController controller;

    private final GmailSetup gmailSetup; // TODO: Remove from App

    /**
     * Constructs a new App object with the specified Professor and controller.
     *
     * @param professor  The Professor using the application.
     * @param controller The controller for the application.
     */
    public App(Professor professor,TextUserController controller) throws GeneralSecurityException, IOException {
        this.professor = professor;
        this.controller = controller;
        this.gmailSetup = new GmailSetup();
    }


    /**
     * Adds students to a course.
     * @throws IOException We will not handle this exception.
     */
    public void addStudentsToCourse() throws IOException{
        int idx = controller.displayAndChooseCourse(professor);
        if(idx<professor.getCourses().size()){
            ArrayList<Student> students = controller.keepAddingStudents();
            professor.getCourse(idx).addStudents(students);
        }
    }

    /**
     * Reads the attendance status for a list of students.
     * @param studentsName The names of the students.
     * @param start Whether the lecture has started.
     * @return The attendance status for the students.
     * @throws IOException We will not handle this exception.
     */
    public ArrayList<AttendanceStatus> readStatusForStudents(ArrayList<String> studentsName,boolean start) throws IOException{
        ArrayList<AttendanceStatus> status = new ArrayList<>();
        for(String s:studentsName){
            status.add(controller.readStudentStatus(s, start));
        }
        return status;
    }


    /**
     * Starts a new lecture.
     * @throws IOException We will not handle this exception.
     */
    public void startNewLecture() throws IOException{
        int idx = controller.displayAndChooseCourse(professor);
        if(idx<professor.getCourses().size()){
            Course course = professor.getCourse(idx);
            ArrayList<AttendanceStatus> status = readStatusForStudents(course.getStudentsDisplayName(),true);
            Lecture lec = course.startLecture(status);
            course.endLecture(lec);
            // controller.stopTheLecture();
            // ArrayList<AttendanceStatus> statusLate = readStatusForStudents(lec.getLateStudentsName(),false);
            // course.endLecture(lec, lec.getLateStudentsID(), statusLate);
        }
        else{
            controller.printPromptAndRead("wrong course number,return to menu.");
            startNewLecture();
        }
    }

    public void updateStudentsRecords() throws IOException{
        controller.updateStudentsRecords(professor);
    }

    /**
     * Displays the students from a course.
     * @throws IOException We will not handle this exception.
     */
    public void displayStudentsFromCourse() throws IOException{
        int idx = controller.displayAndChooseCourse(professor);
        if(idx<professor.getCourses().size()){
            Course course = professor.getCourse(idx);
            controller.displayStudentsFromCourse(course);
        }
    }

    public void changeStudentDisplayName() throws IOException{
        if(professor.getUniversityPolicy()==false){
            controller.print("You cannot change the display name by policy!");
        }
        controller.changeStudentDisplayName(professor);
    }



    /**
     * Welcomes the user.
     * @throws IOException We will not handle this exception.
     */
    public void welcome() throws IOException{
        int cmd = controller.readAction("Hi, "+professor.getName()+". What do you want to do?");
        switch(cmd){
            case 1:
                String courseID = "C"+(professor.getCourses().size()+1);
                Course newCourse = controller.readNewCourse(courseID, professor);
                professor.addCourse(newCourse);
                break;
            case 2:
                addStudentsToCourse();
                break;
            case 3:
                controller.removeStudentsFromCourse(professor);
                break;
            case 4:
                startNewLecture();
                break;
            case 5:
                updateStudentsRecords();
                break;
            case 6:
                changeStudentDisplayName();
                break;
            case 7:
                controller.displayAttendanceFromCourse(professor);
                break;
            case 8:
                displayStudentsFromCourse();
                break;
            case 9:
                return;
        }
        welcome();
    }

    /**
     * The main method for the Attendance Manager application.
     * @param args The command-line arguments.
     * @throws IOException We will not handle this exception.
     */
    public static void main(String[] args) throws IOException, GeneralSecurityException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        TextUserController controller = new TextUserController(input, System.out);
        University university = controller.readUniversity();//TODO!!!!
        ProtectedInfo info = new ProtectedInfo();
        Professor user = controller.register(info,university);
        controller.logIn(info);
        App app = new App(user, controller);
        app.gmailSetup.sendEmail("Test", "This is a test"); // TODO remove from App
        // app.logIn();
        app.welcome();
    }
}
