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
        if (student.getStudentID() != null) {
            // Object already exists in database
            throw new IllegalArgumentException("Student object already exists in database");
        }

        List<Object> values = Arrays.asList(
                student.getLegalName(),
                student.getDisplayName(),
                student.getEmail()
        );

        student.setStudentID(execute(daoFactory,
                "INSERT INTO Student (legalName, displayName, email) VALUES (?, ?, ?)",
                values)); // TODO Fix
    }

    @Override
    public void update(Student student) {
        if (student.getStudentID() == null) {
            // Object does not exist in database
            throw new IllegalArgumentException("Student object does not exist in database");
        }

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
        if (student.getStudentID() == null) {
            // Object does not exist in database
            throw new IllegalArgumentException("Student object does not exist in database");
        }

        List<Object> values = Collections.singletonList(student.getStudentID());

        execute(daoFactory, "DELETE FROM Student WHERE id = ?", values);

        student.setStudentID(null);
    }
}
