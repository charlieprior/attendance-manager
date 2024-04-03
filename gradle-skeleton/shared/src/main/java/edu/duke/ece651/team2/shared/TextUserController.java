package edu.duke.ece651.team2.shared;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

/**
 * The TextUserController class is responsible for handling user input in the
 * text-based user interface
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
     * Constructs a new TextUserController object with the specified BufferedReader
     * and PrintStream.
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
     * Reads the user's input for a new University.
     *
     * @return The new University object created from the user's input.
     * @throws IOException We will not handle this exception.
     */
    public University readUniversity() throws IOException {
        String name = printPromptAndRead("Whats the university?");
        String support = printPromptAndRead("Does it allow for change display name? y for yes");
        if (support.equals("y")) {
            return new University(name, true);
        }
        return new University(name, false);
    }

    /**
     * Registers a new Professor.
     *
     * @param info       is the ProtectedInfo object to store the professor's
     *                   information
     * @param university is the university where the professor works
     * @return The new Professor object created from the user's input.
     * @throws IOException We will not handle this exception.
     */
    public Professor register(ProtectedInfo info, University university) throws IOException {
        String id = printPromptAndRead("Hi, new Professor, this is " + university.getName() + ". What is your id?");
        String password = printPromptAndRead("What is your password?");
        info.storeProtectedInfo(id, password);
        return readNewProfessor(id, university);
    }

    /**
     * Logs in a Professor.
     *
     * @param info is the ProtectedInfo object to store the professor's information.
     * @throws IOException We will not handle this exception.
     */
    public void logIn(ProtectedInfo info) throws IOException {
        String id = printPromptAndRead("Hello, what is your UID?");
        String password = printPromptAndRead("What is your password?");
        boolean res = info.match(id, password);
        if (res) {
            return;
        }
        out.println("Incorrect User/Password. Try again!");
        logIn(info);
    }

    /**
     * Reads the user's input for a new Student.
     *
     * @return The new Student object created from the user's input.
     * @throws IOException We will not handle this exception.
     */
    public Student readNewStudents() throws IOException {
        // assume every input is correct
        String legalName = printPromptAndRead(
                "You are adding new Student, please provide the required info:\nWhats the student's legal name:");
        String displayName = printPromptAndRead("Whats the student's display name:");
        String id = printPromptAndRead("Whats the student's UID:");
        String email = printPromptAndRead("Whats the student's E-Mail:");

        return new Student(legalName, id, email, displayName);
    }

    /**
     * Reads the user's input for a new Lecture.
     *
     * @param studentsName is the String of students' display name in this Course
     * @param start        means beginning of the lecture if true else end of the
     *                     lecture
     * @return The status of the student.
     * @throws IOException We will not handle this exception.
     */
    public AttendanceStatus readStudentStatus(String studentsName, boolean start) throws IOException {
        String prompt = "You are recording students status, please type the status of the student:";
        out.println(prompt);
        if (start) {
            prompt = "p for present, a for absent";
        } else {
            prompt = "l for late, a for absent";
        }

        String ans = printPromptAndRead(studentsName + ":" + prompt);
        if (ans.equals("p") && start) {
            return AttendanceStatus.PRESENT;
        } else {
            return AttendanceStatus.ABSENT;
        }

    }

    /**
     * Reads the user's input for a new Professor.
     *
     * @param id         is the professor ID
     * @param university is the university where the professor works
     * @return The new Professor object created from the user's input.
     * @throws IOException We will not handle this exception.
     */
    public Professor readNewProfessor(String id, University university) throws IOException {
        String prompt = "What's your legal name:";
        String name = printPromptAndRead(prompt);
        prompt = "What's your E-Mail:";
        String email = printPromptAndRead(prompt);
        return new Professor(name, id, email, university);
    }

    /**
     * Reads the user's input for a new Course.
     *
     * @param id  is the course ID
     * @param pro is the professor who teaches this course
     * @return The new Course object created from the user's input.
     * @throws IOException We will not handle this exception.
     */
    public Course readNewCourse(String id, Professor pro) throws IOException {
        String prompt = "You are adding new Course, please provide the required info:\nWhat's the course's name";
        String name = printPromptAndRead(prompt);
        ArrayList<Student> students = loadStudents();
        return new Course(id, name, pro, students);
    }

    /**
     * Reads the user's action.
     *
     * @param prompt is the prompt to ask the user to type the command
     * @return The command number
     * @throws IOException We will not handle this exception.
     */
    public int readAction(String prompt) throws IOException {
        prompt = prompt + "Currently you need to do some actions, please type the number of your desired action:\n";
        ArrayList<String> actions = new ArrayList<>();
        actions.add("1. add Course\n");
        actions.add("2. add Students to Course manually\n");
        actions.add("3. drop Students from Course manually\n");
        actions.add("4. start a new Lecture from one Course,then take attenace records\n");
        actions.add("5. update an attendance record for a student\n");
        actions.add("6. change a student's display name\n");
        actions.add("7. display the attendance records from one course\n");
        actions.add("8. print students from one course\n");
        actions.add("9. quit the program.\n");
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
     *
     * @return The list of students added by the user.
     * @throws IOException We will not handle this exception.
     */
    public ArrayList<Student> keepAddingStudents() throws IOException {
        ArrayList<Student> students = new ArrayList<>();
        String prompt = "Do you want to add a new student? y for Yes.";
        String ans = printPromptAndRead(prompt);
        while (ans.equals("y")) {
            students.add(readNewStudents());
            ans = printPromptAndRead(prompt);
        }
        return students;
    }

    /**
     * Displays the courses and asks the user to select one.
     *
     * @param professor is the professor who teaches the courses
     * @return The index of the selected course.
     * @throws IOException We will not handle this exception.
     */
    public int chooseCourse(Professor professor) throws IOException {
        int maxSize = professor.getCourses().size();
        String prompt = "Please type the number in front of the target course, invalid selection will return.";
        String ans = printPromptAndRead(prompt);
        try {
            int idx = Integer.parseInt(ans) - 1;
            if (idx >= 0 && idx < maxSize) {
                return idx;
            }
            return maxSize;
        } catch (NumberFormatException e) {
            out.println(e.getMessage());
            return maxSize;
        }
    }

    // I am currently working on loading csv. Current progress:

    /**
     * Asks the user if there is a header in the file.
     *
     * @return True if there is a header in the file, false otherwise.
     * @throws IOException We will not handle this exception.
     */
    public boolean readHeaderExists() throws IOException {
        String prompt = "Do you have a header in this file? y for Yes";
        String ans = printPromptAndRead(prompt);
        return ans.equals("y");
    }

    /**
     * Extracts the columns from a line from the csv file.
     *
     * @param line      is the line to read
     * @param separator is the separator in the csv file
     * @return The columns in the line.
     */
    public String[] extractColumns(String line, String separator) {
        return line.split(separator);
    }

    /**
     * Counts the number of columns in the csv file.
     *
     * @param line      is the line to read
     * @param separator is the separator in the csv file
     * @return The number of columns in the csv file.
     */
    public int getNumberOfColumns(String line, String separator) {
        int count = 0;
        if (!line.isEmpty()) {
            count += 1;
        } else {
            return count;
        }
        int idx = line.indexOf(separator);
        while (idx != -1) {
            count += 1;
            idx = line.indexOf(separator, idx + separator.length());
        }
        return count;
    }

    /**
     * Asks the user which column corresponds to the prompted column.
     *
     * @param size   is the number of columns in the csv file.
     * @param prompt is the prompt to ask the user to select the column.
     * @return The index of the selected column.
     * @throws Exception We will not handle this exception.
     */
    public int readColumnNumber(int size, String prompt) throws Exception {
        String ans = printPromptAndRead(prompt);
        int idx = Integer.parseInt(ans);
        if (idx > size || idx <= 0) {
            return readColumnNumber(size, "No such column! Read it again!");
        }
        return idx - 1;
    }

    /**
     * Reads the students from the csv file.
     *
     * @param lines is the list of lines in the csv file
     * @return The list of students read from the csv file.
     * @throws Exception We will not handle this exception.
     */
    public ArrayList<Student> getStudentsFromCSV(ArrayList<String> lines) throws Exception {
        String separater = printPromptAndRead("Which separator in this file?");
        int columns = getNumberOfColumns(lines.get(0), separater);
        int ansln = readColumnNumber(columns, "Which column is for legal name? Remember, column starts from 1.");
        int ansuid = readColumnNumber(columns, "Which column is for uid of this student?");
        int ansemail = readColumnNumber(columns, "Which column is for email?");
        int ansdn = readColumnNumber(columns,
                "Which column is for display name? If not, please select the same column with legal name");
        ArrayList<Integer> ans = new ArrayList<>();
        ans.add(ansln);
        ans.add(ansuid);
        ans.add(ansemail);
        ans.add(ansdn);
        ArrayList<Student> newStudents = new ArrayList<Student>();
        for (String line : lines) {
            String[] lineTokens = extractColumns(line, separater);
            Student newStudent = new Student(lineTokens[ans.get(0)], lineTokens[ans.get(1)], lineTokens[ans.get(2)],
                    lineTokens[ans.get(3)]);
            newStudents.add(newStudent);
        }
        return newStudents;
    }

    /**
     * Reads the csv file.
     *
     * @return The list of students read from the csv file.
     * @throws Exception We will not handle this exception.
     */
    public ArrayList<Student> readCSVFiles() throws Exception {
        String prompt = "what is your path?";// path?
        String ans = printPromptAndRead(prompt);
        ArrayList<Student> newStudents;
        ArrayList<String> lines = new ArrayList<>();
        FileReader filereader;
        BufferedReader bufferedReader;
        try {
            try {
                filereader = new FileReader(ans);
                bufferedReader = new BufferedReader(filereader);
            } catch (IOException e) {
                out.println(e.getMessage());
                String rsp = printPromptAndRead(
                        "cannot read the file, y for loading again, else return. The course will be created.");
                if (rsp.equals("y")) {
                    return readCSVFiles();
                }
                return new ArrayList<Student>();
            }
            String line;
            boolean header = readHeaderExists();
            if (header) {
                bufferedReader.readLine();
            }
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
            bufferedReader.close();
            // ask each column responds to what?
            newStudents = getStudentsFromCSV(lines);
            return newStudents;
        } catch (Exception e) {
            out.println(e.getMessage());
            String res = printPromptAndRead("Do you want to repeat loading? y for yes");
            if (res.equals("y")) {
                return readCSVFiles();
            }
            return new ArrayList<Student>();
        }
    }

    /**
     * Checks whether to load students from csv file, and loads them if so.
     *
     * @return The list of students read from the csv file, or empty.
     */
    public ArrayList<Student> loadStudents() {
        try {
            ArrayList<Student> students = new ArrayList<>();
            String prompt = "whether to load students? y for yes";
            String ans = printPromptAndRead(prompt);
            if (ans.equals("y")) {
                students = readCSVFiles();
            }
            return students;
        } catch (Exception e) {
            return loadStudents();
        }
    }

    /**
     * Asks the user to update the record for a student.
     *
     * @param lecture      is the lecture to update the record for.
     * @param eventManager is the EventManager to notify of the change.
     * @throws IOException              We will not handle this exception.
     * @throws GeneralSecurityException We will not handle this exception.
     */
    public void updateRecordForStudent(Lecture lecture, EventManager eventManager)
            throws IOException, GeneralSecurityException {
        String ans = printPromptAndRead("What is your Student ID?");
        if (lecture.updateForOneStudent(ans, eventManager)) {
            out.println("Successfully update the record for this student. An email may send to the student's email");
        } else {
            out.println("No updated record. The student may not be a student in this lecture");
        }
    }

    // TODO: All of them should be done

    /**
     * Updates the students' records.
     *
     * @param professor    is the professor who teaches the course.
     * @param eventManager is the EventManager to notify of the change.
     * @throws IOException              We will not handle this exception.
     * @throws GeneralSecurityException We will not handle this exception.
     */
    public void updateStudentsRecords(Professor professor, EventManager eventManager)
            throws IOException, GeneralSecurityException {
        int courseIndex = chooseCourse(professor);
        Course course = professor.getCourse(courseIndex);
        Lecture lastLecture = course.getLatestLecture();
        TextUserView view = new TextUserView(out);
        view.printStudentStatus(lastLecture);
        updateRecordForStudent(lastLecture, eventManager);
    }

    /**
     * Changes the student's display name.
     *
     * @param professor is the professor who teaches the course.
     * @throws IOException We will not handle this exception.
     */
    public void changeStudentDisplayName(Professor professor) throws IOException {
        int idx = chooseCourse(professor);
        Course course = professor.getCourse(idx);
        String id = printPromptAndRead("What is the student's ID?");
        String newName = printPromptAndRead("What is your new preferred display name?");
        if (course.changeStudentDisplayName(id, newName)) {
            out.println("Successfully!");
        } else {
            out.println("The id may be wrong.");
        }
    }

    /**
     * Displays the attendance records.
     *
     * @param records is the list of attendance records to display
     * @throws IOException We will not handle this exception.
     */
    public void displayRecords(List<AttendanceRecord> records) throws IOException {
        for (AttendanceRecord r : records) {
            out.println(
                    r.getStudentID() + " " + r.getStudentName() + " " + r.getAttendanceDate() + " " + r.getStatus());
        }
    }

    /**
     * Displays the attendance records from a course.
     *
     * @param professor is the professor who teaches the course.
     * @throws IOException We will not handle this exception.
     */
    public void displayAttendanceFromCourse(Professor professor) throws IOException {
        int idx = chooseCourse(professor);
        Course course = professor.getCourse(idx);
        for (int i = 0; i < course.getLectureSize(); i++) {
            out.println("Lecture " + i + ":");
            List<AttendanceRecord> records = course.getLectureRecords(i);
            displayRecords(records);
        }
    }

    /**
     * Asks user if they would like to remove students from a course.
     *
     * @param professor is the professor who teaches the course.
     * @throws IOException We will not handle this exception.
     */
    public void removeStudentsFromCourse(Professor professor) throws IOException {
        int idx = chooseCourse(professor);
        Course course = professor.getCourse(idx);
        String id = printPromptAndRead("What is the student's UID?");
        if (course.dropStudents(id)) {
            out.println("Successfully!");
        } else {
            out.println("The id may be wrong.");
        }
    }

}
