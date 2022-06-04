package com.hakan.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {

    @FXML
    private TextField ipTextField;

    @FXML
    private TextField portTextField;

    private Connection connection;

    @FXML
    public void onConnectButtonClicked(ActionEvent actionEvent){
        String ip=ipTextField.getText();
        int port=Integer.parseInt(portTextField.getText());
        connection = new Connection(ip, port);
        try {
            connection.connect(1);
            changeScene(actionEvent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onWaitButtonClicked(ActionEvent actionEvent){
        String ip=ipTextField.getText();
        int port=Integer.parseInt(portTextField.getText());
        connection = new Connection(ip, port);
        try {
            connection.waitConnection();
            changeScene(actionEvent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeScene(ActionEvent actionEvent) throws IOException {
        GameController.connection=Square.connection=connection;
        FXMLLoader fxmlLoader = new FXMLLoader(MenuController.class.getResource("game-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 635);
        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setTitle("Amiral Battı");
        stage.setScene(scene);
    }
}
