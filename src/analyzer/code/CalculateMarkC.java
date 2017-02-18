/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analyzer.code;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tigler
 */
public class CalculateMarkC implements ICalculateMark {

    @Override
    public double calculMark(List<Double> metrs, ArrayList<MetricSetting> metricSetting) {
        double mark = 5;
        if (metrs.get(0) >= metricSetting.get(0).getMin()
                && metrs.get(0) <= metricSetting.get(0).getMax()) {
            mark -= 0.2;
        }
        if (metrs.get(0) > metricSetting.get(0).getMax()) {
            mark -= 1.0;
        }
        if (metrs.get(1) > metricSetting.get(1).getMax()) {
            mark -= 1.0;
        }
        if (metrs.get(2) > metricSetting.get(2).getMax()) {
            mark -= 1.0;
        }
        if (metrs.get(2) <= metricSetting.get(2).getMax()
                && metrs.get(2) >= metricSetting.get(2).getMin()) {
            mark -= 0.2;
        }
        return mark;
    }
}
