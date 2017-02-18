/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analyzer.code;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author tigler
 */
public class FXMLAnalyzerController implements Initializable {

    @FXML
    private TableView<Integer> tableMetric;
    @FXML
    private TableView<Integer> tableDescript;
    @FXML
    private ArrayList<String> source;
    @FXML
    private ArrayList<String> nameFile;
    @FXML
    private ArrayList<ArrayList<MetricSetting>> metricSettings;
    @FXML
    private ArrayList<ArrayList<MetricSetting>> metricSettingsDef;
    @FXML
    private Button button;
    @FXML
    private Label labelMark;
    @FXML
    private Label labelMetrics;
    @FXML
    private Label labelDesc;
    private final int C = 0;
    private int curLang;
    private ICalculateMark calculateMark;
    private FXMLMetricsController fxmlmc;
    private FXMLSettingController fxmlsc;
    private boolean countOperatorsEnable;
    private boolean levelNestEnable;
    private boolean middleLenIdentEnable;
    private IAnalayzer analyzer;

    @FXML
    private void calculateMetricButtonAction(ActionEvent event) {
        List<String> nameFiles = nameFile;
        ArrayList<List<String>> metrs;
        if (curLang == C) {
            analyzer.setMetricSettings(metricSettings.get(curLang));
        }
        //вычисляем метрики для каждого файла
        for (String src : source) {
            analyzer.solutMetrics(src);
        }

        //получаем результаты из анализатора
        metrs = analyzer.getResult();

        //добавляем к результат по проекту в общий список
        nameFile.add("По проекту");

        //очищаем таблицы
        tableMetric.refresh();
        tableMetric.getItems().clear();
        tableMetric.getColumns().clear();
        tableDescript.refresh();
        tableDescript.getItems().clear();
        tableDescript.getColumns().clear();

        //создаем строчки у таблиц
        for (int i = 0; i < source.size() + 1; i++) {
            tableMetric.getItems().add(i);
            tableDescript.getItems().add(i);
        }

        //создаем столбик с именами файлов
        TableColumn<Integer, String> nameFl = new TableColumn<>("Name File");
        nameFl.setCellValueFactory(cellData -> {
            Integer rowIndex = cellData.getValue();
            return new ReadOnlyStringWrapper(nameFiles.get(rowIndex));
        });
        tableMetric.getColumns().add(nameFl);  //добавляем столбик в таблицу

        // добавление столбиков с метриками
        EnumNameMetricC[] enumName = EnumNameMetricC.values();
        for (int i = 0; i < metrs.size(); i++) {
            List<String> list = metrs.get(i);
            TableColumn<Integer, String> nameColumn = new TableColumn<>(enumName[i].getName());
            nameColumn.setCellValueFactory(cellData -> {
                Integer rowIndex = cellData.getValue();
                return new ReadOnlyStringWrapper(list.get(rowIndex));
            });
            tableMetric.getColumns().add(nameColumn);
        }

        if (curLang == C) {
            tableDescript.getColumns().add(nameFl);

            TableColumn<Integer, String> columnDesc = new TableColumn<>("Count Operators");
            columnDesc.setCellValueFactory(cellData -> {
                Integer rowIndex = cellData.getValue();
                return new ReadOnlyStringWrapper(CountOperators.getMark(metricSettings.get(curLang).get(0).getMin(),
                        metricSettings.get(curLang).get(0).getMax(), Double.valueOf(metrs.get(0).get(rowIndex))));
            });
            tableDescript.getColumns().add(columnDesc);

            TableColumn<Integer, String> columnDesc1 = new TableColumn<>("Level Nest");
            columnDesc1.setCellValueFactory(cellData -> {
                Integer rowIndex = cellData.getValue();
                return new ReadOnlyStringWrapper(LevelNesting.getMark(metricSettings.get(curLang).get(1).getMin(),
                        metricSettings.get(curLang).get(1).getMax(), Double.valueOf(metrs.get(1).get(rowIndex))));
            });
            tableDescript.getColumns().add(columnDesc1);

            TableColumn<Integer, String> columnDesc2 = new TableColumn<>("Middle Len Ident");
            columnDesc2.setCellValueFactory(cellData -> {
                Integer rowIndex = cellData.getValue();
                return new ReadOnlyStringWrapper(MiddleLenIdent.getMark(metricSettings.get(curLang).get(2).getMin(),
                        metricSettings.get(curLang).get(2).getMax(), Double.valueOf(metrs.get(2).get(rowIndex))));
            });
            tableDescript.getColumns().add(columnDesc2);

            double mark = analyzer.getMark();
            labelMark.setText("Оценка: " + String.format(Locale.US, "%.2f", mark) + " из 5");
            labelMark.setVisible(true);
        }
    }

