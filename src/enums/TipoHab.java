package enums;

/**
 * Enumeracion que contiene los tipos correspondientes a una habitacion.
 * @see app.model.Habitacion
 */
public enum TipoHab {

    INDIVIDUAL(), MATRIMONIAL(), FAMILIAR();

    static int contador;

    String id;

    /**
     * Proporciona un ID unico para el producto que luego podra ser usado por un Conserje o Admin para asignar un
     * TipoHab a una Habitacion o encontrar habitaciones que posean un TipoHab especifico.
     */
    TipoHab() {
        this.id = aumentarcontador();
    }

    /**
     * Busca un producto por su identificador y lo devuelve.
     * @param identificador el ID
     * @return el TipoHab cuyo ID coincide con el parametro
     */
    public static TipoHab buscarPorID(String identificador) {
        TipoHab aux = null;
        for (TipoHab tipo : TipoHab.values()) {
            if (tipo.getID().equals(identificador)) {
                aux = tipo;
            }
        }
        return aux;
    }

    private String aumentarcontador() {
        return String.valueOf(++contador);

    }

    /**
     * Devuelve el ID del TipoHab
     * @return el ID
     */
    public String getID() {
        return id;
    }

}
