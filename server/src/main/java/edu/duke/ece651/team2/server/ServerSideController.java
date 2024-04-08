package edu.duke.ece651.team2.server;

import edu.duke.ece651.team2.shared.Password;
import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;

public class ServerSideController {
    private ObjectInputStream in;
    private ObjectOutputStream out;
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

    public void handleLogin(Socket clientSocket) throws ClassNotFoundException {
        try {

            // Receive user ID and password from client
            Password receivePassword = (Password) in.readObject();

            if (receivePassword == null) {
                String[] response = new String[2];
                response[0] = "0";
                response[1] = "Invalid input!";
                String message = packageMessage(response, ":");
                out.writeObject(message);
            } else {
                int userIDNum = receivePassword.getStudentId();
                String password = receivePassword.getPassword();
                // Validate login credentials and get user type
                String[] response = validateLogin(userIDNum, password);
                String message = packageMessage(response, ":"); // Use ":" as the separator
                out.writeObject(message);
            }

            // Send login status to client
            // format

            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendConnectionStatus(Socket clientSocket) {
        try {
            in = new ObjectInputStream(clientSocket.getInputStream());
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            out.writeObject(1); // Send connection status 1 to client
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
