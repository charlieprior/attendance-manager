package edu.duke.ece651.team2.attendancemanager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;


public class TextUserController {
    private final BufferedReader reader;
    private final PrintStream out;

    public TextUserController(BufferedReader reader, PrintStream out) {
        this.reader = reader;
        this.out = out;
    }

    protected String printPromptAndRead(String prompt) throws IOException {
        out.println(prompt);
        return reader.readLine();
    }

    // assume every input is correct
    public Student readNewStudents() throws IOException {
        String prompt = "You are adding new Student, please provide the required info:\nWhats the student's legal name:";
        String legalName = printPromptAndRead(prompt);
        prompt = "Whats the student's display name:";
        String displayName = printPromptAndRead(prompt);
        prompt = "Whats the student's UID:";
        String id = printPromptAndRead(prompt);
        prompt = "Whats the student's E-Mail:";
        String email = printPromptAndRead(prompt);

        Student newStudent = new Student(legalName, id, email, displayName);
        return newStudent;
    }

    /**
     * @param studentsName is the String of students' display name in this Course
     * @param start    means beginning of the lecture if true else end of the
     *                 lecture
     */
    public AttendanceStatus readStudentStatus(String studentsName, boolean start) throws IOException {
        String prompt = "You are recording students status, please type the status of the student:";
        out.println(prompt);
        if (start == true) {
            prompt = "p for present, a for absent";
        } else {
            prompt = "l for late, a for absent";
        }

        String ans = printPromptAndRead(studentsName + ":" + prompt);
        out.print(ans);
        if (ans.equals("p") && start == true) {
            return AttendanceStatus.PRESENT;
        } else if (ans.equals("l") && start == false) {
            return AttendanceStatus.TARDY;
        } else {
            return AttendanceStatus.ABSENT;
        }

    }

    public Professor readNewProfessor() throws IOException {
        String prompt = "You are adding new Professor, please provide the required info:\nWhat's the professor's legal name:";
        String name = printPromptAndRead(prompt);
        prompt = "What's the professor's UID:";
        String id = printPromptAndRead(prompt);
        prompt = "What's the professor's E-Mail:";
        String email = printPromptAndRead(prompt);

        Professor newProfessor = new Professor(name, id, email);
        return newProfessor;
    }

    public Course readNewCourse(String id, Professor pro, ArrayList<Student> students) throws IOException {
        String prompt = "You are adding new Course, please provide the required info:\nWhat's the course's name";
        String name = printPromptAndRead(prompt);
        Course newCourse = new Course(id, name, pro, students);
        return newCourse;
    }

    public int readAction(String prompt) throws IOException {
        prompt = prompt + "Currently you need to do some actions, please type the number of your desired action:\n";
        ArrayList<String> actions = new ArrayList<>();
        actions.add("1. add Course\n");
        actions.add("2. add Students to Course\n");
        actions.add("3. start a new Lecture from one Course,then take attenace records\n");
        actions.add("4. print students from one course\n");
        actions.add("5. quit the program.\n");
        for (String action : actions) {
            prompt = prompt + action;
        }
        String ans = printPromptAndRead(prompt);
        if (ans.length() == 1) {
            if (Character.isDigit(ans.charAt(0))) {
                int cmd = Character.getNumericValue(ans.charAt(0));
                return cmd;
            }
        }
        return readAction("wrong input, please type the command again!\n");
    }

    public ArrayList<Student> keepAddingStudents() throws IOException{
        ArrayList<Student> students = new ArrayList<>();
        String prompt = "Do you want to add a new student? y for Yes.";
        String ans = printPromptAndRead(prompt);
        while (ans.equals("y")){
            students.add(readNewStudents());
            ans = printPromptAndRead(prompt);
        }
        return students;
    }

    public int selectCourse(int maxSize) throws IOException{
        String prompt = "Please type the number in front of the target course, invalid selection will return.";
        String ans = printPromptAndRead(prompt);
        try{
            int idx = Integer.parseInt(ans)-1;
            return idx;
        }
        catch(NumberFormatException e){
            return maxSize;
        }
    }

    public int displayAndChooseCourse(Professor professor) throws IOException{
        TextUserView view = new TextUserView(out);
        view.printCourses(professor);
        return selectCourse(professor.getCourses().size());
    }

    //maybe can add some time duration later ...?
    public boolean stopTheLecture() throws IOException{
        String prompt = "Would you want to stop right now? y for Yes";
        String ans = printPromptAndRead(prompt);
        if(ans.equals("")||ans.equals("y")){
            return true;
        }
        return stopTheLecture();
    }

    public void displayStudentsFromCourse(Course course) throws IOException{
        TextUserView view = new TextUserView(out);
        view.printStudents(course);
    }

}
