package edu.duke.ece651.team2.attendancemanager;

import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * An EventListener that sends email alerts when the attendance of a Student changes.
 */
public class EmailAlertsListener implements EventListener {

    private final GmailSetup gmailSetup;

    /**
     * Creates a new EmailAlertsListener with the given email address.
     *
     * @param email The email address to send alerts to.
     */
    public EmailAlertsListener() throws GeneralSecurityException, IOException {
        this.gmailSetup = new GmailSetup();
    }

    /**
     * Sends an email alert when the attendance of a Student changes.
     */
    @Override
    public void attendanceChanged(Student student, AttendanceRecord record) throws GeneralSecurityException, IOException {
        String email = student.getEmail();
        System.out.println("Sending email alert to " + email + "...");
        gmailSetup.sendEmail(email, "Attendance Changed for Lecture " + record.getLectureID(),
                "Dear " + student.getDisplayName() + ",\n" +
                "Your attendance has been changed to " + record.getStatus() + " in Lecture " + record.getLectureID());
    }
}
