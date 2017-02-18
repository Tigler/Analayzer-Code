/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analyzer.code;

import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;

/**
 * FXML Controller class
 *
 * @author tigler
 */
public class FXMLSettingController implements Initializable {

    private FXMLAnalyzerController analyzerController;
    @FXML
    Button defaultBut;
    @FXML
    TableView tableSetting;
    @FXML
    private ArrayList<ArrayList<MetricSetting>> metricSettings;
    @FXML
    private ArrayList<ArrayList<MetricSetting>> metricSettingsDef;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

    @FXML
    void setAnalyzer(FXMLAnalyzerController analyzerController) {
        this.analyzerController = analyzerController;
        metricSettings = this.analyzerController.getMetricSettings();
        metricSettingsDef = this.analyzerController.getMetricSettingsDef();
        showTable();

    }

    @FXML
    void showTable() {
        tableSetting.setEditable(true);
        for (int i = 0; i < metricSettings.get(0).size(); i++) {
            tableSetting.getItems().add(i);
        }
        TableColumn<Integer, String> nameFl = new TableColumn<>("Name Metric");
        nameFl.setCellValueFactory(cellData -> {
            Integer rowIndex = cellData.getValue();
            return new ReadOnlyStringWrapper(metricSettings.get(0).get(rowIndex).getNameMetric());
        });
        tableSetting.getColumns().add(nameFl);

        TableColumn<Integer, String> min = new TableColumn<>("MIN");
        min.setCellValueFactory(cellData -> {
            Integer rowIndex = cellData.getValue();
            return new ReadOnlyStringWrapper(String.format(Locale.US, "%.2f", metricSettings.get(0).get(rowIndex).getMin()));
        });
        tableSetting.getColumns().add(min);

        TableColumn<Integer, String> max = new TableColumn<>("MAX");
        max.setCellValueFactory(cellData -> {
            Integer rowIndex = cellData.getValue();
            return new ReadOnlyStringWrapper(String.format(Locale.US, "%.2f", metricSettings.get(0).get(rowIndex).getMax()));
        });
        tableSetting.getColumns().add(max);

        min.setCellFactory(TextFieldTableCell.forTableColumn());
        min.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Integer, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Integer, String> event) {
                TableColumn<Integer, String> tmpp = (TableColumn<Integer, String>) tableSetting.getColumns().get(0);

                tmpp.setCellValueFactory(cellData -> {
                    Integer rowIndex = event.getTablePosition().getRow();
                    return new ReadOnlyStringWrapper(String.format("%.2f", event.getNewValue()));
                });

                metricSettings.get(0).get(event.getTablePosition().getRow()).setMin(Double.parseDouble(event.getNewValue()));
            }
        });

        max.setCellFactory(TextFieldTableCell.forTableColumn());
        max.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Integer, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Integer, String> event) {
                TableColumn<Integer, String> tmpp = (TableColumn<Integer, String>) tableSetting.getColumns().get(0);

                tmpp.setCellValueFactory(cellData -> {
                    Integer rowIndex = event.getTablePosition().getRow();
                    return new ReadOnlyStringWrapper(String.format("%.2f", event.getNewValue()));
                });
                metricSettings.get(0).get(event.getTablePosition().getRow()).setMax(Double.parseDouble(event.getNewValue()));
            }
        });
    }

    @FXML
    void defaultButAction() {
        tableSetting.refresh();
        tableSetting.getItems().clear();
        tableSetting.getColumns().clear();
        metricSettings.get(0).clear();
        for (MetricSetting metr : metricSettingsDef.get(0)) {
            metricSettings.get(0).add(new MetricSetting(metr.getNameMetric(), metr.getMin(), metr.getMax()));
        }
        this.showTable();
    }

}
