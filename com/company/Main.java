package com.company;

import java.util.*;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.*;

public class Main extends Application{

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage){
        VBox root = new VBox();

        List<ComponentLinker> linkers = new ArrayList<>();
        linkers.add(new ComboBoxGroup());
        linkers.add(new SwapGroup());
        linkers.add(new RadioButtonGroup());
        linkers.add(new CheckBoxGroup());
        linkers.add(new TableGroup());
        linkers.add(new MovingTableGroup());

        List<HBox> groups = new ArrayList<>();
        for (ComponentLinker linker : linkers) {
            linker.link();
            groups.add(linker.component());
        }
        root.getChildren().addAll(groups);


        Scene scene = new Scene(root, 820, 700);
        stage.setTitle("PPvIS1");
        stage.setScene(scene);
        stage.show();

    }
}
