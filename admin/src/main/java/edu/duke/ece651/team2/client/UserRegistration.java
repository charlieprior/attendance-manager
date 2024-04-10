package edu.duke.ece651.team2.client;

import edu.duke.ece651.team2.shared.*;
import edu.duke.ece651.team2.server.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

public class UserRegistration {
  private final DAOFactory factory;
  /**
   * The BufferedReader used to read input from the user.
   */
  private final BufferedReader reader;
  /**
   * The PrintStream used to write output to the user.
   */
  private final PrintStream out;

  // constructor for UserRegistration
  public UserRegistration(BufferedReader reader, PrintStream out) {
    this.factory = new DAOFactory();
    this.reader = reader;
    this.out = out;
  }

  /**
   * Prints the specified prompt to the user.
   *
   * @param prompt The prompt to print to the user.
   */
  public void print(String prompt) {
    out.println(prompt);
  }

  /**
   * Prints the specified prompt to the user and reads the user's input.
   *
   * @param prompt The prompt to print to the user.
   * @return The user's input.
   * @throws IOException We will not handle this exception.
   */
  protected String printPromptAndRead(String prompt) throws IOException {
    out.println(prompt);
    return reader.readLine();
  }

  /**
   * Prints the specified prompt and reads a password from the user.
   *
   * @param prompt The prompt to print to the user.
   * @return The user's password.
   * @throws IOException We will not handle this exception.
   */
  public String printAndReceive(String prompt) throws IOException {
    out.println(prompt);
    return reader.readLine();
  }

  // can use an abstraction function for add id/password to database
  /**
   * Constructs a new university class and feeds it name and boolean
   * 
   * @param name    The name of the university
   * @param support The boolean for the support taken in by the University class
   *                constructor
   * @return the created University class variable
   */
  public University createUniversity(String name, boolean support) {
    // create new University
    return new University(name, support);
  }

  /**
   * Reads the user's input for a new University.
   * 
   * @return The new University object created from the user's input.
   * @throws IOException We will not handle this exception.
   */
  public University readUniversity() throws IOException {
    String name = printAndReceive("Whats the university?");
    String support = printPromptAndRead("Does it allow for change display name? y for yes");
    if (support.equals("y")) {
      return new University(name, true);
    }
    return new University(name, false);
  }

  /**
   * Adds a student into the database and their password
   * 
   * @param password   The password of the student's login
   * @param university
   */
  public void addStudent(Password password, University university) throws IOException {
    // add it to table password, get UniversityID from University
    StudentDAO studentDAO = new StudentDAO(factory);
    PasswordDAO passwordDAO = new PasswordDAO(factory);
    university = readUniversity();
    String prompt = "You are adding new Student, please provide the required info:\nWhats the student's legal name:";
    String legalName = printPromptAndRead(prompt);
    prompt = "Whats the student's display name:";
    String displayName = printPromptAndRead(prompt);
    prompt = "Whats the student's E-Mail:";
    String email = printPromptAndRead(prompt);
    Student student = new Student(legalName, email, displayName);
    studentDAO.create(student);
    passwordDAO.create(password);
  }

  /**
   * Adds a professor into the database and their password
   * 
   * @param password   The password of the student's login
   * @param university
   */
  public void addProfessor(Password password, University university) throws IOException {
    // add it to table password, get UniversityID from University
    ProfessorDAO professorDAO = new ProfessorDAO(factory);
    PasswordDAO passwordDAO = new PasswordDAO(factory);
    university = readUniversity();
    String prompt = "What's your legal name:";
    String name = printPromptAndRead(prompt);
    prompt = "What's your E-Mail:";
    String email = printPromptAndRead(prompt);
    Professor professor = new Professor(name, email, university);
    professorDAO.create(professor);
    passwordDAO.create(password);
  }

  /**
   * Removes the student from the database in UserRegistration
   * 
   * @param name        The name of the student
   * @param email       The email of the student
   * @param displayName The displayName of the student
   * @param password    The password of the student's login
   */
  public void removeStudent(String name, String email, String displayName,
      Password password) {
    // delete student from password table
    StudentDAO studentDAO = new StudentDAO(factory);
    PasswordDAO passwordDAO = new PasswordDAO(factory);
    Student student = new Student(name, email, displayName);
    studentDAO.remove(student);
    passwordDAO.remove(password);
  }

  /**
   * Removes a professor from the database and their password
   * 
   * @param name     The name of the student
   * @param email    The email of the student
   * @param password The password of the student's login
   */
  public void removeProfessor(String name, String email, Password password,
      University university) {
    // delete professor from password table
    ProfessorDAO professorDAO = new ProfessorDAO(factory);
    PasswordDAO passwordDAO = new PasswordDAO(factory);
    Professor professor = new Professor(name, email, university);
    professorDAO.remove(professor);
    passwordDAO.remove(password);
  }

  public void updateStudent(Student student, Password newPassword) {
    // set newPassword by id
    StudentDAO studentDAO = new StudentDAO(factory);
    PasswordDAO passwordDAO = new PasswordDAO(factory);
    studentDAO.update(student);
    passwordDAO.update(newPassword);
  }

  public void updateProfessor(Professor professor, Password newPassword) {
    // set newPassword by id
    ProfessorDAO professorDAO = new ProfessorDAO(factory);
    PasswordDAO passwordDAO = new PasswordDAO(factory);
    professorDAO.update(professor);
    passwordDAO.update(newPassword);
  }
}
