package utiles;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Clase utilitaria que trabaja con variables de tipo LocalDate y LocalDateTime. Permite formatear fechas y horas
 * de la forma en que se usan localmente (dd/MM/yyyy HH:mm) como asi tambien comparar diferentes fechas para detectar
 * conflictos a la hora de gestionar una posible reserva.
 */

public class FechaHoraUtil {

    public static final DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static final int horaCheckIn = 15;
    public static final int horaCheckOut = 11;
    public static final int minutoCero = 0;

    private FechaHoraUtil() {
    }

    /**
     * Verifica si dos fechas correspondientes a una reserva en creacion pueden ser compatibles con una reserva ya
     * existente o no.
     * @param inicioNueva La fecha de ingreso de la reserva a crear.
     * @param salidaNueva La fecha de salida de la reserva a crear.
     * @param inicioExistente La fecha de ingreso de una reserva ya existente.
     * @param salidaExistente La fecha de salida de una reserva ya existente.
     * @return true si hay conflictos y no se puede crear, false no hay conflictos y se puede crear.
     */
    public static boolean hayConflictosConFechaDeReserva(LocalDate inicioNueva, LocalDate salidaNueva,
                                                         LocalDate inicioExistente, LocalDate salidaExistente) {

        if (inicioNueva.isBefore(inicioExistente) && (salidaNueva.isBefore(inicioExistente) || salidaNueva.isEqual(inicioExistente))) {
            return false;
        } else if((inicioNueva.isAfter(salidaExistente) || inicioNueva.isEqual(salidaExistente)) && salidaNueva.isAfter(salidaExistente)) {
            return false;
        }
        return true;
    }

    /**
     * Comprueba si una fecha es anterior a la actual.
     * @param fecha La fecha a comparar.
     * @return true si la es, false si no.
     */
    public static boolean yaEsPasada(LocalDate fecha) {
        return fecha.isBefore(LocalDate.now());
    }
}

