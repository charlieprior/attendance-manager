package edu.duke.ece651.team2.admin;

import org.junit.jupiter.api.Test;
import edu.duke.ece651.team2.shared.*;

import static org.junit.jupiter.api.Assertions.*;
public class UserRegistrationTest {
    @Test
    void testAddGetStudentAndProfessor(){
        UserRegistration userRegistration = new UserRegistration();
        Student student = new Student("Kenan Colak", "kc566@duke.edu",1,
                "kencolak");
        userRegistration.addStudent(student,"password");
        Professor professor = new Professor("Charlie Prior","CharlieP@duke.edu",1);
        userRegistration.addProfessor(professor,"passwords");
        userRegistration.getStudentID(student.getStudentID());
        userRegistration.getProfessorID(professor.getProfessorID());
    }

    @Test
    void testRemoveAndUpdate(){

        UserRegistration userRegistration = new UserRegistration();
        Student student = new Student("Kenan Colak", "kc566@duke.edu",1,
                "kencolak");
        userRegistration.addStudent(student,"password");
        Professor professor = new Professor("Charlie Prior","CharlieP@duke.edu",1);
        userRegistration.addProfessor(professor,"passwords");
        userRegistration.updateStudent(student.getStudentID(),"newpassword");
        userRegistration.removeStudent(student.getStudentID());
        assertNull(userRegistration.studentDAO.get(student.getStudentID()));
        userRegistration.updateProfessor(professor.getProfessorID(),"newpassword2");
        userRegistration.removeProfessor(professor.getProfessorID());
        assertNull(userRegistration.professorDAO.get(professor.getProfessorID()));
    }

}
