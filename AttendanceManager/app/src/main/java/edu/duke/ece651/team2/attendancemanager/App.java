/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package edu.duke.ece651.team2.attendancemanager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

enum AttendanceStatus{
    PRESENT,
    ABSENT,
    TARDY
}

public class App {


    private final Professor professor;
    private final TextUserController controller;

    public App(Professor professor,TextUserController controller){
        this.professor = professor;
        this.controller = controller;
    }

    public void addStudentsToCourse() throws IOException{
        int idx = controller.displayAndChooseCourse(professor);
        if(idx<professor.getCourses().size()){
            ArrayList<Student> students = controller.keepAddingStudents();
            professor.getCourse(idx).addStudents(students);
        }
    }

    public ArrayList<AttendanceStatus> readStatusForStudents(ArrayList<String> studentsName,boolean start) throws IOException{
        ArrayList<AttendanceStatus> status = new ArrayList<>();
        for(String s:studentsName){
            status.add(controller.readStudentStatus(s, start));
        }
        return status;
    }


    public void startNewLecture() throws IOException{
        int idx = controller.displayAndChooseCourse(professor);
        if(idx<professor.getCourses().size()){
            Course course = professor.getCourse(idx);
            ArrayList<AttendanceStatus> status = readStatusForStudents(course.getStudentsDisplayName(),true);
            Lecture lec = course.startLecture(status); //startLecture should be modified later
            controller.stopTheLecture();
            ArrayList<AttendanceStatus> statusLate = readStatusForStudents(lec.getLateStudentsName(),false);
            course.endLecture(lec, lec.getLateStudentsID(), statusLate);
        }
    }


    public void welcome() throws IOException{
        int cmd = controller.readAction("Hi, "+professor.getName()+". What do you want to do?");
        switch(cmd){
            case 1:
                int courseID = professor.getCourses().size();
                Course newCourse = controller.readNewCourse(Integer.toString(courseID), professor, new ArrayList<Student>());
                professor.addCourse(newCourse);
                break;
            case 2:
                addStudentsToCourse();
                break;
            case 3:
                startNewLecture();
                break;
            case 4:
                return;
        }
        welcome();
    }

    

    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        TextUserController controller = new TextUserController(input, System.out);
        Professor user = controller.readNewProfessor();
        App app = new App(user, controller);
        app.welcome();
    }
}
