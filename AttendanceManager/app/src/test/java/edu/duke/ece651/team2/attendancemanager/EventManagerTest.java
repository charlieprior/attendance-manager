package edu.duke.ece651.team2.attendancemanager;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.ResourceAccessMode;
import org.junit.jupiter.api.parallel.ResourceLock;
import org.junit.jupiter.api.parallel.Resources;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class EventManagerTest {

    @Test
    @ResourceLock(value = Resources.SYSTEM_OUT, mode = ResourceAccessMode.READ_WRITE)
    public void testNotifyAttendanceChanged() throws IOException {
        EventManager eventManager = new EventManager();
        EmailAlertsListener emailAlertsListener = new EmailAlertsListener("test@example.com");
        eventManager.subscribe(emailAlertsListener);

        EmailAlertsListener emailAlertsListener2 = new EmailAlertsListener("test2@example.com");
        eventManager.subscribe(emailAlertsListener2);
        eventManager.unsubscribe(emailAlertsListener2);

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(bytes, true);
        PrintStream oldOut = System.out;

        try {
            System.setOut(out);
            eventManager.notifyAttendanceChanged();
        } finally {
            System.setOut(oldOut);
        }

        String expected = "Sending email alert to test@example.com...\n";
        String actual = bytes.toString();
        assertEquals(expected, actual);
    }
}