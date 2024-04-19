package edu.duke.ece651.team2.client.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.stage.Stage;

public class ButtonController {
    @FXML
    TextField logInFieldID;
    @FXML
    TextField logInFieldPassword;

    GeneralController controller;


    public ButtonController(GeneralController controller) {
        this.controller = controller;
    }

    public void StudentLogIn(Object source){
        Button b = (Button) source;
        Stage stage= (Stage) b.getScene().getWindow();
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/StudentPage.fxml"));
            loader.setControllerFactory(controller -> new ButtonController(this.controller));
            TitledPane page =(TitledPane) loader.load();
            Scene newScene = new Scene(page);
            stage.setScene(newScene);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void ProfessorLogIn(Object source){
        Button b = (Button) source;
        Stage stage= (Stage) b.getScene().getWindow();
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/ProfessorPage.fxml"));
            loader.setControllerFactory(controller -> new ButtonController(this.controller));
            TitledPane page =(TitledPane) loader.load();            
            Scene newScene = new Scene(page);
            stage.setScene(newScene);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void showAlert(String prompt){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Alert Message");
        alert.setContentText(prompt);
        alert.showAndWait();
    }



    @FXML
    public void onLogInButton(ActionEvent ae) throws ClassNotFoundException{
        if(!controller.getConnected()){
            controller.connectToServer();
        }
        String[] credentials = new String[2];
        credentials[0] = logInFieldID.getText();
        credentials[1] = logInFieldPassword.getText();
        // System.out.println(credentials[0]+" ,"+credentials[1]);
        int res = controller.login(credentials);
        Object source = ae.getSource();
        if(res==1){
            StudentLogIn(source);
        }
        else if(res==2){
            ProfessorLogIn(source);
        }
        else{
            showAlert("Log In fail, please check and type again!");
        }
    }


    public void helperQuit(Object source){
        Button b = (Button) source;
        Stage stage = (Stage) b.getScene().getWindow();
        stage.close();

        return;
    }

    @FXML
    public void onExitButtonStudent(ActionEvent ae){

        controller.studentFunctionality(3);

        Object source = ae.getSource();
        helperQuit(source);

    }

    @FXML
    public void onExitButtonFaculty(ActionEvent ae) throws ClassNotFoundException{

        controller.professorFunctionality(5);
        Object source = ae.getSource();
        helperQuit(source);
    }

}
