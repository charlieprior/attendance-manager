package edu.duke.ece651.team2.attendancemanager;

public class DatabaseTest {
    public static void main(String[] args) {
        DAOFactory factory = new DAOFactory();
        StudentDAO studentDAO = new StudentDAO(factory);

        Student test = new Student("John Smith", "0", "test@example.com", "John");

        studentDAO.create(test);
    }
}
