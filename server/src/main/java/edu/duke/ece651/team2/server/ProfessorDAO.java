package edu.duke.ece651.team2.server;

import edu.duke.ece651.team2.shared.Professor;
import edu.duke.ece651.team2.shared.Student;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ProfessorDAO extends DAO<Professor> {

    private final DAOFactory daoFactory;

    public ProfessorDAO(DAOFactory daoFactory) {
        super();
        this.daoFactory = daoFactory;
    }

    @Override
    Professor map(ResultSet resultSet) throws SQLException {
        Professor professor = new Professor(
                 resultSet.getString("name"),
                resultSet.getString("email")
        );
        professor.setProfessorID(resultSet.getInt("id"));
        return professor;
    }

    @Override
    void create(Professor professor) {
        if (professor.getProfessorID() != null) {
            // Object already exists in database
            throw new IllegalArgumentException("Professor object already exists in database");
        }

        List<Object> values = Arrays.asList(
                professor.getName(),
                professor.getEmail()
        );

        try {
            ResultSet generatedKeys = executeUpdate(daoFactory,
                    "INSERT INTO Professor (name, email) VALUES (?, ?)",
                    values); // TODO Fix
            if (generatedKeys.next()) {
                professor.setProfessorID(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    void update(Professor professor) {
        if (professor.getProfessorID() == null) {
            // Object does not exist in database
            throw new IllegalArgumentException("Professor object does not exist in database");
        }

        List<Object> values = Arrays.asList(
                professor.getName(),
                professor.getEmail(),
                professor.getProfessorID()
        );

        try {
            executeUpdate(daoFactory,
                    "UPDATE Professor SET name = ?, email = ? WHERE id = ?",
                    values); // TODO Fix
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    void remove(Professor professor) {
        if (professor.getProfessorID() == null) {
            // Object does not exist in database
            throw new IllegalArgumentException("Professor object does not exist in database");
        }

        List<Object> values = Collections.singletonList(
                professor.getProfessorID()
        );

        try {
            executeUpdate(daoFactory,
                    "DELETE FROM Professor WHERE id = ?",
                    values); // TODO Fix
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        professor.setProfessorID(null);
    }

    List<Professor> list() {
        return super.list(daoFactory, "SELECT * FROM Professor ORDER BY id");
    }

    Professor get(Integer id) {
        List<Object> values = Collections.singletonList(id);
        return super.get(daoFactory, "SELECT * FROM Professor WHERE id = ?", values);
    }
}
