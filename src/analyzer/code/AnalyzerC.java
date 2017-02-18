/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analyzer.code;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import javafx.fxml.FXML;

/**
 *
 * @author tigler
 */
public class AnalyzerC implements IAnalayzer {

    double countOperatorResult;
    double levelNestingResult;
    double middleLenIdentResult;
    double countOperatorProj;
    double levelNestProj;
    double middleLenIdentProj;
    boolean countOperatorEnable;
    boolean levelNestEnable;
    boolean middleLenIdentEnable;
    List<String> cor;
    List<String> lnr;
    List<String> mlir;
    IMetric countOperator;
    IMetric levelNesting;
    IMetric middleLenIdent;
    Listener listener;
    Listener listener1;
    Listener listener2;
    SyntaxAnalyzer synAn;
    private ICalculateMark calculateMark;
    @FXML
    private ArrayList<MetricSetting> metricSettings;
    @FXML
    private ArrayList<MetricSetting> metricSettingsDef;
    private ArrayList<List<String>> result;

    public AnalyzerC() {
        countOperatorProj = 0;
        levelNestProj = 0;
        middleLenIdentProj = 0;
        countOperatorEnable = true;
        levelNestEnable = true;
        middleLenIdentEnable = true;
        countOperator = new CountOperators();
        levelNesting = new LevelNesting();
        middleLenIdent = new MiddleLenIdent();
        listener = new EventCountOperator(countOperator);
        listener1 = new EventLevelNest(levelNesting, listener);
        listener2 = new EventMiddleLenIdent(middleLenIdent, listener1);
        synAn = new SyntaxAnalyzerCPlusPlus();
        synAn.attach(listener2);
        cor = new LinkedList<>();
        lnr = new LinkedList<>();
        mlir = new LinkedList<>();
        result = new ArrayList<>();
        calculateMark = new CalculateMarkC();
        metricSettings = new ArrayList<>();
        metricSettingsDef = new ArrayList<>();
        //инициализируем настройки проверки качества
        metricSettings.add(new MetricSetting(CountOperators.getName(),
                CountOperators.MINSETTING, CountOperators.MAXSETTING));
        metricSettings.add(new MetricSetting(LevelNesting.getName(),
                LevelNesting.MINSETTING, LevelNesting.MAXSETTING));
        metricSettings.add(new MetricSetting(MiddleLenIdent.getName(),
                MiddleLenIdent.MINSETTING, MiddleLenIdent.MAXSETTING));
        //настройки по умолчанию
        for (MetricSetting metr : metricSettings) {
            metricSettingsDef.add(new MetricSetting(metr.getNameMetric(), metr.getMin(), metr.getMax()));
        }
    }

    public void solutMetrics(String src) {

        synAn.setText(src);
        synAn.start();
        countOperatorResult = countOperator.getResult();
        levelNestingResult = levelNesting.getResult();
        middleLenIdentResult = middleLenIdent.getResult();

        if (countOperatorResult > countOperatorProj) {
            countOperatorProj = countOperatorResult;
        }
        if (levelNestingResult > levelNestProj) {
            levelNestProj = levelNestingResult;
        }
        middleLenIdentProj += middleLenIdentResult;

        cor.add(String.valueOf(String.format(Locale.US, "%.2f", countOperatorResult)));
        lnr.add(String.valueOf(String.format(Locale.US, "%.2f", levelNestingResult)));
        mlir.add(String.valueOf(String.format(Locale.US, "%.2f", middleLenIdentResult)));

        countOperator.reset();
        levelNesting.reset();
        middleLenIdent.reset();
    }

    @Override
    public double getMark() {
        List<Double> dataProj = new LinkedList<>();
        dataProj.add(countOperatorProj);
        dataProj.add(levelNestProj);
        dataProj.add(middleLenIdentProj);
        double mark = calculateMark.calculMark(dataProj, metricSettings);
        return mark;
    }

    @Override
    public ArrayList<List<String>> getResult() {
        result = new ArrayList<>();
        cor.add(String.valueOf(String.format(Locale.US, "%.2f", countOperatorProj)));
        result.add(cor);
        lnr.add(String.valueOf(String.format(Locale.US, "%.2f", levelNestProj)));
        result.add(lnr);
        mlir.add(String.valueOf(String.format(Locale.US, "%.2f", middleLenIdentProj)));
        result.add(mlir);
        return result;
    }

    public void setMetricSettings(ArrayList<MetricSetting> metricSettings) {
        this.metricSettings = metricSettings;
    }

    public void setCountOpetatorsEnable(boolean enable) {
        countOperatorEnable = enable;
    }

    public void setlevelNestEnable(boolean enable) {
        levelNestEnable = enable;
    }

    public void setMiddleLenIdentEnable(boolean enable) {
        middleLenIdentEnable = enable;
    }

    public boolean isCountOperatorsEnable() {
        return countOperatorEnable;
    }

    public boolean isLevelNestEnable() {
        return levelNestEnable;
    }

    public boolean isMiddleLenIdentEnable() {
        return middleLenIdentEnable;
    }

    public void setLevelNestEnable(boolean levelNestEnable) {
        this.levelNestEnable = levelNestEnable;
    }

    public double getCountOperatorProject() {
        return countOperatorProj;
    }

    public double getLevelNestProject() {
        return levelNestProj;
    }

    public double getMiddleLenIdentProject() {
        return middleLenIdentProj;
    }

}
