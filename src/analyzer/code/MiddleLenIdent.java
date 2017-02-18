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
public class MiddleLenIdent implements IMetric {

    int sumLenthIdents;
    int countIdent;
    double middleLenIdent;
    final static double MINSETTING = 3.0;
    final static double MAXSETTING = 15.0;

    public MiddleLenIdent() {
        sumLenthIdents = 0;
        countIdent = 0;
        middleLenIdent = 0.0;
    }

    @Override
    public void calculate(Event event) {
        sumLenthIdents += event.ident.length();
        countIdent++;

    }

    @Override
    public double getResult() {
        middleLenIdent = (double) sumLenthIdents / (double) countIdent;
        return middleLenIdent;
    }

    @Override
    public void reset() {
        sumLenthIdents = 0;
        countIdent = 0;
        middleLenIdent = 0.0;
    }

    public static String getName() {
        return EnumNamesMetric.mode3.toString();
    }

    static String getMark(double bad, double good, double metric) {
        if (metric >= bad && metric <= good) {
            return EnumMarkMiddleLenIdent.mode2.toString();
        }
        if (metric < bad) {
            return EnumMarkMiddleLenIdent.mode1.toString();
        }
        if (metric > good) {
            return EnumMarkMiddleLenIdent.mode3.toString();
        }
        return null;
    }

}
