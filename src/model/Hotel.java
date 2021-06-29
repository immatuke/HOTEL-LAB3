package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;

import archivos.Archivos;
import enums.*;
import menus.Menu;
import model.*;
import utiles.*;

/**
 * Una de las clases fundamentales que componen al programa. Contiene toda la informacion crucial que hace a la
 * administracion del hotel, siendo esta las listas (mapas) de habitaciones, recepcionistas, reservas, clientes, datos del
 * administrador, los ingresos generados y el numero de reservas realizadas. Se pretende que solo haya una instancia
 * unica de esta clase, lograndolo aplicando la tecnica 'simpleton'. <p>
 * Al instanciarse esta clase, lee dentro del directorio designado en la clase Archivos los archivos que contienen
 * la informacion previamente guardada y la asigna dentro de sus atributos. Si no encuentra algun archivo o la
 * lectura no es exitosa, se asigna un valor estandar y se alerta al usuario del problema. Para acceder a este
 * constructor, se debe llamar al metodo estatico 'getInstancia()'.</p>
 */
public class Hotel {

    /*Diseño 'simpleton'. Asegura que solo sea posible una unica instancia de Hotel.*/
    private static Hotel laInstancia = new Hotel();

    private TreeMap<String, Habitacion> habitaciones;
    private TreeMap<String, Recepcionista> recepcionistas;
    private TreeMap<String, Cliente> clientes;
    private TreeMap<String, Reserva> reservas;
    private Admin admin;
    private Double totalIngresos;
    private Integer contadorReservas;

    private Hotel() {

        if ((admin = IOGenericoUtil.leerObjeto(Archivos.ADMIN)) == null) {
            System.out.println("No se ha podido leer objeto Admin. Contacte al administrador.");
            admin = Admin.proveerDefaultAdmin();
        }
        if ((recepcionistas = IOGenericoUtil.leerObjeto(Archivos.RECEPCIONISTAS)) == null) {
            System.out.println("No se ha podido leer objeto Recepcionistas. Contacte al administrador.");
            recepcionistas= new TreeMap<>();
        }
        if ((clientes = IOGenericoUtil.leerObjeto(Archivos.CLIENTES)) == null) {
            System.out.println("No se ha podido leer objeto Clientes. Contacte al administrador.");
            clientes = new TreeMap<>();
        }

        if ((reservas = IOGenericoUtil.leerObjeto(Archivos.RESERVAS)) == null) {
            System.out.println("No se ha podido leer objeto Reservas. Contacte al administrador.");
            reservas = new TreeMap<>();
        }
        if ((contadorReservas = IOGenericoUtil.leerObjeto(Archivos.CONTADOR_RESERVAS)) == null) {
            System.out.println("No se ha podido leer objeto ContadorReservas. Contacte al administrador.");
            contadorReservas = 0;
        }
        if ((habitaciones = IOGenericoUtil.leerObjeto(Archivos.HABITACIONES)) == null) {
            System.out.println("No se ha podido leer objeto Habitaciones. Contacte al administrador.");
            habitaciones = new TreeMap<>();
        }
        if ((totalIngresos = IOGenericoUtil.leerObjeto(Archivos.INGRESOS)) == null) {
            System.out.println("No se ha podido leer objeto Ingresos. Contacte al administrador.");
            totalIngresos = (double) 0;
        }

    }

    /*Metodo de instanciacion del unico objeto de tipo Hotel.*/

    /**
     * Metodo de instanciacion del unico objeto de tipo Hotel. Asegura que solamente haya un solo objeto de Hotel
     * cargado en memoria (simpleton).
     * @return el objeto Hotel
     */
    public static Hotel getInstancia() {
        return laInstancia;
    }

    /*
     * mediante el anterior metodo se listan las hab libres y se pide que se
     * seleccione una. Se retorna en caso de que los datos sean erroneos va a
     * devolver una hab null Deberia tratarse mejor
     */
    /*Siguientes 3 metodos se encargan de proveer una habitacion valida (o null :^\) para reserva.*/

