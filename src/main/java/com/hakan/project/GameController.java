package com.hakan.project;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class GameController {
    Square[][] board = new Square[10][10];
    static IntegerProperty property = new SimpleIntegerProperty(7);
    static Connection connection;
    boolean ready=false;

    @FXML
    private GridPane gridPane;

    @FXML
    private Label label;

    public void initialize(){
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                board[i][j]=new Square();
                gridPane.add(board[i][j].button,i,j);
            }
        }
        label.textProperty().bind(property.asString());
    }

    @FXML
    public void onFinishButtonClicked(){
        if (property.getValue()==0){
            try {
                connection.dataInputStream.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hata!");
            alert.setHeaderText("Lütfen 7 tane gemi koyunuz!");
            alert.show();
        }
    }
}
