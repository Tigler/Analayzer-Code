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
public class Event {

    final static int IFSTART = 1;
    final static int IFEND = 2;
    final static int DESCRIPTION = 3;
    final static int ASSIGMENT = 4;
    final static int OPERATOR = 5;
    final static int ISIDENT = 6;
    String ident;
    int code;

    Event(String ident, int code) {
        this.ident = ident;
        this.code = code;
    }
}
