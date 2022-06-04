package com.hakan.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {

    @FXML
    private TextField ipTextField;

    @FXML
    private TextField portTextField;

    private final Connection connection = new Connection();

    @FXML
    public void onConnectButtonClicked(ActionEvent actionEvent){
        String ip=ipTextField.getText();
        int port=Integer.parseInt(portTextField.getText());
        try {
            connection.connect(ip,port);
            FXMLLoader fxmlLoader = new FXMLLoader(MenuController.class.getResource("game-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 635);
            Node node = (Node) actionEvent.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.setTitle("Amiral Battı");
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onWaitButtonClicked(){
        int port=Integer.parseInt(portTextField.getText());
        try {
            connection.waitConnection(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeScene(){
        GameController.connection=connection;
    }
}
