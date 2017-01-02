package com.dong4j;

import com.dong4j.controller.StageController;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by: dong4j.
 * Date: 2016-12-26.
 * Time: 15:29.
 * Description: 主程序
 */
public class MainApp extends Application {
    public static String              mainViewID      = "MainView";
    public static String              mainViewRes     = "/MainView.fxml";
    public static String              contrastViewID  = "ContrastView";
    public static String              contrastViewRes = "/ContrastView.fxml";
    public static String              messageViewRes  = "/Message.fxml";
    public static Map<String, Object> map             = new HashMap<>();

    //@Override
    //public void start(Stage stage) throws Exception {
    //    Parent root = FXMLLoader.load(getClass().getResource("MainView.fxml"));
    //    Scene scene = new Scene(root, 372, 425);
    //    stage.setTitle("Excel Tool");
    //    stage.setScene(scene);
    //    stage.setResizable(false);
    //    stage.show();
    //}

    @Override
    public void start(Stage primaryStage) {
        //新建一个StageController控制器
        StageController stageController = new StageController();
        //将主舞台交给控制器处理
        stageController.setPrimaryStage(primaryStage);
        //加载两个舞台，每个界面一个舞台
        stageController.loadStage(mainViewID, mainViewRes);
        //stageController.loadStage(contrastViewID, contrastViewRes);
        //显示MainView舞台
        stageController.setStage(mainViewID);
        //stageController.setStage(contrastViewID);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
