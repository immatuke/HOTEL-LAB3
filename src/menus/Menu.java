package menus;

import enums.*;

/**
 * Clase contenedora de menus, es decir, las impresiones por pantalla en donde se le solicita al usuario escojer una
 * opcion para llevar a cabo una determinada accion. Todos los metodos son estaticos y unicamente contienen print-outs.
 * Se recomienda incluir menus con opciones concretas y especificas dentro de esta clase.
 */
public class Menu {

    private Menu() {
    }

    public static void adminMain() {
    	System.out.println("\n----------Menu Admin------------\n");
        System.out.println("\n1. Administrar conserjes");
        System.out.println("2. Administrar habitaciones");
        System.out.println("3. Administrar clientes");
        System.out.println("4. Administrar informacion propia");
        System.out.println();
        System.out.println("0. Cerrar sesion\n");
        seleccionOpcion();
    }

    public static void adminSubMenuClientes() {
    	System.out.println("\n----------Admin SubMenu Clientes------------\n");
        System.out.println("\n1. Ver registros de clientes");
        System.out.println("2. Eliminar cliente del sistema");
        System.out.println();
        System.out.println("0. Volver\n");
        seleccionOpcion();
    }

    public static void adminSubMenuRecepcionista() {
    	System.out.println("\n----------Admin SubMenu Recepcionista------------\n");
        System.out.println("\n1. Dar alta a recepcionista");
        System.out.println("2. Modificar datos de recepcionista");
        System.out.println("3. Habilitar/deshabilitar recepcionista");
        System.out.println("4. Eliminar recepcionista.");
        System.out.println("5. Ver listado recepcionistas");
        System.out.println();
        System.out.println("0. Volver\n");
        seleccionOpcion();
    }

    public static void adminSubMenuHabitaciones() {
    	System.out.println("\n----------Admin SubMenu Habitaciones------------\n");
        System.out.println("\n1. Crear habitacion");
        System.out.println("2. Modificar tipo de habitacion");
        System.out.println("3. Modificar precio de habitacion");
        System.out.println("4. Eliminar habitacion");
        System.out.println("5. Ver todas las habitaciones");
        System.out.println();
        System.out.println("0. Volver\n");
        seleccionOpcion();
    }

    public static void recepcionistaMain() {
    	System.out.println("\n----------Recepcionista Menu------------\n");
        System.out.println("\n1. Gestion de reservas");
        System.out.println("2. Gestion de clientes");
        System.out.println("3. Gestion de habitaciones");
        System.out.println("4. Modificar informacion propia");
        System.out.println();
        System.out.println("0. Cerrar sesion");
        seleccionOpcion();
    }

    public static void recepcionistaSubMenuReservas() {
      	System.out.println("\n----------Recepcionista SubMenu Reservas------------\n");
        System.out.println("\n1. Generar reserva");
        System.out.println("2. Check-in");
        System.out.println("3. Check-out");
        System.out.println("4. Ver reservas");
        System.out.println("5. Ver ocupaciones");
        System.out.println("6. Cancelar reserva");
        System.out.println("7. Agregar consumo en una habitacion");
        System.out.println();
        System.out.println("0. Volver");
        seleccionOpcion();
    }

    public static void recepcionistaSubMenuClientes() {
    	System.out.println("\n----------Recepcionista SubMenu Clientes------------\n");
        System.out.println("\n1. Ver lista de clientes");
        System.out.println("2. Modificar datos de un cliente");
        System.out.println();
        System.out.println("0. Volver");
        seleccionOpcion();
    }

    public static void recepcionistaSubMenuHabitaciones() {
    	System.out.println("\n----------Recepcionista SubMenu Habitaciones------------\n");
        System.out.println("\n1. Ver habitaciones libres");
        System.out.println("2. Ver habitaciones ocupadas");
        System.out.println();
        System.out.println("0. Volver");
        seleccionOpcion();
    }


    public static void subMenuInfoPropia() {
    	System.out.println("\n----------SubMenu InfoPropia------------\n");
        System.out.println("\n1. Ver informacion propia");
        System.out.println("2. Modificar informacion");
        System.out.println();
        System.out.println("0. Volver\n");
        seleccionOpcion();
    }

    public static void menuModificarCliente() {
        System.out.println("\n--MODIFICAR CLIENTE--\n");
        System.out.println("1. Nombre");
        System.out.println("2. Apellido");
        System.out.println("3. DNI");
        System.out.println("4. Nacionalidad");
        System.out.println("5. Direccion");
    }

    public static void listadoProductos() {

        System.out.println("\nLISTADO DE PRODUCTOS\n");
        for (Producto prod : Producto.values()) {
            System.out.println(prod.getID() + "- " + prod + " precio: " + prod.getPrecio());
        }
    }

    public static void listadoTipoHab() {

        System.out.println("\nLISTADO TIPO DE HABITACION\n");
        for (TipoHab tipo : TipoHab.values()) {
            System.out.println(tipo.getID() + "- " + tipo);
        }
    }


    private static void seleccionOpcion() {
        System.out.print("\nSeleccione ... ");
    }

    public static void confirmarConTeclaS() {
        System.out.print("Presione 's' para confirmar: ");
    }

    public static void opcionIncorrecta() {
        System.out.println("Opcion no valida\n");
    }
}
