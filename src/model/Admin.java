package model;

import java.util.List;
import java.util.Scanner;

import enums.*;
import menus.Menu;
import model.*;

/**
 * Clase que administra las cuentas de recepcionistas, las habitaciones y los clientes dentro del
 * sistema. Se pretende que solo haya una instancia de admin, aunque se permite que existan mas.
 *
 * @see Cliente
 * @see Conserje
 * @see Habitacion
 */
public class Admin extends Usuario {
    /**
     * Constrcutor que asigna el ID de usuario, un Password y un nombre personal
     *
     * @param id       El ID de usuario
     * @param password el Password
     * @param nombre   el nombre personal
     * @see Password
     */
    public Admin(String id, Password password, String nombre) {
        super(id, password, nombre);
    }

    /*Credenciales estandar para admin. Este metodo se llama cuando el sistema no detecta usuario admin al iniciar*/
    public static Admin proveerDefaultAdmin() {
        return new Admin("admin", new Password("password"), "nombre");
    }

    /*Metodos de gestion de recepcionistas*/

    /**
     * Metodo que genera un nuevo usuario de tipo Recepcionista. Arroja excepcion, por lo tanto debe ser manejada
     * apropiadamente.
     *
     * @param scanner El scanner necesario para el ingreso de datos por teclado
     * @param hotel   El hotel que contiene datos de recepcionistas ya existentes, necesario para verificar si ya existen
     *                recepcionistas con un ID ya existente
     * @return retorna un nuevo Recepcionista
     * @throws Exception si detecta un ID ya existente
     */
    public Recepcionista altaRecepcionista(Scanner scanner, Hotel hotel) throws Exception {

        //variables para condicionar
        boolean requisitosClave = false;
        String userConfirm = "";
        //variables para recepcionista
        String id = null;
        String psw = null;
        String nombre = null;

        while (!userConfirm.equals("s") && !userConfirm.equals("S")) {

            System.out.println("Ingresar id: ");
            id = scanner.nextLine();

            if (hotel.existeConserje(id)) {
                throw new Exception("El ID ya existe. No se puede crear recepcionista con este mismo ID.\n");
            }

            /*Asigno false para que entre al "while" más de una vez en caso de que el usuario haya ingresado mal
            su posible contraseña*/
            while (!requisitosClave) {
                System.out.println("Ingrese contraseña alfanumerica(8-20 digitos): ");
                psw = scanner.nextLine();

                if (Password.hasLongitudCorrecta(psw) && Password.isAlfanumerico(psw)) {
                    requisitosClave = true;
                } else {
                    System.out.println("La contrase�a ingresada no cumple todos los requisitos: ");
                }
            }

            System.out.println("Ingresar nombre: ");
            nombre = scanner.nextLine();

            System.out.println("Usted ha ingresado los siguientes datos: " +
                    "\nid: " + id +
                    "\npsw: " + psw +
                    "\nnombre: " + nombre);
            Menu.confirmarConTeclaS();
            userConfirm = scanner.nextLine();
        }

        Recepcionista nuevo = new Recepcionista(id, new Password(psw), nombre);

        System.out.println("Recepcionista creado.");
        System.out.println("Desea habilitar a este nuevo recepcionista ahora mismo? (s/n)");
        userConfirm = scanner.nextLine();

        if (userConfirm.equals("s") || userConfirm.equals("S")) {
            nuevo.cambiarEstadoHabilitado();
        }

        return nuevo;
    }

