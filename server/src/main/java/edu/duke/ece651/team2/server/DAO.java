package edu.duke.ece651.team2.server;

import edu.duke.ece651.team2.shared.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class DAO<T> {

    static void setStatementObjects(PreparedStatement statement, List<Object> values) throws SQLException {
        for (int i = 0; i < values.size(); i++) {
            statement.setObject(i + 1, values.get(i));
        }
    }

    static ResultSet executeUpdate(DAOFactory daoFactory, String sql, List<Object> values) throws SQLException {
        Connection connection = daoFactory.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        DAO.setStatementObjects(statement, values);
        if (statement.executeUpdate() == 0) {
            throw new RuntimeException("Execution failed: " + statement);
        }

        return statement.getGeneratedKeys();
    }

    static ResultSet executeQuery(DAOFactory daoFactory, String sql, List<Object> values) throws SQLException {
        Connection connection = daoFactory.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        DAO.setStatementObjects(statement, values);

        return statement.executeQuery();
    }

    abstract T map(ResultSet resultSet) throws SQLException;

    protected List<T> list(DAOFactory daoFactory, String sql) {
        List<T> Ts = new ArrayList<>();
        try (
                ResultSet resultSet = executeQuery(daoFactory,
                        sql,
                        new ArrayList<>());
        ) {
            while (resultSet.next()) {
                Ts.add(map(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Ts;
    }

    protected T get(DAOFactory daoFactory, String sql, List<Object> values) {
        try (
                ResultSet resultSet = executeQuery(daoFactory,
                        sql,
                        values)
        ) {
            if (resultSet.next()) {
                return map(resultSet);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}
