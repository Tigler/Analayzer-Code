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
public abstract class Listener {

    IMetric countOperator;
    IMetric levelNesting;
    IMetric middleLenIdent;
    Listener successor;

    public abstract void onEvent(Event event);
}
