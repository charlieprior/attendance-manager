package edu.duke.ece651.team2.attendancemanager;

import java.sql.*;
import java.util.Collections;
import java.util.List;

public class CourseDAO implements DAO<Course> {
    private static final String SQL_INSERT =
            "INSERT INTO Course (name) VALUES (?)";

    private final DAOFactory daoFactory;

    public CourseDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public void create(Course course) {
        // TODO: Check ID is null, update ID when finished
        List<Object> values = Collections.singletonList(
                course.getName()
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
                course.setCourseID(generatedKeys.getString(1)); // TODO Change to Int
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Course course) {
        // TODO
    }

    @Override
    public void remove(Course course) {
        // TODO
    }
}
