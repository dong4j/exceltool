package com.dong4j.controller;

import com.dong4j.MainApp;
import com.dong4j.utils.ExcelTool;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.poi.ss.usermodel.Workbook;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by: dong4j.
 * Date: 2016-12-26.
 * Time: 15:42.
 * Description: 控件的事件处理
 */
@SuppressWarnings("unchecked")
public class MainViewController implements ControlledStage, Initializable {
    private final Desktop desktop = Desktop.getDesktop();
    @FXML
    public TextField firstFilePath;
    @FXML
    public TextField secondFilePath;
    @FXML
    public ComboBox  firstSheetComboBox;
    @FXML
    public ComboBox  firstRowComboBox;
    @FXML
    public ComboBox  firstCellComboBox;
    @FXML
    public ComboBox  secondRowComboBox;
    @FXML
    public ComboBox  secondCellComboBox;
    @FXML
    public ComboBox  secondSheetComboBox;

    private final FileChooser fileChooser    = new FileChooser();
    private       Workbook    firstWorkbook  = null;
    private       Workbook    secondWorkbook = null;

    private StageController myController;

    private Stage primaryStage;

    @Override
    public void setStageController(StageController stageController) {
        this.myController = stageController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("初始化 mainView");
        System.out.println(location.getPath());
        firstFilePath.setEditable(false);
        secondFilePath.setEditable(false);
        // 设置提示框
        final Tooltip tooltip = new Tooltip();
        tooltip.setText("可以将需要对比的文件拖到这里");
        firstFilePath.setTooltip(tooltip);
    }


    /**
     * Handle frist drag over.
     * 拖动文件到控件上时触发的操作
     * @param dragEvent the drag event
     */
    @FXML
    protected void handleFristDragOver(DragEvent dragEvent) {
        //System.out.println("aaaaa");
        if (dragEvent.getGestureSource() != firstFilePath){
            dragEvent.acceptTransferModes(TransferMode.ANY);
        }
    }

    /**
     * Handle frist drag dropped.
     * 拖动文件到控件上并松开鼠标是的操作
     * @param dragEvent the drag event
     */
    @FXML
    protected void handleFristDragDropped(DragEvent dragEvent) {
        firstSheetComboBox.getItems().clear();
        firstRowComboBox.getItems().clear();
        firstCellComboBox.getItems().clear();
        System.out.println(dragEvent.getEventType());
        Dragboard dragboard = dragEvent.getDragboard();
        if (dragboard.hasFiles()){
            try {
                File file = dragboard.getFiles().get(0);
                if (file != null) {
                    firstFilePath.setText(file.getAbsolutePath());
                    firstWorkbook = ExcelTool.getWorkbook(file);
                    // 1. 读取有多少表单
                    List<String> sheetList = ExcelTool.analysisSheet(firstWorkbook);
                    System.out.println(sheetList.size());
                    // 设置下拉列表
                    firstSheetComboBox.getItems().addAll(sheetList);
                    firstSheetComboBox.getSelectionModel().select(0);
                }
            }catch (Exception e){
                System.out.println(e.toString());
            }
        }
        System.out.println("松开鼠标");

    }

    /**
     * Handle frist drag over.
     * 拖动文件到控件上时触发的操作
     * @param dragEvent the drag event
     */
    @FXML
    protected void handleSecondDragOver(DragEvent dragEvent) {
        //System.out.println("aaaaa");
        if (dragEvent.getGestureSource() != firstFilePath){
            dragEvent.acceptTransferModes(TransferMode.ANY);
        }
    }

    /**
     * Handle frist drag dropped.
     * 拖动文件到控件上并松开鼠标是的操作
     * @param dragEvent the drag event
     */
    @FXML
    protected void handleSecondDragDropped(DragEvent dragEvent) {
        secondSheetComboBox.getItems().clear();
        secondRowComboBox.getItems().clear();
        secondCellComboBox.getItems().clear();
        System.out.println(dragEvent.getEventType());
        Dragboard dragboard = dragEvent.getDragboard();
        if (dragboard.hasFiles()){
            try {
                File file = dragboard.getFiles().get(0);
                if (file != null) {
                    secondFilePath.setText(file.getAbsolutePath());
                    secondWorkbook = ExcelTool.getWorkbook(file);
                    // 1. 读取有多少表单
                    List<String> sheetList = ExcelTool.analysisSheet(secondWorkbook);
                    System.out.println(sheetList.size());
                    // 设置下拉列表
                    secondSheetComboBox.getItems().addAll(sheetList);
                    secondSheetComboBox.getSelectionModel().select(0);
                }
            }catch (Exception e){
                System.out.println(e.toString());
            }
        }
        System.out.println("松开鼠标");

    }

