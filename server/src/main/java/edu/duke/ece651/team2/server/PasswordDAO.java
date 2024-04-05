package edu.duke.ece651.team2.server;

import edu.duke.ece651.team2.shared.Password;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PasswordDAO extends DAO<Password> {

    private final DAOFactory daoFactory;

    public PasswordDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    void create(Password password) {
        List<Object> values = Arrays.asList(
                password.getStudentId(),
                password.getPassword()
        );

        execute(daoFactory,
                "INSERT INTO Passwords (studentId, password) VALUES (?, ?)",
                values); // TODO Fix
    }

    @Override
    void update(Password password) {
        List<Object> values = Arrays.asList(
                password.getPassword(),
                password.getStudentId()
        );

        execute(daoFactory,
                "UPDATE Passwords SET password = ? WHERE studentId = ?",
                values); // TODO Fix
    }

    @Override
    void remove(Password password) {
        List<Object> values = Collections.singletonList(
                password.getStudentId()
        );

        execute(daoFactory,
                "DELETE FROM Passwords WHERE studentId = ?",
                values); // TODO Fix
    }
}
