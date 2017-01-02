package demo2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * This is the modal dialog
 */
public class Test1Controller implements Initializable {

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
