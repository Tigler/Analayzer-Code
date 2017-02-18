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
public class EventLevelNest extends Listener {

    public EventLevelNest(IMetric ln) {
        levelNesting = ln;
        successor=null;
    }
    public EventLevelNest(IMetric ln,Listener successor) {
        levelNesting = ln;
        this.successor = successor;
    }

    @Override
    public void onEvent(Event event) {
        if (event.code == Event.IFSTART
                || event.code == Event.IFEND) {
            levelNesting.calculate(event);
        }else{
            if(successor!=null){
                successor.onEvent(event);
            }
        }
    }
}
