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
public enum EnumMarkCountOperators {
    mode1 ("Много операторов между\nописанием и присваиванием.\nИнициализацию нужно\nпроводить раньше."),
    mode2 ("Количество операторов\nмежду описанием и\nприсваиванием в норме"),
    mode3 ("Kоличество операторов\nмежду описанием и\nприсваиванием соответвтвует\nкачественному коду");

    private final String name;       

    private EnumMarkCountOperators(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        return (otherName == null) ? false : name.equals(otherName);
    }

    public String toString() {
       return this.name;
    }
}
