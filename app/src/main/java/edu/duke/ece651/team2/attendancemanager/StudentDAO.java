package edu.duke.ece651.team2.attendancemanager;

import java.sql.*;
import java.util.Arrays;
import java.util.List;

public class StudentDAO implements DAO<Student> {
    private static final String SQL_INSERT =
            "INSERT INTO Student (legalName, displayName, email) VALUES (?, ?, ?)";

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

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)
        ) {
            DAO.setStatementObjects(statement, values);
            if (statement.executeUpdate() == 0) {
                throw new RuntimeException("Creating Student failed, no rows affected.");
            }

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                student.setStudentID(generatedKeys.getString(1)); // TODO Change to Int
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Student student) {
        // TODO
    }

    @Override
    public void remove(Student student) {
        // TODO
    }
}
