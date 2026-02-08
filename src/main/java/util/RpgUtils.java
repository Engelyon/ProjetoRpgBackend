package util;

public class RpgUtils {
    public static int calcularModificador(int valorAtributo) {
        return (int) Math.floor((valorAtributo - 10) / 2.0);
    }
}
