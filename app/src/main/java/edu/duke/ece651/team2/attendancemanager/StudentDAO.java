package edu.duke.ece651.team2.attendancemanager;

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
        // TODO: Check ID is null, update ID when finished
        List<Object> values = Arrays.asList(
                student.getLegalName(),
                student.getDisplayName(),
                student.getEmail()
        );

        student.setStudentID(String.valueOf(execute(daoFactory,
                "INSERT INTO Student (legalName, displayName, email) VALUES (?, ?, ?)",
                values))); // TODO Change to long
    }

    @Override
    public void update(Student student) {
        // TODO Check if ID is null (if so don't update)

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
        // TODO Check if ID is null (if so don't remove)

        List<Object> values = Collections.singletonList(student.getStudentID());

        execute(daoFactory, "DELETE FROM Student WHERE id = ?", values);
    }
}
