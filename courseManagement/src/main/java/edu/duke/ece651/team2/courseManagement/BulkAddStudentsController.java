package edu.duke.ece651.team2.courseManagement;

import edu.duke.ece651.team2.shared.Section;
import edu.duke.ece651.team2.shared.Student;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BulkAddStudentsController extends UpdateSectionSuperController {
    @FXML Button ChooseFileButton;
    @FXML Text FilenameText;
    @FXML CheckBox HeaderCheckBox;
    @FXML TextField DelimiterField;
    @FXML TextField LegalNameIndexField;
    @FXML TextField DisplayNameIndexField;
    @FXML TextField EmailIndexField;
    @FXML Button AddButton;

    FileChooser fileChooser;

    File selectedFile;

    BulkAddStudentsController(CourseManagement model) {
        super(model, "/views/bulkAddStudents.fxml");

        fileChooser = new FileChooser();
        setChooseFileButton();
        setAddButton();
    }

    private void setChooseFileButton() {
        ChooseFileButton.setOnAction(actionEvent -> {
            selectedFile = fileChooser.showOpenDialog(ChooseFileButton.getScene().getWindow());
            FilenameText.setText(selectedFile.getName());
        });
    }

    private void setAddButton() {
        AddButton.setOnAction(actionEvent -> {
            Section section = SectionSelector.getValue();
            if (section == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a section", ButtonType.OK);
                alert.showAndWait();
                return;
            }

            try {
                Integer legalNameIndex = Integer.valueOf(LegalNameIndexField.getText());
                Integer displayNameIndex = Integer.valueOf(DisplayNameIndexField.getText());
                Integer emailIndex = Integer.valueOf(EmailIndexField.getText());

                if (legalNameIndex.equals(displayNameIndex) ||
                        legalNameIndex.equals(emailIndex) ||
                        displayNameIndex.equals(emailIndex)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Indices must be unique", ButtonType.OK);
                    alert.showAndWait();
                    return;
                }

                String delimiter = DelimiterField.getText();

                if (delimiter.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a delimiter", ButtonType.OK);
                    alert.showAndWait();
                    return;
                }

                if (selectedFile == null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a file", ButtonType.OK);
                    alert.showAndWait();
                    return;
                }

                CSVLoader csvLoader = new CSVLoader();

                try {
                    List<String> lines = csvLoader.getLines(selectedFile.getAbsolutePath(), HeaderCheckBox.isSelected());
                    List<Student> students = csvLoader.getStudents(lines, delimiter, legalNameIndex, emailIndex, displayNameIndex, model.getUniversity().getId());
                    for (Student student : students) {
                        model.addStudentToSection(student, section);
                    }
                } catch (IOException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "File could not be read", ButtonType.OK);
                    alert.showAndWait();
                    AddButton.getScene().setRoot(model.getMainMenuController());
                    return;
                }

            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Indices are required to be numbers", ButtonType.OK);
                alert.showAndWait();
                return;
            }
            AddButton.getScene().setRoot(model.getMainMenuController());
        });
    }
}
