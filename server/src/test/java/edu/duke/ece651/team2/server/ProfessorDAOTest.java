package edu.duke.ece651.team2.server;

import edu.duke.ece651.team2.shared.*;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProfessorDAOTest {
    DAOFactory factory = new DAOFactory();
    ProfessorDAO professorDAO = new ProfessorDAO(factory);

    @Test
    void testCreateRemoveUpdate() {
        Professor test = new Professor("John Smith", "test@example.com", 0);
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
        Professor prof = new Professor("John Smith", "test@example.com", 0);
        professorDAO.create(prof);
        Professor got = professorDAO.get(prof.getProfessorID());
        assertEquals(prof, got);
    }

    // @Test
    // void testGetUniversityID(){
    //    Integer id = professorDAO.getUniversityID(2);
    //    assertEquals(1, id);
    // }
}