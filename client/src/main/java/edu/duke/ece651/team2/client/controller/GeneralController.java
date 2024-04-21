package edu.duke.ece651.team2.client.controller;

import edu.duke.ece651.team2.shared.*;

import java.io.*;
import java.net.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.x.protobuf.MysqlxDatatypes.Object;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class GeneralController {
    private ClientSideController clientSideController;
    private ClientSideView clientSideView;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Socket socket;
    private boolean connected = false;
    private ObjectMapper mapper = new ObjectMapper();

    public GeneralController() {
        clientSideView = new ClientSideView();
        clientSideController = new ClientSideController(clientSideView);
    }

    public String[] parseMessage(String receivedMessage, String delimiter) {
        return receivedMessage.split(delimiter);
    }
    public boolean getConnected(){
        return this.connected;
    }

    public void sendObject(int choice) throws IOException{
        out.writeObject(choice);
        out.flush();
    }

    public void sendObject(Object obj) throws IOException{
        out.writeObject(obj);
        out.flush();
    }

    public void sendObject(List<Character> obj) throws JsonProcessingException, IOException{
        out.writeObject(mapper.writeValueAsString(obj)); // send string type e.g. {'A','T','P'...}
        out.flush();
    }

    public int login(String []credentials) throws ClassNotFoundException {
        int userType = 0;

        try {
            int userID = Integer.parseInt(credentials[0]);
            String input = mapper.writeValueAsString(new Password(userID, credentials[1]));
            out.writeObject(input); // Send userID & password to server (default send Password object)
            out.flush(); // Flush the stream to ensure data is sent immediately

            // Read login result from server (By default, a string object is sent back.Click
            // to apply)
            String res = (String) in.readObject();
            String[] response = parseMessage(res, ":");
            String choice = response[0];
            String prompt = response[1];
            if (choice.equals("1")) {
            userType = 1; // student
            // clientSideView.displayMessage("Login successful.");
            } else if (choice.equals("2")) {
            userType = 2; // Faculty
            // clientSideView.displayMessage("Login successful.");
            } else {
            userType = 0;
            clientSideView.displayMessage(prompt);
            clientSideView.displayMessage("Login failed. Please try again.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userType;
    }

    // Student-specific functionality
    public void studentFunctionality(int choice) {
        try {
            if (choice == 3) {
            // exit
            out.writeObject(choice);
            out.flush();
            disconnectFromServer();
            } else if (choice == 2) {
            // get report
            out.writeObject(choice); // int type to String
            out.flush();
            // getAttendanceReport();
            } else if (choice == 1) {
            // set preferences
            out.writeObject(choice); // int type to String
            out.flush();
            // setEmailPreferences();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generateButtonFXMLPage(List<String> choices,String fileName){
        String path = "client/src/main/resources/ui/"+fileName;

    }

    public List<Section> chooseSection() throws ClassNotFoundException {
        try {
            Section[] sections = mapper.readValue((String) in.readObject(), Section[].class);
            return Arrays.asList(sections);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // private Section chooseSection() throws ClassNotFoundException {
    //     try {
    //     Section[] sections = mapper.readValue((String) in.readObject(), Section[].class);
    //     Section chosen = clientSideController.displayAndChooseSection(sections);
    //     return chosen;

    //     } catch (IOException e) {
    //     // TODO Auto-generated catch block
    //     e.printStackTrace();
    //     return null;
    //     }
    // }

    private Student[] getStudentsFromSection(Section s) throws ClassNotFoundException {
        try {
        out.writeObject(mapper.writeValueAsString(s));
        out.flush();
        Student[] students = mapper.readValue((String) in.readObject(), Student[].class);
        return students;
        } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
        return null;
        }
    }

    public void generateSectionAttendanceReport(String fileName){
        clientSideView.displayMessage("Waiting for the exported file...");
        try{
        String output = (String)in.readObject();
        System.out.println(output);
        FileWriter writer = new FileWriter("export/" + fileName + ".csv");
        writer.write(output);
        clientSideView.displayMessage("Write successfully!");
        writer.close();
        }
        catch (Exception e){
        e.printStackTrace();
        }
    }


    // private void beFaculty() throws ClassNotFoundException {
    //     Section s = chooseSection();
    //     try {
    //     out.writeObject(mapper.writeValueAsString(s));
    //     out.flush();
    //     } catch (IOException e) {
    //     // TODO Auto-generated catch block
    //     e.printStackTrace();
    //     }
    // }

    public List<String> receiveAllEnrolledSectionAndSetChoice(int num) {
        clientSideView.displayMessage("waiting");
        clientSideController.displayPromptForStudent(num);
        try {
        // get from server
        List<String> responseList = mapper.readValue((String) in.readObject(), new TypeReference<ArrayList<String>>() {
        });
        clientSideView.displayMessage(""+responseList.size());
        if (responseList.isEmpty()) {
            clientSideView.displayMessage("You haven't taken any classes this semester!");
            responseList.add("ERROR");
            responseList.add("You haven't taken any classes this semester!");
            return responseList; 
        } else {
            if (responseList.get(0).equals("ERROR")) {
                clientSideView.displayMessage(responseList.get(1));
                return responseList;
            } else {
                return responseList;
            }
        }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // private void receiveAllEnrolledSectionAndSetChoice(int num) {
    //     clientSideController.displayPromptForStudent(num);
    //     try {
    //     // get from server
    //     List<String> responseList = mapper.readValue((String) in.readObject(), new TypeReference<ArrayList<String>>() {
    //     });
    //     if (responseList.isEmpty()) {
    //         clientSideView.displayMessage("You haven't taken any classes this semester!");
    //         return;
    //     } else {
    //         if (responseList.get(0).equals("ERROR")) {
    //         clientSideView.displayMessage(responseList.get(1));
    //         return;
    //         } else {
    //         int len = responseList.size();
    //         int resNum = -1;
    //         while (true) {
    //             String choice = clientSideController.listSectionCourseName(responseList);
    //             if (clientSideController.isValidIntegerInRange(choice, 1, len)) {
    //             resNum = Integer.parseInt(choice);
    //             break;
    //             } else {
    //             clientSideView.displayMessage("Please enter a valid number!");
    //             }
    //         }
    //         out.writeObject(resNum); // send int type to String
    //         if (num == 1) {
    //             changeEmailPreferences();
    //         } else {
    //             // num == 2
    //             receiveReportResult();
    //         }
    //         }
    //     }
    //     } catch (Exception e) {
    //     e.printStackTrace();
    //     }
    // }

    // 1. set email preferences
    private void setEmailPreferences() {
        receiveAllEnrolledSectionAndSetChoice(1);
    }

    public String confirmFromServer() {
        try {
        String responseStr = mapper.readValue((String) in.readObject(), String.class);
        if (!responseStr.isEmpty()) {
            String[] parts = responseStr.split("\\|\\|");
            // String stateCode = parts[0]; // 0/1 0 - error, 1 - success
            String prompt = parts[1];
            // if (stateCode.equals("0")) {
            // clientSideView.displayMessage(prompt);
            // } else {
            // // success
            // clientSideView.displayMessage(prompt);
            // }
            return prompt;
        } else {
            return "Server failed to send a message!";
            //clientSideView.displayMessage("Server failed to send a message!");
        }
        } catch (Exception e) {
        e.printStackTrace();
        return "Some error happens";
        }
    }

    public String changeEmailPreferences() {
        clientSideView.displayMessage("Check Course Subscription Status...");
        try {
        // get from server
        String responseStr = (String)in.readObject();
        if (!responseStr.isEmpty()) {
            String[] parts = responseStr.split("\\|\\|");
            // String stateCode = parts[0]; // 0/1 0 - error, 1 - success
            String prompt = parts[1];
            return prompt;
        //     if (stateCode.equals("0")) {
        //     clientSideView.displayMessage(prompt);
        //     } else if (stateCode.equals("1")) {
        //     int resNum = -1;
        //     while (true) {
        //         String choice = clientSideView.promptUser(prompt);
        //         if (clientSideController.isValidIntegerInRange(choice, 0, 1)) {
        //         resNum = Integer.parseInt(choice);
        //         break;
        //         } else {
        //         clientSideView.displayMessage("Please enter a valid number!");
        //         }
        //     }
        //     // client asked to change status
        //     out.writeObject(resNum);
        //     out.flush();
        //     confirmFromServer();
        //     // if (resNum == 1) {
        //     //   out.writeObject(resNum);
        //     //   out.flush();
        //     //   // receive msg from server
        //     //   confirmFromServer();
        //     // }
        //     } else {
        //     clientSideView.displayMessage("Error request!");
        //     }
        // } else {
        //     clientSideView.displayMessage("Server failed to send a message!");
        }
            return "Some Error happens ,maybe you dont have a course";
        } catch (Exception e) {
            e.printStackTrace();
            return "Some Error happens";
        }
    }

    // 2. get attendance report for a course
    private void getAttendanceReport() {
        receiveAllEnrolledSectionAndSetChoice(2);
    }

    private void receiveReportResult() {
        clientSideView.displayMessage("Pending report...");
        // confirmFromServer();
    }

    private void receiveAttendanceUpdateAndRecordResult(int n) throws ClassNotFoundException {
        clientSideController.displayPromptForFacultyGetConfirm(n);
        try {
        List<String> response = mapper.readValue((String) in.readObject(), new TypeReference<List<String>>() {
        });
        if (response.get(0).equals("ERROR")) {
            // handle error (format we set response[0] = "ERROR")
            clientSideView.displayMessage(response.get(1));
        } else {
            clientSideView.displayMessage(response.get(0));
        }
        } catch (IOException e) {
        e.printStackTrace();
        }
    }

    public List<String> receiveAllStudentInfoByLectureIdForRecord() throws ClassNotFoundException {
        clientSideView.displayMessage(
            "Below are all students and their attendance status for this lecture,  please record their attendances in turn.\n");
        try {
            List<String> response = mapper.readValue((String) in.readObject(), new TypeReference<List<String>>() {
            });
            return response;
        } catch (IOException e) {
            e.printStackTrace();
            List<String> warning = new ArrayList<>();
            warning.add("ERROR");
            warning.add("Something went wrong.");
            return warning;
        }
    }

    // public void receiveAllStudentInfoByLectureIdForRecord() throws ClassNotFoundException {
    //     clientSideView.displayMessage(
    //         "Below are all students and their attendance status for this lecture,  please record their attendances in turn.\n");
    //     try {
    //     List<String> response = mapper.readValue((String) in.readObject(), new TypeReference<List<String>>() {
    //     });
    //     if (response.get(0).equals("ERROR")) {
    //         // handle error (format we set response[0] = "ERROR")
    //         clientSideView.displayMessage(response.get(1));
    //     } else {
    //         int len = response.size();
    //         // display all student
    //         clientSideController.displayAllClassAttendance(response);
    //         clientSideView.displayMessage("=============");
    //         // Ask users for input
    //         List<Character> inputAttendance = new ArrayList<>();
    //         for (int i = 0; i < len; i++) {
    //         clientSideView.displayMessage(response.get(i));
    //         String choice = "";
    //         clientSideView.displayMessage(
    //             "You can only input A,T,P (A-absent,P-present,T-tardy), you can only input it once.\n"
    //                 + "Please confirm it before entering, otherwise, please select 'update attendance' to modify it.");
    //         while (true) {
    //             if (clientSideController.isValidStringForRecord(choice)) {
    //             break;
    //             } else {
    //             choice = clientSideView.promptUser("Please enter a valid input!");
    //             //clientSideView.displayMessage("Please enter a valid input!");
    //             }
    //         }
    //         inputAttendance.add(choice.charAt(0));

    //         }
    //         out.writeObject(mapper.writeValueAsString(inputAttendance)); // send string type e.g. {'A','T','P'...}
    //         out.flush();

    //         // receive from server
    //         receiveAttendanceUpdateAndRecordResult(1);

    //     }
    //     } catch (IOException e) {
    //     e.printStackTrace();
    //     }
    // }

    private void receiveAllStudentInfoByLectureIdForUpdate() throws ClassNotFoundException {
        clientSideView.displayMessage(
            "Below are all students and their attendance status for this lecture, please select one to update your attendance.\n"
                + "Please enter a number and a letter (A-absent, T-tardy, P-present), e.g. for 1. Qianyi(1222) you can enter '1A'.");
        try {
        List<String> response = mapper.readValue((String) in.readObject(), new TypeReference<List<String>>() {
        });
        if (response.get(0).equals("ERROR")) {
            // handle error (format we set response[0] = "ERROR")
            clientSideView.displayMessage(response.get(1));
        } else {
            int len = response.size();
            // Ask users for input
            String choice = "";
            while (true) {
            choice = clientSideController.listSectionCourseName(response);
            if (clientSideController.isValidStringForAttendance(choice, len)) {
                break;
            } else {
                clientSideView.displayMessage("Please enter a valid input!");
            }
            }
            out.writeObject(mapper.writeValueAsString(choice)); // send string type e.g. (1A, 2P, 3T)
            out.flush();
            receiveAttendanceUpdateAndRecordResult(2);
        }
        } catch (IOException e) {
        e.printStackTrace();
        }
    }

    private void receiveExportFileForAttendance() throws ClassNotFoundException, IOException {
        clientSideView.displayMessage("Waiting for the exported file...");
        try {
        File receivedFile = (File) in.readObject();
        String savePath = "your/desired/path/";
        try (FileOutputStream fileOutputStream = new FileOutputStream(savePath + receivedFile.getName())) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
            fileOutputStream.write(buffer, 0, bytesRead);
            }
            clientSideView.displayMessage("File received and saved as " + savePath + receivedFile.getName());
        }
        } catch (Exception e) {
        clientSideView.displayMessage(e.getMessage());
        }

    }

    public List<String> receiveAllLectureBySectionId(int n){
        clientSideController.displayPromptForFacultyGetLectures(n);
        try {
        // get from server
        List<String> responseList = mapper.readValue((String) in.readObject(), new TypeReference<ArrayList<String>>() {
        });
        clientSideView.displayMessage(""+responseList.size());
        if (responseList.isEmpty()) {
            clientSideView.displayMessage("This Section does not have any Lecture right now, please contact Admin!");
            responseList.add("ERROR");
            responseList.add("This Section does not have any Lecture right now, please contact Admin!");
            return responseList; 
        } else {
            if (responseList.get(0).equals("ERROR")) {
                clientSideView.displayMessage(responseList.get(1));
                return responseList;
            } else {
                return responseList;
            }
        }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // private void receiveAllLectureBySectionId(int n) throws ClassNotFoundException {
    //     clientSideController.displayPromptForFacultyGetLectures(n);
    //     try {
    //     List<String> response = mapper.readValue((String) in.readObject(), new TypeReference<List<String>>() {
    //     });
    //     if (response.get(0).equals("ERROR")) {
    //         // handle error (format we set response[0] = "ERROR")
    //         clientSideView.displayMessage(response.get(1));
    //     } else {
    //         int len = response.size();
    //         int resNum = -1;
    //         // Ask users for input
    //         while (true) {
    //         String choice = clientSideController.listSectionCourseName(response);
    //         if (clientSideController.isValidIntegerInRange(choice, 1, len)) {
    //             resNum = Integer.parseInt(choice);
    //             break;
    //         } else {
    //             clientSideView.displayMessage("Please enter a valid number!");
    //         }
    //         }
    //         out.writeObject(mapper.writeValueAsString(resNum)); // send int type
    //         out.flush();
    //         if (n == 2) {
    //         // update
    //         receiveAllStudentInfoByLectureIdForUpdate();
    //         } else if (n == 1) {
    //         // record
    //         receiveAllStudentInfoByLectureIdForRecord();
    //         } 
    //         // else if (n == 3) {
    //         //   // export
    //         //   receiveExportFileForAttendance();
    //         // }

    //     }
    //     } catch (IOException e) {
    //     e.printStackTrace();
    //     }
    // }

    public List<String> receiveAllTakenSectionAndSendChoice(int num) {
        clientSideController.displayPromptForFacultyGetSections(num);
        try {
        // get from server
        List<String> responseList = mapper.readValue((String) in.readObject(), new TypeReference<ArrayList<String>>() {
        });
        clientSideView.displayMessage(""+responseList.size());
        if (responseList.isEmpty()) {
            clientSideView.displayMessage("You haven't taught any classes this semester!");
            responseList.add("ERROR");
            responseList.add("You haven't taught any classes this semester!");
            return responseList; 
        } else {
            if (responseList.get(0).equals("ERROR")) {
                clientSideView.displayMessage(responseList.get(1));
                return responseList;
            } else {
                return responseList;
            }
        }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // private void receiveAllTakenSectionAndSendChoice(int n) throws ClassNotFoundException {
    //     clientSideController.displayPromptForFacultyGetSections(n);
    //     try {
    //     List<String> response = mapper.readValue((String) in.readObject(), new TypeReference<List<String>>() {
    //     });
    //     if (response.get(0).equals("ERROR")) {
    //         // handle error (format we set response[0] = "ERROR")
    //         clientSideView.displayMessage(response.get(1));
    //     } else {
    //         int len = response.size();
    //         int resNum = -1;
    //         // Ask users for input
    //         while (true) {
    //         String choice = clientSideController.listSectionCourseName(response);
    //         if (clientSideController.isValidIntegerInRange(choice, 1, len)) {
    //             resNum = Integer.parseInt(choice);
    //             break;
    //         } else {
    //             clientSideView.displayMessage("Please enter a valid number!");
    //         }
    //         }
    //         out.writeObject(resNum); // send int type
    //         out.flush();
    //         if(n==3){
    //         generateSectionAttendanceReport(response.get(resNum));
    //         }
    //         else{
    //         receiveAllLectureBySectionId(n);
    //         }
    //     }
    //     } catch (IOException e) {
    //     e.printStackTrace();
    //     }
    // }

    // 2. update attendance
    private void updateAttendance() throws ClassNotFoundException {
        // 1. choose a section to update
        // 2. choose a lecture to update
        // 3. choose a student to update
        // 4. change his attendance status
        receiveAllTakenSectionAndSendChoice(2);

    }

    // 3. export student attendance for a lecture
    private void exportStuentAttendance() throws ClassNotFoundException {
        // 1. choose a section to export
        // 2. choose a lecture to export
        receiveAllTakenSectionAndSendChoice(3);
    }

    // 1. record attendance for a lecture
    private void recordAttendance() throws ClassNotFoundException {
        // 1. choose a section to record
        // 2. choose a lecture to record
        // 3. show all students in turn
        receiveAllTakenSectionAndSendChoice(1);
    }

    // Professor-specific functionality
    public void professorFunctionality(int choice) throws ClassNotFoundException {
        try {
            if (choice == 5) {
            out.writeObject(choice); // int type
            out.flush();
            // exit
            disconnectFromServer();
            } else if (choice == 1) {
            // record attendance
            out.writeObject(choice); // int type to String
            out.flush();
            // recordAttendance();
            } else if (choice == 2) {
            // update attendance
            out.writeObject(choice); // int type to String
            out.flush();
            // updateAttendance();
            } else if (choice == 3) {
            // export file
            out.writeObject(choice); // int type to String
            out.flush();
            // exportStuentAttendance();
            } else if (choice == 4) {
            // select courses
            out.writeObject(choice);
            out.flush();
            // beFaculty();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // public void startLogIn(String[] credentials) throws ClassNotFoundException {
    //     int userType = login(credentials);
    //     if (userType == 1) {
    //         clientSideView.displayMessage("Student login successful.");
    //         studentFunctionality();
    //         clientSideView.displayMessage("Student leaving successful.");
    //     } else if (userType == 2) {
    //         clientSideView.displayMessage("Faculty login successful.");
    //         professorFunctionality();
    //         clientSideView.displayMessage("Faculty leaving successful.");
    //     } else {
    //         clientSideView.displayMessage("Unknown user type.");
    //     }
    // }

    public void connectToServer() throws ClassNotFoundException {

        while (!connected) {
        try {
            // Connect to the server
            socket = new Socket("localhost", 8088);
            // in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // out = new PrintWriter(socket.getOutputStream(), true);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            // Read connection status from server
            int connectionStatus = (int) in.readObject(); // int type
            if (connectionStatus == 1) {
            clientSideView.displayMessage("Connected to server.");
            connected = true;
            } else {
            clientSideView.displayMessage("Failed to connect to server.");
            clientSideView.displayMessage("Trying to connect again...");
            try {
                Thread.sleep(2000); // Wait for 2 seconds before retrying
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        }
    }



    // Disconnect from the server
    public void disconnectFromServer() {
        try {
        if (socket != null && !socket.isClosed()) {
            in.close();
            out.close();
            socket.close();
            connected = false;
            clientSideView.displayMessage("Disconnected from the server.");
        }
        } catch (IOException e) {
        e.printStackTrace();
        }
    }
}
