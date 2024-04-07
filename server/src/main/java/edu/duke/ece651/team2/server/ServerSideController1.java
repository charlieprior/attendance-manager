package edu.duke.ece651.team2.server;

import java.net.Socket;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ServerSideController {
    private ServerSideView serverSideView;
    private int userID;

    public ServerSideController(ServerSideView serverSideView) {
        this.serverSideView = serverSideView;
        userID = -1;
    }

    public int validateLogin(int userID, int password) {
        // implement logic to validate login credentials (check database)

        
        int type = 0;
        if (type == 1) {
            this.userID = userID;
            return 1;
        } else if (type == 2) {
            this.userID = userID;
            return 2;
        }
        // student - 1, faculty - 2, error - 0
        // if (userID.equals("student")) {
        // this.userID = userID;
        // return 1; // Student login successful
        // } else if (userID.equals("faculty")) {
        // return 2; // Faculty login successful
        // } else {
        // return 0; // Login failed
        // }
        return 0;
    }

    public void handleLogin(Socket clientSocket) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            // Receive user ID and password from client
            String userID = in.readLine();
            String password = in.readLine();

            int userIDNum = Integer.parseInt(userID);
            int passwordNum = Integer.parseInt(password);

            // Validate login credentials and get user type
            int userType = validateLogin(userIDNum, passwordNum);

            // Send login status to client
            out.println(userType);

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
