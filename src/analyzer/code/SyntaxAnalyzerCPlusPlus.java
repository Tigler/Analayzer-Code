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
public class SyntaxAnalyzerCPlusPlus extends SyntaxAnalyzer {

    public SyntaxAnalyzerCPlusPlus(String text) {
        this.text = text;
        sc = new ScanerCPlusPlus(text);
        listener = null;
    }
    
    public SyntaxAnalyzerCPlusPlus() {
        this.text = null;
        sc = new ScanerCPlusPlus(text);
        listener = null;
    }

    @Override
    public void setText(String text) {
        this.text = text;
        sc = new ScanerCPlusPlus(text);
    }
    
    @Override
    public void attach(Listener listener) {
        this.listener= listener;
    }

    @Override
    public void start() {
        this.Axiom();
    }

    public void Axiom() {
        Lexem l;
        int uk1;
        uk1 = sc.GetUK();
        l = sc.Scan();
        sc.PutUK(uk1);
        while (l.code != Scaner.TEnd) {
            uk1 = sc.GetUK();
            l = sc.Scan();
            if (l.code == Scaner.TDouble) {
                sc.PutUK(uk1);
                this.Description();
            } else if (l.code != Scaner.TInt) {
                sc.PrintError("ожидался int", l.symbol, l.numStr);
            } else {
                l = sc.Scan();
                sc.PutUK(uk1);
                if (l.code == Scaner.TMain) {
                    this.Main();
                    l = sc.Scan();
                } else if (l.code != Scaner.TIdent) {
                    sc.PrintError("ожидался идентификатор", l.symbol, l.numStr);
                } else {
                    this.Description();
                }
            }
        }
        System.err.println("");
    }

    public void IF() {
        Lexem l;
        int uk1;
        l = sc.Scan();
        
            listener.onEvent(new Event(l.symbol, Event.IFSTART));

        if (l.code != Scaner.TIf) {
            sc.PrintError("ожидался if", l.symbol, l.numStr);
        } else {
            l = sc.Scan();
            if (l.code != Scaner.TLBracket) {
                sc.PrintError("ожидался символ (", l.symbol, l.numStr);
            } else {
                this.Expression();

                l = sc.Scan();
                if (l.code != Scaner.TRBracket) {
                    sc.PrintError("ожидался символ )", l.symbol, l.numStr);
                } else {
                    this.Operator();
                    uk1 = sc.GetUK();
                    l = sc.Scan();
                    if (l.code == Scaner.TElse) {                    
                            listener.onEvent(new Event(l.symbol, Event.IFSTART));
                        this.Operator();
                            listener.onEvent(new Event(l.symbol, Event.IFEND));
                    } else {
                        sc.PutUK(uk1);
                    }
                }
            }
        }
            listener.onEvent(new Event(l.symbol, Event.IFEND));
    }

    private void Description() {
        boolean flagOnlyDesc = true;
        int uk1;
        Lexem l;
        String curIdent = null;
        uk1 = sc.GetUK();
        l = sc.Scan();
        if (l.code != Scaner.TInt && l.code != Scaner.TDouble) {
            sc.PrintError("ожидался тип данных", l.symbol, l.numStr);
        }

        do {
            l = sc.Scan();
            if (l.code != Scaner.TIdent) {
                sc.PrintError("ожидался идентификатор", l.symbol, l.numStr);
            } else {               
                    listener.onEvent(new Event(l.symbol, Event.ISIDENT));            
                curIdent = l.symbol;
                l = sc.Scan();
                if (l.code == Scaner.TAssigment) {
                    flagOnlyDesc = false;
                    this.Expression();
                    l = sc.Scan();
                } else if (l.code == sc.TLSqBr) {
                    flagOnlyDesc = false;
                    do {
                        l = sc.Scan();
                        if (l.code != sc.TConstInt) {
                            sc.PrintError("ожидался целая константа", l.symbol, l.numStr);
                        } else {
                            l = sc.Scan();
                            if (l.code != sc.TRSqBr) {
                                sc.PrintError("ожидался символ ] ", l.symbol, l.numStr);
                            } else {
                                l = sc.Scan();
                                if (l.code != sc.TLSqBr) {
                                    if (l.code == sc.TSemicolon) {
                                        return;
                                    }
                                    if (l.code != sc.TAssigment) {
                                        sc.PrintError("ожидалась инициализация массива ", l.symbol, l.numStr);
                                    }
                                    this.InitMas();
                                    l = sc.Scan();
                                    if (l.code == Scaner.TSemicolon) {
                                        return;
                                    }
                                    break;
                                }
                            }
                        }
                    } while (l.code == Scaner.TLSqBr);
                }
            }
        } while (l.code == Scaner.TComma);
        if (l.code != Scaner.TSemicolon) {
            sc.PrintError("ожидался символ ;", l.symbol, l.numStr);
        }
        if (flagOnlyDesc) {

                listener.onEvent(new Event(l.symbol, Event.DESCRIPTION));
            
        }
    }

