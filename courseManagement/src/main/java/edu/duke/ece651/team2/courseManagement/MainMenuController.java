package edu.duke.ece651.team2.courseManagement;

import edu.duke.ece651.team2.shared.University;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.List;

public class MainMenuController extends AnchorPane {
    @FXML Button UpdateCourseButton;
    @FXML Button DeleteCourseButton;
    @FXML Button AddCourseButton;
    @FXML Button UpdateSectionButton;
    @FXML Button DeleteSectionButton;
    @FXML Button AddSectionButton;
    @FXML Button AddStudentsButton;
    @FXML Button AddLecturesButton;
    @FXML Text welcomeText;

    private final CourseManagement model;

    public MainMenuController(CourseManagement model) {
        this.model = model;
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "/views/menu.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        welcomeText.setText("Welcome to course management for " + model.getUniversity().getName());
        setUpdateCourseButton();
    }

    private void setUpdateCourseButton() {
        UpdateCourseButton.setOnAction(actionEvent -> {
            UpdateCourseButton.getScene().setRoot(new ModifyCourseController(model));
        });
    }
}
