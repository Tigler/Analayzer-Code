/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analyzer.code;

import java.util.Scanner;
import javax.swing.DefaultListModel;
import javax.swing.JList;

/**
 *
 * @author tigler
 */
public class ScanerCPlusPlus extends Scaner {

    @Override
    public Lexem Scan() {
        String l = "";
        boolean flag = true;

        while (flag) {
            flag = false;
            if (uk < t.length()) {
                while (t.charAt(uk) == ' ' || t.charAt(uk) == '\t') {
                    uk++;
                    stl++;
                    flag = true;
                }
            }

            if (uk < t.length()) {
                try {
                    while (t.charAt(uk) == '\n') {
                        uk++;
                        str++;
                        stl = 1;
                        flag = true;
                    }
                } catch (Exception e) {
                    System.err.println("");
                }
            }
            //однострочный комментарий
            if (uk < t.length()) {
                if (t.charAt(uk) == '/' && t.charAt(uk + 1) == '/') {
                    uk += 2;
                    stl += 2;
                    if (uk != t.length()) {
                        while (t.charAt(uk) != '\n' && t.charAt(uk) != '\0') {
                            uk++;
                            stl++;
                        }
                    }
                    flag = true;
                }
            }
            //многострочный комментарий
            if (uk != t.length()) {
                if (t.charAt(uk) == '/' && t.charAt(uk + 1) == '*') {
                    uk += 2;
                    stl += 2;
                    boolean flag1 = true;
                    if (uk != t.length()) {
                        while (flag1) {
                            if (t.charAt(uk) == '*' && t.charAt(uk + 1) == '/') {
                                flag1 = false;
                                uk += 2;
                                stl += 2;
                            } else if (uk != t.length()) {
                                if (t.charAt(uk) == '\n' && uk == t.length()) {
                                    str++;
                                    stl = 1;
                                    uk++;
                                } else {
                                    uk++;
                                    stl++;
                                }
                            }
                        }
                    }
                    flag = true;
                }
            }
        }

        //запись лексемы
        if (uk != t.length()) {
            if ((t.charAt(uk) <= '9' && t.charAt(uk) >= '0')) {
                l += t.charAt(uk);
                uk++;
                stl++;
                boolean f = false;
                if (uk != t.length()) {
                    while ((t.charAt(uk) <= '9' && t.charAt(uk) >= '0')) {
                        if (l.length() < MAX_Lexem - 1) {
                            l += t.charAt(uk);
                            uk++;
                            stl++;
                        } else {
                            f = true;
                            uk++;
                            stl++;
                        }
                    }
                    if (f) {
                        return new Lexem(l, TError, str);
                    }
                }
                //считывание double
                if (uk >= t.length()) {
                    l += "#";
                    return new Lexem(l, TEnd, str);
                }
                if (t.charAt(uk) == '.') {
                    l += t.charAt(uk);
                    uk++;
                    stl++;
                    while ((t.charAt(uk) <= '9' && t.charAt(uk) >= '0')) {
                        if (l.length() < MAX_Lexem - 1) {
                            l += t.charAt(uk);
                            uk++;
                            stl++;
                        } else if ((t.charAt(uk) >= 'a' && t.charAt(uk) <= 'z') || (t.charAt(uk) >= 'A' && t.charAt(uk) <= 'Z')) {
                            while ((t.charAt(uk) >= 'a' && t.charAt(uk) <= 'z') || (t.charAt(uk) >= 'A' && t.charAt(uk) <= 'Z')) {
                                l += t.charAt(uk);
                                uk++;
                                stl++;
                            }
                            return new Lexem(l, TError, str);
                        } else {
                            uk++;
                            stl++;
                        }
                    }
                    return new Lexem(l, TConstDouble, str);
                } else if ((t.charAt(uk) >= 'a' && t.charAt(uk) <= 'z') || (t.charAt(uk) >= 'A' && t.charAt(uk) <= 'Z')) {
                    while ((t.charAt(uk) >= 'a' && t.charAt(uk) <= 'z') || (t.charAt(uk) >= 'A' && t.charAt(uk) <= 'Z')) {
                        l += t.charAt(uk);
                        uk++;
                        stl++;
                    }
                    return new Lexem(l, TError, str);
                } else {
                    return new Lexem(l, TConstInt, str);
                }
            } else if ((t.charAt(uk) >= 'a' && t.charAt(uk) <= 'z') || (t.charAt(uk) >= 'A' && t.charAt(uk) <= 'Z')) {
                l += t.charAt(uk);
                uk++;
                stl++;
                while ((t.charAt(uk) >= 'a' && t.charAt(uk) <= 'z') || (t.charAt(uk) >= 'A' && t.charAt(uk) <= 'Z') || (t.charAt(uk) >= '0' && t.charAt(uk) <= '9')) {
                    l += t.charAt(uk);
                    uk++;
                    stl++;

                }
                //является ли лексема ключевым словом
                for (int i = 0; i < keyWords.length; i++) {
                    if (l.compareTo(keyWords[i]) == 0) {
                        //return new Lexem(l, i + 1);
                        return new Lexem(l, indexKeyWords[i], str);
                    }
                }
                //если не ключевое слово, то идентификатор
                return new Lexem(l, TIdent, str);

            } else if (t.charAt(uk) == '.') {
                l += t.charAt(uk);
                uk++;
                stl++;
                if (t.charAt(uk) >= '0' && t.charAt(uk) <= '9') {
                    while (t.charAt(uk) >= '0' && t.charAt(uk) <= '9') {
                        l += t.charAt(uk);
                        uk++;
                        stl++;
                    }
                    return new Lexem(l, TConstDouble, str);
                }
            } else if (t.charAt(uk) == ',') {
                l += t.charAt(uk);
                uk++;
                stl++;
                return new Lexem(l, TComma, str);
            } else if (t.charAt(uk) == ';') {
                l += t.charAt(uk);
                uk++;
                stl++;
                return new Lexem(l, TSemicolon, str);
            } else if (t.charAt(uk) == '(') {
                l += t.charAt(uk);
                uk++;
                stl++;
                return new Lexem(l, TLBracket, str);
            } else if (t.charAt(uk) == ')') {
                l += t.charAt(uk);
                uk++;
                stl++;
                return new Lexem(l, TRBracket, str);
            } else if (t.charAt(uk) == '{') {
                l += t.charAt(uk);
                uk++;
                stl++;
                return new Lexem(l, TLBrace, str);
            } else if (t.charAt(uk) == '}') {
                l += t.charAt(uk);
                uk++;
                stl++;
                return new Lexem(l, TRBrace, str);
            } else if (t.charAt(uk) == '[') {
                l += t.charAt(uk);
                uk++;
                stl++;
                return new Lexem(l, TLSqBr, str);
            } else if (t.charAt(uk) == ']') {
                l += t.charAt(uk);
                uk++;
                stl++;
                return new Lexem(l, TRSqBr, str);
            } else if (t.charAt(uk) == '*') {
                l += t.charAt(uk);
                uk++;
                stl++;
                return new Lexem(l, TMul, str);
            } else if (t.charAt(uk) == '/') {
                l += t.charAt(uk);
                uk++;
                stl++;
                return new Lexem(l, TDiv, str);
            } else if (t.charAt(uk) == '+') {
                l += t.charAt(uk);
                uk++;
                stl++;
                if (t.charAt(uk) == '+') {
                    l += t.charAt(uk);
                    uk++;
                    stl++;
                    return new Lexem(l, TPlusPlus, str);
                }
                if (t.charAt(uk) == '-') {
                    l += t.charAt(uk);
                    uk++;
                    stl++;
                    return new Lexem(l, TError, str);
                }
                return new Lexem(l, TPlus, str);
            } else if (t.charAt(uk) == '-') {
                l += t.charAt(uk);
                uk++;
                stl++;
                if (t.charAt(uk) == '-') {
                    l += t.charAt(uk);
                    uk++;
                    stl++;
                    return new Lexem(l, TMinusMinus, str);
                }
                if (t.charAt(uk) == '+') {
                    l += t.charAt(uk);
                    uk++;
                    stl++;
                    return new Lexem(l, TError, str);
                }
                return new Lexem(l, TMinus, str);
            } else if (t.charAt(uk) == '%') {
                l += t.charAt(uk);
                uk++;
                stl++;
                return new Lexem(l, TMod, str);
            } else if (t.charAt(uk) == '=') {
                l += t.charAt(uk);
                uk++;
                stl++;
                return new Lexem(l, TAssigment, str);
            } else if (t.charAt(uk) == '<') {
                l += t.charAt(uk);
                uk++;
                stl++;
                if (t.charAt(uk) == '=') {
                    l += t.charAt(uk);
                    uk++;
                    stl++;
                    return new Lexem(l, TLessEqual, str);
                }
                return new Lexem(l, TLess, str);
            } else if (t.charAt(uk) == '>') {
                l += t.charAt(uk);
                uk++;
                stl++;
                if (t.charAt(uk) == '=') {
                    l += t.charAt(uk);
                    uk++;
                    stl++;
                    return new Lexem(l, TMoreEqual, str);
                }
                return new Lexem(l, TMore, str);
            } else if (t.charAt(uk) == '\'') {
                l += t.charAt(uk);
                uk++;
                stl++;
                if (t.charAt(uk) == '\\') {
                    l += t.charAt(uk);
                    uk++;
                    stl++;
                    if (t.charAt(uk) == '\'') {
                        l += t.charAt(uk);
                        uk++;
                        stl++;
                        if (t.charAt(uk) == '\'') {
                            l += t.charAt(uk);
                            uk++;
                            stl++;
                            return new Lexem(l, TConstChar, str);
                        }
                    }
                }
                if (t.charAt(uk) != '\'') {
                    l += t.charAt(uk);
                    uk++;
                    stl++;
                }
                if (t.charAt(uk) == '\'') {
                    l += t.charAt(uk);
                    uk++;
                    stl++;
                    return new Lexem(l, TConstChar, str);
                }
                uk++;
                stl++;
                return new Lexem(l, TError, str);
            }
        } else {
            l += "#";
            return new Lexem(l, TEnd, str);
        }

        l += t.charAt(uk);
        uk++;
        stl++;
        return new Lexem(l, TError, str);

    }

    Unknown unknown;

    public ScanerCPlusPlus(String text) {
        this.t = text;
        unknown = new Unknown();
    }

    @Override
    public int GetUK() {
        return uk;
    }

    @Override
    public void PutUK(int auk) {
        if (auk < 0) {
            auk = 0;
        }
        uk = auk;

    }

    @Override
    public void PrintError(String err, String a, int str) {

        if (a.charAt(0) == '\0') {
            unknown.setError("Ошибка :" + err + " " + a);
        } else {
            unknown.setError("Ошибка :" + err + ". Неверный символ: " + a + "Строка:" + str);
        }

    }
}
