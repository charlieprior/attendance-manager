package edu.duke.ece651.team2.admin;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.io.*;
import java.net.*;

import edu.duke.ece651.team2.server.DAOFactory;
import edu.duke.ece651.team2.server.UniversityDAO;
import edu.duke.ece651.team2.shared.University;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TitledPane;
import javafx.stage.Stage;
import javafx.application.Application;

public class App extends Application{
  private final UserRegistrationView userRegistrationView;

  /**
   * Constructs a new App object for the UserRegistration Class
   * 
   * @param userRegistrationView The registration view class being initialized
   */
  public App(UserRegistrationView userRegistrationView) {
    this.userRegistrationView = userRegistrationView;
  }

  public void readUniversities(String filename) throws IOException{
      UniversityDAO universityDAO = new UniversityDAO(new DAOFactory());
      BufferedReader br = new BufferedReader(new FileReader(filename));
      String line = br.readLine();
      while ((line = br.readLine()) != null) {
        // Split the line by comma
        String[] data = line.split(",");
        boolean support;
        if(data[1].equals("False")){
          support = false;
        }
        else{
          support = true;
        }
        universityDAO.create(new University(data[0], support));
    }
  }

  /**
   * The userRegistration method for the Attendance Manager application.
   * 
   * @throws IOException We will not handle this exception.
   */
  public void userRegistrationApp() throws IOException {
    readUniversities("universities.csv");
    //userRegistrationView.menuOptions();
  }

  /**
   * The main method for the Attendance Manager application.
   * 
   * @param args The command-line arguments.
   * @throws IOException We will not handle this exception.
   */
  /*public static void main(String[] args) throws IOException {
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    UserRegistration userRegistration = new UserRegistration();
    UserRegistrationView userRegistrationView = new UserRegistrationView(System.out, userRegistration, input);
    App app = new App(userRegistrationView);
    app.userRegistrationApp();
  }*/
  public void start(Stage stage) throws IOException {
    URL xmlResource = getClass().getResource("/ui/UserSelect.fxml");
    URL cssResource = getClass().getResource("/ui/settings.css");
    FXMLLoader loader = new FXMLLoader(xmlResource);
    //loader.setControllerFactory(controller -> new ButtonController(new GeneralController()));
    TitledPane tp = loader.load();
    Scene scene = new Scene(tp, 640, 480);
    scene.getStylesheets().add(cssResource.toString());
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    launch();
  }

}
