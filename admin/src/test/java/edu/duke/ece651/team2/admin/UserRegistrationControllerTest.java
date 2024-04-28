package edu.duke.ece651.team2.admin;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.util.WaitForAsyncUtils;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)
public class UserRegistrationControllerTest {


    private UserRegistrationView mockController;
    private UserRegistrationController userController;
    TextField studentLegalName;
    TextField studentDisplayName;
    TextField studentEmail;
    TextField studentPassword;
    TextField studentIDNumber;
    TextField facultyLegalName;
    TextField facultyEmail;
    TextField facultyPassword;
    TextField facultyIDNumber;

    @BeforeEach
    void setUp() {
        // Create a mock GeneralController
        mockController = Mockito.mock(UserRegistrationView.class);
        userController = new UserRegistrationController(mockController);
        studentLegalName = new TextField();
        studentDisplayName = new TextField();
        studentEmail = new TextField();
        studentPassword = new TextField();
        studentIDNumber = new TextField();
        facultyLegalName = new TextField();
        facultyEmail = new TextField();
        facultyPassword = new TextField();
        facultyIDNumber = new TextField(); 
        //buttonController.setlogInFieldID(logInFieldID);
        //buttonController.setlogInFieldPassword(logInFieldPassword);
    }

}
