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
public enum EnumNameMetricC {
    mode1("CountOperators"),
    mode2("Level Nest"),
    mode3("Middle Len Ident");

    public String getName() {
        return name;
    }

    private final String name;

    private EnumNameMetricC(String name) {
        this.name = name;
    }
}
