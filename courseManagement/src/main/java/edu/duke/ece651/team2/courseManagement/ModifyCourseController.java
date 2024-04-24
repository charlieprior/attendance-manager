package edu.duke.ece651.team2.courseManagement;

import edu.duke.ece651.team2.shared.Course;
import edu.duke.ece651.team2.shared.University;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

import java.io.IOException;
import java.util.List;

public class ModifyCourseController extends AnchorPane {
    @FXML ComboBox<Course> CourseSelector;
    @FXML TextField CourseNameField;
    @FXML Button ConfirmButton;
    @FXML Button CancelButton;

    private final CourseManagement model;

    ModifyCourseController(CourseManagement model) {
        this.model = model;
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "/views/modifyCourse.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        setCourses(model.listCourses());
        setCancelButton();
    }

    public void setCourses(List<Course> courses) {
        CourseSelector.getItems().addAll(courses);
        Callback<ListView<Course>, ListCell<Course>> cellFactory = new Callback<ListView<Course>, ListCell<Course>>() {
            @Override
            public ListCell<Course> call(ListView<Course> universityListView) {
                return new ListCell<Course>() {
                    @Override
                    public void updateItem(Course item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            setText(item.getName());
                        }
                    }
                };
            }
        };

        CourseSelector.setCellFactory(cellFactory);
        CourseSelector.setButtonCell(cellFactory.call(null));
    }

    private void setCancelButton() {
        CancelButton.setOnAction(actionEvent -> {
            CancelButton.getScene().setRoot(model.getMainMenuController());
        });
    }

}
