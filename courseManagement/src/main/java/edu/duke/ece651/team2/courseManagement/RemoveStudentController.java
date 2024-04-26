package edu.duke.ece651.team2.courseManagement;

import edu.duke.ece651.team2.shared.Section;
import edu.duke.ece651.team2.shared.Student;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;

public class RemoveStudentController extends UpdateSectionSuperController {
    @FXML ComboBox<Student> StudentSelector;
    @FXML Button DeleteButton;
    RemoveStudentController(CourseManagement model) {
        super(model, "/views/removeStudent.fxml");
        setDeleteButton();
    }

    @FXML public void sectionSelected() {
        Section section = SectionSelector.getValue();
        StudentSelector.getItems().clear();
        StudentSelector.getItems().addAll(model.getStudentsBySection(section));
        Callback<ListView<Student>, ListCell<Student>> cellFactory = new Callback<ListView<Student>, ListCell<Student>>() {
            @Override
            public ListCell<Student> call(ListView<Student> universityListView) {
                return new ListCell<Student>() {
                    @Override
                    public void updateItem(Student item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            setText(item.getLegalName());
                        }
                    }
                };
            }
        };

        StudentSelector.setCellFactory(cellFactory);
        StudentSelector.setButtonCell(cellFactory.call(null));
    }

    private void setDeleteButton() {
        DeleteButton.setOnAction(actionEvent -> {
            Student student = StudentSelector.getValue();
            if(student == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a student", ButtonType.OK);
                alert.showAndWait();
                return;
            }
            model.removeStudent(student);
            DeleteButton.getScene().setRoot(model.getMainMenuController());
        });
    }
}
