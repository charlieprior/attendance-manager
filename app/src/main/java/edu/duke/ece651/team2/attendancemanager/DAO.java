package edu.duke.ece651.team2.attendancemanager;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public interface DAO<T> {
    static void setStatementObjects(PreparedStatement statement, List<Object> values) throws SQLException {
        for (int i = 0; i < values.size(); i++) {
            statement.setObject(i + 1, values.get(i));
        }
    }

    void create(T t);

    void update(T t);

    void remove(T t);
}
