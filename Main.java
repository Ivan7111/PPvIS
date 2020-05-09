package com.company;

import java.util.*;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.layout.FlowPane;
import javafx.scene.control.Alert.AlertType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleStringProperty;
import javafx.util.Callback;

public class Main extends Application{

    public static void main(String[] args) {
        Application.launch(args);
    }

    public static class Table{
        private final SimpleStringProperty firstColumn;
        private final SimpleStringProperty secondColumn;

        private Table(String first, String second){
            this.firstColumn = new SimpleStringProperty(first);
            this.secondColumn = new SimpleStringProperty(second);
        }

        public String getFirstColumn(){
            return firstColumn.get();
        }
        public void setFirstColumn(String str){
            firstColumn.set(str);
        }

        public String getSecondColumn(){
            return secondColumn.get();
        }
        public void setSecondColumn(String str){
            secondColumn.set(str);
        }
    }

    private void showAlertAlreadyExist() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Alert");

        // Header Text: null
        alert.setHeaderText(null);
        alert.setContentText("This element already exist!");

        alert.showAndWait();
    }

    private void showAlertNotExist() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Alert");

        // Header Text: null
        alert.setHeaderText(null);
        alert.setContentText("This option does not exist!");

        alert.showAndWait();
    }

    private ObservableList<ObservableList<String>> moveLeft(ObservableList<ObservableList<String>> data){
        int Y = data.size();
        int X = data.get(0).size();

        for (int i = 0; i < Y; i++) {
            ObservableList<String> temp = data.get(i);
            int flag = 0;
            for (int j = X - 1; j >= 0; j--) {
                if (temp.get(j) == "Text") {
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
                        if (temp.get(0) == "Text") {
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
                if (temp1.get(j) == "Text") {
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
                        if (temp2.get(j) == "Text") {
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

    static Thread thr;

    @Override
    public void start(Stage stage){

        Group group1 = new Group();

        TextField textField1 = new TextField();
        textField1.setPrefColumnCount(15);
        textField1.setLayoutY(60);
        textField1.setLayoutX(20);

        ComboBox<String> comboBox1 = new ComboBox<>();
        comboBox1.setLayoutY(20);
        comboBox1.setLayoutX(140);

        Vector<String> vector1 = new Vector<>();
        Button button1 = new Button("Add to ComboBox");
        button1.setLayoutY(20);
        button1.setLayoutX(20);
        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(vector1.contains(textField1.getText())){
                    showAlertAlreadyExist();
                    return;
                }
                vector1.addElement(textField1.getText());
                comboBox1.getItems().addAll(textField1.getText());
            }
        });

        group1.getChildren().addAll(button1, textField1, comboBox1);

        Group group2 = new Group();

        TextField textField2 = new TextField();
        textField2.setPrefColumnCount(15);
        textField2.setLayoutY(60);
        textField2.setLayoutX(20);

        Button button3 = new Button("Switch text");
        Button button2 = new Button("Set text");
        button2.setLayoutY(20);
        button2.setLayoutX(20);
        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                button3.setText(textField2.getText());
            }
        });

        button3.setLayoutY(20);
        button3.setLayoutX(100);
        button3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String str;
                str = button2.getText();
                button2.setText(button3.getText());
                button3.setText(str);
            }
        });

        group2.getChildren().addAll(textField2, button2, button3);

        Group group3 = new Group();

        TextField textField3 = new TextField();
        textField3.setPrefColumnCount(11);
        textField3.setLayoutY(20);
        textField3.setLayoutX(100);

        Vector<String> vector2 = new Vector<>();
        ToggleGroup tgroup1 = new ToggleGroup();
        RadioButton rbutton1 = new RadioButton("Option 1");
        rbutton1.setToggleGroup(tgroup1);
        rbutton1.setLayoutY(20);
        rbutton1.setLayoutX(20);
        rbutton1.setSelected(true);
        vector2.addElement(rbutton1.getText());

        RadioButton rbutton2 = new RadioButton("Option 2");
        rbutton2.setToggleGroup(tgroup1);
        rbutton2.setLayoutY(40);
        rbutton2.setLayoutX(20);
        vector2.addElement(rbutton2.getText());

        RadioButton rbutton3 = new RadioButton("Option 3");
        rbutton3.setToggleGroup(tgroup1);
        rbutton3.setLayoutY(60);
        rbutton3.setLayoutX(20);
        vector2.addElement(rbutton3.getText());

        Button button4 = new Button("Choose");
        button4.setLayoutY(60);
        button4.setLayoutX(100);
        button4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String str = textField3.getText();
                if(!vector2.contains(str)){
                    showAlertNotExist();
                    return;
                }
                if(vector2.indexOf(str) == 0){
                    rbutton1.setSelected(true);
                }
                if(vector2.indexOf(str) == 1){
                    rbutton2.setSelected(true);
                }
                if(vector2.indexOf(str) == 2){
                    rbutton3.setSelected(true);
                }
            }
        });

        group3.getChildren().addAll(textField3, rbutton1, rbutton2, rbutton3, button4);

        Group group4 = new Group();

        TextField textField4 = new TextField();
        textField4.setPrefColumnCount(11);
        textField4.setLayoutY(20);
        textField4.setLayoutX(100);

        Vector<String> vector3 = new Vector<>();
        CheckBox checkBox1 = new CheckBox("Option 1");
        checkBox1.setLayoutY(20);
        checkBox1.setLayoutX(20);
        vector3.addElement(checkBox1.getText());

        CheckBox checkBox2 = new CheckBox("Option 2");
        checkBox2.setLayoutY(40);
        checkBox2.setLayoutX(20);
        vector3.addElement(checkBox2.getText());

        CheckBox checkBox3 = new CheckBox("Option 3");
        checkBox3.setLayoutY(60);
        checkBox3.setLayoutX(20);
        vector3.addElement(checkBox3.getText());

        Button button5 = new Button("Choose");
        button5.setLayoutY(60);
        button5.setLayoutX(100);
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

        group4.getChildren().addAll(textField4, checkBox1, checkBox2, checkBox3, button5);

        Group group5 = new Group();

        TextField textField5 = new TextField();
        textField5.setPrefColumnCount(11);
        textField5.setLayoutY(20);
        textField5.setLayoutX(20);

        ObservableList<Table> data = FXCollections.observableArrayList();

        TableView<Table> table1 = new TableView<>();

        Button button6 = new Button("To table");
        button6.setLayoutY(60);
        button6.setLayoutX(20);
        button6.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                data.add(new Table(
                        textField5.getText(),
                        ""));
            }
        });

        Button button7 = new Button("To the right");
        button7.setLayoutY(100);
        button7.setLayoutX(20);
        button7.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Table selectedItem = table1.getSelectionModel().getSelectedItem();
                if(selectedItem.getFirstColumn() != "") {
                    selectedItem.setSecondColumn(selectedItem.getFirstColumn());
                    selectedItem.setFirstColumn("");
                    table1.refresh();
                }
            }
        });

        Button button8 = new Button("To the left");
        button8.setLayoutY(140);
        button8.setLayoutX(20);
        button8.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Table selectedItem = table1.getSelectionModel().getSelectedItem();
                if(selectedItem.getSecondColumn() != "") {
                    selectedItem.setFirstColumn(selectedItem.getSecondColumn());
                    selectedItem.setSecondColumn("");
                    table1.refresh();
                }
            }
        });

        table1.setPrefWidth(250);
        table1.setPrefHeight(200);
        table1.setLayoutY(60);
        table1.setLayoutX(100);

        TableColumn<Table, String> column11 = new TableColumn<>("First column");

        TableColumn<Table, String> column12 = new TableColumn<>("Second column");

        column11.setCellValueFactory(new PropertyValueFactory<>("FirstColumn"));
        column12.setCellValueFactory(new PropertyValueFactory<>("SecondColumn"));

        table1.setItems(data);

        table1.getColumns().addAll(column11, column12);

        group5.getChildren().addAll(textField5, button6, button7, button8, table1);

        Group group6 = new Group();

        TextField textField6 = new TextField();
        textField6.setLayoutY(20);
        textField6.setLayoutX(20);
        textField6.setPrefColumnCount(4);

        TextField textField7 = new TextField();
        textField7.setLayoutY(20);
        textField7.setLayoutX(90);
        textField7.setPrefColumnCount(4);

        TableView table2 = new TableView();
        table2.setLayoutY(120);
        table2.setLayoutX(20);
        table2.setPrefWidth(350);
        table2.setPrefHeight(200);

        ObservableList<ObservableList<String>> data1 = FXCollections.observableArrayList();

        Button button9 = new Button("Gen table");
        button9.setLayoutY(60);
        button9.setLayoutX(20);
        button9.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                table2.getColumns().clear();
                data1.clear();
                /*for(int i = 0; i < Integer.valueOf(textField6.getText()); i++){
                    Vector<String> temp = new Vector<>();
                    for(int j = 0; j < Integer.valueOf(textField7.getText()); j++){
                        temp.add("");
                    }
                    final int f = i;
                    TableColumn<Vector<String>, String> column = new TableColumn<>("Column" +(i+1));
                    column.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Vector<String>, String>, ObservableValue<String>>() {
                        @Override
                        public ObservableValue<String> call(TableColumn.CellDataFeatures<Vector<String>, String> param) {
                            return new SimpleStringProperty(param.getValue().get(f).toString());
                        }
                    });
                    table2.getColumns().add(column);
                    data1.add(temp);
                }*/
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
        button10.setLayoutY(90);
        button10.setLayoutX(20);
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
        button11.setLayoutY(90);
        button11.setLayoutX(140);
        button11.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(thr.isAlive()){
                    thr.interrupt();
                }
            }
        });

        group6.getChildren().addAll(textField6, textField7, button9, button10, button11, table2);

        FlowPane root = new FlowPane();
        root.getChildren().addAll(group1, group2, group3, group4, group5, group6);
        root.setVgap(8);
        root.setHgap(10);

        Scene scene = new Scene(root, 300, 300);
        stage.setScene(scene);
        stage.setTitle("Lab 1");
        stage.setWidth(750);
        stage.setHeight(720);
        stage.show();
    }
}
