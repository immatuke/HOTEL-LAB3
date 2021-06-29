package archivos;

/**
 * Clase contenedora de rutas donde se guardara y leera la informacion relevante para la administracion del hotel.
 * Sus atributos son estaticos y finales. Esta clase no es instanciable.
 */
public class Archivos {


    private static final String DIR = "dir/";


    public static final String ADMIN = DIR + "admin.dat";
    public static final String RECEPCIONISTAS = DIR + "recepcionistas.dat";
    public static final String CLIENTES = DIR + "clientes.dat";
    public static final String RESERVAS = DIR + "reservas.dat";
    public static final String HABITACIONES = DIR + "habitaciones.dat";
    public static final String INGRESOS = DIR + "ingresos.dat";
    public static final String CONTADOR_RESERVAS = DIR + "contador_reservas.dat";


}