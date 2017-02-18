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
public class Unknown {
    ArrayList<String> errorSynax;

    public Unknown() {
        errorSynax=new ArrayList<>();
    }
    
    public void setError(String err){
        errorSynax.add(err);
    }
    
}