    /**
     * Devuelve una habitacion valida para una reserva, segun el dato introducido por el usuario.
     * @param scanner El scanner necesario para la introduccion de datos por teclado
     * @return la Habitacion valida
     */
    public Habitacion seleccionarHabitacionParaReserva(Scanner scanner) {

        listadoHabitacionesPorTipo(scanner);
        System.out.println("Ingrese el numero de habitacion a seleccionar: \n");
        String numero = scanner.nextLine();
        Habitacion aux = null;

        for (Habitacion h : habitaciones.values()) {
            if (numero.equals(h.getNumero())) {
                aux = h;
            }
        }

        verReservasHabitacion(numero);

        if (aux == null) {
            System.out.println("Los datos ingresados son incorrectos \n");
            aux = seleccionarHabitacionParaReserva(scanner);
        }
        return aux;
    }

    /**
     * Muestra al usuario si existen reservas correspondientes a una habitacion especifica, con el fin de informar al
     * mismo si se puede generar un conflicto de fechas con una potencial reserva a realizarse.
     * @param numeroHab El numero de la habitacion en el que se desea ver si existen reservas
     */
    public void verReservasHabitacion(String numeroHab) {
        boolean valido = false;
        for (Reserva r : reservas.values()) {
            if (r.getHabitacion().getNumero().equals(numeroHab)) {
                while (!valido) {
                    System.out.println("Existen reservas pertenecientes a dicha habitacion:");
                    valido = true;
                }
                System.out.println(r);
            }
        }
    }

    /*metodo privado*/
    private void listadoHabitacionesPorTipo(Scanner scanner) {
        System.out.println("Ingrese el tipo de Habitacion que desea: ");
        Menu.listadoTipoHab();
        String opcion = scanner.nextLine();

        boolean haydato = false;
        /* Verifico que los datos ingresados sean los correctos */
        if (TipoHab.buscarPorID(opcion) != null) {
            for (Habitacion h : habitaciones.values()) {
                /*
                 * Compruebo q la habitacion este libre y que sea del tipo
                 * seleccionado
                 */

                if (h.getTipo().getID().equals(opcion)) {
                    /*
                     * Una vez que entra significa que hay al menos una
                     * habitacion
                     */
                    if (!haydato) {
                        System.out.println("Listado de Habitaciones libres:");
                        haydato = true;
                    }
                    System.out.println(h.getNumero() + " " + h.toStringEstadoHab());

                }
            }

            if (!haydato) {
                System.out.println("No se ha encontrado ninguna habitacion del tipo " + TipoHab.buscarPorID(opcion));
                listadoHabitacionesPorTipo(scanner);
            }
        } else {
            System.out.println("Los datos ingresados son incorrectos");
            listadoHabitacionesPorTipo(scanner);
        }
    }


    /*Metodos de listado de elementos en mapas*/

    /**
     * Lista todos los recepcionistas en el sistema.
     */
    public void listarTodosLosRecepcionistas() {
        for (Recepcionista r : recepcionistas.values()) {
            System.out.println(r);
        }
    }

    /**
     * Lista todas las habitaciones en el sistema.
     */
    public void listarTodasLashabitaciones() {
        for (Habitacion h : habitaciones.values()) {
            System.out.println(h);
        }
    }

    /**
     * Lista aquellas habitaciones que se encuentren libres, es decir, sin ocupacion.
     */
    public void listarHabitacionesLibres() {
        boolean existen = false;
        for (Habitacion h : habitaciones.values()) {
            if (h.elEstado()) {
                System.out.println(h);
                existen = true;
            }
        }
        if (!existen) {
            System.out.println("No hay habitaciones libres\n");
        }
    }

    /**
     * Lista aquellas habitaciones que se encuentren ocupadas. Notese que 'ocupadas' no implica que tengan alguna
     * reserva a futuro.
     */
    public void listarHabitacionesOcupadas() {
        boolean existen = false;
        for (Habitacion h : habitaciones.values()) {
            if (!h.elEstado()) {
                System.out.println(h);
                existen = true;
            }
        }
        if (!existen) {
            System.out.println("No hay habitaciones ocupadas\n");
        }
    }

