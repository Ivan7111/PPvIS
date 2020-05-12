package com.company;

import java.util.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;

public class RadioButtonGroup implements ComponentLinker{

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

        TextField textField3 = new TextField();
        textField3.setPrefColumnCount(11);

        Vector<String> vector2 = new Vector<>();
        ToggleGroup tGroup1 = new ToggleGroup();
        RadioButton rButton1 = new RadioButton("Option 1");
        rButton1.setToggleGroup(tGroup1);
        rButton1.setSelected(true);
        vector2.addElement(rButton1.getText());

        RadioButton rButton2 = new RadioButton("Option 2");
        rButton2.setToggleGroup(tGroup1);
        vector2.addElement(rButton2.getText());

        RadioButton rButton3 = new RadioButton("Option 3");
        rButton3.setToggleGroup(tGroup1);
        vector2.addElement(rButton3.getText());

        Button button4 = new Button("Choose");
        button4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String str = textField3.getText();
                if(!vector2.contains(str)){
                    showAlertNotExist();
                    return;
                }
                if(vector2.indexOf(str) == 0){
                    rButton1.setSelected(true);
                }
                if(vector2.indexOf(str) == 1){
                    rButton2.setSelected(true);
                }
                if(vector2.indexOf(str) == 2){
                    rButton3.setSelected(true);
                }
            }
        });

        hBox.setPadding(new Insets(15, 20, 20, 12));
        hBox.getChildren().addAll(textField3, rButton1, rButton2, rButton3, button4);
        hBox.setBackground(new Background(new BackgroundFill(Color.ORANGE, CornerRadii.EMPTY, Insets.EMPTY)));
        hBox.setSpacing(10);
    }

    @Override
    public HBox component() {
        return this.hBox;
    }
}
