package com.company;

import javafx.beans.property.SimpleStringProperty;

public class Table {
    private final SimpleStringProperty firstColumn;
    private final SimpleStringProperty secondColumn;

    public Table(String first, String second){
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
