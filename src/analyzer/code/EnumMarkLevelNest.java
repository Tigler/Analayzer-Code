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
public enum EnumMarkLevelNest {
    mode1("Уровень вложенности\nоператоров if большой."),
    mode2("Уровень вложенности\nоператоров if в норме"),
    mode3("Уровень вложенности\nоператоров if\nсоответвтвует\nкачественному коду");

    private final String name;

    private EnumMarkLevelNest(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        return (otherName == null) ? false : name.equals(otherName);
    }

    public String toString() {
        return this.name;
    }
}
