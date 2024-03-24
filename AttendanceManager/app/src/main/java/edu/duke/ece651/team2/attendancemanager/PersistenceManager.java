package main.java.edu.duke.ece651.team2.attendancemanager;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import edu.duke.ece651.team2.attendancemanager.AttendanceRecord;
import edu.duke.ece651.team2.attendancemanager.AttendanceSession;

public class PersistenceManager {
    private final String separator = ",";
    private final String newLine = "\n";
    private final ArrayList<String> headers = "Student ID,Lecture ID,Status"; //more info

    public void writeRecordsToCSV(String fileName, AttendanceSession session){
        ArrayList<AttendanceRecord> records = session.getRecords();
        try{
            FileWriter writer = new FileWriter(fileName+".csv");
            writer.append(headers+newLine);
            for(AttendanceRecord r:records){
                writer.append(r.getStudentID()+separator);
                writer.append(r.getLectureID()+separator);
                writer.append(r.getStatus()+newLine);
            }
            System.out.println("Succesfully write to csv");
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }

        
    }
}