    /**
     * Handle select first file.
     * 第一个文件选择框事件处理
     *
     * @param event the event
     */
    @FXML
    protected void handleSelectFirstFile(ActionEvent event) {
        configureFileChooser(fileChooser);
        //FileChooserBuilder fileChooserBuilder = FileChooserBuilder.create();
        //FileChooser fileChooser = fileChooserBuilder.build();
        //File file = fileChooser.showOpenDialog(null);
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            firstSheetComboBox.getItems().clear();
            // todo 异常 当 firstRowComboBox中选了值后再次选择文件时报错
            firstRowComboBox.getItems().clear();
            firstCellComboBox.getItems().clear();
            firstFilePath.setText(file.getAbsolutePath());
            MainApp.map.put("firstFilePath", firstFilePath);
            //firstFilePath.setDisable(true);
            firstWorkbook = ExcelTool.getWorkbook(file);
            // 1. 读取有多少表单
            List<String> sheetList = ExcelTool.analysisSheet(firstWorkbook);
            System.out.println(sheetList.size());
            // 设置下拉列表
            firstSheetComboBox.getItems().addAll(sheetList);
            firstSheetComboBox.getSelectionModel().select(0);
        }
    }

    /**
     * Handle select second file.
     * 第二个文件选择框事件处理
     *
     * @param event the event
     */
    @FXML
    protected void handleSelectSecondFile(ActionEvent event) {
        configureFileChooser(fileChooser);
        //FileChooserBuilder fileChooserBuilder = FileChooserBuilder.create();
        //FileChooser fileChooser = fileChooserBuilder.build();
        //File file = fileChooser.showOpenDialog(null);
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            secondSheetComboBox.getItems().clear();
            // todo 异常 当 secondRowComboBox中选了值后再次选择文件时报错
            secondRowComboBox.getItems().clear();
            secondCellComboBox.getItems().clear();
            secondFilePath.setText(file.getAbsolutePath());
            secondFilePath.setDisable(true);
            secondWorkbook = ExcelTool.getWorkbook(file);
            // 1. 读取有多少表单
            List<String> sheetList = ExcelTool.analysisSheet(secondWorkbook);
            System.out.println(sheetList.size());
            // 设置下拉列表
            secondSheetComboBox.getItems().addAll(sheetList);
            secondSheetComboBox.getSelectionModel().select(0);
        }
    }

    /**
     * Clicked first sheet.
     * 第一个 sheet 鼠标点击事件处理
     *
     * @param event the event
     */
    @FXML
    protected void clickedFirstSheet(MouseEvent event) {
        //System.out.println("鼠标点击:"+ event.getClickCount());
        //firstSheetComboBox.getItems().clear();
        //String firstFile = firstFilePath.getText();
        //if(!firstFile.isEmpty()){
        //    firstWorkbook = ExcelTool.getWorkbook(new File(firstFile));
        //    // 1. 读取有多少表单
        //    List<String> sheetList = ExcelTool.analysisSheet(firstWorkbook);
        //    System.out.println(sheetList.size());
        //    // 设置下拉列表
        //    firstSheetComboBox.getItems().addAll(sheetList);
        //}
    }

    /**
     * Handle select first sheet.
     * 第一个 sheet 下拉列表处理
     *
     * @param event the event
     */
    @FXML
    protected void handleSelectFirstSheet(ActionEvent event) {
        firstRowComboBox.getItems().clear();
        firstCellComboBox.getItems().clear();
        System.out.println("表单选择");
        //System.out.println(firstSheetComboBox.getSelectionModel().getSelectedIndex());
        int sheetIndex = firstSheetComboBox.getSelectionModel().getSelectedIndex();
        // 通过 sheetIndex 获取行数据
        List<Object> rowList = ExcelTool.analysisRow(firstWorkbook, sheetIndex);
        firstRowComboBox.getItems().addAll(rowList);
    }

    /**
     * Clicked first row.
     * 第一个 row 鼠标点击事件处理
     *
     * @param event the event
     */
    @FXML
    protected void clickedFirstRow(MouseEvent event) {
    }

    /**
     * Handle select first row.
     * 第一个 row 下拉列表处理
     *
     * @param event the event
     */
    @FXML
    protected void handleSelectFirstRow(ActionEvent event) {
        firstCellComboBox.getItems().clear();
        System.out.println(firstRowComboBox.getSelectionModel().getSelectedIndex());
        int rowIndex = firstRowComboBox.getSelectionModel().getSelectedIndex();
        // 通过 sheetIndex 获取行数据
        List<Object> cellList = ExcelTool.analysisCell(firstWorkbook, firstSheetComboBox.getSelectionModel().getSelectedIndex(),
                rowIndex);
        firstCellComboBox.getItems().addAll(cellList);
    }

    /**
     * Clicked first cell.
     * 第一个 cell 鼠标点击事件处理
     *
     * @param mouseEvent the mouse event
     */
    @FXML
    protected void clickedFirstCell(MouseEvent mouseEvent) {

    }

    /**
     * Handle select first cell.
     * 第一个 cell 下拉列表处理
     *
     * @param actionEvent the action event
     */
    @FXML
    protected void handleSelectFirstCell(ActionEvent actionEvent) {

    }

    /**
     * Clicked second sheet.
     * 第二个 sheet 鼠标点击事件处理
     *
     * @param mouseEvent the mouse event
     */
    @FXML
    protected void clickedSecondSheet(MouseEvent mouseEvent) {}

    /**
     * Handle select second sheet.
     * 第二个 sheet 下拉列表处理
     *
     * @param actionEvent the action event
     */
    @FXML
    protected void handleSelectSecondSheet(ActionEvent actionEvent) {
        secondRowComboBox.getItems().clear();
        secondCellComboBox.getItems().clear();
        System.out.println("表单选择");
        //System.out.println(firstSheetComboBox.getSelectionModel().getSelectedIndex());
        int sheetIndex = secondSheetComboBox.getSelectionModel().getSelectedIndex();
        // 通过 sheetIndex 获取行数据
        List<Object> rowList = ExcelTool.analysisRow(secondWorkbook, sheetIndex);
        secondRowComboBox.getItems().addAll(rowList);
    }

    /**
     * Clicked second row.
     * 第二个 row 鼠标点击事件处理
     *
     * @param mouseEvent the mouse event
     */
    @FXML
    protected void clickedSecondRow(MouseEvent mouseEvent) {}

    /**
     * Handle select second row.
     * 第二个 row 下拉列表事件处理
     *
     * @param actionEvent the action event
     */
    @FXML
    protected void handleSelectSecondRow(ActionEvent actionEvent) {
        secondCellComboBox.getItems().clear();
        System.out.println(secondRowComboBox.getSelectionModel().getSelectedIndex());
        int rowIndex = secondRowComboBox.getSelectionModel().getSelectedIndex();
        // 通过 sheetIndex 获取行数据
        List<Object> cellList = ExcelTool.analysisCell(secondWorkbook, secondSheetComboBox.getSelectionModel().getSelectedIndex(),
                rowIndex);
        secondCellComboBox.getItems().addAll(cellList);
    }

    /**
     * Clicked second cell.
     * 第二个 cell 鼠标点击事件处理
     *
     * @param mouseEvent the mouse event
     */
    @FXML
    protected void clickedSecondCell(MouseEvent mouseEvent) {}

    /**
     * Handle select second cell.
     * 第二个 cell 下拉列表处理
     *
     * @param actionEvent the action event
     */
    @FXML
    protected void handleSelectSecondCell(ActionEvent actionEvent) {}

    /**
     * Handle submite.
     * 开始对比
     * @param actionEvent the action event
     */
    @FXML
    public void handleSubmite(ActionEvent actionEvent) {
        //// 开始加载第二个舞台
        //myController.loadStage(MainApp.contrastViewID, MainApp.contrastViewRes);
        //// myController.getStage(MainApp.mainViewID).get
        //// 显示第二个舞台,隐藏第一个舞台
        //myController.setStage(MainApp.contrastViewID, MainApp.mainViewID);
        // 控件选择验证
        if(firstFilePath.getText().isEmpty()
                || secondFilePath.getText().isEmpty()
                || firstSheetComboBox.getValue() == null
                || firstRowComboBox.getValue() == null
                || firstCellComboBox.getValue() == null
                || secondRowComboBox.getValue() == null
                || secondCellComboBox.getValue() == null
                || secondSheetComboBox.getValue() == null){
            System.out.println("You clicked me!");
            FXMLLoader fl = new FXMLLoader();
            fl.setLocation(getClass().getResource(MainApp.messageViewRes));
            try {
                fl.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = fl.getRoot();
            Stage modal_dialog = new Stage(StageStyle.DECORATED);
            //modal_dialog.initModality(Modality.WINDOW_MODAL);
            //modal_dialog.initOwner(StageController.stages.get("primaryStage"));
            Scene scene = new Scene(root);
            DialogMessageController t1 = (DialogMessageController)fl.getController();
            t1.setStage(modal_dialog);
            modal_dialog.setScene(scene);
            modal_dialog.showAndWait();
        }else{
            // 开始加载第二个舞台
            myController.loadStage(MainApp.contrastViewID, MainApp.contrastViewRes);
            // myController.getStage(MainApp.mainViewID).get
            // 显示第二个舞台,隐藏第一个舞台
            myController.setStage(MainApp.contrastViewID, MainApp.mainViewID);
        }
    }

    /**
     * 设置默认的选择目录
     * 设置过滤后缀
     *
     * @param fileChooser
     */
    private static void configureFileChooser(final FileChooser fileChooser) {
        fileChooser.setTitle("View Pictures");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Office2003-2010", "*.xls"),
                new FileChooser.ExtensionFilter("Office2007及以上", "*.xlsx")
        );
    }

    private void openFile(File file) {
        EventQueue.invokeLater(() -> {
            try {
                desktop.open(file);
            } catch (IOException ex) {
                Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void setStage(Stage temp){
        primaryStage = temp;
    }
}


