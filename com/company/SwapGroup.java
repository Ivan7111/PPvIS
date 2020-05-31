package com.company;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;

public class SwapGroup implements ComponentLinker{

    private HBox hBox;

    @Override
    public void link() {
        hBox = new HBox();

        TextField nameField = new TextField();
        nameField.setPrefColumnCount(15);

        Button switchButton = new Button("Switch text");
        Button setButton = new Button("Set text");
        setButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                switchButton.setText(nameField.getText());
                nameField.clear();
            }
        });

        switchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String str;
                str = setButton.getText();
                setButton.setText(switchButton.getText());
                switchButton.setText(str);
            }
        });

        hBox.setPadding(new Insets(15, 20, 20, 12));
        hBox.getChildren().addAll(nameField, setButton, switchButton);
        hBox.setBackground(new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
        hBox.setSpacing(10);
    }

    @Override
    public HBox component() {
        return this.hBox;
    }
}
