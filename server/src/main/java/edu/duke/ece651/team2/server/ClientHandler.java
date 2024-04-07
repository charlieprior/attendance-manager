package edu.duke.ece651.team2.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private View view;
    private Controller controller;

    public ClientHandler(Socket clientSocket, View view, Controller controller) {
        this.clientSocket = clientSocket;
        this.view = view;
        this.controller = controller;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            // Receive client request and process it
            String request;
            while ((request = in.readLine()) != null) {
                // Process client request using controller
                String response = controller.processRequest(request);

                // Send response back to client
                out.println(response);
            }

            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}