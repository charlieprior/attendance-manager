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
        String inputData = "1\n";
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        BufferedReader input = new BufferedReader(new StringReader(inputData));
        PrintStream output = new PrintStream(bytes, true);

        UniversityDAO uniDAO = new UniversityDAO(new DAOFactory());
        uniDAO.deleteAll();
        University uni1 = new University("Duke", true);
        University uni2 = new University("NC State", false);
        uniDAO.create(uni1);
        uniDAO.create(uni2);

        SelectUniversity select = new SelectUniversity(output, input);
        select.readUniversity();
        assertEquals("", output.toString());
    }
}