    private void Main() {
        int uk1;
        Lexem l;
        l = sc.Scan();
        if (l.code != sc.TInt) {
            sc.PrintError("ожидался int", l.symbol, l.numStr);
        }
        l = sc.Scan();
        if (l.code != sc.TMain) {
            sc.PrintError("ожидался main", l.symbol, l.numStr);
        }
        l = sc.Scan();
        if (l.code != sc.TLBracket) {
            sc.PrintError("ожидался символ (", l.symbol, l.numStr);
        }
        l = sc.Scan();
        if (l.code != sc.TRBracket) {
            sc.PrintError("ожидался символ )", l.symbol, l.numStr);
        }
        this.Bloc();
    }

    private void Bloc() {
        Lexem l;
        int uk1;
        l = sc.Scan();
        if (l.code != sc.TLBrace) {
            sc.PrintError("ожидался символ {", l.symbol, l.numStr);
        }
        uk1 = sc.GetUK();
        l = sc.Scan();
        while (l.code != sc.TRBrace) {
            if (l.code == sc.TEnd) {
                break;
            }
            sc.PutUK(uk1);
            this.Operator();
            uk1 = sc.GetUK();
            l = sc.Scan();
        }
        if (l.code != sc.TRBrace) {
            sc.PrintError("ожидался символ }", l.symbol, l.numStr);
        }

    }

    private void Expression() {
        int uk1;
        Lexem l;
        this.SumSub();
        uk1 = sc.GetUK();
        l = sc.Scan();
        while ((l.code == sc.TLessEqual) || (l.code == sc.TLess) || (l.code == sc.TMore) || (l.code == sc.TMoreEqual)) {
            this.SumSub();
            uk1 = sc.GetUK();
            l = sc.Scan();
        }
        sc.PutUK(uk1);
    }

    private void Operator() {
        Lexem l;
        int uk1 = sc.GetUK();
        l = sc.Scan();
        
            listener.onEvent(new Event(l.symbol, Event.OPERATOR));
        
        if (l.code == Scaner.TSemicolon) {
            return; // пустой оператор
        }
        if (l.code == Scaner.TFor) {
            sc.PutUK(uk1);
            this.For();
            return;
        }
        if (l.code == Scaner.TIf) {
            sc.PutUK(uk1);
            this.IF();
            return;
        }

        if (l.code == Scaner.TInt || l.code == Scaner.TDouble) {
            sc.PutUK(uk1);
            this.Description();
            return;
        }
        if (l.code == Scaner.TIdent) {
            sc.PutUK(uk1);
            this.Assigment();
            return;
        }
        if (l.code == sc.TLBrace) {
            sc.PutUK(uk1);
            this.Bloc();
            return;
        }
        sc.PrintError("ожидался идентификатор", l.symbol, l.numStr);
    }

    private void Assigment() {
        int uk1;
        Lexem l;
        String curIdent = null;
        l = sc.Scan();
        do {
            if (l.code != sc.TIdent) {
                sc.PrintError("ожидался идентификатор", l.symbol, l.numStr);
            }
                listener.onEvent(new Event(l.symbol, Event.ISIDENT));
            
            curIdent = l.symbol;
            l = sc.Scan();
            if (l.code == sc.TSemicolon) {
                return;
            }
            if (l.code != sc.TAssigment) {
                if (l.code == sc.TLSqBr) {
                    while (l.code == sc.TLSqBr) {
                        if (l.code != sc.TLSqBr) {
                            sc.PrintError("ожидался символ {", l.symbol, l.numStr);
                        }
                        this.Expression();
                        l = sc.Scan();
                        if (l.code != sc.TRSqBr) {
                            sc.PrintError("ожидался символ ]", l.symbol, l.numStr);
                        }
                        l = sc.Scan();
                    }
                }
            }
            uk1 = sc.GetUK();
            l = sc.Scan();
        } while (l.code == Scaner.TIdent);
        sc.PutUK(uk1);
        this.Expression();
        l = sc.Scan();
        if (l.code != Scaner.TSemicolon) {
            sc.PrintError("ожидался символ ;", l.symbol, l.numStr);
        }
       
            listener.onEvent(new Event(l.symbol, Event.ASSIGMENT));
    }

