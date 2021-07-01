package archivos;

/**
 * Clase contenedora de rutas donde se guardara y leera la informacion relevante para la administracion del hotel.
 * Sus atributos son estaticos y finales. Esta clase no es instanciable.
 */
public class Archivos {


    private static final String DIR = "dir/";


    public static final String ADMIN = DIR + "administrador.dat";
    public static final String RECEPCIONISTAS = DIR + "recepcionista.dat";
    public static final String CLIENTES = DIR + "cliente.dat";
    public static final String RESERVAS = DIR + "reserva.dat";
    public static final String HABITACIONES = DIR + "habitacione.dat";
    public static final String INGRESOS = DIR + "ingreso.dat";
    public static final String CONTADOR_RESERVAS = DIR + "contador_reserva.dat";


}