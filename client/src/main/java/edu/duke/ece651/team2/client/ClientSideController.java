package edu.duke.ece651.team2.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ClientSideController {

    // /**
    // * The BufferedReader used to read input from the user.
    // */
    // private final BufferedReader reader;
    // /**
    // * The PrintStream used to write output to the user.
    // */
    // private final PrintStream out;

    // /**
    // * Constructs a new TextUserController object with the specified
    // BufferedReader
    // * and PrintStream.
    // *
    // * @param reader The BufferedReader used to read input from the user.
    // * @param out The PrintStream used to write output to the user.
    // */
    // // public ClientSideController(BufferedReader reader, PrintStream out) {
    // // this.reader = reader;
    // // this.out = out;
    // // }
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
                if (choice == 1 && choice == 3 && choice == 2) {
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
                if (choice == 1 || choice == 2 || choice == 3 || choice == 4 || choice == 5) {
                    return choice;
                } else {
                    clientSideView.displayMessage("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                clientSideView.displayMessage("Invalid input. Please enter a number.");
            }
        }
    }

    public String joinEnrolledSectionPreference(List<String> names) throws IOException {
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

    public boolean isValidIntegerInRange(String str, int min, int max) {
        // Use regular expressions to check whether it is an integer
        if (!Pattern.matches("\\d+", str)) {
            return false;
        }

        int num = Integer.parseInt(str);

        return num >= min && num <= max;
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
}
