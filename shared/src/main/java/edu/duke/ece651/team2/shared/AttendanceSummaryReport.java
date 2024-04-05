package edu.duke.ece651.team2.shared;
import java.util.ArrayList;

public class AttendanceSummaryReport {

    private ArrayList<AttendanceRecord> records;

    private float score;

    public AttendanceSummaryReport(ArrayList<AttendanceRecord> rec){
        records = rec;
        score = 0;
        for(AttendanceRecord r:rec){
            if(r.getStatus()==AttendanceStatus.TARDY){
                score+=0.8;
            }
            else if(r.getStatus()==AttendanceStatus.PRESENT){
                score+=1;
            }
        }
        score = score/rec.size();
    }

    public float getScore(){
        return score;
    }
    
    public Student getStudent(){
        return records.get(0).getStudent();
    }

    public Section getSection(){
        return records.get(0).getSection();
    }

    public ArrayList<Lecture> getLecture(){
        ArrayList<Lecture> ans = new ArrayList<>();
        for(AttendanceRecord r:records){
            ans.add(r.getLecture());
        }
        return ans;
    }

}
