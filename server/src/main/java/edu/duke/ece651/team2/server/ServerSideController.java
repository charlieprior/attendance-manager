package edu.duke.ece651.team2.server;

import edu.duke.ece651.team2.shared.Password;
import java.net.Socket;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ServerSideController {
    private ServerSideView serverSideView;
    private int user_id;
    private int status; // whether the user is professor of student; // student - 1, faculty - 2, error
                        // - 0

    public ServerSideController(ServerSideView serverSideView) {
        this.serverSideView = serverSideView;
        user_id = -1;
        status = -1;
    }

    public int getUserId() {
        return user_id;
    }

    public int getStatus() {
        return status;
    }

    public String[] validateLogin(int userID, String password) {
        // implement logic to validate login credentials (check database)
        // student - 1, faculty - 2, error - 0
        String[] resultStr = new String[2];
        PasswordDAO passwordDAO = new PasswordDAO(null);
        Password result = passwordDAO.get(userID);

        // id not found
        if (result == null) {
            resultStr[0] = "0";
            resultStr[1] = "UserId not found!";
            return resultStr;
        }
        // wrong password
        if (!result.getPassword().equals(password)) {
            resultStr[0] = "0";
            resultStr[1] = "Wrong password!";
            return resultStr;
        } else {
            // pass verification
            if (userID % 2 == 1) {
                // studentId is odd
                resultStr[0] = "1";
                resultStr[1] = "Welcome to xxx system!";
                user_id = userID;
                status = 1;
                return resultStr;
            } else if (userID % 2 == 0) {
                // facultyId is even
                resultStr[0] = "2";
                resultStr[1] = "Welcome to xxx system!";
                status = 2;
                user_id = userID;
                return resultStr;
            }
        }
        return resultStr;
    }

    public String packageMessage(String[] messageArray, String delimiter) {
        return String.join(delimiter, messageArray);
    }

    public void handleLogin(Socket clientSocket) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            // Receive user ID and password from client
            String userID = in.readLine();
            String password = in.readLine();

            int userIDNum = Integer.parseInt(userID);

            // Validate login credentials and get user type
            String[] response = validateLogin(userIDNum, password);
            String message = packageMessage(response, ":"); // Use ":" as the separator

            // Send login status to client
            out.println(message);

            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendConnectionStatus(Socket clientSocket) {
        try {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            out.println("1"); // Send connection status "1" to client
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
