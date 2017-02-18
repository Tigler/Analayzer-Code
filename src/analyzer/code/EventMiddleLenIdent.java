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
public class EventMiddleLenIdent extends Listener{

    public EventMiddleLenIdent(IMetric mli) {
        middleLenIdent = mli;
        successor=null;
    }
    
    public EventMiddleLenIdent(IMetric mli,Listener succsessor) {
        middleLenIdent = mli;
        this.successor=succsessor;
    }
    
    @Override
    public void onEvent(Event event) {   
        if(event.code == Event.ISIDENT){
            middleLenIdent.calculate(event);
        }else{
            if(successor!=null){
                successor.onEvent(event);
            }
        }
        
    }
}
