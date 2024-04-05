package edu.duke.ece651.team2.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

public class ClientSideController {
    
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
    public ClientSideController(BufferedReader reader, PrintStream out) {
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

    public int returnStudentCommand(String prompt) throws IOException{
        prompt = prompt + "Currently you need to do some actions, please type the number of your desired action:\n";
        ArrayList<String> actions = new ArrayList<>();
        actions.add("1. change Notification Preferences\n");
        actions.add("2. get Summary Report\n");
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
        return returnStudentCommand("wrong input, please type the command again!\n");
    }
}
