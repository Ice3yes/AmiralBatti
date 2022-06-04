package com.hakan.project;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class Square {
    Button button;
    Status status=Status.sea;

    public Square(){
        button = new Button();
        button.setMinWidth(60);
        button.setMaxHeight(60);
        GridPane.setValignment(button, VPos.CENTER);
        GridPane.setHalignment(button, HPos.CENTER);
        button.setStyle("-fx-border-style: dotted;" +
                "-fx-background-color: lightblue");
        button.setOnAction(actionEvent -> {
            if (status == Status.sea) {
                if(GameController.property.getValue()==0){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Hata!");
                    alert.setHeaderText("Yeterince gemi koydunuz.");
                    alert.show();
                } else {
                    status = Status.ship;
                    button.setStyle("-fx-border-style: dotted;" +
                            "-fx-background-color: blue");
                    GameController.property.setValue(GameController.property.getValue()-1);

                }
            } else {
                status = Status.sea;
                button.setStyle("-fx-border-style: dotted;" +
                        "-fx-background-color: lightblue");
                GameController.property.setValue(GameController.property.getValue()+1);

            }
        });
    }
}
