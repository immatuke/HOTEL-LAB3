package enums;

/**
 * Listado inmutable de productos que un cliente presente en una habitacion puede solicitar. El valor correspondiente
 * a cada producto sera luego agregado al precio producto del total de noches en la habitacion, generando el total a
 * pagar. Para agregar, modificar o quitar productos, trabajar directamente desde esta enumeracion.
 * @see app.model.Reserva
 */
public enum Producto {

    AGUA(20), CHOCOLATE(25), CARGADOR_CELULAR(200), CERVEZA(50), GASEOSA(30), CHAMPAGNE(250), SERVICIO_PPV(150);

    static int contador;
    double precio;
    /*
     * Atributo numero para que sea mas facil a la hora de elegirlo en un menu
     * Es un id
     */
    String id;

    /**
     * Asigna el precio del producto. A su vez, proporciona un ID unico para el producto que luego podra ser usado
     * por un conserje para agregar un producto a la lista de consumos de un cliente.
     * @param precio El precio correspondiente al producto
     */
    Producto(double precio) {
        this.id = aumentarcontador();
        this.precio = precio;
    }

    /**
     * Busca un producto por su identificador y lo devuelve.
     * @param identificador el ID del producto que se desea obtener
     * @return el Producto
     */
    public static Producto buscarPorID(String identificador) {
        Producto aux = null;
        for (Producto prod : Producto.values()) {
            if (prod.getID().equals(identificador)) {
                aux = prod;
            }
        }
        return aux;
    }


    private String aumentarcontador() {
        return String.valueOf(++contador);

    }

    /**
     * Devuelve el precio de un producto
     * @return El precio
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * Devuelve el ID de un producto
     * @return el ID
     */
    public String getID() {
        return id;
    }

}
