package com.dong4j.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.HashMap;

/**
 * Created by: dong4j.
 * Date: 2016-12-27.
 * Time: 09:28.
 * Description:
 * 主要是加载fxml资源文件和对应的View控制器、
 * 生成Stage对象以及对Stage对象进行管理，
 * 因此该StageController控制器对象也需要被注入到每个fxml的View控制器中。
 */
public class StageController {
    //建立一个专门存储Stage的Map，全部用于存放Stage对象
    public static HashMap<String, Stage>           stages      = new HashMap<String, Stage>();

    /**
     * 将加载好的Stage放到Map中进行管理
     *
     * @param name  设定Stage的名称
     * @param stage Stage的对象
     */
    public void addStage(String name, Stage stage) {
        stages.put(name, stage);
    }

    /**
     * 通过Stage名称获取Stage对象
     *
     * @param name Stage的名称
     * @return 对应的Stage对象
     */
    public Stage getStage(String name) {
        return stages.get(name);
    }

    /**
     * 将主舞台的对象保存起来，这里只是为了以后可能需要用，目前还不知道用不用得上
     *
     * @param primaryStage     主舞台对象，在Start()方法中由JavaFx的API建立
     */
    public void setPrimaryStage(Stage primaryStage) {
        this.addStage("primaryStage", primaryStage);
    }

    /**
     * 加载窗口地址，需要fxml资源文件属于独立的窗口并用Pane容器或其子类继承
     *
     * @param name      注册好的fxml窗口的文件
     * @param resources fxml资源地址
     * @param styles    可变参数，init使用的初始化样式资源设置
     * @return 是否加载成功
     */
    public boolean loadStage(String name, String resources, StageStyle... styles) {
        try {
            //Parent tempPane = FXMLLoader.load(getClass().getResource(name));
            //加载FXML资源文件
            FXMLLoader loader   = new FXMLLoader(getClass().getResource(resources));
            Parent     tempPane = loader.load();

            //通过Loader获取FXML对应的ViewCtr，并将本StageController注入到ViewCtr中
            ControlledStage controlledStage = loader.getController();
            ControlledStage.parentMap.put(name, tempPane);
            controlledStage.setStageController(this);
            //构造对应的Stage
            Scene tempScene = new Scene(tempPane);
            Stage tempStage = new Stage();
            if (name.toLowerCase().contains("main")) {
                tempStage.setTitle("Excel Tool");
            } else {
                tempStage.setTitle("对比结果");
            }
            tempStage.setScene(tempScene);
            tempStage.setResizable(false);

            //配置initStyle
            for (StageStyle style : styles) {
                tempStage.initStyle(style);
            }
            //将设置好的Stage放到HashMap中
            this.addStage(name, tempStage);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 显示Stage但不隐藏任何Stage
     *
     * @param name 需要显示的窗口的名称
     * @return 是否显示成功
     */
    public boolean setStage(String name) {
        this.getStage(name).show();
        return true;
    }


    /**
     * 显示Stage并隐藏对应的窗口
     *
     * @param show  需要显示的窗口
     * @param close 需要删除的窗口
     * @return
     */
    public boolean setStage(String show, String close) {
        getStage(close).close();
        setStage(show);
        return true;
    }


    /**
     * 在Map中删除Stage加载对象
     *
     * @param name 需要删除的fxml窗口文件名
     * @return 是否删除成功
     */
    public boolean unloadStage(String name) {
        if (stages.remove(name) == null) {
            System.out.println("窗口不存在，请检查名称");
            return false;
        } else {
            System.out.println("窗口移除成功");
            return true;
        }
    }

    public static HashMap<String, Stage> getStages() {
        return stages;
    }
}
