package edu.duke.ece651.team2.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

import org.junit.jupiter.api.Test;

public class LectureTest {

    Integer secId = 4;

    @Test
    public void testConstructorsGettersAndSetters(){
        Lecture l1 = new Lecture(secId);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        assertEquals(year, l1.getYear());
        assertEquals(month, l1.getMonth());
        assertEquals(day, l1.getDay());
        LocalDate specificDate = LocalDate.of(2023, 8, 25);
        Lecture l2 = new Lecture(secId, specificDate);
        assertEquals(2023, l2.getYear());
        assertEquals(8, l2.getMonth());
        assertEquals(25, l2.getDay());
        assertEquals(secId, l2.getSectionId());
        l2.setLectureID(89);
        assertEquals(89, l2.getLectureID());
    }


}
