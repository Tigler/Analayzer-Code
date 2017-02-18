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
public class Lexem {

    public int code;
    public String symbol;
    public int numStr;

    public Lexem(String symbol, int code, int numStr) {      
        this.symbol = symbol;
        this.code = code;
        this.numStr = numStr;
    }
}
