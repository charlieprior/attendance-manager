package edu.duke.ece651.team2.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import edu.duke.ece651.team2.shared.*;

public class ClientSideController {
    private ClientSideView clientSideView;

    public ClientSideController(ClientSideView clientSideView) {
        this.clientSideView = clientSideView;
    }

    public String[] login() throws IOException {
        String userID = clientSideView.promptUser("Enter ID: ");
        String password = clientSideView.promptUser("Enter password: ");
        return new String[] { userID, password };
    }

    public int studentOperations() throws IOException {
        while (true) {
            clientSideView.displayMessage(
                    "1. Set email preferences (Students can choose to subscribe to attendance notifications for their enrolled courses)."
                            + "\n" + "2. Get attendance report for a specific course.\n" + "3. Exit.");

            // Process student's choice
            String choiceStr = clientSideView.promptUser("Please enter your choice: ");
            try {
                int choice = Integer.parseInt(choiceStr); // Convert the user's input to an integer

                // Handle the user's choice
                if (1 <= choice && choice <= 3) {
                    return choice;
                } else {
                    clientSideView.displayMessage("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                clientSideView.displayMessage("Invalid input. Please enter a number.");
            }
        }
    }

    public int professorOperations() throws IOException {
        while (true) {
            clientSideView.displayMessage(
                    "1. Record attendance.\n" +
                            "2. Update attendance.\n" +
                            "3. Export student attendance information.\n" +
                            "4. Select courses to teach that are not yet assigned.\n " +
                            "5.Exit.");

            // Process professor's choice
            String choiceStr = clientSideView.promptUser("Please enter your choice: ");
            try {
                int choice = Integer.parseInt(choiceStr); // Convert the user's input to an integer

                // Handle the user's choice
                if (1 <= choice && choice <= 5) {
                    return choice;
                } else {
                    clientSideView.displayMessage("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                clientSideView.displayMessage("Invalid input. Please enter a number.");
            }
        }
    }

    public String listSectionCourseName(List<String> names) throws IOException {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < names.size(); i++) {
            builder.append((i + 1)).append(". ").append(names.get(i));
            if (i < names.size() - 1) {
                builder.append("\n");
            }
        }
        builder.append("Please select (only one): Example 1, 2, 3");
        String choice = clientSideView.promptUser(builder.toString());
        return choice;
    }

    public void displayAllClassAttendance(List<String> names) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < names.size(); i++) {
            builder.append((i + 1)).append(". ").append(names.get(i)).append("     ");
            if ((i + 1) % 4 == 0 && i != names.size() - 1) {
                builder.append("\n");
            }
        }
        clientSideView.displayMessage(builder.toString());
    }

    public boolean isValidIntegerInRange(String str, int min, int max) {
        // Use regular expressions to check whether it is an integer
        if (!Pattern.matches("\\d+", str)) {
            return false;
        }

        int num = Integer.parseInt(str);

        return num >= min && num <= max;
    }

    public void displayPromptForStudent(int n) {
        if (n == 1) {
            clientSideView.displayMessage(
                    "Below are all the courses you are enrolled in this semester, please select one to set your email preferences.");
        } else if (n == 2) {
            clientSideView.displayMessage(
                    "Below are all the courses you are enrolled in this semester, please select one to get your attendance report.");
        }
    }

    public void displayPromptForFacultyGetSections(int n) {
        if (n == 1) {
            clientSideView.displayMessage(
                    "Below are the courses you are teaching this semester, please select a course to record your attendance.");
        } else if (n == 2) {
            clientSideView.displayMessage(
                    "Below are the courses you are teaching this semester, please select a course to update your attendance.");
        } else if (n == 3) {
            clientSideView.displayMessage(
                    "Below are the courses you are teaching this semester, please select a course to export students attendance info.");
        }
    }

    public void displayPromptForFacultyGetLectures(int n) {
        if (n == 1) {
            clientSideView.displayMessage(
                    "Below are the lectures you are teaching this semester, please select a course to record your attendance.");
        } else if (n == 2) {
            clientSideView.displayMessage(
                    "Below are the lectures you are teaching this semester, please select a course to update your attendance.");
        } else if (n == 3) {
            clientSideView.displayMessage(
                    "Below are the lectures you are teaching this semester, please select a course to export students attendance info.");
        }
    }

    public void displayPromptForFacultyGetConfirm(int n) {
        if (n == 1) {
            clientSideView.displayMessage(
                    "Waiting for an record......");
        } else if (n == 2) {
            clientSideView.displayMessage(
                    "Waiting for an update......");
        }
    }

    // /**
    // * Prints the specified prompt to the user and reads the user's input.
    // *
    // * @param prompt The prompt to print to the user.
    // * @return The user's input.
    // * @throws IOException We will not handle this exception.
    // */
    // protected String printPromptAndRead(String prompt) throws IOException {
    // out.println(prompt);
    // return reader.readLine();
    // }

    // public int returnStudentCommand(String prompt) throws IOException {
    // prompt = prompt + "Currently you need to do some actions, please type the
    // number of your desired action:\n";
    // ArrayList<String> actions = new ArrayList<>();
    // actions.add("1. change Notification Preferences\n");
    // actions.add("2. get Summary Report\n");
    // for (String action : actions) {
    // prompt = prompt + action;
    // }
    // String ans = printPromptAndRead(prompt);
    // if (ans.length() == 1) {
    // if (Character.isDigit(ans.charAt(0))) {
    // int cmd = Character.getNumericValue(ans.charAt(0));
    // return cmd;
    // }
    // }
    // return returnStudentCommand("wrong input, please type the command again!\n");
    // }

    public Section displayAndChooseSection(Section[] sec) {
        for (int i = 0; i < sec.length; i++) {
            clientSideView.displayMessage(i + 1 + sec[i].getName());
        }
        int choice = 0;
        try {
            while (choice <= 0 || choice > sec.length) {
                String ans = clientSideView.promptUser("Choose the valid Section");
                choice = Integer.parseInt(ans);
            }
            return sec[choice - 1];
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public List<AttendanceRecord> getStudentsStatus(Student[] students, Lecture l) {
        List<AttendanceRecord> records = new ArrayList<>();
        try {

            for (Student s : students) {
                String ans = clientSideView
                        .promptUser("What is the attendance for " + s.getDisplayName() + "? y for Yes");
                AttendanceStatus status;
                if (ans.equals("y")) {
                    status = AttendanceStatus.PRESENT;
                } else {
                    status = AttendanceStatus.ABSENT;
                }
                records.add(new AttendanceRecord(s.getStudentID(), status, l.getLectureID()));
            }

            return records;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean isValidStringForAttendance(String input, int num) {
        String regex = "^[1-" + num + "][ATP]$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    public boolean isValidStringForRecord(String input) {
        if (input == null || input.length() != 1) {
            return false;
        }
        char ch = input.charAt(0);
        return ch == 'A' || ch == 'T' || ch == 'P';
    }

}
