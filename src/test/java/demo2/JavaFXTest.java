package demo2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * Demonstrates a modal dialog with controller class
 */
public class JavaFXTest extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fl = new FXMLLoader();
        fl.setLocation(getClass().getResource("/Test.fxml"));
        fl.load();
        Parent root = fl.getRoot();
        TestController tc = (TestController)fl.getController();
        tc.setStage(stage);

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
