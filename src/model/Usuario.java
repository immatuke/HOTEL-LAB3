package model;

import java.io.Serializable;
import java.util.Scanner;

/**
 * Clase abstracta de la cual derivan dos clases concretas, Admin y Recepcionista. Posee los atributos fundamentales que
 * conforman a un usuario para la administracion del hotel. Permite proveer de los datos necesarios para la
 * identificacion del usuario en el sistema para un inicio de sesion exitoso, asi como tambien modificar su
 * informacion propia una vez en linea. <p>Si se desea agregar otros tipos de usuario en el sistema, asegurarse
 * que extiendan de esta clase.</p>
 *
 * @see Admin
 * @see Recepcionista
 */
public abstract class Usuario implements Serializable {

    private String id;
    private Password password;
    private String nombre;


    /**
     * Unico constructor de la clase.
     *
     * @param id       Identificador de usuario
     * @param password Clave para identificacion
     * @param nombre   Nombre personal
     */
    public Usuario(String id, Password password, String nombre) {
        this.id = id;
        this.password = password;
        this.nombre = nombre;
    }

    /**
     * Devuelve el ID de usuario.
     *
     * @return El String correspondiente al ID
     */
    public String getId() {
        return id;
    }

    private void setId(String id) {
        this.id = id;
    }

    /**
     * Devuelve el Password. Necesario unicamente para el inicio de sesion.
     *
     * @return El Password
     */
    public Password getPassword() {
        return password;
    }

    private void setPassword(Password password) {
        this.password = password;
    }

    /**
     * Devuelve el nombre personal. Util para el listado de usuarios.
     *
     * @return El String correspondiente al nombre
     */
    public String getNombre() {
        return nombre;
    }

    private void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Representacion textual del usuario. Provee unicamente el ID y el nombre personal, ya que el Password es
     * obviamente personal.
     *
     * @return El String con la informacion relevante
     */
    @Override
    public String toString() {
        return "\nID: " + id + "\nNombre: " + nombre;
    }


    /**
     * Permite la modificacion propia de datos. Todos los usuarios poseen esta capacidad.
     *
     * @param scanner El scanner necesario para el ingreso de datos por teclado
     */
    public void modificarDatosUsuario(Scanner scanner, Hotel hotel) {

        boolean requisitosClave = false;
        String userConfirm;
        String id;
        String psw = null;
        String nombre;

        System.out.println("Ingresar nuevo id: ");
        id = scanner.nextLine();


        if (this instanceof Admin) {

            /*Solo se debe tener en cuenta si existe conserje con ese ID*/
            if (hotel.existeConserje(id)) {
                System.out.println("Existe un usuario con ese ID. Pruebe con uno diferente.");
                return;
            }
        } else if (this instanceof Recepcionista) {
            /*Se impide que el ID de recepcionista coincida con el de Admin y que dicho ID sea 'admin'*/
            if (hotel.existeAdmin(id)) {
                System.out.println("Existe un usuario con ese ID. Pruebe con uno diferente.");
                return;
            } else if (id.equals("admin") || id.equals("Admin") || id.equals("ADMIN")) {
                System.out.println("No puede escojer ese ID.");
                return;
                /*Si existe recepcionista con un ID igual al introducido y no es el mismo que el del recepcionista que
                * desea modificar su propia informacion, se lo niega.*/
            } else if (hotel.existeConserje(id) && !id.equals(this.getId())) {
                System.out.println("Existe un usuario con ese ID. Pruebe con uno diferente.");
                return;
            }
        }

        System.out.println("Introduzca la vieja clave: ");
        psw = scanner.nextLine();

        /*Se debe conocer de la vieja clave para cambiarla*/
        if (!password.sonCoincidentes(psw)) {
            System.out.println("La clave introducida no es correcta. Saliendo...");
            return;
        }

        while (!requisitosClave) {
            System.out.println("Ingrese nueva clave (alfanumerica 8-20 digitos): ");
            psw = scanner.nextLine();

            if (Password.hasLongitudCorrecta(psw) && Password.isAlfanumerico(psw)) {
                requisitosClave = true;
            } else {
                System.out.println("La clave ingresada no cumple con los requisitos.");
            }
        }
        System.out.println("Ingresar nuevo nombre: ");
        nombre = scanner.nextLine();

        System.out.println("Usted ha ingresado los siguientes datos: " +
                "\nid: " + id +
                "\npassword: " + psw +
                "\nnombre: " + nombre +
                "\nDesea confirmar los datos? s/n");
        userConfirm = scanner.nextLine();

        if (userConfirm.equals("s") || userConfirm.equals("S")) {
            /*Segun si el usuario es Admin o Recepcionista, el cambio se realiza de manera diferente*/
            if (this instanceof Admin) {
                setId(id);
                setPassword(new Password(psw));
                setNombre(nombre);

            } else if (this instanceof Recepcionista) {
                hotel.removerRecepcionista(this.id);
                setId(id);
                setPassword(new Password(psw));
                setNombre(nombre);

                hotel.agregarRecepcionista((Recepcionista) this);
            }
            System.out.println("Los datos han sido modificados.\n");
        } else {
            System.out.println("No se han realizado cambios.\n");
        }
    }
}

