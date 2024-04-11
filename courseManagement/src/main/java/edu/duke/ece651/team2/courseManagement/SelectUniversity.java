package edu.duke.ece651.team2.courseManagement;

import edu.duke.ece651.team2.server.DAOFactory;
import edu.duke.ece651.team2.server.UniversityDAO;
import edu.duke.ece651.team2.shared.University;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

/**
 * A class to select a university from a list
 */
public class SelectUniversity {
    /**
     * The output stream
     */
    private final PrintStream out;
    /**
     * The input stream
     */
    private final BufferedReader reader;
    /**
     * The DAO factory
     */
    private static final DAOFactory daoFactory = new DAOFactory();
    /**
     * The university DAO
     */
    private static final UniversityDAO uniDAO = new UniversityDAO(daoFactory);

    /**
     * Creates a new university selector
     * @param out the output stream
     * @param reader the input stream
     */
    public SelectUniversity(PrintStream out, BufferedReader reader) {
        this.out = out;
        this.reader = reader;
    }

    /**
     * Lists all universities in the database
     */
    protected void listUniversities() {
        List<University> universityList = uniDAO.list();
        for(University u : universityList) {
            out.println(u.getId() + ". " + u.getName());
        }
    }

    /**
     * Prompts the user to select a university
     * @return the selected university
     * @throws IOException if an I/O error occurs
     */
    public University readUniversity() throws IOException {
        while(true) {
            out.println("Please select a university to modify:");
            listUniversities();

            try {
                Integer id = Integer.valueOf(reader.readLine());
                University u = uniDAO.get(id);
                if (u != null) {
                    return u;
                }

            } catch (NumberFormatException e) {
                out.println("Invalid selection. Please try again");
            }
        }
    }
}
