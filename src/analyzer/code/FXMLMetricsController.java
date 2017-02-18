/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analyzer.code;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;

/**
 * FXML Controller class
 *
 * @author tigler
 */
public class FXMLMetricsController implements Initializable {

    @FXML
    private AnalyzerC analyzer;
    @FXML
    private FXMLAnalyzerController analyzerController;
    @FXML
    private CheckBox checkBoxCountOperators;
    @FXML
    private CheckBox checkBoxLevelNest;
    @FXML
    private CheckBox checkBoxMibbleNelIdent;

    @FXML
    private void actionCheckBoxCountOperators() {
        if (analyzer != null) {

            analyzer.setCountOpetatorsEnable(checkBoxCountOperators.isSelected());
        }
    }

    @FXML
    private void actionCheckBoxLevelNest() {
        if (analyzer != null) {
            analyzer.setlevelNestEnable(checkBoxLevelNest.isSelected());
        }
    }

    @FXML
    private void actionCheckBoxMibbleNelIdent() {
        if (analyzer != null) {
            analyzer.setMiddleLenIdentEnable(checkBoxMibbleNelIdent.isSelected());
        }
    }

    public void setAnalyzer(AnalyzerC analayzer) {
        this.analyzer = analayzer;
    }


    /**
     * Initializes the controller class.
     */
    @Override
    
    public void initialize(URL url, ResourceBundle rb) {
        checkBoxCountOperators.setSelected(true);
        checkBoxLevelNest.setSelected(true);
        checkBoxMibbleNelIdent.setSelected(true);
        checkBoxCountOperators.setDisable(true);
        checkBoxLevelNest.setDisable(true);
        checkBoxMibbleNelIdent.setDisable(true);
    }

}
