package edu.duke.ece651.team2.client.controller;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

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

    public void showAlertTF(String prompt){
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Alert Message");
        alert.setContentText(prompt);
        ButtonType buttonTrue = new ButtonType("True");
        ButtonType buttonFalse = new ButtonType("False");
        alert.getButtonTypes().setAll(buttonTrue, buttonFalse);
        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == buttonTrue) {
                try {
                    controller.sendObject(1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } 
            else if (buttonType == buttonFalse) {
                try {
                    controller.sendObject(0);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }



    @FXML
    public void onLogInButton(ActionEvent ae) throws ClassNotFoundException{
        if(!controller.getConnected()){
            controller.connectToServer();
        }
        String[] credentials = new String[2];
        credentials[0] = logInFieldID.getText();
        credentials[1] = logInFieldPassword.getText();
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

    @FXML
    //havent handled the case on server!
    public void onReturnStudent(ActionEvent ae) throws IOException{
        controller.sendObject(-1);
        Object source = ae.getSource();
        StudentLogIn(source);
    }

    @FXML
    public void onReturnFaculty(ActionEvent ae) throws IOException{
        controller.sendObject(-1);
        Object source = ae.getSource();
        ProfessorLogIn(source);
    }


    @FXML
    public void onSubmitSection(ActionEvent ae) throws IOException{
        int selectedIndex = chooseSection.getSelectionModel().getSelectedIndex();
        controller.sendObject(selectedIndex);
        Object source = ae.getSource();
        StudentLogIn(source);
    }

    @FXML
    public void onSubmitSectionFaculty(ActionEvent ae) throws IOException{
        int selectedIndex = chooseSection.getSelectionModel().getSelectedIndex();
        controller.sendObject(selectedIndex);
        Object source = ae.getSource();
        ProfessorLogIn(source);
    }

    @FXML
    public void onSubmitSectionReceivePrompt(ActionEvent ae) throws IOException{
        onSubmitSection(ae);
        String prompt = controller.changeEmailPreferences();
        showAlertTF(prompt);
        prompt = controller.confirmFromServer();
        showAlert(prompt);
        onReturnStudent(ae);
    }

    public void helperShowSection(List<String> res, Object source,String file){
        System.out.println("Showing section...");
        Button b = (Button) source;
        Stage stage= (Stage) b.getScene().getWindow();
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(file));
            loader.setControllerFactory(controller -> new ButtonController(this.controller));
            TitledPane page =(TitledPane) loader.load();  
            ButtonController bcontroller = loader.getController();
            ComboBox<String> chooseSection = bcontroller.chooseSection;
            if (chooseSection != null) {
                ObservableList<String> sections = FXCollections.observableArrayList(res);
                chooseSection.setItems(sections);
                Scene newScene = new Scene(page);
                stage.setScene(newScene);
            } else {
                System.out.println("ComboBox not found in FXML file.");
            }        
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
            System.out.println("Get Sections email Pre");
            Object source = ae.getSource();
            helperShowSection(res,source,"/ui/SectionChoice1.fxml");
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
            helperShowSection(res,source,"/ui/SectionChoice.fxml");
        }
    }

    public List<String> helperReadName(List<Section> sec){
        List<String> res = new ArrayList<>();
        for(Section s:sec){
            res.add("ID: "+s.getSectionID()+" Name: "+s.getName());
        }
        return res;
    }

    @FXML
    public void onSetFaculty(ActionEvent ae) throws ClassNotFoundException{
        controller.professorFunctionality(4);
        List<Section> sections = controller.chooseSection();
        List<String> res = helperReadName(sections);
        Object source = ae.getSource();
        helperShowSection(res,source,"/ui/SectionChoice2.fxml");
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
