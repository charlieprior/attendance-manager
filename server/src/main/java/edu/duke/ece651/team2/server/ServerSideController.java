package edu.duke.ece651.team2.server;

import edu.duke.ece651.team2.shared.AttendanceRecord;
import edu.duke.ece651.team2.shared.AttendanceReport;
import edu.duke.ece651.team2.shared.Lecture;
import edu.duke.ece651.team2.shared.Password;
import edu.duke.ece651.team2.shared.Section;
import edu.duke.ece651.team2.shared.Student;

import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ServerSideController {
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private ServerSideView serverSideView;
    ObjectMapper mapper = new ObjectMapper();
    DAOFactory factory = new DAOFactory();
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

    public ObjectInputStream getObjectInputStream() {
        return in;
    }

    public ObjectOutputStream getObjectOutputStream() {
        return out;
    }

    public String[] validateLogin(int userID, String password) {
        // implement logic to validate login credentials (check database)
        // student - 1, faculty - 2, error - 0
        String[] resultStr = new String[2];
        PasswordDAO passwordDAO = new PasswordDAO(factory);
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
            if (result.isStudent()) {
                // studentId is odd
                resultStr[0] = "1";
                resultStr[1] = "Welcome to xxx system!";
                user_id = userID;
                status = 1;
                return resultStr;
            } else {
                // facultyId is even
                resultStr[0] = "2";
                resultStr[1] = "Welcome to xxx system!";
                status = 2;
                user_id = userID;
                return resultStr;
            }
        }
    }

    public String packageMessage(String[] messageArray, String delimiter) {
        return String.join(delimiter, messageArray);
    }

    public void handleLogin(Socket clientSocket) throws ClassNotFoundException {
        try {

            // Receive user ID and password from client
            // Password receivePassword = (Password) in.readObject();
            Password receivePassword = mapper.readValue((String) in.readObject(), Password.class);

            if (receivePassword == null) {
                String[] response = new String[2];
                response[0] = "0";
                response[1] = "Invalid input!";
                String message = packageMessage(response, ":");
                out.writeObject(message);
            } else {
                int userIDNum = receivePassword.getId();
                String password = receivePassword.getPassword();
                // Validate login credentials and get user type
                String[] response = validateLogin(userIDNum, password);
                String message = packageMessage(response, ":"); // Use ":" as the separator
                out.writeObject(message);
            }

            // Send login status to client
            // format

            // in.close();
            // out.close();
            // clientSocket.close();
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

    public List<Section> getInstructSection() {
        SectionDAO sectionDAO = new SectionDAO(factory);
        return sectionDAO.list(user_id);
    }

    public List<Section> getNoFacultySection() {
        SectionDAO sectionDAO = new SectionDAO(factory);
        return sectionDAO.noInstructorSection();
    }

    public Section getChosenSection(List<Section> s) throws ClassNotFoundException {
        try {
            String json = mapper.writeValueAsString(s);
            out.writeObject(json);
            Section chosen = mapper.readValue((String) in.readObject(), Section.class);
            return chosen;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public void setFaculty(Section s) {
        SectionDAO sectionDAO = new SectionDAO(factory);
        s.setInstructorId(user_id);
        sectionDAO.update(s);
    }

    public List<String> getCourseSectionList(List<String> sectionNames, List<String> courseNames) {
        List<String> res = new ArrayList<>();
        for (int i = 0; i < sectionNames.size(); i++) {
            String sectionName = sectionNames.get(i + 1);
            String courseName = courseNames.get(i);
            String combined = sectionName + "_" + courseName;
            res.add(combined);
        }
        return res;
    }

    public int getLectureIdSelected(List<Integer> lectureIdList, int choice) {
        int lectureId = -1;
        int count = 0;
        for (Integer id : lectureIdList) {
            if (count == choice - 1) {
                lectureId = id;
            }
            count++;
        }

        return lectureId;
    }

    public int getSectionIdSelected(Set<Integer> mapset, int choice) {
        int count = 0;
        int sectionId = -1;
        for (Integer key : mapset) {
            if (count == choice - 1) {
                sectionId = key;
            }
            count++;
        }
        return sectionId;
    }

    public List<AttendanceReport> getAttendanceReportForLecture(int lectureId) {
        StudentDAO studentDAO = new StudentDAO(null);
        Map<Student, String> resMap = studentDAO.getAttendanceByLectureId(lectureId);
        if (resMap == null) {
            throw new IllegalStateException("No student data found for lecture with ID: " + lectureId);
        }
        LectureDAO lectureDAO = new LectureDAO(null);
        Lecture lecture = lectureDAO.get(lectureId);
        if (lecture == null) {
            throw new IllegalArgumentException("Lecture with ID " + lectureId + " not found.");
        }

        int year = lecture.getYear();
        int month = lecture.getMonth();
        int day = lecture.getDay();

        String dateStr = String.format("%04d-%02d-%02d", year, month, day);

        AttendanceDAO attendanceDAO = new AttendanceDAO(null);
        List<AttendanceRecord> attendanceRecords = attendanceDAO.getAllAttendancesForLecture(lectureId);
        if (attendanceRecords.isEmpty()) {

            throw new IllegalStateException("No attendance records found for lecture with ID: " + lectureId);
        }
        if (attendanceRecords.size() != resMap.size()) {

            throw new IllegalStateException(
                    "Mismatch between attendance data and records for lecture with ID: " + lectureId);
        }
        List<AttendanceReport> attendanceReports = new ArrayList<>();
        for (Student student : resMap.keySet()) {
            int studentId = student.getStudentID();
            for (AttendanceRecord attendanceRecord : attendanceRecords) {
                int studentId2 = attendanceRecord.getStudentId();

                if (studentId == studentId2) {
                    attendanceReports.add(new AttendanceReport(attendanceRecord, student.getLegalName(), dateStr));
                }
            }
        }
        if (attendanceReports.isEmpty()) {
            throw new IllegalStateException(
                    "Failed to generate report for lecture with ID: " + lectureId);
        }
        return attendanceReports;
    }
}
