package com.hakan.project;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class Square {
    Button button;
    Status status=Status.sea;
    static Connection connection;
    static boolean war=false;
    static boolean yourTurn=false;
    static int a=7;

    public Square(){
        button = new Button();
        button.setMinWidth(60);
        button.setMaxHeight(60);
        GridPane.setValignment(button, VPos.CENTER);
        GridPane.setHalignment(button, HPos.CENTER);
        button.setStyle("-fx-border-style: dotted;" +
                "-fx-background-color: lightblue");
        button.setOnAction(actionEvent -> {
            if (!war) {
                if (status == Status.sea) {
                    if (GameController.property.getValue() == 0) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Hata!");
                        alert.setHeaderText("Yeterince gemi koydunuz.");
                        alert.show();
                    } else {
                        status = Status.ship;
                        button.setStyle("-fx-border-style: dotted;" +
                                "-fx-background-color: blue");
                        GameController.property.setValue(GameController.property.getValue() - 1);

                    }
                } else {
                    status = Status.sea;
                    button.setStyle("-fx-border-style: dotted;" +
                            "-fx-background-color: lightblue");
                    GameController.property.setValue(GameController.property.getValue() + 1);

                }
            } else {
                if(yourTurn){
                    int x = GridPane.getColumnIndex(button);
                    int y = GridPane.getRowIndex(button);
                    try {
                        connection.connect(x*10+y);
                        yourTurn=false;
                        Thread.sleep(1000);
                        connection.waitConnection();
                        yourTurn=true;
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

}
