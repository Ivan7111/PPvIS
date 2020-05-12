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

    private static Thread thr;

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

        TextField textField6 = new TextField();
        textField6.setPrefColumnCount(3);

        TextField textField7 = new TextField();
        textField7.setPrefColumnCount(3);

        TableView table2 = new TableView();
        table2.setPrefWidth(350);
        table2.setPrefHeight(200);

        ObservableList<ObservableList<String>> data1 = FXCollections.observableArrayList();

        Button button9 = new Button("Gen table");
        button9.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                table2.getColumns().clear();
                data1.clear();
                int X = Integer.valueOf(textField6.getText());
                int Y = Integer.valueOf(textField7.getText());
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
                    table2.getColumns().add(column);
                }
                int ctr = 0;
                for(int i = 0; i < Y; i++) {
                    ObservableList<String> t = FXCollections.observableArrayList();
                    for(int j = 0; j < X; j++){
                        if(ctr < 4 && Math.random()*100 > 70){
                            t.add("Text");
                            ctr++;
                        }
                        else {
                            t.add("");
                        }
                    }
                    data1.add(t);
                }
                table2.setItems(data1);
            }
        });

        Button button10 = new Button("Start movement");
        button10.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                thr = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int Y = data1.size();
                        ObservableList<ObservableList<String>> temp;
                        while(true) {
                            temp = moveLeft(data1);
                            for(int i = 0; i < Y; i++){
                                data1.set(i, temp.get(i));
                            }
                            try{
                                Thread.sleep(500);
                            }catch (InterruptedException e){
                                return;
                            }
                            temp = moveUp(data1);
                            for(int i = 0; i < Y; i++){
                                data1.set(i, temp.get(i));
                            }
                            try {
                                Thread.sleep(500);
                            }catch(InterruptedException e){
                                return;
                            }
                        }
                    }
                });
                thr.setDaemon(true);
                thr.start();
            }
        });

        Button button11 = new Button("Stop movement");
        button11.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(thr.isAlive()){
                    thr.interrupt();
                }
            }
        });

        hBox.setPadding(new Insets(15, 20, 20, 12));
        hBox.getChildren().addAll(textField6, textField7, button9, button10, button11, table2);
        hBox.setBackground(new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
        hBox.setSpacing(10);
    }

    @Override
    public HBox component() {
        return this.hBox;
    }
}
