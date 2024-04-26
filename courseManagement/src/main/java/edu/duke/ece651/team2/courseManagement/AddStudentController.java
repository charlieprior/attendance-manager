package edu.duke.ece651.team2.courseManagement;

import edu.duke.ece651.team2.shared.Course;
import edu.duke.ece651.team2.shared.Section;
import edu.duke.ece651.team2.shared.Student;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class AddStudentController extends UpdateSectionSuperController {
    @FXML TextField LegalNameField;
    @FXML TextField DisplayNameField;
    @FXML TextField EmailField;
    @FXML Button AddButton;

    AddStudentController(CourseManagement model) {
        super(model, "/views/addStudent.fxml");

        setAddButton();
    }

    private void setAddButton() {
        AddButton.setOnAction(actionEvent -> {
            Section section = SectionSelector.getValue();
            if(section == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a section", ButtonType.OK);
                alert.showAndWait();
                return;
            }
            String legalName = LegalNameField.getText();
            String displayName = DisplayNameField.getText();
            String email = EmailField.getText();
            if(legalName.isEmpty() || displayName.isEmpty() || email.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "All fields are required", ButtonType.OK);
                alert.showAndWait();
                return;
            }
            Student student = new Student(legalName, email, model.getUniversity().getId(), displayName);
            model.addStudentToSection(student, section);
            AddButton.getScene().setRoot(model.getMainMenuController());
        });
    }
}
