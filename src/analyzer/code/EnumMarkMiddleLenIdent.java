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
public enum EnumMarkMiddleLenIdent {
    mode1("Длина идентификатора\nслишком мала.\nКод не читаемый"),
    mode2("Длина идентификаторов\nв норме.\nКод читаемый"),
    mode3("Длина идентификаторов\nслишком велика\nсоздается нагромождение");

    private final String name;

    private EnumMarkMiddleLenIdent(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        return (otherName == null) ? false : name.equals(otherName);
    }

    public String toString() {
        return this.name;
    }
}
