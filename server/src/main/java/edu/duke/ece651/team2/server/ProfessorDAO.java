package edu.duke.ece651.team2.server;

import edu.duke.ece651.team2.shared.Professor;

import java.util.Arrays;
import java.util.List;

public class ProfessorDAO extends DAO<Professor> {

    private final DAOFactory daoFactory;

    public ProfessorDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    void create(Professor professor) {
        if (professor.getProfessorID() != null) {
            // Object already exists in database
            throw new IllegalArgumentException("Professor object already exists in database");
        }

        List<Object> values = Arrays.asList(
                professor.getName(),
                professor.getEmail()
        );

        professor.setProfessorID(execute(daoFactory,
                "INSERT INTO Professor (name, email) VALUES (?, ?)",
                values)); // TODO Fix
    }

    @Override
    void update(Professor professor) {
        if (professor.getProfessorID() == null) {
            // Object does not exist in database
            throw new IllegalArgumentException("Professor object does not exist in database");
        }

        List<Object> values = Arrays.asList(
                professor.getName(),
                professor.getEmail(),
                professor.getProfessorID()
        );

        execute(daoFactory,
                "UPDATE Professor SET name = ?, email = ? WHERE id = ?",
                values); // TODO Fix
    }

    @Override
    void remove(Professor professor) {
        if (professor.getProfessorID() == null) {
            // Object does not exist in database
            throw new IllegalArgumentException("Professor object does not exist in database");
        }

        List<Object> values = Arrays.asList(
                professor.getProfessorID()
        );

        execute(daoFactory,
                "DELETE FROM Professor WHERE id = ?",
                values); // TODO Fix

        professor.setProfessorID(null);
    }
}
