package edu.duke.ece651.team2.server;

import edu.duke.ece651.team2.shared.Student;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StudentDAO extends DAO<Student> {

    private final DAOFactory daoFactory;

    public StudentDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    Student map(ResultSet resultSet) throws SQLException {
        Student student = new Student(
                resultSet.getString("legalName"),
                resultSet.getString("email"),
                resultSet.getString("displayName")
        );
        student.setStudentID(resultSet.getInt("id"));
        return student;
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

        try {
            ResultSet generatedKeys = executeUpdate(daoFactory,
                    "INSERT INTO Student (legalName, displayName, email) VALUES (?, ?, ?)",
                    values);
            if (generatedKeys.next()) {
                student.setStudentID(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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


        try {
            executeUpdate(daoFactory, "UPDATE Student SET legalName = ?, displayName = ?, email = ? WHERE id = ?", values);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(Student student) {
        if (student.getStudentID() == null) {
            // Object does not exist in database
            throw new IllegalArgumentException("Student object does not exist in database");
        }

        List<Object> values = Collections.singletonList(student.getStudentID());

        try {
            executeUpdate(daoFactory, "DELETE FROM Student WHERE id = ?", values);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        student.setStudentID(null);
    }

    @Override
    List<Student> list() {
        List<Student> students = new ArrayList<>();
        try (
                ResultSet resultSet = executeQuery(daoFactory,
                        "SELECT * FROM Student ORDER BY id",
                        new ArrayList<>());
        ) {
            while (resultSet.next()) {
                students.add(map(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return students;
    }
}
