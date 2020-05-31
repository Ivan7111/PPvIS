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

        TextField nameField = new TextField();
        nameField.setPrefColumnCount(11);

        ObservableList<Table> data = FXCollections.observableArrayList();

        TableView<Table> table = new TableView<>();

        Button toTable = new Button("To table");
        toTable.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                data.add(new Table(nameField.getText(), ""));
                nameField.clear();
            }
        });

        Button moveRight = new Button("To the right");
        moveRight.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Table selectedItem = table.getSelectionModel().getSelectedItem();
                if(!selectedItem.getFirstColumn().equals("")) {
                    selectedItem.setSecondColumn(selectedItem.getFirstColumn());
                    selectedItem.setFirstColumn("");
                    table.refresh();
                }
            }
        });

        Button moveLeft = new Button("To the left");
        moveLeft.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Table selectedItem = table.getSelectionModel().getSelectedItem();
                if(!selectedItem.getSecondColumn().equals("")) {
                    selectedItem.setFirstColumn(selectedItem.getSecondColumn());
                    selectedItem.setSecondColumn("");
                    table.refresh();
                }
            }
        });

        table.setPrefWidth(250);
        table.setPrefHeight(200);

        TableColumn<Table, String> leftColumn = new TableColumn<>("First column");

        TableColumn<Table, String> rightColumn = new TableColumn<>("Second column");

        leftColumn.setCellValueFactory(new PropertyValueFactory<>("FirstColumn"));
        rightColumn.setCellValueFactory(new PropertyValueFactory<>("SecondColumn"));

        table.setItems(data);

        table.getColumns().addAll(leftColumn, rightColumn);

        hBox.setPadding(new Insets(15, 20, 20, 12));
        hBox.getChildren().addAll(nameField, toTable, moveRight, moveLeft, table);
        hBox.setBackground(new Background(new BackgroundFill(Color.ORANGE, CornerRadii.EMPTY, Insets.EMPTY)));
        hBox.setSpacing(10);
    }

    @Override
    public HBox component() {
        return this.hBox;
    }
}