    /**
     * Lista todos los clientes registrados.
     */
    public void listarTodosLosClientes() {
        for (Cliente c : clientes.values()) {
            System.out.println(c);
        }
    }

    /**
     * Lista todas las reservas, confirmadas (con ocupacion) o no.
     */
    public void listarTodasLasReservas() {
        for (Reserva r : reservas.values()) {
            System.out.println(r);
        }
    }

    /**
     * Lista aquellas reservas que se encuentran confirmadas, es decir con presente ocupacion en una habitacion.
     */
    public void listarReservasConfirmadas() {
        for (Reserva r : reservas.values()) {
            if (r.isConfirmada()) {
                System.out.println(r);
            }
        }
    }

    /*metodos de agregado y quita de elementos en los mapas*/

    /**
     * Agrega un objeto de tipo Recepcionista al mapa de conserjes.
     * @see Conserje
     * @param c El recepcionista a agregar
     */
    public void agregarRecepcionista(Recepcionista r) {
    	recepcionistas.put(r.getId(), r);
    }
    /**
     * Agrega un objeto de tipo Habitacion al mapa de habitaciones.
     * @see Habitacion
     * @param h La habitacion a agregar
     */
    public void agregarHabitacion(Habitacion h) {
        habitaciones.put(h.getNumero(), h);
    }
    /**
     * Agrega un objeto de tipo Cliente al mapa de clientes.
     * @see Cliente
     * @param c El cliente a agregar
     */
    public void agregarCliente(Cliente c) {
        clientes.put(c.getDni(), c);
    }

    /**
     * Agrega un objeto de tipo Reserva al mapa de reservas.
     * @see Habitacion
     * @param r La reserva a agregar
     */
    public void agregarReserva(Reserva r) {
        r.setNroReserva(String.valueOf(++contadorReservas));
        IOGenericoUtil.escribirObjeto(contadorReservas, Archivos.CONTADOR_RESERVAS);
        reservas.put(r.getNroReserva(), r);
    }

    /**
     * Quita una reserva del mapa de reservas.
     * @param key La clave asignada a la reserva
     */
    public void removerReserva(String key) {
        reservas.remove(key);
    }

    /**
     * Quita una cliente del mapa de clientes.
     * @param key La clave asignada al cliente
     */
    public void removerCliente(String key) {
        clientes.remove(key);
    }

    /**
     * Quita una habitacion del mapa de habitaciones.
     * @param key La clave asignada a la habitacion
     */
    public void removerHabitacion(String key) {
        habitaciones.remove(key);
    }

    /**
     * Quita un recepcionista del mapa de conserjes.
     * @param key La clave asignada al conserje
     */
    public void removerRecepcionista(String key) {
    	recepcionistas.remove(key);
    }

    /**
     * Agrega un valor numerico 'double' al total de ingresos.
     * @param ingreso El valor a agregar
     */
    public void agregarIngreso(double ingreso) {
        totalIngresos += ingreso;
    }


    /*Metodos que devuelven un elemento en los mapas segun clave. Arrojan excepciones en caso de que la clave
    * no sea valida*/

    /**
     * Devuelve un objeto de tipo Recepcionista segun la clave pasada por parametro. Arroja un NullPointerException
     * si la clave no se encuentra en el mapa.
     * @param key la clave asignada al elemento a encontrar
     * @return el objeto
     */
    public Recepcionista encontrarRecepcionistaPorClave(String key) {
        if (!recepcionistas.containsKey(key)) {
            throw new NullPointerException("No existe conserje con ese ID.");
        }
        return recepcionistas.get(key);
    }
    /**
     * Devuelve un objeto de tipo Cliente segun la clave pasada por parametro. Arroja un NullPointerException
     * si la clave no se encuentra en el mapa.
     * @param key la clave asignada al elemento a encontrar
     * @return el objeto
     */
    public Cliente encontrarClientePorClave(String key) {
        if (!clientes.containsKey(key)) {
            throw new NullPointerException("No existe cliente con ese DNI.");
        }
        return clientes.get(key);
    }

