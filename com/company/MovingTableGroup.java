package com.company;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Callback;

public class MovingTableGroup implements ComponentLinker{

    private HBox hBox;

    private static Thread otherThread;

    private ObservableList<ObservableList<String>> moveLeft(ObservableList<ObservableList<String>> data){
        int Y = data.size();
        int X = data.get(0).size();

        for (int i = 0; i < Y; i++) {
            ObservableList<String> temp = data.get(i);
            int flag = 0;
            for (int j = X - 1; j >= 0; j--) {
                if (temp.get(j).equals("Text")) {
                    if (j == 0 && flag == 1) {
                        temp.set(j + 2, "Text");
                        continue;
                    }
                    if (j == 0 && flag == 2) {
                        continue;
                    }
                    if (j + 2 < X) {
                        temp.set(j, "");
                        temp.set(j + 2, "Text");
                    } else {
                        if (temp.get(0).equals("Text")) {
                            flag = 1;
                            temp.set(j, "");
                        } else {
                            flag = 2;
                            temp.set(j, "");
                            temp.set(0, "Text");
                        }
                    }
                }
            }
            data.set(i, temp);
        }

        return data;
    }

    private ObservableList<ObservableList<String>> moveUp(ObservableList<ObservableList<String>> data){
        int Y = data.size();
        int X = data.get(0).size();

        int[] flag = new int[X];
        for (int i = 0; i < Y; i++) {
            ObservableList<String> temp1 = data.get(i);
            ObservableList<String> temp2;
            if (i == 0) {
                temp2 = data.get(Y - 1);
            } else {
                temp2 = data.get(i - 1);
            }

            for (int j = 0; j < X; j++) {
                if (temp1.get(j).equals("Text")) {
                    if (i == Y - 1 && flag[j] == 1) {
                        temp2.set(j, "Text");
                        continue;
                    }
                    if (i == Y - 1 && flag[j] == 2) {
                        continue;
                    }
                    if (i > 0) {
                        temp1.set(j, "");
                        temp2.set(j, "Text");
                    } else {
                        if (temp2.get(j).equals("Text")) {
                            flag[j] = 1;
                            temp1.set(j, "");
                        } else {
                            flag[j] = 2;
                            temp1.set(j, "");
                            temp2.set(j, "Text");
                        }
                    }
                }
            }
            data.set(i, temp1);
            if (i == 0) {
                data.set(Y - 1, temp2);
            } else {
                data.set(i - 1, temp2);
            }
        }

        return data;
    }

    @Override
    public void link() {
        hBox = new HBox();

        TextField xCoordinateField = new TextField();
        xCoordinateField.setPrefColumnCount(3);

        TextField yCoordinateField = new TextField();
        yCoordinateField.setPrefColumnCount(3);

        TableView movingTable = new TableView();
        movingTable.setPrefWidth(350);
        movingTable.setPrefHeight(200);

        ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();

        Button generateButton = new Button("Gen table");
        generateButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                movingTable.getColumns().clear();
                data.clear();
                int X = Integer.valueOf(xCoordinateField.getText());
                int Y = Integer.valueOf(yCoordinateField.getText());
                ObservableList<String> temp = FXCollections.observableArrayList();
                for(int i = 0; i < X; i++){
                    final int f = i;
                    temp.add("");
                    TableColumn<ObservableList<String>, String> column = new TableColumn<>("Column" +(i+1));
                    column.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList<String>, String>, ObservableValue<String>>() {
                        @Override
                        public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList<String>, String> param) {
                            return new SimpleStringProperty(param.getValue().get(f).toString());
                        }
                    });
                    movingTable.getColumns().add(column);
                }
                int ctr = 0;
                for(int i = 0; i < Y; i++) {
                    ObservableList<String> tmp = FXCollections.observableArrayList();
                    for(int j = 0; j < X; j++){
                        if(ctr < 4 && Math.random()*100 > 70){
                            tmp.add("Text");
                            ctr++;
                        }
                        else {
                            tmp.add("");
                        }
                    }
                    data.add(tmp);
                }
                movingTable.setItems(data);
            }
        });

        Button startButton = new Button("Start movement");
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                otherThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int Y = data.size();
                        ObservableList<ObservableList<String>> temp;
                        while(true) {
                            temp = moveLeft(data);
                            for(int i = 0; i < Y; i++){
                                data.set(i, temp.get(i));
                            }
                            try{
                                Thread.sleep(500);
                            }catch (InterruptedException e){
                                return;
                            }
                            temp = moveUp(data);
                            for(int i = 0; i < Y; i++){
                                data.set(i, temp.get(i));
                            }
                            try {
                                Thread.sleep(500);
                            }catch(InterruptedException e){
                                return;
                            }
                        }
                    }
                });
                otherThread.setDaemon(true);
                otherThread.start();
            }
        });

        Button stopButton = new Button("Stop movement");
        stopButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(otherThread.isAlive()){
                    otherThread.interrupt();
                }
            }
        });

        hBox.setPadding(new Insets(15, 20, 20, 12));
        hBox.getChildren().addAll(xCoordinateField, yCoordinateField, generateButton, startButton, stopButton, movingTable);
        hBox.setBackground(new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
        hBox.setSpacing(10);
    }

    @Override
    public HBox component() {
        return this.hBox;
    }
}
