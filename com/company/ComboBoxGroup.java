package com.company;

import java.util.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;

public class ComboBoxGroup implements ComponentLinker{

    private HBox hBox;

    private void showAlertAlreadyExist() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Alert");
        alert.setHeaderText(null);
        alert.setContentText("This element already exist!");
        alert.showAndWait();
    }

    @Override
    public void link() {
        hBox = new HBox();

        TextField nameField = new TextField();
        nameField.setPrefColumnCount(15);

        ComboBox<String> nameBox = new ComboBox<>();

        ArrayList<String> optList = new ArrayList<>();
        Button addToComboBox = new Button("Add to ComboBox");
        addToComboBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(optList.contains(nameField.getText())){
                    showAlertAlreadyExist();
                    return;
                }
                optList.add(nameField.getText());
                nameBox.getItems().addAll(nameField.getText());
                nameField.clear();
            }
        });

        hBox.setPadding(new Insets(15, 20, 20, 12));
        hBox.getChildren().addAll(nameField, nameBox, addToComboBox);
        hBox.setBackground(new Background(new BackgroundFill(Color.ORANGE, CornerRadii.EMPTY, Insets.EMPTY)));
        hBox.setSpacing(10);
    }

    @Override
    public HBox component() {
        return this.hBox;
    }
}
