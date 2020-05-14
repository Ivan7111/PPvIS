package com.company;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class TableGroup implements ComponentLinker{

    private HBox hBox;

    @Override
    public void link() {
        hBox = new HBox();

        TextField textField5 = new TextField();
        textField5.setPrefColumnCount(11);

        ObservableList<Table> data = FXCollections.observableArrayList();

        TableView<Table> table1 = new TableView<>();

        Button button6 = new Button("To table");
        button6.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                data.add(new Table(textField5.getText(), ""));
                textField5.clear();
            }
        });

        Button button7 = new Button("To the right");
        button7.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Table selectedItem = table1.getSelectionModel().getSelectedItem();
                if(!selectedItem.getFirstColumn().equals("")) {
                    selectedItem.setSecondColumn(selectedItem.getFirstColumn());
                    selectedItem.setFirstColumn("");
                    table1.refresh();
                }
            }
        });

        Button button8 = new Button("To the left");
        button8.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Table selectedItem = table1.getSelectionModel().getSelectedItem();
                if(!selectedItem.getSecondColumn().equals("")) {
                    selectedItem.setFirstColumn(selectedItem.getSecondColumn());
                    selectedItem.setSecondColumn("");
                    table1.refresh();
                }
            }
        });

        table1.setPrefWidth(250);
        table1.setPrefHeight(200);

        TableColumn<Table, String> column11 = new TableColumn<>("First column");

        TableColumn<Table, String> column12 = new TableColumn<>("Second column");

        column11.setCellValueFactory(new PropertyValueFactory<>("FirstColumn"));
        column12.setCellValueFactory(new PropertyValueFactory<>("SecondColumn"));

        table1.setItems(data);

        table1.getColumns().addAll(column11, column12);

        hBox.setPadding(new Insets(15, 20, 20, 12));
        hBox.getChildren().addAll(textField5, button6, button7, button8, table1);
        hBox.setBackground(new Background(new BackgroundFill(Color.ORANGE, CornerRadii.EMPTY, Insets.EMPTY)));
        hBox.setSpacing(10);
    }

    @Override
    public HBox component() {
        return this.hBox;
    }
}
