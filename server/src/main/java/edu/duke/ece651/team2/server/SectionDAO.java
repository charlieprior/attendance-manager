package edu.duke.ece651.team2.server;

import edu.duke.ece651.team2.shared.Lecture;
import edu.duke.ece651.team2.shared.Section;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SectionDAO extends DAO<Section> {
    private final DAOFactory daoFactory;

    public SectionDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    @Override
    Section map(ResultSet resultSet) throws SQLException {
        Section section =  new Section(resultSet.getInt("courseId"),
                resultSet.getInt("instructorId"),
                resultSet.getString("name"));
        section.setSectionID(resultSet.getInt("id"));
        return section;
    }

    public void create(Section section) {
        if (section.getSectionID() != null) {
            throw new IllegalArgumentException("Section object already exists in database");
        }

        List<Object> values = Arrays.asList(
                section.getCourseId(),
                section.getInstructorId(),
                section.getName()
        );

        try {
            ResultSet generatedKeys = executeUpdate(daoFactory,
                    "INSERT INTO Section (courseId, instructorId, name) VALUES (?, ?, ?)",
                    values);
            if (generatedKeys.next()) {
                section.setSectionID(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Section section) {
        if (section.getSectionID() == null) {
            throw new IllegalArgumentException("Section object does not exist in database");
        }


        List<Object> values = Arrays.asList(
                section.getCourseId(),
                section.getInstructorId(),
                section.getName(),
                section.getSectionID()
        );

        try {
            executeUpdate(daoFactory, "UPDATE Section SET courseId = ?, instructorId = ?, name = ? WHERE id = ?", values);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void remove(Section section) {
        if (section.getSectionID() == null) {
            throw new IllegalArgumentException("Section object does not exist in database");
        }

        List<Object> values = Collections.singletonList(section.getSectionID());

        try {
            executeUpdate(daoFactory, "DELETE FROM Section WHERE id = ?", values);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        section.setSectionID(null);
    }

    public List<Section> list() {
        return super.list(daoFactory, "SELECT * FROM Section ORDER BY courseId", new ArrayList<>());
    }

    public List<Section> noInstructorSection(){
        return super.list(daoFactory, "SELECT * FROM Section WHERE instructorId IS NULL", new ArrayList<>());
    }

    public List<Section> list(Integer userID){
        return super.list(daoFactory, "SELECT * FROM Section WHERE instructorId = "+userID, new ArrayList<>());
    }


    // Not sure what get methods to write
}
