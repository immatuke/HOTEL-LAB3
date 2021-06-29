package model;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Scanner;

import archivos.Archivos;
import enums.*;
import menus.Menu;
import model.*;
import utiles.*;

/**
 * Clase concreta perteneciente al objeto Hotel. Recepcionista, junto con Admin, es una clase concreta derivada de Usuario.
 * Recepcionistas tienen la capacidad de dar alta a reservas, cancelarlas, registrar informacion de clientes y gestionar
 * check-in y check-out, entre otras acciones.
 *
 * @see Hotel
 * @see Usuario
 * @see Admin
 */
public class Recepcionista extends Usuario {

    private boolean habilitado = false;

    /**
     * Constructor unico para la clase. Se asignan un String que corresponde al nombre de usuario, un Password y un
     * nombre personal.
     *
     * @param id       el String identificador
     * @param password el Password
     * @param nombre   el nombre personal
     * @see Password
     */
    public Recepcionista(String id, Password password, String nombre) {
        super(id, password, nombre);
    }

    /**
     * Crea una reserva y la da de alta en el sistema.
     *
     * @param scanner El scanner necesario para el ingreso de datos por teclado
     * @param hotel   El hotel que contiene los datos relevantes que ayudan a determinar si hay posibles conflictos
     *                a la hora de generar la reserva
     * @return la reserva creada
     */
    public Reserva altaReserva(Scanner scanner, Hotel hotel) {

        Reserva reserva = null;
        LocalDate ingreso = null;
        LocalDate salida = null;

        Cliente cAux = null;
        boolean repetir = true;
        boolean confirmarReserva = false;

        while (!confirmarReserva) {
            boolean fechaValida = false;
            System.out.println("CREACION DE LA RESERVA\n");
            while (repetir) {
                System.out.println("1-Crear Cliente");
                System.out.println("2-Buscar Cliente");
                String opcion = scanner.nextLine();

                switch (opcion) {
                    case "1":
                        cAux = altaCliente(scanner, hotel);
                        repetir = false;
                        break;
                    case "2":
                        cAux = buscarCliente(scanner, hotel);
                        if (cAux == null) {
                            cAux = altaCliente(scanner, hotel);
                        }
                        repetir = false;
                        break;
                    default:
                        System.out.println("Opcion incorrecta");
                }
            }

            Habitacion hAux = hotel.seleccionarHabitacionParaReserva(scanner);

            while (!fechaValida) {
                try {
                    System.out.println("Ingrese fecha ingreso (dd/MM/yyyy): ");
                    ingreso = LocalDate.parse(scanner.nextLine(), FechaHoraUtil.formatoFecha);
                    if (FechaHoraUtil.yaEsPasada(ingreso)) {

                        throw new Exception("La fecha ya es pasada");
                    }
                    System.out.println("Ingrese fecha salida (dd/MM/yyyy): ");
                    salida = LocalDate.parse(scanner.nextLine(), FechaHoraUtil.formatoFecha);
                    if (FechaHoraUtil.yaEsPasada(salida) || salida.isEqual(ingreso)) {

                        throw new Exception("La fecha ya es pasada o es la misma que la de ingreso.");
                    }

                    for (Reserva r : hotel.getReservas().values()) {

                        if (r.getHabitacion().getNumero().equals(hAux.getNumero())) {

                            if (FechaHoraUtil.hayConflictosConFechaDeReserva(ingreso, salida,
                                    r.getFechaIngreso(), r.getFechaSalida())) {

                                throw new Exception("Existen conflictos con una reserva ya existente: " + r);
                            }
                        }

                    }
                    System.out.println("Las fechas son validas");
                    fechaValida = true;

                } catch (DateTimeParseException e) {
                    System.out.println("Fecha no valida");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

            System.out.println("Usted esta a punto de crear la siguiente reserva: ");
            System.out.println("DNI: " + cAux.getDni());
            System.out.println("Habitacion " + hAux.getNumero());
            System.out.println(ingreso);
            System.out.println(salida);
            Menu.confirmarConTeclaS();
            String aux = scanner.nextLine();
            if (aux.equals("s")) {
                confirmarReserva = true;
                reserva = new Reserva(cAux, hAux, ingreso, salida);
                System.out.println("RESERVA EXITOSA");
            }
        }
        return reserva;
    }

    /**
     * Cancela una reserva ya generada. El metodo no realiza nada si el dato introducido no corresponde a una reserva
     * creada o si el usuario no confirma la cancelacion de la misma.
     *
     * @param scanner El scanner necesario para el ingreso de datos por teclado
     * @param hotel   El hotel que contiene la informacion de las reservas dadas de alta
     */
    public void cancelarReserva(Scanner scanner, Hotel hotel) {

        System.out.println("Ingrese el numero de reserva que desea cancelar:");
        String numero = scanner.nextLine();
        boolean eliminado = false;
        boolean encontrado = false;
        Reserva aux = null;

        for (Reserva r : hotel.getReservas().values()) {
            if (numero.equals(r.getNroReserva())) {
                aux = r;
                encontrado = true;
                break;
            }
        }

        if (aux == null) {
            System.out.println("El codigo de la reserva no es valido.");
            return;
        }

        System.out.println("Usted esta por cancelar la siguiente reserva: \n");
        System.out.println(aux);
        Menu.confirmarConTeclaS();
        String confirmacion = scanner.nextLine();

        if (confirmacion.equals("s") || confirmacion.equals("S")) {
            hotel.removerReserva(aux.getNroReserva());
            eliminado = true;
        }

        if (encontrado) {
            if (eliminado) {
                System.out.println("Reserva cancelada.");
            } else {
                System.out.println("La reserva no se ha cancelado.");
            }
        } else {
            System.out.println("El codigo no es valido");
        }
    }

    /**
     * Confirma la estadia de un cliente en la habitacion. Cambia los estados de la reserva y la habitacion
     * correspondiente a la reserva
     *
     * @param scanner El scanner necesario para el ingreso de datos por teclado
     * @param hotel   El hotel que contiene la informacion de las reservas dadas de alta
     */
    public void checkIn(Scanner scanner, Hotel hotel) {

        List<Reserva> reservas = hotel.obtenerReservasDeLaFecha();
        String codigo;
        Reserva paraCheckIn = null;

        if (reservas.isEmpty()) {
            System.out.println("No existen reservas validas para check-in");
            return;
        }

        System.out.println("Lista de reservas para hoy:");

        for (Reserva r : reservas) {
            System.out.println(r);
        }

        System.out.println("Seleccione el codigo de la reserva: ");

        codigo = scanner.nextLine();

        for (Reserva r : reservas) {
            if (codigo.equals(r.getNroReserva())) {
                paraCheckIn = r;
            }
        }

        if (paraCheckIn == null) {
            System.out.println("El codigo es invalido.");
            return;
        }

        if (!paraCheckIn.isConfirmada()) {
            paraCheckIn.confirmarOcupacion();
            /*
             * Una vez confirmado se guarda la reserva en la lista
             * historial del Cliente
             */
            paraCheckIn.getCliente().agregarEntradaHistorial(paraCheckIn.toString());
            System.out.println("Check-in exitoso.");
        } else {
            System.out.println("La reserva ya esta confirmada.");
        }
    }

    /**
     * Calcula el total correspondiente a la estadia, sumando la cantidad de noches por el total de la habitacion
     * por dia, mas posibles consumos que haya solicitado el cliente. Esta funcion arroja una excepcion si no
     * encuentra la reserva para check-out, y debe manejarse debidamente
     *
     * @param scanner El scanner necesario para el ingreso de datos por teclado
     * @param hotel   El hotel que contiene la informacion de las reservas dadas de alta
     * @return el monto a pagar
     * @throws Exception Si no encuentra reservas validas para realizar check-out
     */
    /* Retorna el precio total de la reserva, incluyendo consumos, para que sea atrapado
    por el controlador y lo sume a la variable Double ingresosdentro de la clase Hotel*/
    public double checkOut(Scanner scanner, Hotel hotel) throws Exception {

        List<Reserva> reservasConfirmadas = hotel.obtenerReservasConfirmadas();
        String codigo;
        Reserva paraCheckOut = null;

        if (reservasConfirmadas.isEmpty()) {
            throw new Exception("No existen reservas validas para check-out");
        }

        System.out.println("Lista de reservas validas:");

        for (Reserva r : reservasConfirmadas) {
            System.out.println(r);
        }

        System.out.println("Seleccione el codigo de la reserva:");

        codigo = scanner.nextLine();

        for (Reserva r : reservasConfirmadas) {
            if (codigo.equals(r.getNroReserva())) {
                paraCheckOut = r;
            }
        }

        if (paraCheckOut == null) {
            throw new Exception("Codigo invalido");
        }

        /* Calculo la cantidad de dias que duro la reserva */

        LocalDate fechaActual = LocalDate.now();
        int periodo;
        boolean pasadoDeFecha = false;

        if (fechaActual.isAfter(paraCheckOut.getFechaSalida())) {
            periodo = (int) ChronoUnit.DAYS.between(paraCheckOut.getFechaIngreso(), fechaActual);
            pasadoDeFecha = true;
        } else {
            periodo = (int) ChronoUnit.DAYS.between(paraCheckOut.getFechaIngreso(), paraCheckOut.getFechaSalida());
        }

        double precioTotal = paraCheckOut.getHabitacion().getPrecioPorDia() * periodo;
        System.out.println("CHECK OUT");
        System.out.println("Precio por noche en habitacion: " + paraCheckOut.getHabitacion().getPrecioPorDia());
        System.out.println("Cantidad de noches: " + periodo);
        if (pasadoDeFecha) {
            precioTotal += 200;
            System.out.println("Penalizacion por check-out luego de la fecha de salida: 200");
        }
        System.out.println("El precio de la estadia es de $" + precioTotal);

        double precioProductos = 0;
        for (Producto p : paraCheckOut.getConsumos()) {
            System.out.println(p + " - $" + p.getPrecio());
            precioProductos = precioProductos + p.getPrecio();
        }
        precioTotal += precioProductos;
        System.out.println("TOTAL A PAGAR: $" + precioTotal);

        paraCheckOut.getHabitacion().setEstado(true);
        hotel.removerReserva(paraCheckOut.getNroReserva());

        return precioTotal;
    }

    /**
     * Agrega un consumo de la lista fija de productos a una reserva confirmada.
     *
     * @param scanner El scanner necesario para el ingreso de datos por teclado
     * @param hotel   El hotel que contiene la informacion de las reservas confirmadas
     */
    /*Muestra el listado de Productos y da a seleccionar cual se agrega a la cuenta.*/
    public void agregarConsumo(Scanner scanner, Hotel hotel) {

        List<Reserva> validas = hotel.obtenerReservasConfirmadas();
        Reserva aux = null;

        System.out.println("Escoja una reserva de las siguientes: ");
        for (Reserva r : validas) {
            System.out.println(r);
        }

        String codigo = scanner.nextLine();

        for (Reserva r : validas) {
            if (codigo.equals(r.getNroReserva())) {
                aux = r;
            }

            if (aux != null) {
                while (true) {
                    int numero;
                    Menu.listadoProductos();
                    System.out.println("\nSeleccione el numero de producto: ");
                    String opcion = scanner.nextLine();

                    try {
                        numero = Integer.parseInt(opcion);
                    } catch (NumberFormatException e) {
                        numero = 0;
                    }
                    if (numero <= 0 || numero > Producto.values().length) {
                        System.out.println("El dato es incorrecto.");
                        break;
                    } else {
                        r.agregarConsumo(Producto.buscarPorID(opcion));
                        System.out.println("Se ha agregado " + Producto.buscarPorID(opcion) + " a la reserva");
                        System.out.println("Agregar otro producto? s/n: ");
                        opcion = scanner.nextLine();

                        if (!opcion.equals("S") && !opcion.equals("s")) {
                            break;
                        }
                    }
                }
            } else {
                System.out.println("El codigo no es valido.");
            }
        }
    }

    /**
     * Crea un cliente y lo registra en el sistema. Esto se hace unicamente cuando se desea generar una reserva y
     * la informacion del cliente no se encuentra aun registrada en el sistema.
     *
     * @param scanner El scanner necesario para el ingreso de datos por teclado
     * @param hotel   El hotel que contiene la lista de clientes registrados
     * @return el Cliente generado
     */
    /*Se crea un cliente si se confirma, sino se vuelve a crear.
    Esto es por si los datos ingresados son no deseados*/
    public Cliente altaCliente(Scanner scanner, Hotel hotel) {
        System.out.println("CREACION DE CLIENTE\n");
        System.out.println("Ingrese nombre:");
        String nom = scanner.nextLine();
        System.out.println("Ingrese apellido:");
        String ape = scanner.nextLine();
        System.out.println("Ingrese dni:");
        String dni = scanner.nextLine();
        System.out.println("Ingrese nacionalidad:");
        String nac = scanner.nextLine();
        System.out.println("Ingrese direccion:");
        String dir = scanner.nextLine();

        System.out.println("1-Confirmar ");
        System.out.println("2-Volver a ingresar los datos ");
        String confirmar = scanner.nextLine();
        if (confirmar.equals("1")) {
            Cliente aux = new Cliente(nom, ape, dni, nac, dir);
            hotel.agregarCliente(aux);
            IOGenericoUtil.escribirObjeto(hotel.getClientes(), Archivos.CLIENTES);
            System.out.println("Se ha creado el siguiente Cliente " + aux);
            return aux;
        } else {
            // el cliente
            return altaCliente(scanner, hotel);
        }
    }

    /**
     * Busca un cliente dentro del mapa de clientes en el objeto Hotel y lo retorna.
     * ADVERTENCIA: el metodo puede retornar null. Asegurar su llamado manejandolo apropiadamente.
     *
     * @param scanner El scanner necesario para el ingreso de datos por teclado
     * @param hotel   El hotel que contiene la lista de clientes registrados
     * @return el cliente
     */
    /*Este metodo busca un cliente x dni si no lo encuentra retorna null*/
    public Cliente buscarCliente(Scanner scanner, Hotel hotel) {
        System.out.println("Ingrese dni del Cliente a buscar: ");
        String dni = scanner.nextLine();

        Cliente aux = null;

        for (Cliente c : hotel.getClientes().values()) {

            if (c.getDni().equals(dni)) {
                aux = c;
                break;
            }
        }

        if (aux == null) {
            System.out.println("No se encontro ningun cliente con el dni " + dni + " en la base de datos.");
            System.out.println("Volver a intentar? s/n");
            String opcion = scanner.nextLine();
            if (opcion.equals("s")) {
                aux = buscarCliente(scanner, hotel);
            }

        } else {
            System.out.println("Se ha encontrado el siguiente Cliente: " + aux);
        }

        return aux;
    }

    /**
     * Modifica los datos del cliente y lo registra nuevamente en el sistema.
     *
     * @param scanner El scanner necesario para el ingreso de datos por teclado
     * @param hotel   El hotel que contiene la lista de clientes registrados
     */
    public void modificarCliente(Scanner scanner, Hotel hotel) {
        Cliente aux = buscarCliente(scanner, hotel);
        if (aux == null) {
            System.out.println("No hay cliente en sistema");
            return;
        }
        Menu.menuModificarCliente();

        String opcion = scanner.nextLine();

        switch (opcion) {
            case "1":
                System.out.println("Ingrese el nuevo nombre:\n");
                String nom = scanner.nextLine();
                aux.setNombre(nom);
                break;
            case "2":
                System.out.println("Ingrese el nuevo apellido::\n");
                String ape = scanner.nextLine();
                aux.setApellido(ape);
                break;
            case "3":
                System.out.println("Ingrese el nuevo dni:\n");
                String dni = scanner.nextLine();
                aux.setDni(dni);
                break;
            case "4":
                System.out.println("Ingrese la nuevo nacionalidad:\n");
                String nac = scanner.nextLine();
                aux.setNacionalidad(nac);
                break;
            case "5":
                System.out.println("Ingrese la nuevo direccion:\n");
                String dir = scanner.nextLine();
                aux.setNacionalidad(dir);
                break;
        }

    }

    /**
     * Modifica el estado del recepcionista. Si se deshabilita, el recepcionista no podra realizar ningun tipo de actividad
     * al cerrar sesion.
     */
    public void cambiarEstadoHabilitado() {
        habilitado = !habilitado;
    }

    /**
     * Devuelve el valor boolean que indica si el recepcionista esta habilitado para trabajar en el sistema o no.
     * @return true o false
     */
    public boolean isHabilitado() {
        return habilitado;
    }

    /**
     * Representacion textual del recepcionista. Provee informacion relevante correspondiente al recepcionista.
     *
     * @return el String con la informacion correspondiente
     */
    @Override
    public String toString() {
        return super.toString() + "\nHabilitado: " + habilitado + "\n";
    }

}
