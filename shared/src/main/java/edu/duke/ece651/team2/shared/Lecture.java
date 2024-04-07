package edu.duke.ece651.team2.shared;

import java.util.Calendar;

/**
 * The Lecture class represents a lecture for a course.
 */
public class Lecture {
    private int LectureID;
    private Section section;
    private int year;
    private int month;
    private int day;

    public Lecture(Section sec){
        section = sec;
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH)+1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }

    public int getLectureID() {
        return LectureID;
    }

    public void setLectureID(int lectureID) {
        LectureID = lectureID;
    }

    public Section getSection() {
        return section;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

}
