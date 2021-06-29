package model;

import java.io.Serializable;

import model.*;

/**
 * Clase que contiene como unico atributo propio un string. Todos los usuarios dentro del sistema deben disponer de
 * un Password para poder iniciar sesion.
 * @see Usuario
 */
public class Password implements Serializable {

    private static final int LONGITUD_MIN = 8;
    private static final int LONGITUD_MAX = 20;

    private String clave;

    /**
     * Constructor que genera un Password.
     * @param clave Valor literal que sera la clave del Password.
     */
    public Password(String clave) {
        this.clave = clave;
    }

    /**
     * Metodo estatico que comprobara si la clave es alfanumerica. Su proposito es que sea invocado previo a la
     * instanciacion de la clase.
     * @param string El valor a comprobar.
     * @return true si es alfanumerico (unicamente digitos o letras), false si no lo es.
     */
    public static boolean isAlfanumerico(String string) {

        char[] charArray = string.toCharArray();
        for (char c : charArray) {
            if (!Character.isLetterOrDigit(c))
                return false;
        }
        return true;
    }

    /**
     * Metodo estatico que comprueba si la clave introducida esta dentro de los limites establecidos dentro de la
     * clase. Por defecto, la longitud minima es de 8 caracteres y la maxima es de 20.
     * @param string El valor a comprobar.
     * @return true si esta dentro de los limites establecidos, false si no.
     */
    public static boolean hasLongitudCorrecta(String string) {

        return string.length() >= LONGITUD_MIN && string.length() <= LONGITUD_MAX;
    }

    /**
     * Devuelve el literal de la clave. Su unico fin es el de comprobar si la clave coincide con la introducida por
     * un usuario al iniciar sesion.
     * @return La clave.
     */
    public String getClave() {
        return clave;
    }

    public boolean sonCoincidentes(String clave) {
        return this.clave.equals(clave);
    }
}

