package edu.duke.ece651.team2.admin;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.io.IOContext;

import edu.duke.ece651.team2.shared.Section;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.stage.Stage;


public class UserRegistrationController {
    UserRegistrationView controller;

    public UserRegistrationController(UserRegistrationView controller) {
        this.controller = controller;
    }

    public void setGeneralController(UserRegistrationView controller){
        this.controller = controller;
    }

    public void StudentRegistration(Object source){
        Button b = (Button) source;
        Stage stage= (Stage) b.getScene().getWindow();
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/StudentRegPage.fxml"));
            loader.setControllerFactory(controller -> new UserRegistrationController(this.controller));
            TitledPane page =(TitledPane) loader.load();
            Scene newScene = new Scene(page);
            stage.setScene(newScene);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void ProfessorRegistration(Object source){
        Button b = (Button) source;
        Stage stage= (Stage) b.getScene().getWindow();
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/ProfessorRegPage.fxml"));
            loader.setControllerFactory(controller -> new UserRegistrationController(this.controller));
            TitledPane page =(TitledPane) loader.load();
            Scene newScene = new Scene(page);
            stage.setScene(newScene);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}
