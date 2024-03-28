package edu.duke.ece651.team2.attendancemanager;

import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * The EventListener interface is implemented by classes that wish to be notified when the attendance of a Student changes.
 */
public interface EventListener {

    void attendanceChanged(Student student, AttendanceRecord record) throws GeneralSecurityException, IOException;
}
