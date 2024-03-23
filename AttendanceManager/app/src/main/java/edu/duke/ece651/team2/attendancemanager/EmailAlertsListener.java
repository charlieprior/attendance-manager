package edu.duke.ece651.team2.attendancemanager;

/**
 * An EventListener that sends email alerts when the attendance of a Student changes.
 */
public class EmailAlertsListener implements EventListener {
    /**
     * The email address to send alerts to.
     */
    private final String email;

    /**
     * Creates a new EmailAlertsListener with the given email address.
     *
     * @param email The email address to send alerts to.
     */
    public EmailAlertsListener(String email) {
        this.email = email;
    }

    /**
     * Sends an email alert when the attendance of a Student changes.
     */
    @Override
    public void attendanceChanged() {
        System.out.println("Sending email alert to " + email + "...");
    }
}
