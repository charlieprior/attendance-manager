package edu.duke.ece651.team2.attendancemanager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

import edu.duke.ece651.team2.attendancemanager.App.AttendanceStatus;

public class TextUserController {
    private final BufferedReader reader;
    private final PrintStream out;


    public TextUserController(BufferedReader reader,PrintStream out){
        this.reader = reader;
        this.out = out;
    }

    protected String printPromptAndRead(String prompt) throws IOException{
        out.println(prompt);
        return reader.readLine();
    }

    //assume every input is correct 
    public Student readNewStudents() throws IOException{
        String prompt = "You are adding new Student, please provide the required info:\nWhats the student's legal name:";
        String legalName = printPromptAndRead(prompt);
        prompt = "Whats the student's display name:";
        String displayName = printPromptAndRead(prompt);
        prompt = "Whats the student's UID:";
        String id = printPromptAndRead(prompt);
        prompt = "Whats the student's E-Mail:";
        String email = printPromptAndRead(prompt);

        Student newStudent = new Student(legalName, id, email,displayName);
        return newStudent;
    }

    /**
     * @param students is the ArrayList<Student> in this Course
     * @param start means beginning of the lecture if true else end of the lecture
    */
    public AttendanceStatus readStudentStatus(Student student,boolean start) throws IOException{
        String prompt = "You are recording students status, please type the status of the student:";
        out.println(prompt);
        if (start==true){
            prompt = "p for present, a for absent";
        }
        else{
            prompt = "l for late, a for absent";
        }
        String ans = printPromptAndRead(student.getDisplayName()+":"+prompt);
        if(ans=="p" && start==true){
            return AttendanceStatus.present;
        }
        else if(ans=="l" && start==false){
            return AttendanceStatus.tardy;
        }
        else{
            return AttendanceStatus.absent;
        }
        
    }

    public Professor readNewProfessor() throws IOException{
        String prompt = "You are adding new Professor, please provide the required info:\nWhats the professor's legal name:";
        String name = printPromptAndRead(prompt);
        prompt = "Whats the professor's UID:";
        String id = printPromptAndRead(prompt);
        prompt = "Whats the professor's E-Mail:";
        String email = printPromptAndRead(prompt);

        Professor newProfessor = new Professor(name, id, email);
        return newProfessor;
    }

    public Course readNewCourse(String id,Professor pro,ArrayList<Student> students) throws IOException{
        String prompt = "You are adding new Course, please provide the required info:\nWhats the course's name";
        String name = printPromptAndRead(prompt);
        Course newCourse = new Course(id, name, pro, students);
        return newCourse;
    }

    public int readAction(String prompt) throws IOException{
        prompt = prompt+"Currently you need to do some actions, please type the number of your desired action:\n";
        ArrayList<String> actions = new ArrayList<>();
        actions.add("1. add Course\n");
        actions.add("2. add Students to Course\n");
        actions.add("3. start a new Lecture from one Course,then take attenace records\n");
        for(String action:actions){
            prompt = prompt+action;
        }
        String ans = printPromptAndRead(prompt);
        if(ans.length()==1){
            if(Character.isDigit(ans.charAt(0))){
                int cmd = Character.getNumericValue(ans.charAt(0));
                return cmd;
            }
        }
        return readAction("wrong input, please type the command again!\n");

    }
}

