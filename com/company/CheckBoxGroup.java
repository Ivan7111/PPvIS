package com.company;

import java.util.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class CheckBoxGroup implements ComponentLinker{

    private HBox hBox;

    private void showAlertNotExist() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Alert");

        // Header Text: null
        alert.setHeaderText(null);
        alert.setContentText("This option does not exist!");

        alert.showAndWait();
    }

    @Override
    public void link() {
        hBox = new HBox();

        TextField textField4 = new TextField();
        textField4.setPrefColumnCount(11);

        Vector<String> vector3 = new Vector<>();
        CheckBox checkBox1 = new CheckBox("Option 1");
        vector3.addElement(checkBox1.getText());

        CheckBox checkBox2 = new CheckBox("Option 2");
        vector3.addElement(checkBox2.getText());

        CheckBox checkBox3 = new CheckBox("Option 3");
        vector3.addElement(checkBox3.getText());

        Button button5 = new Button("Choose");
        button5.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String str = textField4.getText();
                if(!vector3.contains(str)){
                    showAlertNotExist();
                    return;
                }
                if(vector3.indexOf(str) == 0){
                    checkBox1.fire();
                }
                if(vector3.indexOf(str) == 1){
                    checkBox2.fire();
                }
                if(vector3.indexOf(str) == 2){
                    checkBox3.fire();
                }
            }
        });

        hBox.setPadding(new Insets(15, 20, 20, 12));
        hBox.getChildren().addAll(textField4, checkBox1, checkBox2, checkBox3, button5);
        hBox.setBackground(new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
        hBox.setSpacing(10);
    }

    @Override
    public HBox component() {
        return this.hBox;
    }
}
