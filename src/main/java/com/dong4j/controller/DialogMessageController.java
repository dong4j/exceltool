package com.dong4j.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by: dong4j.
 * Date: 2016-12-27.
 * Time: 15:53.
 * Description: 对话框
 */
public class DialogMessageController implements Initializable{
    private Stage parentStage;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me 2!");
        parentStage.close();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setStage(Stage temp){
        parentStage = temp;
    }
}
