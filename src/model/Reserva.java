package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import model.*;
import enums.*;
import utiles.*;

/**
 * Clase que hace parte de un objeto Hotel. Contiene toda la información relevante que conforma a una reserva,
 * incluyendo su número único de identificación, cliente, habitación y horarios de ingreso y salida.
 *
 * @see app.model.Hotel
 * @see app.model.Cliente
 * @see app.model.Habitacion
 */
public class Reserva implements Serializable {

    private String nroReserva;
    private Cliente cliente;
    private Habitacion habitacion;
    private LocalDateTime horarioCreacion = LocalDateTime.now();
    private LocalDateTime horarioOcupacion;
    private LocalDate fechaIngreso;
    private LocalDate fechaSalida;
    private boolean confirmada = false;
    private List<Producto> consumos = new ArrayList<>();

    /**
     * Unico constructor válido para su instanciacion. Objetos de esta clase son unicamente creados por usuarios de
     * tipo Conserje.
     *
     * @see Conserje
     *
     * @param cliente El cliente (que debe estar dentro del sistema) que realizo la reserva
     * @param habitacion La habitacion a reservar
     * @param fechaIngreso La fecha de ingreso
     * @param fechaSalida La fecha de salida
     */
    public Reserva(Cliente cliente, Habitacion habitacion,
                   LocalDate fechaIngreso, LocalDate fechaSalida) {

        this.cliente = cliente;
        this.habitacion = habitacion;
        this.fechaIngreso = fechaIngreso;
        this.fechaSalida = fechaSalida;
    }

    /**
     * Devuelve el cliente que genero la reserva
     * @return El cliente
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * Devuelve la lista de consumos hechos por el cliente. El proposito es que los valores que son parte de los
     * productos que son parte de esta lista sean sumados y agregados al total a pagar luego de la conclusion de
     * la estadia del cliente.
     * @return La list de consumos
     */
    public List<Producto> getConsumos() {
        return consumos;
    }

    /**
     * Devuelve el numero de reserva. Fundamental para hacer comparaciones en diversas operaciones dentro del programa.
     * @return El string correspondiente al numero de reserva
     */
    public String getNroReserva() {
        return nroReserva;
    }

    /**
     * Asigna un numero a la reserva. Dicha operacion es realizada por la clase Hotel, que conserva un registro del
     * numero de reservas realizadas. Este metodo no debe ser accedido en una situacion normal.
     * @see Hotel
     * @param nroReserva el string que se asigna a la reserva
     */
    public void setNroReserva(String nroReserva) {
        this.nroReserva = nroReserva;
    }

    /**
     * Verifica que la reserva fue confirmada. Es decir, si el cliente comenzo la ocupacion de la habitacion.
     * @return el valor booleano, es decir, true o false
     */
    public boolean isConfirmada() {
        return confirmada;
    }

    /**
     * Devuelve la habitacion correspondiente a la reserva.
     * @return la habitacion
     */
    public Habitacion getHabitacion() {
        return habitacion;
    }

    /**
     * Modifica el estado de la reserva, en el cual la confirmacion de la reserva (la estadia del cliente en el hotel)
     * se vuelve verdadera, se registra el horario de ingreso del cliente al hotel y se cambia el estado de la
     * habitacion reservada a ocupado.
     */
    public void confirmarOcupacion() {
        confirmada = true;
        horarioOcupacion = LocalDateTime.now();
        habitacion.setEstado(false);
    }

    /**
     * Obtiene la fecha de ingreso. Fundamental para realizar comparaciones con otras fechas para evitar conflictos
     * con horarios y fechas de otras reservas.
     * @see app.utils.FechaHoraUtil
     * @return la fecha de ingreso
     */
    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    /**
     * Obtiene la fecha de salida. Fundamental para realizar comparaciones con otras fechas para evitar conflictos
     * con horarios y fechas de otras reservas.
     * @see app.utils.FechaHoraUtil
     * @return la fecha de salida
     */
    public LocalDate getFechaSalida() {
        return fechaSalida;
    }

    /**
     * Representacion textual de la informacion de la reserva.
     * @return el string con la informacion relevante
     */

    /**
     * Agrega un producto a la lista de consumos dentro de la reserva.
     * @see Producto
     * @param producto El producto a agregar
     */
    public void agregarConsumo(Producto producto) {
        consumos.add(producto);
    }
    @Override
    public String toString() {
        return "ID reserva: " + nroReserva + "\nHabitacion: " + habitacion.getNumero() + "\nCliente: " +
                cliente.getNombreCompleto() + ", DNI " + cliente.getDni() + "\nIngreso: " + fechaIngreso +
                "\nSalida: " + fechaSalida + "\n";
    }
}
