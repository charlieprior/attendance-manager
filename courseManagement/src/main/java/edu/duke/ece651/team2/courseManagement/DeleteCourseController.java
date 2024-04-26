package edu.duke.ece651.team2.courseManagement;

import edu.duke.ece651.team2.shared.Course;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

public class DeleteCourseController extends UpdateCourseSuperController {
    @FXML
    Button ConfirmButton;

    DeleteCourseController(CourseManagement model) {
        super(model, "/views/deleteCourse.fxml");

        setConfirmButton();
    }

    private void setConfirmButton() {
        ConfirmButton.setOnAction(actionEvent -> {
            Course course = CourseSelector.getValue();
            if(course == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a course", ButtonType.OK);
                alert.showAndWait();
                return;
            }
            model.removeCourse(course);
            ConfirmButton.getScene().setRoot(model.getMainMenuController());
        });
    }
}
