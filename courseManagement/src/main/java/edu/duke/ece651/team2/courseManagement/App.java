/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package edu.duke.ece651.team2.courseManagement;

import edu.duke.ece651.team2.server.DAOFactory;
import edu.duke.ece651.team2.server.UniversityDAO;
import edu.duke.ece651.team2.shared.University;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.checkerframework.checker.units.qual.C;

import java.util.List;

public class App extends Application {

    private List<University> universityNames() {
        DAOFactory daoFactory = new DAOFactory();
        UniversityDAO uniDAO = new UniversityDAO(daoFactory);
        return uniDAO.list();
    }

//    private final CourseManagementController controller;

//    public App(CourseManagementController controller) {
//        this.controller = controller;
//    }

//    public static void main(String[] args) throws IOException {
////        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
////        SelectUniversity select = new SelectUniversity(System.out, input);
////        University university = select.readUniversity();
////        CourseManagement model = new CourseManagement(university);
////        CourseManagementController controller = new CourseManagementController(model, System.out, input);
////
////        App app = new App(controller);
////        app.run();
//    }

    @Override
    public void start(Stage stage) throws Exception {
        LoginController login = new LoginController(universityNames());
        stage.setScene(new Scene(login));
        stage.setWidth(600);
        stage.setHeight(400);
        stage.show();
    }

//    public void run() throws IOException {
//        while(!controller.isShouldExit()) {
//            try {
//                controller.chooseOption();
//            } catch (IllegalArgumentException e) {
//                controller.invalidSelection();
//            }
//        }
//    }
}
