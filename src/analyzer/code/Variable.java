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
public class Variable {
    String ident;
    int countOperators;

    public Variable(String ident) {
        this.ident = ident;
        countOperators=0;
    }
    
    public void incCountOperators(){
        countOperators++;
    }
    
    public int getCountOperators(){
        return countOperators;
    }
}
