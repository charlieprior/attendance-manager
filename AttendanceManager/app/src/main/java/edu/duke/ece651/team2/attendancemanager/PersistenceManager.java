package edu.duke.ece651.team2.attendancemanager;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class PersistenceManager {
    private final String separator = ",";
    private final String newLine = "\n";
    private final String headers = "Student ID,Lecture ID,Status"; //more info

    public void writeRecordsToCSV(String fileName, AttendanceSession session){
        List<AttendanceRecord> records = session.getRecords();
        try{
            FileWriter writer = new FileWriter(fileName+".csv"); //path ???
            writer.append(headers+newLine);
            for(AttendanceRecord r:records){
                writer.write(r.getStudentID()+separator);
                writer.write(r.getLectureID()+separator);
                writer.write(r.getStatus()+newLine);
            }
            System.out.println("Succesfully create and write csv");
            writer.close();
            System.out.println("Succesfully write to csv");
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
        
    }
}
