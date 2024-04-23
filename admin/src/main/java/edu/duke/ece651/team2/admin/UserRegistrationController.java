package edu.duke.ece651.team2.admin;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Objects;

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
    @FXML
    TextField studentLegalName;
    @FXML
    TextField studentDisplayName;
    @FXML
    TextField studentEmail;
    @FXML
    TextField studentPassword;
    @FXML
    TextField studentIDNumber;
    @FXML
    ComboBox<String> chooseUniversity;

    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    UserRegistration userRegistration = new UserRegistration();
    UserRegistrationView controller = new UserRegistrationView(System.out, userRegistration, input);

    public void showAlert(String prompt){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Alert Message");
        alert.setContentText(prompt);
        alert.showAndWait();
    }

    @FXML
    public void handleStudentRegistration(ActionEvent event){
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/StudentOptions.fxml"));
            TitledPane page = (TitledPane) loader.load();
            Scene newScene = new Scene(page);
            stage.setScene(newScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void goToAddStudent(ActionEvent event){
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/AddStudent.fxml"));
            TitledPane page = (TitledPane) loader.load();
            Scene newScene = new Scene(page);
            stage.setScene(newScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onAddStudentSubmit(ActionEvent event) throws ClassNotFoundException{
        String[] credentials = new String[4];
        credentials[0] = studentLegalName.getText();
        credentials[1] = studentDisplayName.getText();
        credentials[2] = studentEmail.getText();
        credentials[3] = studentPassword.getText();
        //int selectedIndex = chooseUniversity.getSelectionModel().getSelectedIndex();
        int res = controller.addStudentController(credentials);
        //Object source = event.getSource();
        if(res != 0 ||
                !Objects.equals(credentials[0], "") || !Objects.equals(credentials[1], "") ||
                !Objects.equals(credentials[2], "") || !Objects.equals(credentials[3], "")){
            showAlert("Sign-Up Successful!");
        }
        else{
            showAlert("Sign-Up Failed, please check and type again!");
        }
    }

    @FXML
    public void goToRemoveStudent(ActionEvent event){
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/RemoveStudent.fxml"));
            TitledPane page = (TitledPane) loader.load();
            Scene newScene = new Scene(page);
            stage.setScene(newScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onRemoveStudentSubmit(){
        String[] credentials = new String[1];
        credentials[0] = studentIDNumber.getText();
        int res = controller.removeStudentController(credentials);
        if(res != 0 && !Objects.equals(credentials[0], "")){
            showAlert("Removal Successful!");
        }
        else{
            showAlert("Removal Failed, please check and type again!");
        }
    }

    @FXML
    public void goToUpdateStudent(ActionEvent event){
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/UpdateStudent.fxml"));
            TitledPane page = (TitledPane) loader.load();
            Scene newScene = new Scene(page);
            stage.setScene(newScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onUpdateStudentSubmit(){
        String[] credentials = new String[2];
        credentials[0] = studentIDNumber.getText();
        credentials[1] = studentPassword.getText();
        int res = controller.updateStudentController(credentials);
        if(res != 0 || !Objects.equals(credentials[0], "")
                    || !Objects.equals(credentials[1], "")){
            showAlert("Update Successful!");
        }
        else{
            showAlert("Update Failed, please check and type again!");
        }
    }

    @FXML
    public void handleProfessorRegistration(ActionEvent event) {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/FacultyOptions.fxml"));
            TitledPane page = (TitledPane) loader.load();
            Scene newScene = new Scene(page);
            stage.setScene(newScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void returnToMainRegistration(ActionEvent event){
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/UserSelect.fxml"));
            TitledPane page = (TitledPane) loader.load();
            Scene newScene = new Scene(page);
            stage.setScene(newScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleExit(ActionEvent event) {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        stage.close();
    }

}
