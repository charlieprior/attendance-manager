package edu.duke.ece651.team2.server;

import edu.duke.ece651.team2.shared.Password;
import edu.duke.ece651.team2.shared.Student;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PasswordDAO extends DAO<Password> {

    private final DAOFactory daoFactory;

    public PasswordDAO(DAOFactory daoFactory) {
        super();
        this.daoFactory = daoFactory;
    }

    @Override
    Password map(ResultSet resultSet) throws SQLException {
        Password password = new Password(
                resultSet.getInt("studentId"), // TODO fix when column is renamed
                resultSet.getString("password"));
        return password;
    }

    @Override
    void create(Password password) {
        List<Object> values = Arrays.asList(
                password.getStudentId(),
                password.getPassword());

        try {
            executeUpdate(daoFactory,
                    "INSERT INTO Passwords (studentId, password) VALUES (?, ?)",
                    values); // TODO Fix
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    void update(Password password) {
        List<Object> values = Arrays.asList(
                password.getPassword(),
                password.getStudentId());

        try {
            executeUpdate(daoFactory,
                    "UPDATE Passwords SET password = ? WHERE studentId = ?",
                    values); // TODO Fix
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    void remove(Password password) {
        List<Object> values = Collections.singletonList(
                password.getStudentId());

        try {
            executeUpdate(daoFactory,
                    "DELETE FROM Passwords WHERE studentId = ?",
                    values); // TODO Fix
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Password> list() {
        return super.list(daoFactory, "SELECT * FROM Passwords ORDER BY studentId");
    }

    public Password get(Integer studentId) {
        List<Object> values = Collections.singletonList(studentId);
        return super.get(daoFactory, "SELECT * FROM Passwords WHERE studentId = ?", values);
    }
}
