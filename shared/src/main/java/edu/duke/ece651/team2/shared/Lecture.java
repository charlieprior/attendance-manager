package edu.duke.ece651.team2.shared;

import java.time.LocalDate;
import java.util.Calendar;

/**
 * The Lecture class represents a lecture for a course.
 */
public class Lecture {
    private Integer LectureID;
    private final Integer sectionId;
    private final int year;
    private final int month;
    private final int day;

    public Lecture(Integer sectionId){
        this.sectionId = sectionId;
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH)+1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }

    public Lecture(Integer sectionId, LocalDate date) {
        this.sectionId = sectionId;
        year = date.getYear();
        month = date.getMonthValue();
        day = date.getDayOfMonth();
    }

    public Integer getLectureID() {
        return LectureID;
    }

    public void setLectureID(Integer lectureID) {
        LectureID = lectureID;
    }

    public Integer getSectionId() {
        return sectionId;
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
