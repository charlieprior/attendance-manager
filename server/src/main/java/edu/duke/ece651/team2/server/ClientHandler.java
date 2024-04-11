package edu.duke.ece651.team2.server;

import edu.duke.ece651.team2.shared.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ClientHandler implements Runnable {
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Socket clientSocket;
    private ServerSideView serverSideView;
    private ServerSideController serverSideController;
    private ObjectMapper mapper = new ObjectMapper();
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
            handleRecordAttendance();
        } else if (request == 2) {
            // Execute updating attendance
            handleUpdateAttendance();
        } else if (request == 3) {
            // Execute exporting student attendance information
            handleExportAttendanceInfo();
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

    private void receiveUpdateAttendanceResult(int lectureId, List<Integer> studentIds) {
        serverSideView.displayMessage("Waiting for attendance updated information....");
        try {
            String choice = mapper.readValue((String) in.readObject(), String.class);
            List<String> resList = new ArrayList<>();
            if (choice == null) {
                throw new IllegalStateException("Users send an invalid choice!");
            } else {
                int num = Character.getNumericValue(choice.charAt(0));
                char status = choice.charAt(1);
                int studentId = studentIds.get(num - 1);
                AttendanceStatus attendanceStatus;
                switch (status) {
                    case 'A':
                        attendanceStatus = AttendanceStatus.ABSENT;
                        break;
                    case 'T':
                        attendanceStatus = AttendanceStatus.TARDY;
                        break;
                    case 'P':
                        attendanceStatus = AttendanceStatus.PRESENT;
                        break;
                    default:
                        throw new IllegalStateException("Database error: can not get correct attendance status!");
                }
                AttendanceDAO attendanceDAO = new AttendanceDAO(null);
                AttendanceRecord attendanceRecord = new AttendanceRecord(studentId, attendanceStatus, lectureId);
                attendanceDAO.update(attendanceRecord);
                resList.add("Updated successfully!");
                out.writeObject(mapper.writeValueAsString(resList));
                out.flush();
            }
        } catch (Exception e) {
            try {
                List<String> errorList = new ArrayList<>();
                // send exception to client
                errorList.add("ERROR");
                errorList.add(e.getMessage());
                out.writeObject(mapper.writeValueAsString(errorList));
                out.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void receiveReocrdAttendanceResult(int lectureId, List<Integer> studentIds) {
        serverSideView.displayMessage("Waiting for attendance recorded information....");
        try {
            List<Character> response = mapper.readValue((String) in.readObject(), new TypeReference<List<Character>>() {
            });
            List<String> resList = new ArrayList<>();
            if (response == null) {
                throw new IllegalStateException("Users send an invalid choice!");
            } else {
                // confirm that response and studentIds are equal in size
                for (int i = 0; i < response.size(); i++) {
                    char status = response.get(i);
                    int studentId = studentIds.get(i);
                    AttendanceStatus attendanceStatus;
                    switch (status) {
                        case 'A':
                            attendanceStatus = AttendanceStatus.ABSENT;
                            break;
                        case 'T':
                            attendanceStatus = AttendanceStatus.TARDY;
                            break;
                        case 'P':
                            attendanceStatus = AttendanceStatus.PRESENT;
                            break;
                        default:
                            throw new IllegalStateException("Database error: can not get correct attendance status!");
                    }
                    AttendanceDAO attendanceDAO = new AttendanceDAO(null);
                    AttendanceRecord attendanceRecord = new AttendanceRecord(studentId, attendanceStatus, lectureId);
                    attendanceDAO.update(attendanceRecord);
                }
                resList.add("Recorded successfully!");
                out.writeObject(mapper.writeValueAsString(resList));
                out.flush();
            }
        } catch (Exception e) {
            try {
                List<String> errorList = new ArrayList<>();
                // send exception to client
                errorList.add("ERROR");
                errorList.add(e.getMessage());
                out.writeObject(mapper.writeValueAsString(errorList));
                out.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

    private void sendALLStudentsEnrolled(List<Integer> lectureIdList, int n) {
        serverSideView.displayMessage("Professor's choice of lecture received....");
        try {
            Integer choice = mapper.readValue((String) in.readObject(), Integer.class);
            List<String> resList = new ArrayList<>();
            if (choice == null) {
                throw new IllegalStateException("Users send an invalid choice!");
            } else {
                // get lectureId of the choice
                int lectureId = serverSideController.getLectureIdSelected(lectureIdList, choice);
                // get student list and attendance status
                StudentDAO studentDAO = new StudentDAO(null);
                Map<Student, String> resMap = studentDAO.getAttendanceByLectureId(lectureId);
                if (resMap.isEmpty()) {
                    throw new IllegalStateException("Database error: can not get student list!");
                } else {
                    List<Integer> studentIds = new ArrayList<>();
                    for (Map.Entry<Student, String> entry : resMap.entrySet()) {
                        Student student = entry.getKey();
                        String value = entry.getValue();
                        String resultString = student.getDisplayName() + " (" + student.getStudentID() + ") ——" + value;
                        resList.add(resultString);
                        studentIds.add(student.getStudentID());
                    }
                    out.writeObject(mapper.writeValueAsString(resList));
                    out.flush();

                    // receive string type e.g. (1A, 2P, 3T)
                    if (n == 2) {
                        // update
                        receiveUpdateAttendanceResult(lectureId, studentIds);
                    } else {
                        // n == 1 & record
                        receiveReocrdAttendanceResult(lectureId, studentIds);
                    }
                }
            }
        } catch (Exception e) {
            try {
                List<String> errorList = new ArrayList<>();
                // send exception to client
                errorList.add("ERROR");
                errorList.add(e.getMessage());
                out.writeObject(mapper.writeValueAsString(errorList));
                out.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void sendAttendanceFILE(int sectionId, List<Integer> lectureIdList) {
        serverSideView.displayMessage("Professor's choice of lecture received....");
        try {
            Integer choice = mapper.readValue((String) in.readObject(), Integer.class);
            if (choice == null) {
                throw new IllegalStateException("Users send an invalid choice!");
            } else {
                // get lectureId of the choice
                int lectureId = serverSideController.getLectureIdSelected(lectureIdList, choice);
                // get student list and attendance status
                List<AttendanceReport> attendanceReports = serverSideController
                        .getAttendanceReportForLecture(lectureId);
                PersistenceManager persistenceManager = new PersistenceManager();
                persistenceManager.writeRecordsToCSV(lectureId + "-attendance-report", attendanceReports);
                // send file to client
                File fileToSend = new File("export/" + lectureId + "-attendance-report" + ".csv");
                try (FileInputStream fileInputStream = new FileInputStream(fileToSend)) {

                    out.writeObject(fileToSend);
                    out.flush();
                    serverSideView.displayMessage("File sent.");
                }
            }
        } catch (Exception e) {
            try {
                List<String> errorList = new ArrayList<>();
                // send exception to client
                errorList.add("ERROR");
                errorList.add(e.getMessage());
                out.writeObject(mapper.writeValueAsString(errorList));
                out.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void sendLectureListBySectionId(Map<Integer, String> map, int n)
            throws ClassNotFoundException, IOException {
        // receive choice (int) from client
        serverSideView.displayMessage("Professor's choice of setcion received....");
        try {
            Integer choice = mapper.readValue((String) in.readObject(), Integer.class);
            List<String> resList = new ArrayList<>();
            if (choice == null) {
                throw new IllegalStateException("Users send an invalid choice!");
            } else {
                // get sectionId of the choice
                int sectionId = serverSideController.getSectionIdSelected(map.keySet(), choice);
                LectureDAO lectureDAO = new LectureDAO(null);
                List<Lecture> lectures = lectureDAO.getLecturesBySectionId(sectionId);
                if (lectures.isEmpty()) {
                    throw new IllegalStateException("Database error: can not get lectures!");
                } else {
                    List<Integer> lectureIdList = new ArrayList<>();
                    int i = 0;
                    for (Lecture lecture : lectures) {
                        lectureIdList.add(lecture.getLectureID());
                        resList.add("Lecture_" + (i + 1));
                        i++;
                    }
                    // we don't set name for each lecture.
                    out.writeObject(mapper.writeValueAsString(resList));
                    out.flush();
                    if (n == 2 || n == 1) {
                        // update & record
                        sendALLStudentsEnrolled(lectureIdList, n);
                    } else {
                        // n == 3
                        sendAttendanceFILE(sectionId, lectureIdList);
                    }
                }
            }

        } catch (Exception e) {
            try {
                List<String> errorList = new ArrayList<>();
                // send exception to client
                errorList.add("ERROR");
                errorList.add(e.getMessage());
                out.writeObject(mapper.writeValueAsString(errorList));
                out.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

    private void sendAllTeachedSectionNames(int n) {
        SectionDAO sectionDAO = new SectionDAO(null);
        try {
            Map<Integer, String> namesWithSectionId = sectionDAO.getCourseAndSectionNamesByInstructorId(userId);
            List<String> resList = new ArrayList<>();
            if (namesWithSectionId.isEmpty()) {
                throw new IllegalStateException("Failed to query database!");
            } else {
                for (Integer key : namesWithSectionId.keySet()) {
                    String value = namesWithSectionId.get(key);
                    if (!value.isEmpty()) {
                        resList.add(value);
                    } else {
                        // throw exception
                        throw new RuntimeException("Database error: can not get course and section name!");
                    }
                }
            }
            out.writeObject(mapper.writeValueAsString(resList));
            out.flush();
            sendLectureListBySectionId(namesWithSectionId, n);
        } catch (Exception e) {
            try {
                List<String> errorList = new ArrayList<>();
                // send exception to client
                errorList.add("ERROR");
                errorList.add(e.getMessage());
                out.writeObject(mapper.writeValueAsString(errorList));
                out.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

    private void handleUpdateAttendance() {
        // 1. send all course and section and get user's choice
        // 2. send all lecture and get user's choice
        // 3. send all enrolled students'info and status and get user's choice
        // 4. return updated successfully
        sendAllTeachedSectionNames(2);
    }

    private void handleRecordAttendance() {
        // 1. send all course and section and get user's choice
        // 2. send all lecture and get user's choice
        // 3. send all enrolled students'info and status and get user's all choice
        // 4. return recorded successfully
        sendAllTeachedSectionNames(1);
    }

    private void handleExportAttendanceInfo() {
        // 1. send all course and section and get user's choice
        // 2. send all lecture and get user's choice
        // 3. generate file and send file
        sendAllTeachedSectionNames(3);
    }

    // private List<Section> sendAllEnrolledSectionNames() throws IOException {
    //     EnrollmentDAO enrollmentDAO = new EnrollmentDAO(null);
    //     try {
    //         List<Enrollment> enrollments = enrollmentDAO.findEnrollmentsByStudentId(userId);
    //         List<String> errorList = new ArrayList<>();
    //         if (enrollments.isEmpty()) {
    //             throw new IllegalStateException("You are not enrolled in any classes this semester!");
    //         } else {
    //             List<String> sectionNames = new ArrayList<>();
    //             List<String> courseNames = new ArrayList<>();
    //             List<Section> sections = new ArrayList<>();
    //             for (Enrollment enrollment : enrollments) {
    //                 // get Section
    //                 SectionDAO sectionDAO = new SectionDAO(null);
    //                 Section section = sectionDAO.getBySectionId(enrollment.getSectionId());
    //                 if (section == null) {
    //                     // throw
    //                     throw new Exception("Section not found for sectionId: " + enrollment.getSectionId());
    //                 }
    //                 CourseDAO courseDAO = new CourseDAO(null);
    //                 Course course = courseDAO.getCourseByCourseId(section.getCourseId());
    //                 if (course == null) {
    //                     // throw
    //                     throw new Exception("Course not found for courseId: " + section.getCourseId());
    //                 }
    //                 sectionNames.add(section.getName());
    //                 courseNames.add(course.getName());
    //                 sections.add(section);

    //             }
    //             List<String> response = serverSideController.getCourseSectionList(sectionNames, courseNames);
    //             out.writeObject(mapper.writeValueAsString(response));
    //             out.flush();
    //             return sections;
    //         }
    //         // out.writeObject(sectionIdResult);
    //     } catch (Exception e) {
    //         List<String> errorList = new ArrayList<>();
    //         errorList.add("ERROR");
    //         try {
    //             // send exception to client
    //             errorList.add(e.getMessage());
    //             out.writeObject(errorList);
    //             out.flush();
    //         } catch (IOException ex) {
    //             ex.printStackTrace();
    //         }
    //     }
    //     return null;
    // }

    // private void receiveEmailPreferenceFromClient(int sectionId) {
    //     // get result from client
    //     try {
    //         Integer num = mapper.readValue((String) in.readObject(), Integer.class);
    //         if (num != null) {
    //             if (num == 1) {// 1-change, 0-change
    //                 EnrollmentDAO enrollmentDAO = new EnrollmentDAO(null);
    //                 // write in the db
    //                 enrollmentDAO.updateNotifyBySectionIdAndStudentId(sectionId, userId);
    //                 out.writeObject(mapper.writeValueAsString("1||" + "Update Successfully!"));
    //                 out.flush();
    //             } else {
    //                 out.writeObject(mapper.writeValueAsString("0||" + "Invalid Input from Client!"));
    //                 out.flush();
    //             }
    //         } else {
    //             out.writeObject("0||" + "Invalid Input from Client!");
    //             out.flush();
    //         }
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    // }

    // private void handleChangeEmailPreference() throws IOException {
    //     List<Section> parseSections = serverSideController.sendAllEnrolledSectionNames(userId);
    //     if (!parseSections.isEmpty()) {
    //         // get result from client
    //         try {
    //             Integer num = (Integer) in.readObject();
    //             if (num != null) {
    //                 // Default eligible

    //                 // EnrollmentDAO enrollmentDAO = new EnrollmentDAO(null);
    //                 // Integer sectionId = parseSections.get(num - 1).getSectionID();
    //                 // boolean isSubscribed = enrollmentDAO.checkNotify(sectionId, userId);
    //                 // String subscriptionStatus = isSubscribed ? "Subscribed" : "Unsubscribed";

    //                 String subscriptionStatus = serverSideController.checkEnrollmentStatus(parseSections,num,userId);
    //                 // send to client
    //                 // 1 here is valid, 0 is invalid
    //                 String sendMsg = "1||" + "The current state of the course is: " + subscriptionStatus + "\n"
    //                         + "Do you want to change it (change -1, no change -0)";
    //                 out.writeObject(mapper.writeValueAsString(sendMsg));
    //                 out.flush();
    //                 // receive msg from client
    //                 receiveEmailPreferenceFromClient(sectionId);

    //             } 
    //             // else {
    //             //     String sendMsg = "0||" + "Invalid request format (please input a number)!";
    //             //     out.writeObject(mapper.writeValueAsString(sendMsg));
    //             //     out.flush();
    //             // }
    //         } catch (Exception e) {
    //             e.printStackTrace();
    //         }
    //     } else {
    //         return;
    //     }

    // }

    private void handleGetAttendanceReport() throws IOException {
        List<Section> parseSections = serverSideController.sendAllEnrolledSectionNames(userId);
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
            serverSideController.handleChangeEmailPreference(userId);
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