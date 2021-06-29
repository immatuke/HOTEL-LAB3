package model;

import java.io.Serializable;

import model.*;
import enums.*;

/**
 * Clase que conforma a un objeto Hotel y que es parte de una reserva. Posee un numero unico de identificacion, tipo
 * (individual, matrimonial, familiar), su precio por dia y diferentes estados, que segun ellos, le permiten a la
 * habitacion ser modificada o no.
 *
 * @see Hotel
 */
public class Habitacion implements Serializable {

    private String numero;
    private boolean estado;//true libre, false ocupado
    private TipoHab tipo;
    private double precioPorDia;
    private boolean habilitada; /*Si es 'false', es modificable*/

    /**
     * Constructor unico. Se asigna numero de identificacion, el tipo de habitacion (ver descripcion general de la
     * clase) y su precio por dia.
     *
     * @param numero       su identificador
     * @param tipo         su tipo
     * @param precioPorDia su precio por dia
     */
    public Habitacion(String numero, TipoHab tipo, double precioPorDia) {
        this.numero = numero;
        this.tipo = tipo;
        this.precioPorDia = precioPorDia;
        estado = true;
        habilitada = true;
    }

    /**
     * Cambia el estado de la habitacion entre habilitada y deshabilitada.
     */
    public void cambiarEstadoHabilitado() {
        habilitada = !habilitada;
    }

    /**
     * Devuelve el tipo de la habitacion
     *
     * @return su tipo
     */
    public TipoHab getTipo() {
        return tipo;
    }

    /**
     * Asigna un nuevo tipo a la habitacion. Notese que esto solo es posible si la habitacion esta habilitada para
     * modificaciones.
     *
     * @param tipo El tipo de habitacion a asignar
     */
    public void setTipo(TipoHab tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtiene el numero de identificacion de la habitacion.
     *
     * @return el numero de identificacion
     */
    public String getNumero() {
        return numero;
    }

    /**
     * Devuelve el estado de la habitacion, es decir, si esta habilitada o no.
     *
     * @return true o false, segun su estado
     */
    public boolean elEstado() {
        return estado;
    }

    /**
     * Asigna un estado a la habitacion.
     *
     * @param estado true o false
     */
    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    /**
     * Obtiene el precio por dia de la habitacion. Necesario para el proceso de Check-out.
     *
     * @return el valor correspondiente a una noche de estadia en la habitacion
     * @see Conserje
     */
    public double getPrecioPorDia() {
        return precioPorDia;
    }

    /**
     * Asigna un valor nuevo para la habitacion. Notese que esto solo es posible si la habitacion esta habilitada
     * para modificaciones.
     *
     * @param precioPorDia
     */
    public void setPrecioPorDia(double precioPorDia) {
        this.precioPorDia = precioPorDia;
    }

    /**
     * Devuelve una representacion textual del estado de la habitacion.
     *
     * @return el String correspondiente al estado
     */
    public String toStringEstadoHab() {
        String aux;
        if (estado == true) {
            aux = "LIBRE";
        } else {
            aux = "OCUPADO";
        }
        return aux;
    }

    /**
     * Representacion textual de la habitacion. Provee informacion relevante correspondiente a la habitacion.
     *
     * @return el String con la informacion correspondiente
     */
    @Override
    public String toString() {
        return "Habitacion " + numero + "\nTipo: " + tipo + "\nPrecio: " + precioPorDia + " x dia" + "\n" +
                "Estado: " + toStringEstadoHab() + "\n";
    }
}
