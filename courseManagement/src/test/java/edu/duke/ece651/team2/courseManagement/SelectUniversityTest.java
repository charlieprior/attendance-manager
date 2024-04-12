package edu.duke.ece651.team2.courseManagement;

import edu.duke.ece651.team2.server.DAOFactory;
import edu.duke.ece651.team2.server.UniversityDAO;
import edu.duke.ece651.team2.shared.University;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class SelectUniversityTest {

    @Test
    void testReadUniversity() throws IOException {
        UniversityDAO uniDAO = new UniversityDAO(new DAOFactory());
        University uni1 = new University("Duke", true);
        University uni2 = new University("NC State", false);
        uniDAO.deleteAll();
        uniDAO.create(uni1);
        uniDAO.create(uni2);

        Integer id1 = uni1.getId();
        Integer id2 = uni2.getId();

        String inputData = "a\n" + id1;
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        BufferedReader input = new BufferedReader(new StringReader(inputData));
        PrintStream output = new PrintStream(bytes, true);

        SelectUniversity select = new SelectUniversity(output, input);
        select.readUniversity();
        String expected = "Please select a university to modify:\n" +
                id1 + ". Duke\n" +
                id2 + ". NC State\n";
        assertEquals(expected +
                "Invalid selection. Please try again\n" +
                expected, bytes.toString());
        uniDAO.remove(uni2);
        uniDAO.remove(uni1);
    }
}