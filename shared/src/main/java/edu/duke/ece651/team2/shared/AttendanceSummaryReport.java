package edu.duke.ece651.team2.shared;
import java.util.ArrayList;

public class AttendanceSummaryReport {

    private final Section section;

    private ArrayList<AttendanceRecord> records;

    private float score;

    public AttendanceSummaryReport(Section section, ArrayList<AttendanceRecord> rec){
        this.section = section;
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
    
    public Integer getStudent(){
        return records.get(0).getStudentId();
    }

    public Section getSection(){
        return this.section;
    }

    public ArrayList<Integer> getLecture(){
        ArrayList<Integer> ans = new ArrayList<>();
        for(AttendanceRecord r:records){
            ans.add(r.getLectureId());
        }
        return ans;
    }

}
