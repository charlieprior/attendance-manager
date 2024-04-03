package edu.duke.ece651.team2.attendancemanager;

import java.util.Collections;
import java.util.List;

public class CourseDAO extends DAO<Course> {

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

        course.setCourseID(String.valueOf(execute(daoFactory, "INSERT INTO Course (name) VALUES (?)", values)));
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
