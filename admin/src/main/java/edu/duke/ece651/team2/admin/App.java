package edu.duke.ece651.team2.admin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class App {
  private final UserRegistrationView userRegistrationView;

  /**
   * Constructs a new App object for the UserRegistration
   * 
   * @param userRegistrationView The registration view class being initialized
   */
  public App(UserRegistrationView userRegistrationView) {
    this.userRegistrationView = userRegistrationView;
  }

  /**
   * The userRegistration method for the Attendance Manager application.
   * 
   * @throws IOException We will not handle this exception.
   */
  public void userRegistrationApp() throws IOException {
    userRegistrationView.menuOptions();
  }

  /**
   * The main method for the Attendance Manager application.
   * 
   * @param args The command-line arguments.
   * @throws IOException We will not handle this exception.
   */
  public static void main(String[] args) throws IOException {
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    UserRegistration userRegistration = new UserRegistration();
    UserRegistrationView userRegistrationView = new UserRegistrationView(System.out, userRegistration, input);
    App app = new App(userRegistrationView);
    app.userRegistrationApp();
  }
}
