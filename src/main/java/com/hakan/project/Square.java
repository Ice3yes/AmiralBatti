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
    static int remain=7;
    static int point=0, enemyPoint=0;
    static int message;

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
                    send();
                }
            }
        });
    }

    public void send(){
        yourTurn=false;
        int x = GridPane.getColumnIndex(button);
        int y = GridPane.getRowIndex(button);
        try {
            connection.connect(x*10+y);
            System.out.println("paket gonderiliyor: "+(x*10+y));
            Thread.sleep(500);
            System.out.println("mesaj bekleniyor..");
            waitMessage();
            System.out.println("Mesaj: "+message);
            if (message==0){
                button.setStyle("-fx-border-style: dotted;" +
                        "-fx-border-width: 5 ;"+
                        "-fx-background-color: lightblue;"+
                        "-fx-border-color: yellow");
                status=Status.miss;
            } else if(message==1){
                button.setStyle("-fx-border-style: dotted;" +
                        "-fx-border-width: 5 ;"+
                        "-fx-background-color: blue;"+
                        "-fx-border-color: red");
                status=Status.sunken;
                point++;
            }
            Thread.sleep(100);
            waitMessage();
            System.out.println("Gelen mesaj: "+message);
            Square opponent=GameController.getSquare(message);
            Thread.sleep(500);
            if (opponent.status==Status.sea){
                connection.connect(0);
            } else if (opponent.status==Status.ship){
                connection.connect(1);
                enemyPoint++;
            }
            yourTurn=true;
            if(enemyPoint==7){
                war=false;
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Oyun bitti!");
                alert.setHeaderText("Rakip kazandı");
                alert.show();
            } else if (point==7){
                war=false;
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Oyun bitti!");
                alert.setHeaderText("Sen kazandın");
                alert.show();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void waitMessage(){
        message=-1;
        connection.message();
        while (message==-1){
            try {
                Thread.sleep(1000);
                System.out.println(message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(message);
    }

}
