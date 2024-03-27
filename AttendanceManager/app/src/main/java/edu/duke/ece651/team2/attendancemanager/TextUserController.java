package edu.duke.ece651.team2.attendancemanager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;


/**
 * The TextUserController class is responsible for handling user input in the text-based user interface
 * and displaying relevant output for collecting input.
 */
public class TextUserController {
    /**
     * The BufferedReader used to read input from the user.
     */
    private final BufferedReader reader;
    /**
     * The PrintStream used to write output to the user.
     */
    private final PrintStream out;

    /**
     * Constructs a new TextUserController object with the specified BufferedReader and PrintStream.
     *
     * @param reader The BufferedReader used to read input from the user.
     * @param out    The PrintStream used to write output to the user.
     */
    public TextUserController(BufferedReader reader, PrintStream out) {
        this.reader = reader;
        this.out = out;
    }

    /**
     * Prints the specified prompt to the user and reads the user's input.
     *
     * @param prompt The prompt to print to the user.
     * @return The user's input.
     * @throws IOException We will not handle this exception.
     */
    protected String printPromptAndRead(String prompt) throws IOException {
        out.println(prompt);
        return reader.readLine();
    }

    /**
     * Reads the user's input for a new Student.
     *
     * @return The new Student object created from the user's input.
     * @throws IOException We will not handle this exception.
     */
    public Student readNewStudents() throws IOException {
        // assume every input is correct
        String prompt = "You are adding new Student, please provide the required info:\nWhats the student's legal name:";
        String legalName = printPromptAndRead(prompt);
        prompt = "Whats the student's display name:";
        String displayName = printPromptAndRead(prompt);
        prompt = "Whats the student's UID:";
        String id = printPromptAndRead(prompt);
        prompt = "Whats the student's E-Mail:";
        String email = printPromptAndRead(prompt);

        return new Student(legalName, id, email, displayName);
    }

    /**
     * Reads the user's input for a new Lecture.
     * @param studentsName is the String of students' display name in this Course
     * @param start    means beginning of the lecture if true else end of the
     *                 lecture
     * @return The status of the student.
     * @throws IOException We will not handle this exception.
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

    /**
     * Reads the user's input for a new Professor.
     * @return The new Professor object created from the user's input.
     * @throws IOException We will not handle this exception.
     */
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

    /**
     * Reads the user's input for a new Course.
     * @param id is the course ID
     * @param pro is the professor who teaches this course
     * @param students is the list of students in this course
     * @return The new Course object created from the user's input.
     * @throws IOException We will not handle this exception.
     */
    public Course readNewCourse(String id, Professor pro, ArrayList<Student> students) throws IOException {
        String prompt = "You are adding new Course, please provide the required info:\nWhat's the course's name";
        String name = printPromptAndRead(prompt);
        Course newCourse = new Course(id, name, pro, students);
        return newCourse;
    }

    /**
     * Reads the user's action.
     * @param prompt is the prompt to ask the user to type the command
     * @return The command number
     * @throws IOException We will not handle this exception.
     */
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

    /**
     * Asks if the user would like to keep adding students.
     * @return The list of students added by the user.
     * @throws IOException We will not handle this exception.
     */
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

    /**
     * Asks the user to select a course.
     * @param maxSize is the maximum number of courses.
     * @return The index of the selected course.
     * @throws IOException We will not handle this exception.
     */
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

    /**
     * Displays the courses and asks the user to select one.
     * @param professor is the professor who teaches the courses
     * @return The index of the selected course.
     * @throws IOException We will not handle this exception.
     */
    public int displayAndChooseCourse(Professor professor) throws IOException{
        TextUserView view = new TextUserView(out);
        view.printCourses(professor);
        return selectCourse(professor.getCourses().size());
    }

    /**
     * Asks if the user would like to stop the lecture.
     * @return True if the user would like to stop the lecture, false otherwise.
     * @throws IOException We will not handle this exception.
     */
    public boolean stopTheLecture() throws IOException{
        //maybe can add some time duration later ...?
        String prompt = "Would you want to stop right now? y for Yes";
        String ans = printPromptAndRead(prompt);
        if(ans.equals("")||ans.equals("y")){
            return true;
        }
        return stopTheLecture();
    }

    /**
     * Displays the students in the specified course.
     * @param course is the course to display the students
     * @throws IOException We will not handle this exception.
     */
    public void displayStudentsFromCourse(Course course) throws IOException{
        TextUserView view = new TextUserView(out);
        view.printStudents(course);
    }

}
