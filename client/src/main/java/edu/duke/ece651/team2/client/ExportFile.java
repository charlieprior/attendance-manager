package edu.duke.ece651.team2.client;

import edu.duke.ece651.team2.shared.AttendanceRecord;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class ExportFile {
    /**
     * The separator for the CSV file.
     */
    private final String separator = ",";
    /**
     * The new line for the CSV file.
     */
    private final String newLine = "\n";
    /**
     * The headers for the CSV file.
     */
    private final String headers = "Student ID,Student Name,Section Name,Year,Month,Day,Status"; // more info

    /**
     * This function will write the AttendanceRecord into CSV
     *
     * @param fileName is the file's name
     * @param session  is the AttendanceSession that contain the records information
     */
    public void writeRecordsToCSV(String fileName, ArrayList<AttendanceRecord> records) {
        try {
            FileWriter writer = new FileWriter("export/" + fileName + ".csv"); // path ???
            writer.append(headers + newLine);
            // for (AttendanceRecord r : records) {
            //     writer.write(r.getStudent().getStudentID() + separator);
            //     writer.write(r.getStudent().getDisplayName() + separator);
            //     writer.write(r.getSection().getName()+ separator);
            //     writer.write(r.getLecture().getYear()+ separator);
            //     writer.write(r.getLecture().getMonth()+ separator);
            //     writer.write(r.getLecture().getDay()+ separator);
            //     writer.write(r.getStatus() + newLine);
            // }
            System.out.println("Successfully create and write csv");
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
