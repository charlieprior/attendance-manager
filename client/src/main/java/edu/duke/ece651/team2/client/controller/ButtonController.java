package edu.duke.ece651.team2.client.controller;

import java.io.IOException;
import java.util.List;

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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.stage.Stage;

public class ButtonController {
    @FXML
    TextField logInFieldID;
    @FXML
    TextField logInFieldPassword;

    @FXML
    ComboBox<String> chooseSection;

    GeneralController controller;


    public ButtonController(GeneralController controller) {
        this.controller = controller;
    }


    public void setGeneralController(GeneralController controller){
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

    public void onReturnStudent(ActionEvent ae){
        Object source = ae.getSource();
        StudentLogIn(source);
    }


    public void onSubmitSection(ActionEvent ae) throws IOException{
        int selectedIndex = chooseSection.getSelectionModel().getSelectedIndex();
        System.out.println(""+selectedIndex);
        controller.sendObject(selectedIndex);
    }

    public void helperShowSection(List<String> res, Object source){
        Button b = (Button) source;
        Stage stage= (Stage) b.getScene().getWindow();
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/SectionChoice.fxml"));
            loader.setControllerFactory(controller -> new ButtonController(this.controller));
            TitledPane page =(TitledPane) loader.load();  
            ButtonController bcontroller = loader.getController();
            ComboBox<String> chooseSection = bcontroller.chooseSection;
            if (chooseSection != null) {
                // Set items for the ComboBox
                ObservableList<String> sections = FXCollections.observableArrayList(res);
                chooseSection.setItems(sections);
                Scene newScene = new Scene(page);
                stage.setScene(newScene);


                // int selectedIndex = chooseSection.getSelectionModel().getSelectedIndex();
                // controller.sendObject(selectedIndex);
            } else {
                System.out.println("ComboBox not found in FXML file.");
            }
            // loader.setControllerFactory(controller -> new ButtonController(this.controller));          
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onGetEmailPreference(ActionEvent ae){
        controller.studentFunctionality(1);
        List<String> res = controller.receiveAllEnrolledSectionAndSetChoice(1);
        if(res!=null && res.get(0).equals("ERROR")){
            showAlert(res.get(1));
        }
        else{
            Object source = ae.getSource();
            helperShowSection(res,source);
        }
    }

    @FXML
    public void onGetReport(ActionEvent ae){
        controller.studentFunctionality(2);
        List<String> res = controller.receiveAllEnrolledSectionAndSetChoice(2);
        if(res!=null && res.get(0).equals("ERROR")){
            showAlert(res.get(1));
        }
        else{
            Object source = ae.getSource();
            helperShowSection(res,source);
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
