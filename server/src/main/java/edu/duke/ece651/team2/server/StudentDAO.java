package edu.duke.ece651.team2.server;

import edu.duke.ece651.team2.shared.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StudentDAO extends DAO<Student> {

    private final DAOFactory daoFactory;

    public StudentDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public void create(Student student) {
        // TODO Check if ID already exists?

        List<Object> values = Arrays.asList(
                student.getStudentID(),
                student.getLegalName(),
                student.getDisplayName(),
                student.getEmail()
        );

        execute(daoFactory,
                "INSERT INTO Student (id, legalName, displayName, email) VALUES (?, ?, ?, ?)",
                values);
    }

    @Override
    public void update(Student student) {
        // TODO Check if ID is in table

        List<Object> values = Arrays.asList(
                student.getLegalName(),
                student.getDisplayName(),
                student.getEmail(),
                student.getStudentID()
        );

        execute(daoFactory, "UPDATE Student SET legalName = ?, displayName = ?, email = ? WHERE id = ?", values);
    }

    @Override
    public void remove(Student student) {
        // TODO Check if ID is in table

        List<Object> values = Collections.singletonList(student.getStudentID());

        execute(daoFactory, "DELETE FROM Student WHERE id = ?", values);
    }
}
