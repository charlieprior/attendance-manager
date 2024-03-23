package edu.duke.ece651.team2.attendancemanager;

import java.util.ArrayList;

/**
 * The EventManager class is responsible for managing the list of EventListeners and notifying them when the attendance
 * of a Student changes.
 */
public class EventManager {
    /**
     * The list of EventListeners that are subscribed to this EventManager.
     */
    private final ArrayList<EventListener> listeners = new ArrayList<>();

    /**
     * Subscribes an EventListener to this EventManager.
     *
     * @param listener The EventListener to subscribe.
     */
    public void subscribe(EventListener listener) {
        listeners.add(listener);
    }

    /**
     * Unsubscribes an EventListener from this EventManager.
     *
     * @param listener The EventListener to unsubscribe.
     */
    public void unsubscribe(EventListener listener) {
        listeners.remove(listener);
    }

    /**
     * Notifies all subscribed EventListeners that the attendance of a Student has changed.
     */
    public void notifyAttendanceChanged() {
        for (EventListener listener : listeners) {
            listener.attendanceChanged();
        }
    }
}