    /**
     * Devuelve un objeto de tipo Habitacion segun la clave pasada por parametro. Arroja un NullPointerException
     * si la clave no se encuentra en el mapa.
     * @param key la clave asignada al elemento a encontrar
     * @return el objeto
     */
    public Habitacion encontrarHabitacionPorClave(String key) {
        if (!habitaciones.containsKey(key)) {
            throw new NullPointerException("No existe habitacion con ese numero.");
        }
        return habitaciones.get(key);
    }


    /*Metodos que devuelven listas de elementos dentro de los mapas que cumplan con un cierto requisito*/
    public List<Reserva> obtenerReservasDeLaFecha() {

        List<Reserva> validas = new ArrayList<>();
        for (Reserva r : reservas.values()) {
            if (r.getFechaIngreso().equals(LocalDate.now())) {
                validas.add(r);
            }
        }
        return validas;
    }

    public List<Reserva> obtenerReservasConfirmadas() {
        List<Reserva> validas = new ArrayList<>();
        for (Reserva r : reservas.values()) {
            if (r.isConfirmada()) {
                validas.add(r);
            }
        }
        return validas;
    }

    public List<Habitacion> obtenerHabitacionesModificables() {

        List<Habitacion> lista = new ArrayList<>();

        for (Habitacion h : habitaciones.values()) {
            boolean modificable = true;
            for (Reserva r : reservas.values()) {
                if (h.getNumero().equals(r.getHabitacion().getNumero())) {
                    modificable = false;
                    break;
                }
            }
            if (modificable) {
                lista.add(h);
            }
        }
        return lista;
    }

    /**
     * Verifica si en el mapa existe una Habitacion.
     *
     * @param key La clave asignada al elemento a encontrar
     * @return true/false
     */
    public boolean existeHabitacion(String key) {
        return habitaciones.containsKey(key);
    }

    /**
     * Verifica si en el mapa existe un Recepcionista.
     * @param key La clave asignada al elemento a encontrar
     * @return true/false
     */
    public boolean existeConserje(String key) {
        return recepcionistas.containsKey(key);
    }


    /**
     * Comprueba si existe un admin con el ID pasado por parametro.
     * @param id el ID
     * @return true/false
     */
    public boolean existeAdmin(String id) {
        return admin.getId().equals(id);
    }

    /**
     * Comprueba si un cliente tiene reservas generadas. Esto evita la eliminacion del mismo en el sistema.
     * @param key la clave asignada al cliente en el sistema
     * @return true/false
     */
    public boolean tieneClienteReservasActivas(String key) {
        Cliente cliente = encontrarClientePorClave(key);

        for (Reserva r : reservas.values()) {
            if (r.getCliente().equals(cliente)) {
                return true;
            }
        }
        return false;
    }

    /*getters y setters*/

    /**
     * Devuelve el objeto Admin
     * @return objeto Admin
     */
    public Admin getAdmin() {
        return admin;
    }

    /**
     * Devuelve el mapa de habitaciones
     * @return el mapa
     */
    public TreeMap<String, Habitacion> getHabitaciones() {
        return habitaciones;
    }

    /**
     * Devuelve el mapa de recepcionistas
     * @return el mapa
     */
    public TreeMap<String, Recepcionista> getRecepcionista() {
        return recepcionistas;
    }

    /**
     * Devuelve el mapa de clientes
     * @return el mapa
     */
    public TreeMap<String, Cliente> getClientes() {
        return clientes;
    }

    /**
     * Devuelve el mapa de reservas
     * @return el mapa
     */
    public TreeMap<String, Reserva> getReservas() {
        return reservas;
    }

    /**
     * Devuelve el total de ingresos generados
     * @return el valor
     */
    public Double getTotalIngresos() {
        return totalIngresos;
    }
}