    @FXML
    private void openProjectMenuItem() throws FileNotFoundException, IOException {
        source.clear();
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("C project");
        File selectedDirectory = chooser.showDialog(null);
        if (null != selectedDirectory) {
            File[] files = selectedDirectory.listFiles();
            for (File file : files) {
                if (FilenameUtils.getExtension(file.getAbsolutePath()).equals("java")) {
                    analyzer = new AnalyzerC();
                    curLang = C;
                    countOperatorsEnable = true;
                    levelNestEnable = true;
                    middleLenIdentEnable = true;
                    tableMetric.setVisible(true);
                    tableDescript.setVisible(true);
                    labelDesc.setVisible(true);
                    labelMetrics.setVisible(true);
                    button.setVisible(true);
                    Scanner scanner = new Scanner(file);
                    nameFile.add(FilenameUtils.getName(file.getAbsolutePath()));
                    String tmpSource = new String();
                    while (scanner.hasNext()) {
                        tmpSource += scanner.nextLine() + '\n';
                    }
                    source.add(tmpSource);
                }
            }
        }
    }

    @FXML
    private void metricsCplusPlusMunuItem() throws FileNotFoundException, IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLMetrics.fxml"));
        try {
            AnchorPane pane = (AnchorPane) loader.load();
            fxmlmc = loader.getController();
            fxmlmc.setAnalyzer((AnalyzerC) analyzer);
            Scene scene = new Scene(pane);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Метрики С++");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void aboutProgrammMunuItem() throws FileNotFoundException, IOException {
        Parent root2 = (Parent) FXMLLoader.load(getClass().getResource("FXMLAboutProgramm.fxml"));
        Scene scene = new Scene(root2);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("О программе");
        stage.show();
    }

    @FXML
    private void closeProgramm() {
        System.exit(0);
    }

    @FXML
    private void settingMunuItem() throws FileNotFoundException, IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLSetting.fxml"));
        try {
            AnchorPane pane = (AnchorPane) loader.load();
            fxmlsc = loader.getController();
            fxmlsc.setAnalyzer(this);
            Scene scene = new Scene(pane);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Настройка");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ArrayList<MetricSetting>> getMetricSettings() {
        return metricSettings;
    }

    public ArrayList<ArrayList<MetricSetting>> getMetricSettingsDef() {
        return metricSettingsDef;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        countOperatorsEnable = false;
        levelNestEnable = false;
        middleLenIdentEnable = false;
        labelMark.setVisible(false);
        labelDesc.setVisible(false);
        labelMetrics.setVisible(false);
        tableMetric.setVisible(false);
        tableDescript.setVisible(false);
        tableMetric.setEditable(true);
        button.setVisible(false);
        source = new ArrayList<>();
        nameFile = new ArrayList<>();
        metricSettings = new ArrayList<>();
        metricSettingsDef = new ArrayList<>();
        ArrayList<MetricSetting> metricSettingsC = new ArrayList<>();
        ArrayList<MetricSetting> metricSettingsDefC = new ArrayList<>();
        //инициализируем настройки проверки качества
        metricSettingsC.add(new MetricSetting(CountOperators.getName(),
                CountOperators.MINSETTING, CountOperators.MAXSETTING));
        metricSettingsC.add(new MetricSetting(LevelNesting.getName(),
                LevelNesting.MINSETTING, LevelNesting.MAXSETTING));
        metricSettingsC.add(new MetricSetting(MiddleLenIdent.getName(),
                MiddleLenIdent.MINSETTING, MiddleLenIdent.MAXSETTING));
        //настройки по умолчанию
        for (MetricSetting metr : metricSettingsC) {
            metricSettingsDefC.add(new MetricSetting(metr.getNameMetric(), metr.getMin(), metr.getMax()));
        }
        metricSettings.add(metricSettingsC);
        metricSettingsDef.add(metricSettingsDefC);

    }
}
