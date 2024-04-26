package edu.duke.ece651.team2.courseManagement;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import java.io.File;

public class BulkAddStudentsController extends UpdateSectionSuperController {
    @FXML Button ChooseFileButton;
    @FXML Text FilenameText;
    @FXML TextField DelimiterField;
    @FXML TextField LegalNameIndexField;
    @FXML TextField DisplayNameIndexField;
    @FXML TextField EmailIndexField;
    @FXML Button AddButton;

    FileChooser fileChooser;

    BulkAddStudentsController(CourseManagement model) {
        super(model, "/views/bulkAddStudents.fxml");

//        setFileChooser();
//        setChooseFileButton();
    }

    private void setFileChooser() {
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("Text Files", "*.txt"),
                new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
                new ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.aac"),
                new ExtensionFilter("All Files", "*.*"));
    }

    private void setChooseFileButton() {
        ChooseFileButton.setOnAction(actionEvent -> {
            File selectedFile = fileChooser.showOpenDialog(ChooseFileButton.getScene().getWindow());
            FilenameText.setText(selectedFile.getName());
        });
    }
}
