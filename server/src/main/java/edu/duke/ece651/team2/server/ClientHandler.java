package edu.duke.ece651.team2.server;

import edu.duke.ece651.team2.shared.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.ArrayList;

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

    public void takeAttendance() throws ClassNotFoundException {
        List<Section> sections = serverSideController.getInstructSection();
        Section s_chosen = serverSideController.getChosenSection(sections);
        // To get List of students
        // TODO
    }

    public void beFaculty() throws ClassNotFoundException {
        List<Section> sections = serverSideController.getNoFacultySection();
        Section s_chosen = serverSideController.getChosenSection(sections);
        serverSideController.setFaculty(s_chosen);
    }

    private String handleFacultyRequest(Integer request) throws ClassNotFoundException {
        System.out.println("you are doing:" + request);
        String res = "";
        if (request == 1) {
            // Execute recording attendance
        } else if (request == 2) {
            // Execute updating attendance
        } else if (request == 3) {
            // Execute exporting student attendance information
        } else if (request == 4) {
            // Handle selecting course to teach
            beFaculty();
        } else if (request == 5) {
            return "break";
        } else {
            res = "Invalid request!";
        }
        return res;
    }

    private List<Section> sendAllEnrolledSectionNames() throws IOException {
        EnrollmentDAO enrollmentDAO = new EnrollmentDAO(null);
        try {
            List<Enrollment> enrollments = enrollmentDAO.findEnrollmentsByStudentId(userId);
            if (enrollments.isEmpty()) {
                out.writeObject("You are not enrolled in any classes this semester!");
            } else {
                List<String> sectionNames = new ArrayList<>();
                List<String> courseNames = new ArrayList<>();
                List<Section> sections = new ArrayList<>();
                for (Enrollment enrollment : enrollments) {
                    // get Section
                    SectionDAO sectionDAO = new SectionDAO(null);
                    Section section = sectionDAO.getBySectionId(enrollment.getSectionId());
                    if (section == null) {
                        // throw
                        throw new Exception("Section not found for sectionId: " + enrollment.getSectionId());
                        // continue;
                    }
                    CourseDAO courseDAO = new CourseDAO(null);
                    Course course = courseDAO.getCourseByCourseId(section.getCourseId());
                    if (course == null) {
                        // throw
                        throw new Exception("Course not found for courseId: " + section.getCourseId());
                        // continue;
                    }
                    sectionNames.add(section.getName());
                    courseNames.add(course.getName());
                    sections.add(section);

                }
                List<String> response = serverSideController.getCourseSectionList(sectionNames, courseNames);
                out.writeObject(response);
                return sections;
            }
            // out.writeObject(sectionIdResult);
            return null;
        } catch (Exception e) {
            try {
                // send exception to client
                out.writeObject(e.getMessage());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    private void receiveEmailPreferenceFromClient(int sectionId) {
        // get result from client
        try {
            Object response = in.readObject();
            if (response instanceof Integer) {
                int num = (Integer) response; // 1-change, 0-change
                if (num == 1) {
                    EnrollmentDAO enrollmentDAO = new EnrollmentDAO(null);
                    // write in the db
                    enrollmentDAO.updateNotifyBySectionIdAndStudentId(sectionId, userId);
                    out.writeObject("1||" + "Update Successfully!");
                } else {
                    out.writeObject("0||" + "Invalid Input from Client!");
                }
            } else {
                out.writeObject("0||" + "Invalid Input from Client!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleChangeEmailPreference() throws IOException {
        List<Section> parseSections = sendAllEnrolledSectionNames();
        if (!parseSections.isEmpty()) {
            // get result from client
            try {
                Object response = in.readObject();
                if (response instanceof Integer) {
                    int num = (Integer) response;
                    // Default eligible
                    EnrollmentDAO enrollmentDAO = new EnrollmentDAO(null);
                    Integer sectionId = parseSections.get(num - 1).getSectionID();
                    boolean isSubscribed = enrollmentDAO.checkNotify(sectionId, userId);
                    String subscriptionStatus = isSubscribed ? "Subscribed" : "Unsubscribed";
                    // send to client
                    out.writeObject("1||" + "The current state of the course is: " + subscriptionStatus + "\n"
                            + "Do you want to change it (change -1, no change -0)");

                    // receive msg from client
                    receiveEmailPreferenceFromClient(sectionId);

                } else {
                    out.writeObject("0||" + "Invalid request format (please input a number)!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void handleGetAttendanceReport() throws IOException {
        List<Section> parseSections = sendAllEnrolledSectionNames();
        if (!parseSections.isEmpty()) {
            // get result from client (choice)
            try {
                Object response = in.readObject();
                if (response instanceof Integer) {
                    int num = (Integer) response;
                    // assume num is eligible
                    // sending report function
                    // ...

                    // finished sending
                } else {
                    out.writeObject("0||" + "Invalid request format (please input a number)!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String handleStudentRequest(Integer request) throws IOException, ClassNotFoundException {
        String res = "";
        if (request == 1) {
            // Execute setting email preferences
            handleChangeEmailPreference();

        } else if (request == 2) {
            // Execute getting attendance report
            handleGetAttendanceReport();
        } else if (request == 3) {
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
                // if (userId == -1 || status == -1) {
                // String response = "User authentication failed!";
                // out.writeObject(response); // Send response back to client
                // break;
                // }

                // Process client request using controller
                if (status == 1) {
                    // stuent
                    String response = handleStudentRequest((int) request);
                    if (response.equals("break")) {
                        System.out.println("The student is leaving.");
                        break;
                    }

                } else {
                    // faculty
                    // handleFacultyRequest(request);
                    String response = handleFacultyRequest((int) request);
                    if (response.equals("break")) {
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