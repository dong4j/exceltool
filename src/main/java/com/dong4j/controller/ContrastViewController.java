package com.dong4j.controller;

import com.dong4j.MainApp;
import com.dong4j.entity.AnalysisEntity;
import com.dong4j.entity.ExcelEntity;
import com.dong4j.utils.ExcelTool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.*;

/**
 * Created by: dong4j.
 * Date: 2016-12-27.
 * Time: 09:48.
 * Description:
 */
@SuppressWarnings("unchecked")
public class ContrastViewController implements ControlledStage, Initializable {
    @FXML
    private ListView firstListView;
    @FXML
    private ListView contrastListView;
    @FXML
    private ListView secondListView;
    @FXML
    private Label    firstLabel;
    @FXML
    private Label    contrastLabel;
    @FXML
    private Label    secondLabel;

    private StageController myController;

    private       List<Object> firstAnalysisList    = null;
    private       List<Object> secondAnalysisList   = null;
    private       List<Object> contrastAnalysisList = null;
    private       List<Object> firstList            = null;
    private       List<Object> secondList           = null;
    private final Tooltip      tooltip              = new Tooltip();

    @Override
    public void setStageController(StageController stageController) {
        this.myController = stageController;
    }

    /**
     * 加载舞台是初始化数据
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        firstListView.setTooltip(tooltip);
        secondListView.setTooltip(tooltip);
        contrastListView.setTooltip(tooltip);
        System.out.println("初始化 contrastView");
        Parent parent = parentMap.get(MainApp.mainViewID);

        String firstFilePath = ((TextField)parent.lookup("#firstFilePath")).getText();
        int firstSheet = ((ComboBox)parent.lookup("#firstSheetComboBox")).getSelectionModel().getSelectedIndex();
        int firstRow = ((ComboBox)parent.lookup("#firstRowComboBox")).getSelectionModel().getSelectedIndex();
        int firstCell = ((ComboBox)parent.lookup("#firstCellComboBox")).getSelectionModel().getSelectedIndex();
        System.out.println(firstFilePath);
        String secondFilePath = ((TextField)parent.lookup("#secondFilePath")).getText();
        int secondSheet = ((ComboBox)parent.lookup("#secondSheetComboBox")).getSelectionModel().getSelectedIndex();
        int secondRow = ((ComboBox)parent.lookup("#secondRowComboBox")).getSelectionModel().getSelectedIndex();
        int secondCell = ((ComboBox)parent.lookup("#secondCellComboBox")).getSelectionModel().getSelectedIndex();

        //String firstFilePath = "/Users/codeai/Downloads/1.xls";
        //int    firstSheet    = 0;
        //int    firstRow      = 3;
        //int    firstCell     = 1;
        //System.out.println(firstFilePath);
        //String secondFilePath = "/Users/codeai/Downloads/2.xls";
        //int    secondSheet    = 2;
        //int    secondRow      = 4;
        //int    secondCell     = 14;

        // 设置第一个列表
        firstList = ExcelTool.analysis(new File(firstFilePath), firstSheet, firstRow, firstCell);
        //AnalysisEntity test = new AnalysisEntity();
        //test.setColumn(1111);
        //test.setRowNum(1111);
        //test.setSheetIndex(1111);
        //test.setData("段应祥");
        //firstList.add(test);
        List<Object> firstData = new ArrayList<>();
        for (Object object : firstList) {
            AnalysisEntity analysisEntity = (AnalysisEntity) object;
            firstData.add(analysisEntity.getData());
        }
        firstAnalysisList = firstData;
        ObservableList<Object> items = FXCollections.observableArrayList(firstData);
        firstListView.setItems(items);
        firstListView.setCellFactory(TextFieldListCell.forListView());
        firstListView.setEditable(true);
        firstLabel.setText("第一个文件:(" + firstData.size() + ")");

        // 设置第三个列表
        secondList = ExcelTool.analysis(new File(secondFilePath), secondSheet, secondRow, secondCell);
        List<Object> secondData = new ArrayList<>();
        for (Object object : secondList) {
            AnalysisEntity analysisEntity = (AnalysisEntity) object;
            secondData.add(analysisEntity.getData());
        }
        secondAnalysisList = secondData;
        ObservableList<Object> secondItems = FXCollections.observableArrayList(secondData);
        secondListView.setItems(secondItems);
        secondListView.setCellFactory(TextFieldListCell.forListView());
        secondListView.setEditable(true);
        secondLabel.setText("第二个文件:(" + secondData.size() + ")");
        // 设置对比结果
        List<Object> contrastList = ExcelTool.removeDuplicateWithOrder(ExcelTool.intersect(firstData, secondData));
        contrastAnalysisList = contrastList;
        ObservableList<Object> contrastItems = FXCollections.observableArrayList(contrastList);
        contrastListView.setItems(contrastItems);
        contrastListView.setCellFactory(TextFieldListCell.forListView());
        contrastListView.setEditable(true);
        contrastLabel.setText("对比结果:(" + contrastList.size() + ")");

        //firstListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
        //    @Override
        //    public void changed(ObservableValue observable, Object oldValue, Object newValue) {
        //        System.out.println(oldValue);
        //        System.out.println(newValue);
        //        int index = firstListView.getSelectionModel().getSelectedIndex();
        //        AnalysisEntity analysisEntity = (AnalysisEntity)firstList.get(index);
        //        tooltip.setText("数据名:" + firstListView.getSelectionModel().getSelectedItem().toString() + "\n"
        //        + "表单:" + (analysisEntity.getSheetIndex() + 1) + "\n"
        //        + "第" + (analysisEntity.getRowNum() + 1) + "行\n"
        //        + "第" + (analysisEntity.getColumn() + 1) + "列\n");
        //    }
        //});
    }


    /**
     * Handle copy all.
     * 复制全部数据
     */
    @FXML
    public void handleCopyAll() {
        Clipboard        clipboard        = Clipboard.getSystemClipboard();
        ClipboardContent clipboardContent = new ClipboardContent();
        ObservableList   observableList   = contrastListView.getItems();
        String           str              = "";
        for (Object anObservableList : observableList) {
            System.out.println(anObservableList);
            str += String.valueOf(anObservableList) + "\n";
        }
        clipboardContent.putString(str);
        clipboard.setContent(clipboardContent);
    }

