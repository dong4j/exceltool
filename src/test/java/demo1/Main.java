package demo1;

//import javafx.application.Application;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.stage.Stage;

//public class Main extends Application {
//
//    @Override
//    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
//        primaryStage.setTitle("Hello World");
//        primaryStage.setScene(new Scene(root, 300, 275));
//        primaryStage.show();
//    }
//
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//}

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Main extends Application {

    private final Desktop desktop = Desktop.getDesktop();

    @Override
    public void start(final Stage stage) {
        stage.setTitle("File Chooser Sample");

        Label label1 = new Label("Search");
        //Image image  = new Image(getClass().getResourceAsStream("labels.jpg"));
        //label1.setGraphic(new ImageView(image));
        label1.setTextFill(Color.web("#0076a3"));

        // 文件选择框对象
        final FileChooser fileChooser        = new FileChooser();
        final Button      openButton         = new Button("Open a Picture...");
        final Button      openMultipleButton = new Button("Open Pictures...");

        final Button browseButton = new Button("...");

        browseButton.setOnAction(
                (final ActionEvent e) -> {
                    final DirectoryChooser directoryChooser  = new DirectoryChooser();
                    final File             selectedDirectory = directoryChooser.showDialog(stage);
                    if (selectedDirectory != null) {
                        selectedDirectory.getAbsolutePath();
                    }
                    fileChooser.setInitialDirectory(selectedDirectory);
                });

        openButton.setOnAction(
                (final ActionEvent e) -> {
                    configureFileChooser(fileChooser);
                    File file = fileChooser.showOpenDialog(stage);
                    if (file != null) {
                        openFile(file);
                    }
                });
        openMultipleButton.setOnAction(
                (final ActionEvent e) -> {
                    configureFileChooser(fileChooser);
                    List<File> list =
                            fileChooser.showOpenMultipleDialog(stage);
                    if (list != null) {
                        list.stream().forEach((file) -> {
                            openFile(file);
                        });
                    }
                });

        final GridPane inputGridPane = new GridPane();
        GridPane.setConstraints(openButton, 0, 0);
        GridPane.setConstraints(openMultipleButton, 1, 0);
        GridPane.setConstraints(browseButton, 3, 0);
        inputGridPane.setHgap(6);
        inputGridPane.setVgap(6);
        inputGridPane.getChildren().addAll(openButton, openMultipleButton, browseButton);

        final Pane rootGroup = new VBox(12);
        rootGroup.getChildren().addAll(inputGridPane);
        rootGroup.setPadding(new Insets(12, 12, 12, 12));

        stage.setScene(new Scene(rootGroup));
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    /**
     * 设置默认的选择目录
     * 设置过滤后缀
     * @param fileChooser
     */
    private static void configureFileChooser(
            final FileChooser fileChooser) {
        fileChooser.setTitle("View Pictures");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Excel", "*.*"),
                new FileChooser.ExtensionFilter("Office2003-2010", "*.xls"),
                new FileChooser.ExtensionFilter("Office2007及以上", "*.xlsx")
        );
    }

    private void openFile(File file) {
        EventQueue.invokeLater(() -> {
            try {
                desktop.open(file);
            } catch (IOException ex) {
                Logger.getLogger(Main.
                        class.getName()).
                        log(Level.SEVERE, null, ex);
            }
        });
    }
}