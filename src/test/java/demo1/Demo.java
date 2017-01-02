package demo1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by: dong4j.
 * Date: 2016-12-26.
 * Time: 16:13.
 * Description:
 */
public class Demo extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root  = FXMLLoader.load(getClass().getResource("/sample.fxml"));
        Scene  scene = new Scene(root, 372, 425);
        stage.setTitle("Excel Tool");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