    /**
     * Handle export.
     * 将数据导出为表格
     */
    @FXML
    public void handleExport() {
        //得到用户导出的文件路径
        FileChooser                 fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter   = new FileChooser.ExtensionFilter("Excel files (*.xls)", "*.xls");
        fileChooser.getExtensionFilters().add(extFilter);
        Stage s    = new Stage();
        File  file = fileChooser.showSaveDialog(s);
        if (file == null) return;
        String exportFilePath = file.getAbsolutePath();
        System.out.println(exportFilePath);

        // 导出到文件
        ExcelEntity excelEntity = new ExcelEntity();
        excelEntity.setSheetName("导出结果");
        excelEntity.setColumnNames(new String[]{"第一个文件", "对比结果", "第二个文件"});
        excelEntity.setPropertyNames(new String[]{"first", "contrast", "second"});
        for (int i = 0; i < (firstAnalysisList.size() > secondAnalysisList.size() ? firstAnalysisList.size() : secondAnalysisList.size())
                ; i++) {
            Map<String, Object> map = new HashMap<>();
            if (i < firstAnalysisList.size()) {
                map.put("first", firstAnalysisList.get(i));
            } else {
                map.put("first", "");
            }
            if (i < secondAnalysisList.size()) {
                map.put("second", secondAnalysisList.get(i));
            } else {
                map.put("second", "");
            }
            if (i < contrastAnalysisList.size()) {
                map.put("contrast", contrastAnalysisList.get(i));
            } else {
                map.put("contrast", "");
            }
            excelEntity.getResultList().add(map);
        }
        ExcelTool.exportExcel(excelEntity, exportFilePath);
    }

