/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analyzer.code;

import java.util.Scanner;

/**
 *
 * @author tigler
 */
public abstract class Scaner {

    static final int MAX_Lexem = 20;

    static final int TIdent = 1;
    static final int TMain = 2;
    static final int TFor = 3;
    static final int TInt = 4;
    static final int TDouble = 5;
    //Константы
    static final int TConstInt = 6;
    static final int TConstChar = 7;

    //знаки операций
    static final int TPlusPlus = 8;
    static final int TMinusMinus = 9;
    static final int TMore = 10;
    static final int TMoreEqual = 11;
    static final int TLess = 12;
    static final int TLessEqual = 13;
    static final int TMinus = 14;
    static final int TPlus = 15;
    static final int TMul = 16;
    static final int TDiv = 17;
    static final int TMod = 18;
    //Точка с запятой, скобки, присваивание
    static final int TSemicolon = 19;
    static final int TLBracket = 20;
    static final int TRBracket = 21;
    static final int TLBrace = 22;
    static final int TRBrace = 23;
    static final int TLSqBr = 24;
    static final int TRSqBr = 25;
    static final int TAssigment = 26;
    //Точка для double  и запятая для массива
    static final int TDot = 27;
    static final int TComma = 28;
    //Ошибочный символ
    static final int TError = 29;
    //Конец исходного модуля
    static final int TEnd = 30;
    static final int TConstDouble = 31;
    static final int TIf = 32;
    static final int TElse = 33;

    static final String keyWords[] = {"main", "for", "int", "double","if","else"};
    static final int indexKeyWords[] = {TMain, TFor, TInt, TDouble,TIf,TElse};

    protected String t;
    protected int uk;  //указатель текущей позиции
    protected int str; //указатель номера строки
    protected int stl;  //указатель позиции в строке

    public abstract Lexem Scan();

    public abstract int GetUK();

    public abstract void PutUK(int uk);

    public abstract void PrintError(String err, String a, int str);
}
