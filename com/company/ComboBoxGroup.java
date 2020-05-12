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

        TextField textField1 = new TextField();
        textField1.setPrefColumnCount(15);

        ComboBox<String> comboBox1 = new ComboBox<>();

        Vector<String> vector1 = new Vector<>();
        Button button1 = new Button("Add to ComboBox");
        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(vector1.contains(textField1.getText())){
                    showAlertAlreadyExist();
                    return;
                }
                vector1.addElement(textField1.getText());
                comboBox1.getItems().addAll(textField1.getText());
                textField1.clear();
            }
        });

        hBox.setPadding(new Insets(15, 20, 20, 12));
        hBox.getChildren().addAll(textField1, comboBox1, button1);
        hBox.setBackground(new Background(new BackgroundFill(Color.ORANGE, CornerRadii.EMPTY, Insets.EMPTY)));
        hBox.setSpacing(10);
    }

    @Override
    public HBox component() {
        return this.hBox;
    }
}
