package edu.duke.ece651.team2.attendancemanager;

import java.util.HashMap;

/**
 * Stores passwords for the system.
 */
public class ProtectedInfo {
    /**
     * Maps user IDs to passwords.
     */
    private final HashMap<String, String> idToPassword = new HashMap<>();

    /**
     * Stores the password for a user.
     *
     * @param id             The user ID.
     * @param passwordString The password.
     */
    public void storeProtectedInfo(String id, String passwordString) {
        idToPassword.put(id, passwordString);
    }

    /**
     * Checks if the password matches the stored password for the user.
     *
     * @param id         The user ID.
     * @param passString The password to check.
     * @return True if the password matches the stored password, false otherwise.
     */
    public boolean match(String id, String passString) {
        return idToPassword.containsKey(id) && idToPassword.get(id).equals(passString);
    }
}
