package edu.duke.ece651.team2.server;

import edu.duke.ece651.team2.shared.AttendanceRecord;
import edu.duke.ece651.team2.shared.AttendanceReport;
import edu.duke.ece651.team2.shared.AttendanceStatus;
import edu.duke.ece651.team2.shared.Course;
import edu.duke.ece651.team2.shared.Enrollment;
import edu.duke.ece651.team2.shared.GmailSetup;
import edu.duke.ece651.team2.shared.Lecture;
import edu.duke.ece651.team2.shared.Password;
import edu.duke.ece651.team2.shared.PersistenceManager;
import edu.duke.ece651.team2.shared.Section;
import edu.duke.ece651.team2.shared.Student;

import java.net.Socket;
import java.security.GeneralSecurityException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


import java.io.IOException;
import java.io.InputStream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.services.gmail.Gmail;
import com.google.protobuf.compiler.PluginProtos.CodeGeneratorResponse.File;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class ServerSideController {
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private ServerSideView serverSideView;
    ObjectMapper mapper = new ObjectMapper();
    DAOFactory factory = new DAOFactory();
    private int user_id;
    private int status; // whether the user is professor of student; // student - 1, faculty - 2, error
                        // - 0
    private Integer universityID;
    private GmailSetup gmailSetup;
    private PasswordDAO passwordDAO = new PasswordDAO(factory);
    private StudentDAO studentDAO = new StudentDAO(factory);
    private CourseDAO courseDAO = new CourseDAO(factory);
    private SectionDAO sectionDAO = new SectionDAO(factory);
    private LectureDAO lectureDAO = new LectureDAO(factory);
    private ProfessorDAO professorDAO = new ProfessorDAO(factory);
    private EnrollmentDAO enrollmentDAO = new EnrollmentDAO(factory);
    private AttendanceDAO attendanceDAO = new AttendanceDAO(factory);

    public void setPasswordDAO(PasswordDAO passwordDAO) {
        this.passwordDAO = passwordDAO;
    }

    public void setStudentDAO(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    public void setCourseDAO(CourseDAO courseDAO) {
        this.courseDAO = courseDAO;
    }

    public void setSectionDAO(SectionDAO sectionDAO) {
        this.sectionDAO = sectionDAO;
    }

    public void setLectureDAO(LectureDAO lectureDAO) {
        this.lectureDAO = lectureDAO;
    }

    public void setProfessorDAO(ProfessorDAO professorDAO) {
        this.professorDAO = professorDAO;
    }

    public void setEnrollmentDAO(EnrollmentDAO enrollmentDAO) {
        this.enrollmentDAO = enrollmentDAO;
    }

    public void setAttendanceDAO(AttendanceDAO attendanceDAO) {
        this.attendanceDAO = attendanceDAO;
    }

    public void setGmailSetup(GmailSetup gmailSetup){
        this.gmailSetup = gmailSetup;
    }

    public ServerSideController(ServerSideView serverSideView) throws IOException, GeneralSecurityException {
        this.serverSideView = serverSideView;
        user_id = -1;
        status = -1;
        gmailSetup = new GmailSetup();
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

    public void setObjectInputStream(ObjectInputStream in){
        this.in = in;
    }

    public ObjectOutputStream getObjectOutputStream() {
        return out;
    }

    public void setObjectOutputStream(ObjectOutputStream out){
        this.out = out;
    }

    public void setMapper(ObjectMapper mapper){
        this.mapper = mapper;
    }

    public String[] validateLogin(int userID, String password) {
        // implement logic to validate login credentials (check database)
        // student - 1, faculty - 2, error - 0
        String[] resultStr = new String[2];
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
                universityID = studentDAO.getUniversityID(user_id);
                return resultStr;
            } else {
                // facultyId is even
                resultStr[0] = "2";
                resultStr[1] = "Welcome to xxx system!";
                status = 2;
                user_id = userID;
                universityID = professorDAO.getUniversityID(user_id);
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

    public List<String> getCourseSectionList(List<String> sectionNames, List<String> courseNames) {
        List<String> res = new ArrayList<>();
        for (int i = 0; i < sectionNames.size(); i++) {
            String sectionName = sectionNames.get(i);
            String courseName = courseNames.get(i);
            String combined =  courseName+ " :"+sectionName;
            res.add(combined);
        }
        return res;
    }

    public List<Section> sendAllEnrolledSectionNames(Integer userId) throws IOException {
        try {
            List<Enrollment> enrollments = enrollmentDAO.findEnrollmentsByStudentId(userId);
            if (enrollments==null) {
                throw new IllegalStateException("You are not enrolled in any classes this semester!");
            } else {
                List<String> sectionNames = new ArrayList<>();
                List<String> courseNames = new ArrayList<>();
                List<Section> sections = new ArrayList<>();
                for (Enrollment enrollment : enrollments) {
                    Section section = sectionDAO.getBySectionId(enrollment.getSectionId());
                    // if (section == null) {
                    //     // throw
                    //     throw new Exception("Section not found for sectionId: " + enrollment.getSectionId());
                    // }
                    Course course = courseDAO.getCourseByCourseId(section.getCourseId());
                    // if (course == null) {
                    //     // throw
                    //     throw new Exception("Course not found for courseId: " + section.getCourseId());
                    // }
                    sectionNames.add(section.getName());
                    courseNames.add(course.getName());
                    sections.add(section);
                }
                List<String> response = getCourseSectionList(sectionNames, courseNames);
                System.out.println("Going to send!!");
                out.writeObject(mapper.writeValueAsString(response));
                out.flush();
                return sections;
            }
            // out.writeObject(sectionIdResult);
        } catch (Exception e) {
            List<String> errorList = new ArrayList<>();
            errorList.add("ERROR");
            try {
                // send exception to client
                errorList.add(e.getMessage());
                out.writeObject(errorList);
                out.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return null;
        }
    }

    public String checkEnrollmentStatus(List<Section> parseSections,int num,int userId){
        Integer sectionId = parseSections.get(num - 1).getSectionID();
        boolean isSubscribed = enrollmentDAO.checkNotify(sectionId, userId);
        String subscriptionStatus = isSubscribed ? "Subscribed" : "Unsubscribed";
        return subscriptionStatus;
    }

    public void receiveEmailPreferenceFromClient(int sectionId,int userId) {
        // get result from client
        try {
            Integer num = (Integer) in.readObject();
            if (num != null) {
                if (num == 1) {// 1-change, 0-change
                    // write in the db
                    enrollmentDAO.updateNotifyBySectionIdAndStudentId(sectionId, userId);
                    out.writeObject(mapper.writeValueAsString("1||" + "Update Successfully!"));
                    out.flush();
                } 
                else {
                    out.writeObject(mapper.writeValueAsString("0||" + "No update."));
                    out.flush();
                }
            } else {
                out.writeObject("0||" + "Invalid Input from Client!");
                out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleChangeEmailPreference(int userId) throws IOException {
        List<Section> parseSections = sendAllEnrolledSectionNames(userId);
        if (parseSections!=null) {
            // get result from client
            try {
                Integer num = (Integer) in.readObject();
                if (num != -1) {
                    // Default eligible
                    Integer sectionId = parseSections.get(num).getSectionID();
                    boolean isSubscribed = enrollmentDAO.checkNotify(sectionId, userId);
                    String subscriptionStatus = isSubscribed ? "Subscribed" : "Unsubscribed";
                    // send to client
                    // 1 here is valid, 0 is invalid
                    String sendMsg = "1||" + "The current state of the course is: " + subscriptionStatus
                            + "\nDo you want to change it (change :1, no change :0)";
                    out.writeObject(sendMsg);
                    out.flush();
                    // receive msg from client
                    receiveEmailPreferenceFromClient(sectionId,userId);

                } 
                // else {
                //     String sendMsg = "0||" + "Invalid request format (please input a number)!";
                //     out.writeObject(mapper.writeValueAsString(sendMsg));
                //     out.flush();
                // }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return;
        }

    }

    public List<Section> getInstructSection() {
        return sectionDAO.list(user_id);
    }

    public List<Section> getNoFacultySection() {
        List<Course> courses = courseDAO.listByUniversity(universityID);
        List<Section> ans = new ArrayList<>();
        for(Course c:courses){
            ans.addAll(sectionDAO.noInstructorSection(c.getCourseID()));
        }
        return ans;
    }

    public int getChosenSection(List<Section> s) throws ClassNotFoundException {
        try {
            String json = mapper.writeValueAsString(s);
            out.writeObject(json);
            int chosen = (int) in.readObject();
            return chosen;
            // Section chosen = mapper.readValue((String) in.readObject(), Section.class);
            // return chosen;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
            // return null;
        }
    }

    public void setFaculty(Section s) {
        s.setInstructorId(user_id);
        sectionDAO.update(s);
    }

    public void receiveUpdateAttendanceResult(int lectureId, List<Integer> studentIds) {
        serverSideView.displayMessage("Waiting for attendance updated information....");
        try {
            List<Integer> response = mapper.readValue((String) in.readObject(), new TypeReference<List<Integer>>() {
            });
            int num = response.get(0);
            if(num==-1){
                return;
            }
            int status = response.get(1);
            int studentId = studentIds.get(num );
            AttendanceStatus attendanceStatus = AttendanceStatus.UNRECORDED;
            switch (status) {
                case 1:
                    attendanceStatus = AttendanceStatus.PRESENT;
                    break;
                case 2:
                    attendanceStatus = AttendanceStatus.TARDY;
                    break;
                case 3:
                    attendanceStatus = AttendanceStatus.ABSENT;
                    break;
                }
                AttendanceRecord attendanceRecord = new AttendanceRecord(studentId, attendanceStatus, lectureId);
                attendanceDAO.update(attendanceRecord);
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

    public void receiveReocrdAttendanceResult(int lectureId, List<Integer> studentIds) {
        serverSideView.displayMessage("Waiting for attendance recorded information....");
        try {
            List<Character> response = mapper.readValue((String) in.readObject(), new TypeReference<List<Character>>() {
            });
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
                    AttendanceRecord attendanceRecord = new AttendanceRecord(studentId, attendanceStatus, lectureId);
                    if(attendanceDAO.get(lectureId, studentId)==null){
                        attendanceDAO.create(attendanceRecord);
                    }
                    else{
                        attendanceDAO.update(attendanceRecord);
                    }
                }
            }
        } catch (Exception e) {
            try {
                List<String> errorList = new ArrayList<>();
                // send exception to client
                System.out.println("what happens: "+e.getMessage());
                errorList.add("ERROR");
                errorList.add(e.getMessage());
                out.writeObject(mapper.writeValueAsString(errorList));
                out.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }


    public void sendALLStudentsEnrolled(List<Integer> lectureIdList, int sectionId, int n) {
        serverSideView.displayMessage("Professor's choice of lecture received....");
        try {
            // Integer choice = mapper.readValue((String) in.readObject(), Integer.class);
            int choice = (int) in.readObject();
            List<String> resList = new ArrayList<>();
            if (choice == -1) {
                return;
            } else {
                // get lectureId of the choice
                int lectureId = getLectureIdSelected(lectureIdList, choice);
                // get student list and attendance status
                Map<Student, String> resMap = studentDAO.getAttendanceByLectureId(sectionId,lectureId);
                // if(n==2){
                //     resMap = studentDAO.getAttendanceByLectureId(lectureId);
                // }
                // else{
                //     resMap = studentDAO.getStudentsBySectionID(sectionId);
                // }
                if (resMap.isEmpty()) {
                    throw new IllegalStateException("Database error: can not get student list!");
                } else {
                    List<Integer> studentIds = new ArrayList<>();
                    for (Map.Entry<Student, String> entry : resMap.entrySet()) {
                        Student student = entry.getKey();
                        String value = entry.getValue();
                        String resultString = student.getDisplayName() + " (" + student.getStudentID() + ") Status:" + value+"\n";
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

    public void writeRecordsToCSV(HashMap<Integer,List<AttendanceReport>> reports){
        StringBuffer whole = new StringBuffer();
        String header = "Student ID,Student Name,Date,Status,Score";
        whole.append(header+"\n");
        for(Integer id:reports.keySet()){
            for(AttendanceReport report:reports.get(id)){
                whole.append(id.toString()+",");
                whole.append(report.getStudentLegalName()+",");
                whole.append(report.getAttendanceDate()+",");
                whole.append(report.getRecord().getStatus()+",");
                whole.append(report.getScore());
                whole.append("\n");
            }
            whole.append("\n");
        }
        try {
            out.writeObject(whole.toString());
            out.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void sendAttendanceRecord(Map<Integer, String> map) {
        serverSideView.displayMessage("Professor's choice of setcion received....");
        try {
            Integer choice = (int) in.readObject();
            if(choice==-1){
                return;
            }
            // get sectionId of the choice
            int sectionId = getSectionIdSelected(map.keySet(), choice);
            Map<Student, String> students = studentDAO.getStudentsBySectionID(sectionId);
            HashSet<Integer> studentID = new HashSet<>();
            for(Student s:students.keySet()){
                studentID.add(s.getStudentID());
            }
            List<Lecture> lectures = lectureDAO.getLecturesBySectionId(sectionId);
            if (lectures.isEmpty()) {
                throw new IllegalStateException("Database error: can not get lectures!");
            }
            HashMap<Integer,List<AttendanceReport>> wholeReport = new HashMap<>();
            for(Lecture l:lectures){
                HashMap<Integer,AttendanceReport> attendanceReports = getAttendanceReportForLecture(l.getLectureID(),studentID);
                for(Integer id:attendanceReports.keySet()){
                    List<AttendanceReport> list = wholeReport.getOrDefault(id, new ArrayList<>());
                    list.add(attendanceReports.get(id));
                    wholeReport.put(id,list);
                }
            }
            writeRecordsToCSV(wholeReport);
            // out.writeObject(mapper.writeValueAsString(reports));
            // out.flush();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendLectureListBySectionId(Map<Integer, String> map, int n) throws ClassNotFoundException, IOException {
        // receive choice (int) from client
        serverSideView.displayMessage("Professor's choice of setcion received....");
        try {
            Integer choice = (int) in.readObject();
            if(choice==-1){
                return;
            }
            List<String> resList = new ArrayList<>();
            // get sectionId of the choice
            int sectionId = getSectionIdSelected(map.keySet(), choice);
            List<Lecture> lectures = lectureDAO.getLecturesBySectionId(sectionId);
            if (lectures.isEmpty()) {
                throw new IllegalStateException("Database error: can not get lectures!");
            } else {
                List<Integer> lectureIdList = new ArrayList<>();
                int i = 0;
                for (Lecture lecture : lectures) {
                    lectureIdList.add(lecture.getLectureID());
                    resList.add("Lecture_" + (i + 1)+" Date: "+lecture.getYear()+"-"+lecture.getMonth()+"-"+lecture.getDay());
                    i++;
                }
                // we don't set name for each lecture.
                out.writeObject(mapper.writeValueAsString(resList));
                out.flush();
                sendALLStudentsEnrolled(lectureIdList, sectionId,n);
                // if (n == 2 || n == 1) {
                //     // update & record
                //     sendALLStudentsEnrolled(lectureIdList, sectionId,n);
                // } else {
                //     // n == 3
                //     sendAttendanceFILE(sectionId, lectureIdList);
                // }
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

    public void sendAllTeachedSectionNames(int n,int userId) {
        SectionDAO sectionDAO = new SectionDAO(factory);
        try {
            Map<Integer, String> namesWithSectionId = sectionDAO.getCourseAndSectionNamesByInstructorId(userId);
            List<String> resList = new ArrayList<>();
            if (namesWithSectionId==null) {
                throw new IllegalStateException("Failed to query database, or you dont have any Course!");
            } else {
                for (Integer key : namesWithSectionId.keySet()) {
                    String value = namesWithSectionId.get(key);
                    resList.add(value);
                    // if (!value.isEmpty()) {
                    //     resList.add(value);
                    // } else {
                    //     // throw exception
                    //     throw new RuntimeException("Database error: can not get course and section name!");
                    // }
                }
            }
            out.writeObject(mapper.writeValueAsString(resList));
            out.flush();
            if(n==1||n==2){
                sendLectureListBySectionId(namesWithSectionId, n);
            }
            else{
                sendAttendanceRecord(namesWithSectionId);
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

    public int getLectureIdSelected(List<Integer> lectureIdList, int choice) {
        int lectureId = -1;
        int count = 0;
        for (Integer id : lectureIdList) {
            if (count == choice) {
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
            if (count == choice) {
                sectionId = key;
            }
            count++;
        }
        return sectionId;
    }

    public HashMap<Integer,AttendanceReport> getAttendanceReportForLecture(int lectureId,HashSet<Integer> students) {
        // Map<Student, String> resMap = studentDAO.getAttendanceByLectureId(lectureId);
        // if (resMap == null) {
        //     //throw new IllegalStateException("No student data found for lecture with ID: " + lectureId);
        //     return null;
        // }
        Lecture lecture = lectureDAO.get(lectureId);
        // if (lecture == null) {
        //     throw new IllegalArgumentException("Lecture with ID " + lectureId + " not found.");
        // }
        int year = lecture.getYear();
        int month = lecture.getMonth();
        int day = lecture.getDay();

        String dateStr = String.format("%04d-%02d-%02d", year, month, day);

        List<AttendanceRecord> attendanceRecords = attendanceDAO.getAllAttendancesForLecture(lectureId);
        // if (attendanceRecords.isEmpty()) {
        //     // throw new IllegalStateException("No attendance records found for lecture with ID: " + lectureId);
        //     return null;
        // }
        // if (attendanceRecords.size() != resMap.size()) {

        //     throw new IllegalStateException(
        //             "Mismatch between attendance data and records for lecture with ID: " + lectureId);
        // }

        HashMap<Integer,AttendanceReport> attendanceReports = new HashMap<>();
        for (AttendanceRecord attendanceRecord : attendanceRecords) {
            if(!students.contains(attendanceRecord.getStudentId())){
                continue;//removed the student before
            }
            String legal_name = studentDAO.get(attendanceRecord.getStudentId()).getLegalName();
            attendanceReports.put(attendanceRecord.getStudentId(),new AttendanceReport(attendanceRecord, legal_name, dateStr));
        }
        for(Integer id:students){
            if(!attendanceReports.containsKey(id)){
                String legal_name = studentDAO.get(id).getLegalName();
                AttendanceRecord attendanceRecord = new AttendanceRecord(id, AttendanceStatus.UNRECORDED, lectureId);
                attendanceReports.put(id,new AttendanceReport(attendanceRecord, legal_name, dateStr));
            }
        }
        return attendanceReports;
    }

    public void handleGetAttendanceReport(Integer userId) throws IOException {
        List<Section> parseSections = sendAllEnrolledSectionNames(userId);
        if (!parseSections.isEmpty()) {
            // get result from client (choice)
            try {
                Integer num = (Integer) in.readObject();
                if (num != -1) {
                    // Default eligible
                    // assume num is eligible
                    // sending report function
                    // ...
                    Integer sectionId = parseSections.get(num).getSectionID();

                    // loop up all lectures for this setcion
                    sendEmailToClient(sectionId);

                    // finished sending
                } 
                // else {
                //     out.writeObject("0||" + "Invalid request format (please input a number)!");
                // }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void sendEmailToClient(int sectionId) throws IOException, GeneralSecurityException {
        List<Lecture> lectures = lectureDAO.getLecturesBySectionId(sectionId);
        if (lectures.isEmpty()) {
            throw new IllegalStateException("No lectures found for section with ID: " + sectionId);
        }
        String result = "";
        for (Lecture lecture : lectures) {
            int lectureId = lecture.getLectureID();
            AttendanceRecord attendanceRecord = attendanceDAO.get(lectureId, user_id);
            int year = lecture.getYear();
            int month = lecture.getMonth();
            int day = lecture.getDay();
            String dateStr = String.format("%04d-%02d-%02d", year, month, day);
            if (attendanceRecord == null) {
                // throw new IllegalStateException("No attendance record found for lecture with ID: " + lectureId);
                attendanceRecord = new AttendanceRecord(user_id, AttendanceStatus.UNRECORDED,lecture.getLectureID());
            }
            String statuString = attendanceRecord.getStatus().toString();
            float f = 0;
            if(attendanceRecord.getStatus()==AttendanceStatus.PRESENT){
                f=1;
            }
            else if (attendanceRecord.getStatus()==AttendanceStatus.TARDY){
                f+=0.8;
            }
            // if(attendanceRecord!=null){
            //     if(attendanceRecord.getStatus()==AttendanceStatus.PRESENT){
            //         f=1;
            //     }
            //     else if (attendanceRecord.getStatus()==AttendanceStatus.TARDY){
            //         f+=0.8;
            //     }
            // }
            result += String.format("Lecture ID: %d, Date: %s, Status: %s,Score %f", lectureId, dateStr, statuString,f) + "\n";
        }

        // get student's email

        Student student = studentDAO.get(user_id);
        // if (student == null) {
        //     throw new IllegalStateException("Student not found with ID: " + user_id);
        // }

        Section section = sectionDAO.getBySectionId(sectionId);
        // if (section == null) {
        //     throw new IllegalStateException("Section not found with ID: " + sectionId);
        // }

        Course course = courseDAO.getCourseByCourseId(section.getCourseId());
        // if (course == null) {
        //     throw new IllegalStateException("Course not found with ID: " + section.getCourseId());
        // }

        String email = student.getEmail();
        serverSideView.displayMessage("Sending email alert to " + email + "...");
        gmailSetup.sendEmail(email, "Attendance Records for " + course.getName() + "_" + section.getName(),
                "Dear " + student.getDisplayName() + "\n" + "Here is your attendance record for " + course.getName()
                        + "_" + section.getName() + "this semester.\n" + result);
    }


    public void sendWeeklyReport() throws IOException, GeneralSecurityException {
        List<Student> studentList = studentDAO.list();

        if (studentList.isEmpty()) {
            serverSideView.displayMessage("Please notice admin user to import student data first!");
            return;
        }
        for (Student student : studentList) {
            int studentId = student.getStudentID();
            // get all section they take
            List<Enrollment> enrollments = enrollmentDAO.findEnrollmentsByStudentId(studentId);
            if (enrollments.isEmpty())
                continue;
            else {
                String sendMsg = "";
                for (Enrollment enrollment : enrollments) {
                    int sectionId = enrollment.getSectionId();
                    boolean notified = enrollment.isNotify();
                    if (!notified)
                        continue;
                    // get the latest lecture
                    List<Lecture> lectures = lectureDAO.getLecturesBySectionIdDECS(sectionId);

                    if (lectures.isEmpty())
                        continue;
                    int lectureId = lectures.get(0).getLectureID();

                    int year = lectures.get(0).getYear();
                    int month = lectures.get(0).getMonth();
                    int day = lectures.get(0).getDay();

                    String dateStr = String.format("%04d-%02d-%02d", year, month, day);

                    AttendanceRecord attendanceRecord = attendanceDAO.get(lectureId, studentId);
                    float f = 0;
                    if(attendanceRecord!=null){
                        if(attendanceRecord.getStatus()==AttendanceStatus.PRESENT){
                            f=1;
                        }
                        else if (attendanceRecord.getStatus()==AttendanceStatus.TARDY){
                            f+=0.8;
                        }
                        sendMsg += String.format("Lecture ID: %d, Date: %s, Status: %s, Score %f", lectureId, dateStr,
                            attendanceRecord.getStatus().toString(),f)
                            + "\n";
                    }
                    else{
                        sendMsg += String.format("Lecture ID: %d, Date: %s, Status: %s, Score %f", lectureId, dateStr,
                            AttendanceStatus.UNRECORDED.toString(),f)
                            + "\n";
                    }

                }

                // send Email
                serverSideView.displayMessage("Sending weekly report to " + student.getEmail() + "...");
                gmailSetup.sendEmail(student.getEmail(),
                        "Weekly Attendance Report for " + student.getDisplayName(),
                        "Dear " + student.getDisplayName() + "\n" + "Here is your attendance report for this week.\n"
                                + sendMsg);

            }
        }
    }

    public void executePeriodicTask() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                try {
                    sendWeeklyReport();
                } catch (IOException e) {
                    serverSideView.displayMessage(e.getMessage());
                    e.printStackTrace();
                } catch (GeneralSecurityException e) {
                    serverSideView.displayMessage(e.getMessage());
                    e.printStackTrace();
                }
            }
        };
        // execute it every 10 minutes
        timer.scheduleAtFixedRate(task, 0, 1 * 60 * 1000);
    }

}