    /**
     * Cambia el estado de un recepcionista. Notese que cada vez que se crea un Recepcionista mediante el metodo 'altaRecepcionista'
     * este siempre esta deshabilitado para funcionar, por ende este metodo debe ser llamado por el admin para que
     * el recepcionista en cuestion tenga la capacidad de funcionar sin restricciones.
     *
     * @param hotel   El hotel que contiene la informacion de los recepcionistas
     * @param scanner El scanner necesario para el ingreso de datos por teclado
     * @see Admin#altaHabitacion(Scanner, Hotel)
     * @see Recepcionista
     */
    public void cambiarEstadoRecepcionista(Hotel hotel, Scanner scanner) {
        try {
        	Recepcionista recepcionista = obtenerRecepcionistaPorClave(scanner, hotel);
        	recepcionista.cambiarEstadoHabilitado();
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    public void modificarDatosRecepcionista(Hotel hotel, Scanner scanner) {

        System.out.println("Ingrese el ID del recepcionista que desea modificar: ");
        try {
        	Recepcionista aModificar = obtenerRecepcionistaPorClave(scanner, hotel);
            aModificar.modificarDatosUsuario(scanner, hotel);
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Elimina un recepcionista del sistema.
     *
     * @param hotel   El hotel que contiene la informacion de los recepcionistas
     * @param scanner El scanner necesario para el ingreso de datos por teclado
     */
    public void eliminarRecepcionista(Hotel hotel, Scanner scanner) {
        try {
        	Recepcionista aEliminar = obtenerRecepcionistaPorClave(scanner, hotel);

            System.out.println("Esta seguro que desea eliminar el siguiente recepcionista: \n");
            System.out.println(aEliminar);
            Menu.confirmarConTeclaS();
            String opcion = scanner.nextLine();

            if (opcion.equals("S") || opcion.equals("s")) {
                hotel.removerRecepcionista(aEliminar.getId());
                System.out.println("El recepcionista se ha eliminado exitosamente.");
            } else {
                System.out.println("El recepcionista no se ha eliminado");
            }
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    /*Debido a que este metodo es privado, documentacion del mismo es innecesario*/
    private Recepcionista obtenerRecepcionistaPorClave(Scanner scanner, Hotel hotel) {
    	Recepcionista recepcionista;
        System.out.println("Lista de recepcionistas:\n");
        hotel.listarTodosLosRecepcionistas();

        System.out.println("Seleccione el ID del recepcionista: ");

        recepcionista = hotel.encontrarRecepcionistaPorClave(scanner.nextLine());

        return recepcionista;
    }

    /*Metodos de gestion de habitaciones*/

    /**
     * Crea una nueva habitacion y la devuelve.
     *
     * @param scanner El scanner necesario para el ingreso de datos por teclado
     * @return La nueva habitacion
     * @throws Exception Si el sistema encuentra una habitacion con un numero ya existente de alta o si el usuario
     *                   no confirma la creacion de la nueva habitacion
     * @see Habitacion
     */
    public Habitacion altaHabitacion(Scanner scanner, Hotel hotel) throws Exception {
        boolean flag = false;
        String opcion = "";
        TipoHab tipoHabitacion;

        System.out.println("Ingresar numero de habitacion: ");
        String numeroHabitacion = scanner.nextLine();

        if (hotel.existeHabitacion(numeroHabitacion)) {
            throw new Exception("Ya existe una habitacion con ese numero.\n");
        }

        /*Mientras el usuario NO ingrese una opcion valida, va a seguir en el bucle.*/
        while (!flag) {
            try {
                System.out.println("Que clase de habitacion desea agregar?: \n1: Individual \n" +
                        "2: Matrimonial \n3: Familiar");
                opcion = scanner.nextLine();

                //Válido si el usuario ingresó una opción correcta.
                if (Integer.parseInt(opcion) >= 1 && Integer.parseInt(opcion) <= 3) {
                    //si ingresó la opción correcta, finalizo el bucle
                    flag = true;
                } else {
                    //si el usuario no ingresó una opción correcta vuelve a repetirse el proceso.
                    throw new Exception("No se ha ingresado una opcion valida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("No se ha ingresado un numero.");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        //Cuando confirmo que no ingresó una opcion valida. asigno el valor escogido por el usuario.
        tipoHabitacion = TipoHab.buscarPorID(opcion);

        double precioxdia = generarNuevoPrecioHabitacion(scanner);

        System.out.println("Usted esta a punto de crear la siguiente habitacion: \n");
        System.out.println("Numero: " + numeroHabitacion);
        System.out.println("Tipo: " + tipoHabitacion);
        System.out.println("Precio: " + precioxdia + "\n");
        System.out.println("Para confirmar, presione 's'.");

        opcion = scanner.nextLine();


        if (opcion.equals("S") || opcion.equals("s")) {
            return new Habitacion(numeroHabitacion, tipoHabitacion, precioxdia);
        } else {
            throw new Exception("La habitacion no ha sido creada.");
        }
    }

    /**
     * Pide ingresar el numero de habitacion que se desea elminiar. En caso de encontrarla, la elimina. Si ocurre lo
     * contrario, avisa que la habitacion no existe o no se ha eliminado y el metodo finaliza.
     *
     * @param scanner El scanner necesario para el ingreso de datos por teclado
     * @param hotel   El hotel que contiene la informacion de las habitaciones
     */
    public void eliminarHabitacion(Scanner scanner, Hotel hotel) {

        Habitacion habitacion;
        System.out.println("Listado de habitaciones validas para su eliminacion: \n");

        List<Habitacion> habitaciones = hotel.obtenerHabitacionesModificables();

        if (habitaciones.isEmpty()) {
            System.out.println("No hay habitaciones validas para eliminacion en este momento.");
            return;
        }

        for (Habitacion h : habitaciones) {
            System.out.println(h);
        }

        System.out.println("Ingrese el ID de habitacion a buscar.");
        scanner.nextLine();

        try {
            habitacion = hotel.encontrarHabitacionPorClave(scanner.nextLine());
            System.out.println("Esta seguro que desea eliminar la siguiente habitacion: ");
            System.out.println(habitacion);
            String opcion = scanner.nextLine();

            if (opcion.equals("S") || opcion.equals("s")) {
                hotel.removerHabitacion(habitacion.getNumero());
            }
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Permite modificar el precio de una habitacion que se encuentre en condiciones de ser modificada, es decir, que
     * se encuentre libre de cualquier tipo de reserva u ocupacion. Pide ingresar el numero de habitacion a modificar
     * de una lista de habitaciones modificables. Si el numero es valido, permite su modificacion de precio. Si no es
     * valido o no hay habitaciones modificables, se notifica y el metodo finaliza.
     *
     * @param scanner El scanner necesario para el ingreso de datos por teclado
     * @param hotel   El hotel que contiene la informacion de las habitaciones
     */
    public void modificarPrecioHabitacion(Scanner scanner, Hotel hotel) {

        List<Habitacion> modificables = hotel.obtenerHabitacionesModificables();

        if (!modificables.isEmpty()) {
            System.out.println("Habitaciones modificables: \n");
            for (Habitacion h : modificables) {
                System.out.println(h);
            }

            System.out.println("Ingrese el numero de la habitacion a modificar: ");
            String numero = scanner.nextLine();

            for (Habitacion h : modificables) {
                if (numero.equals(h.getNumero())) {
                    h.setPrecioPorDia(generarNuevoPrecioHabitacion(scanner));
                } else {
                    System.out.println("El numero no es valido");
                }
            }
        } else {
            System.out.println("No hay habitaciones modificables en este momento.");
        }
    }

    /*Descripcion para JavaDoc innecesaria ya que es un metodo privado*/
    private double generarNuevoPrecioHabitacion(Scanner scanner) {

        boolean valido = false;
        String valorIngresado = null;

        while (!valido) {
            try {
                System.out.println("Ingrese el valor de la habitacion (minimo 200): ");
                valorIngresado = scanner.nextLine();

                if (Double.parseDouble(valorIngresado) >= 200) {
                    valido = true;
                } else {
                    throw new Exception("El valor debe ser 200 o superior.");
                }
            } catch (NumberFormatException e) {
                System.out.println("El dato ingresado no es un numero.");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return Double.parseDouble(valorIngresado);
    }

    /**
     * Permite modificar el tipo de una habitacion que se encuentre en condiciones de ser modificada, es decir, que
     * se encuentre libre de cualquier tipo de reserva u ocupacion. Pide ingresar el numero de habitacion a modificar
     * de una lista de habitaciones modificables. Si el numero es valido, permite su modificacion de tipo. Si no es
     * valido o no hay habitaciones modificables, se notifica y el metodo finaliza.
     *
     * @param scanner El scanner necesario para el ingreso de datos por teclado
     * @param hotel   El hotel que contiene la informacion de las habitaciones
     */
    public void modificarTipoHabitacion(Scanner scanner, Hotel hotel) {

        List<Habitacion> modificables = hotel.obtenerHabitacionesModificables();

        if (!modificables.isEmpty()) {
            System.out.println("Habitaciones modificables: \n");
            for (Habitacion h : modificables) {
                System.out.println(h);
            }

            System.out.println("Ingrese el numero de la habitacion a modificar: ");
            String numero = scanner.nextLine();

            for (Habitacion h : modificables) {
                if (numero.equals(h.getNumero())) {
                    h.setTipo(generarTipoHabitacion(scanner));
                } else {
                    System.out.println("El numero no es valido");
                }
            }
        } else {
            System.out.println("No hay habitaciones modificables en este momento.");
        }
    }

    /*Descripcion para JavaDoc innecesaria ya que es un metodo privado*/
    private TipoHab generarTipoHabitacion(Scanner scanner) {
        //variable para condiciones
        boolean flag = false;
        String opcion = "";

        /*Mientras el usuario NO ingrese una opcion valida, va a seguir en el bucle.*/
        while (!flag) {
            try {

                System.out.println("Seleccione un tipo de habitacion: ");
                Menu.listadoTipoHab();
                opcion = scanner.nextLine();

                //Válido si el usuario ingresó una opción correcta.
                if (Integer.parseInt(opcion) >= 1 && Integer.parseInt(opcion) <= 3) {
                    //si ingresó la opción correcta, finalizo el bucle While
                    flag = true;
                } else {
                    //si el usuario no ingresó una opción correcta vuelve a repetirse el proceso.
                    throw new Exception("No se ha ingresado una opcion valida.");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        return TipoHab.buscarPorID(opcion);
    }

    /*Metodos de gestion de clientes*/

    /**
     * Elimina un cliente del sistema. Si no existe, se notifica y el metodo finaliza.
     *
     * @param scanner El scanner necesario para el ingreso de datos por teclado
     * @param hotel   El hotel que contiene la informacion de las habitaciones
     */
    public void eliminarCliente(Scanner scanner, Hotel hotel) {
        try {
            Cliente aEliminar = obtenerClientePorClave(hotel, scanner);

            if (hotel.tieneClienteReservasActivas(aEliminar.getDni())) {
                System.out.println("No puede eliminar este cliente ya que dispone de reservas activas.");
                return;
            }

            System.out.println("Esta seguro que desea eliminar el siguiente cliente del registro: ");
            System.out.println(aEliminar);
            Menu.confirmarConTeclaS();
            String opcion = scanner.nextLine();

            if (opcion.equals("S") || opcion.equals("s")) {
                hotel.removerCliente(aEliminar.getDni());
                System.out.println("Cliente eliminado.\n");
            } else {
                System.out.println("No se ha eliminado el cliente");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /*Metodo privado, JavaDoc innecesario*/
    private Cliente obtenerClientePorClave(Hotel hotel, Scanner scanner) {
        Cliente cliente;
        System.out.println("Lista de clientes:\n");
        hotel.listarTodosLosClientes();

        System.out.println("Seleccione el DNI del cliente a eliminar: ");

        cliente = hotel.encontrarClientePorClave(scanner.nextLine());

        return cliente;
    }
}



