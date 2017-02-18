/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analyzer.code;

import java.util.ArrayList;

/**
 *
 * @author tigler
 */
public class CountOperators implements IMetric {

    private ArrayList<Variable> variables;
    private int maxCountOperator;
    final static double MINSETTING = 5.0;
    final static double MAXSETTING = 10.0;

    /**
     * 
     */
    public CountOperators() {
        variables = new ArrayList<>();
        maxCountOperator = 0;
    }

    /**
     * 
     * @param event 
     */
    @Override
    public void calculate(Event event) {
        if (event.code == Event.DESCRIPTION) {
            Variable var = new Variable(event.ident);
            variables.add(var);
        }
        if (event.code == Event.OPERATOR) {
            for (int i = 0; i < variables.size(); i++) {
                variables.get(i).incCountOperators();
            }
        }
        if (event.code == Event.ASSIGMENT) {
            for (int i = 0; i < variables.size(); i++) {
                if (variables.get(i).ident.equals(event.ident)) {
                    if (variables.get(i).getCountOperators() > maxCountOperator) {
                        maxCountOperator = variables.get(i).getCountOperators();
                    }
                }
            }
        }
    }

    @Override
    public double getResult() {
        return maxCountOperator;
    }

    @Override
    public void reset() {
        variables = new ArrayList<>();
        maxCountOperator = 0;
    }

    public static String getName() {
        return EnumNamesMetric.mode1.toString();
    }

    public static String getMark(double bad, double good, double metric) {
        if(metric>bad && metric<good){
            return EnumMarkCountOperators.mode2.toString();
        }
        if (metric >= bad) {
            return EnumMarkCountOperators.mode1.toString();
        }
        if (metric <= good) {
            return EnumMarkCountOperators.mode3.toString();
        }
        return null;
    }
}
