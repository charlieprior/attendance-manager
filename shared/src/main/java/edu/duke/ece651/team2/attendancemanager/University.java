package edu.duke.ece651.team2.shared;

/**
 * The University class represents a university with a name
 * and a boolean value indicating whether the university supports changing the
 * display name of students.
 */
public class University {
    // TODO? may add functions: This class will be asked whether it supports to
    // change the display name
    /**
     * The name of the university.
     */
    private final String name;
    /**
     * Whether the university supports changing the display name of students.
     */
    private final boolean change;

    /**
     * Constructs a new University object with the specified name and whether it
     * supports changing the display name of students.
     *
     * @param name   The name of the university.
     * @param change Whether the university supports changing the display name of
     *               students.
     */
    public University(String name, boolean change) {
        this.name = name;
        this.change = change;
    }

    /**
     * Returns the name of the university.
     *
     * @return The name of the university.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns whether the university supports changing the display name of
     * students.
     *
     * @return Whether the university supports changing the display name of
     *         students.
     */
    public boolean getSupportChange() {
        return this.change;
    }
}
