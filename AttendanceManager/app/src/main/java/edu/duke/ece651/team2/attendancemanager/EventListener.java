package edu.duke.ece651.team2.attendancemanager;

/**
 * The EventListener interface is implemented by classes that wish to be notified when the attendance of a Student changes.
 */
public interface EventListener {
    /**
     * Called when the attendance of a Student changes.
     */
    void attendanceChanged();
}
