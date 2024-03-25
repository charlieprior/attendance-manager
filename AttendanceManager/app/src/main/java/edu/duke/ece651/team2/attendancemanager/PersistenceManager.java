package edu.duke.ece651.team2.attendancemanager;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class PersistenceManager {
    private final String separator = ",";
    private final String newLine = "\n";
    private final String headers = "Student ID,Student Name,Time,Status"; //more info

    public void writeRecordsToCSV(String fileName, AttendanceSession session){
        List<AttendanceRecord> records = session.getRecords();
        try{
            FileWriter writer = new FileWriter("export/"+fileName+".csv"); //path ???
            writer.append(headers+newLine);
            for(AttendanceRecord r:records){
                writer.write(r.getStudentID()+separator);
                writer.write(r.getStudentName()+separator);
                writer.write(r.getAttendanceDate()+separator);
                writer.write(r.getStatus()+newLine);
            }
            System.out.println("Successfully create and write csv");
            writer.close();
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
        
    }

    public void writeRecordsToJSON(String fileName, String courseName, String lectureID, AttendanceSession session){
        List<AttendanceRecord> records = session.getRecords();
        try{
            
            FileWriter writer = new FileWriter("export/"+fileName+".JSON"); //path ???
            writer.write("{"+newLine);
            writer.write("\"Course Name\": "+"\""+courseName+"\""+separator+newLine);
            writer.write("\"Lecture ID\": "+"\""+lectureID+"\""+separator+newLine);
            writer.write("\"Attendance\": "+"{"+newLine);
            for(AttendanceRecord r:records){
                writer.write("\""+r.getStudentID()+"\": "+"{"+newLine);
                writer.write("\"Student Name\": "+"\""+r.getStudentName()+"\""+separator+newLine);
                writer.write("\"Time\": "+"\""+r.getAttendanceDate()+"\""+separator+newLine);
                writer.write("\"Status\": "+"\""+r.getStatus()+"\""+newLine);
                if(r.equals(records.get(records.size()-1))){
                    writer.write("}"+newLine);
                }
                else{
                    writer.write("},"+newLine);
                }
            }
            writer.append("}"+newLine);
            writer.append("}");
            System.out.println("Succesfully create and write csv");

            writer.close();
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
        
    }
}
