package edu.duke.ece651.team2.shared;

import java.util.ArrayList;

/**
 * The Professor class represents a professor with a name, professor ID, and a
 * list of courses they teach.
 */
public class Professor {
    /**
     * The name of the professor.
     */
    private final String name;

    /**
     * The unique ID of the professor.
     */
    private int professorID;
    /**
     * The email address of the professor.
     */
    private final String email;

    private final University university;

    private ArrayList<Section> sections;

    /**
     * Constructs a new Professor object with the specified name and professor ID.
     *
     * @param name        The name of the professor.
     * @param professorID The unique ID of the professor.
     * @param email       The email address of the professor.
     * @param university  The university the professor is affiliated with.
     */
    public Professor(String name, String email, University university) {
        this.name = name;
        this.email = email;
        this.university = university;
    }

    /**
     * Returns the name of the professor.
     *
     * @return The name of the professor.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the email address of the professor.
     *
     * @return The email address of the professor.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Returns the professor ID of the professor.
     *
     * @return The professor ID of the professor.
     */
    public int getProfessorID() {
        return professorID;
    }

    public void setProfessorID(int professorID){
        this.professorID = professorID;
    }

    public void setSections(ArrayList<Section> sections){
        this.sections = sections;
    }

    public ArrayList<Section> getSections(){
        return this.sections;
    }
}