    /**
     * Go to main.
     */
    @FXML
    public void goToMain() {
        myController.setStage(MainApp.mainViewID, MainApp.contrastViewID);
    }

    /**
     * Handle mouse clicked.
     * 第二种处理选中方式
     * @param mouseEvent the mouse event
     */
    @FXML
    public void firstHandleMouseClicked(MouseEvent mouseEvent) {
        // 第一种处理选中方式
        int            index          = firstListView.getSelectionModel().getSelectedIndex();
        AnalysisEntity analysisEntity = (AnalysisEntity) firstList.get(index);
        tooltip.setText("数据名:" + firstListView.getSelectionModel().getSelectedItem().toString() + "\n"
                + "表单:" + (analysisEntity.getSheetIndex() + 1) + "\n"
                + "第" + (analysisEntity.getRowNum() + 1) + "行\n"
                + "第" + (analysisEntity.getColumn() + 1) + "列\n");
    }

    /**
     * Second handle mouse clicked.
     * @param mouseEvent the mouse event
     */
    @FXML
    public void secondHandleMouseClicked(MouseEvent mouseEvent) {
        int            index          = secondListView.getSelectionModel().getSelectedIndex();
        AnalysisEntity analysisEntity = (AnalysisEntity) secondList.get(index);
        tooltip.setText("数据名:" + secondListView.getSelectionModel().getSelectedItem().toString() + "\n"
                + "表单:" + (analysisEntity.getSheetIndex() + 1) + "\n"
                + "第" + (analysisEntity.getRowNum() + 1) + "行\n"
                + "第" + (analysisEntity.getColumn() + 1) + "列\n");
    }

    /**
     * Handle find index.
     * 对比结果列表 点击的时候查找左右2各列表中相同的数据的位置
     * @param mouseEvent the mouse event
     */
    @FXML
    public void handleFindIndex(MouseEvent mouseEvent) {
        String data = contrastListView.getSelectionModel().getSelectedItem().toString();
        AnalysisEntity analysisEntity = new AnalysisEntity();
        analysisEntity.setData(data);
        String               str   = "第一个文件:\n";
        for (Object o : firstList) {
            AnalysisEntity ana = (AnalysisEntity) o;
            if (ana.equals(analysisEntity)) {
                System.out.println("表单:" + (ana.getSheetIndex() + 1));
                System.out.println("行:" + (ana.getRowNum() + 1));
                System.out.println("列:" + (ana.getColumn() + 1));
                str += "\t表单:" + (ana.getSheetIndex() + 1) + "\t" + "行:" + (ana.getRowNum() + 1) + "\t" + "列:" + (ana.getColumn() + 1) + "\n";

                // 获取第一个列表中相同数据的索引,然后改变颜色
                System.out.println(firstListView.getItems().indexOf(data));
                //firstListView.getSelectionModel().select(firstListView.getItems().indexOf(data));
                firstListView.getSelectionModel().select(data);
                firstListView.scrollTo(data);
            }
        }
        str += "第二个文件:\n";
        for (Object o : secondList) {
            AnalysisEntity ana = (AnalysisEntity) o;
            if (ana.equals(analysisEntity)) {
                System.out.println("表单:" + (ana.getSheetIndex() + 1));
                System.out.println("行:" + (ana.getRowNum() + 1));
                System.out.println("列:" + (ana.getColumn() + 1));
                str += "\t表单:" + (ana.getSheetIndex() + 1) + "\t" + "行:" + (ana.getRowNum() + 1) + "\t" + "列:" + (ana.getColumn() + 1) + "\n";

                // 获取第二个列表中相同数据的索引,然后改变颜色
                System.out.println(secondListView.getItems().indexOf(data));
                secondListView.getSelectionModel().select(data);
                secondListView.scrollTo(data);
            }
        }
        tooltip.setText(str);
    }
}
