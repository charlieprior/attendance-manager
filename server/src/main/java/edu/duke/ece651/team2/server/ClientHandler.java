package edu.duke.ece651.team2.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private ObjectInputStream in;
    private ObjectOutputStream out;
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

    private String handleFacultyRequest(Integer request) {
        String response = "";
        if (request==1) {
            // Execute recording attendance
        } else if (request==2) {
            // Execute updating attendance
        } else if (request==3) {
            // Execute exporting student attendance information
        } else if (request==4) {
            // Handle selecting course to teach
        } else {

        }
        return response;
    }

    private String handleStudentRequest(Integer request) {
        String res = "";
        if (request==1) {
            // Execute setting email preferences
        } else if (request==2) {
            // Execute getting attendance report
        } else if (request==3){
            return "break";
        } else {
            res = "Invalid request!";
        }
        return res;
    }

    @Override
    public void run() {
        try {
            // in = new ObjectInputStream(clientSocket.getInputStream());
            // out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = serverSideController.getObjectInputStream();
            out = serverSideController.getObjectOutputStream();

            // Receive client request and process it
            while (true) {
                // User authentication fail
                int request = (int) in.readObject();
                System.out.println(request);
                // if (userId == -1 || status == -1) {
                //     String response = "User authentication failed!";
                //     out.writeObject(response); // Send response back to client
                //     break;
                // }

                // Process client request using controller
                if (status == 1) {
                    // stuent
                    String response = handleStudentRequest((int)request);
                    if(response.equals("break")){
                        System.out.println("The student is leaving.");
                        break;
                    }
                        
                } else {
                    // faculty
                    // handleFacultyRequest(request);
                    String response = handleFacultyRequest((int)request);
                    if(response.equals("break")){
                        System.out.println("The faculty is leaving.");
                        break;
                    }

                    }
                }

            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}