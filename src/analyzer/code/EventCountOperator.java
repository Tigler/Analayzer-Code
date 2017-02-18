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
public class EventCountOperator extends Listener {

    public EventCountOperator(IMetric ce) {
        countOperator = ce;
        successor=null;
    }
    
    public EventCountOperator(IMetric ce,Listener successer) {
        countOperator = ce;
        this.successor=successor;
    }

    @Override
    public void onEvent(Event event) {
        if (event.code == Event.ASSIGMENT
                || event.code == Event.DESCRIPTION
                || event.code == Event.OPERATOR) {
            countOperator.calculate(event);
        }else{
            if(successor!=null){
                successor.onEvent(event);
            }
        }
    }
}
