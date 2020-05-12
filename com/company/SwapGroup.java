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

        TextField textField2 = new TextField();
        textField2.setPrefColumnCount(15);

        Button button3 = new Button("Switch text");
        Button button2 = new Button("Set text");
        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                button3.setText(textField2.getText());
                textField2.clear();
            }
        });

        button3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String str;
                str = button2.getText();
                button2.setText(button3.getText());
                button3.setText(str);
            }
        });

        hBox.setPadding(new Insets(15, 20, 20, 12));
        hBox.getChildren().addAll(textField2, button2, button3);
        hBox.setBackground(new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
        hBox.setSpacing(10);
    }

    @Override
    public HBox component() {
        return this.hBox;
    }
}
