package edu.duke.ece651.team2.courseManagement;

import edu.duke.ece651.team2.server.DAOFactory;
import edu.duke.ece651.team2.server.UniversityDAO;
import edu.duke.ece651.team2.shared.University;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

public class SelectUniversity {
    private final PrintStream out;
    private final BufferedReader reader;
    private static final DAOFactory daoFactory = new DAOFactory();
    private static final UniversityDAO uniDAO = new UniversityDAO(daoFactory);

    public SelectUniversity(PrintStream out, BufferedReader reader) {
        this.out = out;
        this.reader = reader;
    }

    protected void listUniversities() {
        List<University> universityList = uniDAO.list();
        for(University u : universityList) {
            out.println(u.getId() + ". " + u.getName());
        }
    }

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
