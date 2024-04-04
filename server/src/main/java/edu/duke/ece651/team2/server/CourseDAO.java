package edu.duke.ece651.team2.server;

import edu.duke.ece651.team2.shared.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CourseDAO extends DAO<Course> {

    private final DAOFactory daoFactory;

    public CourseDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public void create(Course course) {
        // TODO: Check if ID already exists?
        List<Object> values = Arrays.asList(
                course.getCourseID(),
                course.getName()
// TODO: course.getUniversityId
        );

        // TODO FIX
        execute(daoFactory, "INSERT INTO Course (name) VALUES (?)", values);
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
