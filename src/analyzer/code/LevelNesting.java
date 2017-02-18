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
public class LevelNesting implements IMetric {

    int maxLevel;
    int curLevel;
    final static double MINSETTING = 1.0;
    final static double MAXSETTING = 6.0;

    public LevelNesting() {
        maxLevel = 0;
        curLevel = 0;
    }

    @Override
    public void calculate(Event event) {
        if (event.code == Event.IFSTART) {
            curLevel++;
            if (maxLevel < curLevel) {
                maxLevel = curLevel;
            }
        }
        if (event.code == Event.IFEND) {
            curLevel--;
        }
    }

    @Override
    public double getResult() {
        return maxLevel;
    }

    @Override
    public void reset() {
        maxLevel = 0;
        curLevel = 0;
    }

    public static String getName() {
        return EnumNamesMetric.mode2.toString();
    }

    public static String getMark(double bad, double good, double metric) {
        if (metric >= bad && metric <= good) {
            return EnumMarkLevelNest.mode2.toString();
        }
        if (metric < bad) {
            return EnumMarkLevelNest.mode3.toString();
        }
        if (metric > good) {
            return EnumMarkLevelNest.mode1.toString();
        }
        return null;
    }

}
