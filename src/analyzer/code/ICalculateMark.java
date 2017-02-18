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
public interface ICalculateMark {
    public double calculMark(List<Double> metrs, ArrayList<MetricSetting> metricSetting);
}
