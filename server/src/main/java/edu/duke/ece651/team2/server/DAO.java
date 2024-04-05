package edu.duke.ece651.team2.server;

import edu.duke.ece651.team2.shared.*;

import java.sql.*;
import java.util.List;

public abstract class DAO<T> {
    static void setStatementObjects(PreparedStatement statement, List<Object> values) throws SQLException {
        for (int i = 0; i < values.size(); i++) {
            statement.setObject(i + 1, values.get(i));
        }
    }

    static int execute(DAOFactory daoFactory, String sql, List<Object> values) {
        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            DAO.setStatementObjects(statement, values);
            if (statement.executeUpdate() == 0) {
                throw new RuntimeException("Execution failed: " + statement.toString());
            }

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            }
            else {
                return -1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    abstract void create(T t);
    abstract void update(T t);
    abstract void remove(T t);
}
