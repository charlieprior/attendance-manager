package edu.duke.ece651.team2.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private ServerSideView serverSideView;
    private ServerSideController serverSideController;
    private int userId;
    private int status; // whether the user is professor of student; // student - 1, faculty - 2, error
                        // - 0

    public ClientHandler(Socket clientSocket, ServerSideView serverSideView,
            ServerSideController serverSideController) {
        this.clientSocket = clientSocket;
        this.serverSideView = serverSideView;
        this.serverSideController = serverSideController;
        this.userId = serverSideController.getUserId();
        this.status = serverSideController.getStatus();
    }

    private void handleFacultyRequest(String request) {
        if (request.equals("1")) {
            // Execute recording attendance
        } else if (request.equals("2")) {
            // Execute updating attendance
        } else if (request.equals("3")) {
            // Execute exporting student attendance information
        } else if (request.equals("4")) {
            // Handle selecting course to teach
        } else {

        }
    }

    private String handleStudentRequest(String request) {
        String res = "";
        if (request.equals("1")) {
            // Execute setting email preferences
        } else if (request.equals("2")) {
            // Execute getting attendance report
        } else {
            res = "Invalid request!";
        }
        return res;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            // Receive client request and process it
            String request;
            while ((request = in.readLine()) != null) {
                // User authentication failed
                if (userId == -1 || status == -1) {
                    String response = "User authentication failed!";
                    out.println(response); // Send response back to client
                    break;
                }

                // Process client request using controller
                if (request.isEmpty()) {
                    // If it's empty, continue waiting for user input
                    continue;
                }
                if (status == 1) {
                    // stuent
                    String response = handleStudentRequest(request);
                } else if (status == 2) {
                    // faculty
                    handleFacultyRequest(request);

                } else {
                    String response = "The request is invalid!";
                    out.println(response);
                }

            }

            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}