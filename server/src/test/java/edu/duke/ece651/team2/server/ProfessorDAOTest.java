package edu.duke.ece651.team2.server;

import edu.duke.ece651.team2.shared.Professor;
import edu.duke.ece651.team2.shared.Student;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProfessorDAOTest {

    DAOFactory factory = new DAOFactory();
    ProfessorDAO professorDAO = new ProfessorDAO(factory);

    @Test
    void testCreateRemoveUpdate() {
        // TODO How do we get 100% coverage of exceptions?
        Professor test = new Professor("John Doe", "john.doe@duke.edu");
        professorDAO.create(test);
        assertNotNull(test.getProfessorID());
        assertThrows(IllegalArgumentException.class, () -> professorDAO.create(test));

        test.setName("Mary Jane");
        professorDAO.update(test);

        professorDAO.remove(test);
        assertNull(test.getProfessorID());
        assertThrows(IllegalArgumentException.class, () -> professorDAO.remove(test));
        assertThrows(IllegalArgumentException.class, () -> professorDAO.update(test));
    }

    @Test
    void testList() {
        List<Professor> profs = professorDAO.list();
        for(Professor prof : profs) {
            System.out.println(prof.getName());
        }
    }

    @Test
    void testGet() {
        Professor test = new Professor("John Doe", "john.doe@duke.edu");
        professorDAO.create(test);
        Professor got = professorDAO.get(test.getProfessorID());
        assertEquals(test, got);
    }
}