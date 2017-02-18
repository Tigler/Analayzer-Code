/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analyzer.code;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author tigler
 */
public abstract class SyntaxAnalyzer {
    protected Scaner sc;
    protected String text;
    protected Listener listener;
    public abstract void attach(Listener listener);
    public abstract void start();
    public abstract void setText(String text);
}