    private void For() {

        Lexem l;
        int uk1;
        l = sc.Scan();
        if (l.code != Scaner.TFor) {
            sc.PrintError("ожидался символ for", l.symbol, l.numStr);
        }
        l = sc.Scan();
        if (l.code != Scaner.TLBracket) {
            sc.PrintError("ожидался символ (", l.symbol, l.numStr);
        }
        uk1 = sc.GetUK();
        l = sc.Scan();
        if (l.code == Scaner.TInt || l.code == Scaner.TDouble) {
            sc.PutUK(uk1);
            this.Description();
            uk1 = sc.GetUK();
            sc.PutUK(uk1 - 1);
            l = sc.Scan();
        } else if (l.code == Scaner.TIdent) {
            this.Assigment();
        }

        if (l.code != Scaner.TSemicolon) {
            sc.PrintError("ожидался символ ;", l.symbol, l.numStr);
        }
        this.Expression();
        l = sc.Scan();
        if (l.code != Scaner.TSemicolon) {
            sc.PrintError("ожидался символ ;", l.symbol, l.numStr);
        }
        this.Expression();
        l = sc.Scan();
        if (l.code != Scaner.TRBracket) {
            sc.PrintError("ожидался символ)", l.symbol, l.numStr);
        }
        this.Operator();
    }

    private void SumSub() {
        int uk1;
        Lexem l;
        this.MulDiv();
        uk1 = sc.GetUK();
        l = sc.Scan();
        while ((l.code == Scaner.TPlus) || (l.code == Scaner.TMinus)) {
            this.MulDiv();
            uk1 = sc.GetUK();
            l = sc.Scan();
        }
        sc.PutUK(uk1);
    }

    private void MulDiv() {
        int uk1;
        Lexem l;
        this.Unary();
        uk1 = sc.GetUK();
        l = sc.Scan();
        while ((l.code == Scaner.TDiv) || (l.code == Scaner.TMul) || (l.code == Scaner.TMod)) {
            this.SimpleEx();
            uk1 = sc.GetUK();
            l = sc.Scan();
        }
        sc.PutUK(uk1);
    }

    private void Unary() {
        int uk1;
        Lexem l;
        uk1 = sc.GetUK();
        l = sc.Scan();
        if (l.code == Scaner.TPlusPlus || l.code == Scaner.TMinusMinus) {
            this.SimpleEx();
        } else {
            sc.PutUK(uk1);
            this.SimpleEx();
            uk1 = sc.GetUK();
            l = sc.Scan();
            if (l.code != Scaner.TPlusPlus && l.code != Scaner.TMinusMinus) {
                sc.PutUK(uk1);
            }
        }
    }

    private void SimpleEx() {//поправить со скобками
        int uk1;
        Lexem l;
        uk1 = sc.GetUK();
        l = sc.Scan();
        if ((l.code == Scaner.TConstChar) || (l.code == Scaner.TConstInt) || (l.code == Scaner.TConstDouble)) {
            return;
        }
        if (l.code == Scaner.TLBracket) {
            this.Expression();
            l = sc.Scan();
            if (l.code != Scaner.TRBracket) {
                sc.PrintError("ожидался символ )", l.symbol, l.numStr);
            }
            return;
        }
        if (l.code != Scaner.TIdent) {
            sc.PrintError("ожидался идентификатор", l.symbol, l.numStr);
        } else {
            uk1 = sc.GetUK();
            l = sc.Scan();
            while (l.code == Scaner.TLSqBr) {
                if (l.code != Scaner.TLSqBr) {
                    return;
                } else {
                    this.Expression();
                    l = sc.Scan();
                    if (l.code != Scaner.TRSqBr) {
                        sc.PrintError("ожидался символ ]", l.symbol, l.numStr);
                    } else {
                        l = sc.Scan();
                    }
                }
            }
            sc.PutUK(uk1); //???
        }
    }

    private void InitMas() {
        Lexem l;
        int uk1;
        l = sc.Scan();
        if (l.code == Scaner.TLBrace) {
            do {
                uk1 = sc.GetUK();
                l = sc.Scan();
                if (l.code == Scaner.TLBrace) {
                    sc.PutUK(uk1);
                    this.InitMas();
                } else {
                    sc.PutUK(uk1);
                    this.Expression();
                }
                l = sc.Scan();
            } while (l.code == Scaner.TComma);
            if (l.code != Scaner.TRBrace) {
                sc.PrintError("ожидался символ }", l.symbol, l.numStr);
            }
        } else {
            sc.PrintError("ожидался символ {", l.symbol, l.numStr);
        }
    }
}
