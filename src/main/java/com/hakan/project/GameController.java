package com.hakan.project;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class GameController {
    static Square[][] board = new Square[10][10];
    static int message;
    static IntegerProperty property = new SimpleIntegerProperty(7);
    static Connection connection;
    boolean ready=false;

    @FXML
    private GridPane gridPane;

    @FXML
    private Label label;

    @FXML
    private Button finishButton;

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
            finishButton.setDisable(true);
            Square.war=true;
            try {
                connection.connect(1);
                Thread.sleep(100);
                System.out.println("Sonradan hazır");

                System.out.println("paket bekleniyor");
                waitMessage();
                System.out.println("Mesaj alındı: "+message);
                Square square = getSquare(message);
                if (square.status==Status.sea){
                    System.out.println("deniz");
                    square.button.setStyle("-fx-border-style: dotted;" +
                            "-fx-border-width: 5 ;"+
                            "-fx-background-color: lightblue;"+
                            "-fx-border-color: yellow");
                    square.status=Status.miss;
                    Thread.sleep(500);
                    connection.connect(0);
                } else if (square.status==Status.ship){
                    System.out.println("gemi");
                    square.button.setStyle("-fx-border-style: dotted;" +
                            "-fx-border-width: 5 ;"+
                            "-fx-background-color: blue;"+
                            "-fx-border-color: black");
                    square.status=Status.sunken;
                    Thread.sleep(500);
                    connection.connect(1);
                }
                Thread.sleep(100);
                Square.yourTurn=true;
            } catch (IOException e) {
                System.out.println("ilk hazır");
                waitMessage();
                System.out.println(message);
                Square.yourTurn=true;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hata!");
            alert.setHeaderText("Lütfen 7 tane gemi koyunuz!");
            alert.show();
        }
    }

    private void game(){

    }

    public static Square getSquare(int xy){
        return board[xy/10][xy%10];
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
