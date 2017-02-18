/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analyzer.code;

/**
 *
 * @author tigler
 */
public class MetricSetting {
    private String nameMetric;
    private double min;
    private double max;

    public MetricSetting(String nameMetric, double min, double max) {
        this.nameMetric = nameMetric;
        this.min = min;
        this.max = max;
    }

    public String getNameMetric() {
        return nameMetric;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public void setNameMetric(String nameMetric) {
        this.nameMetric = nameMetric;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public void setMax(double max) {
        this.max = max;
    }
    
}
