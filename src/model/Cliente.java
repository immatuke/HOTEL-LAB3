package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que contiene los datos de los clientes que ocuparan alguna habitacion en el hotel.
 */
public class Cliente implements Serializable {

    private String nombre;
    private String apellido;
    private String dni;
    private String nacionalidad;
    private String direccion;
    private List<String> historial = new ArrayList<>();

    /**
     * Unico constructor, con parametros de tipo String.
     * @param nombre El nombre del cliente
     * @param apellido El apellido del cliente
     * @param dni El DNI o identificador personal del cliente
     * @param nacionalidad La nacionalidad del cliente
     * @param direccion La direccion fisica del cliente
     */
    public Cliente(String nombre, String apellido, String dni, String nacionalidad, String direccion) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.nacionalidad = nacionalidad;
        this.direccion = direccion;
    }


    /**
     * Agrega una entrada al historial. Su proposito es tener un registro de reservas y visitas que el cliente
     * efectuo en el hotel
     * @param entrada Representacion textual de la informacion relevante de la reserva hecha por el cliente
     */
    public void agregarEntradaHistorial(String entrada) {
        historial.add(entrada);
    }

    /**
     * Modifica el nombre del cliente
     * @param nombre el nuevo nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Modifica el apellido del cliente
     * @param apellido el nuevo apellido
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /**
     * Modifica el DNI o clave de identificacion del cliente
     * @param dni el nuevo codigo de identificacion
     */
    public void setDni(String dni) {
        this.dni = dni;
    }

    /**
     * Modifica la nacionalidad del cliente
     * @param nacionalidad la nueva nacionalidad
     */
    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    /**
     * Modifica la direccion del cliente
     * @param direccion la nueva direccion
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Obitene el DNI o clave de identificacion del cliente
     * @return la clave de identificacion
     */
    public String getDni() {
        return dni;
    }

    /**
     * Retorna un string con el nombre y el apellido del cliente
     * @return el string
     */
    public String getNombreCompleto() {
        return apellido + ", " + nombre;
    }

    /**
     * Retorna la lista con el historial del cliente
     * @return la lista
     */
    public List<String> getHistorial() {
        return historial;
    }

    /**
     * Representacion textual del cliente. Provee informacion relevante correspondiente al cliente.
     * @return el String con la informacion correspondiente
     */
    @Override
    public String toString() {
        return "\nDNI: " + dni + "\nNombre: " + apellido + ", " + nombre + "\nDireccion: " +
                direccion + "\nNacionalidad: " + nacionalidad.toString() + "\n";
    }

}
