package utiles;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Clase generica utilitaroa cuyo fin es el de poder escribir y leer los datos relevantes pertenecientes al hotel
 * en y de archivos. Esta clase no es instanciable.
 */
public class IOGenericoUtil {

    private IOGenericoUtil() {
    }

    /**
     * Escribe un objeto serializable en un archivo.
     *
     * @param t    El tipo de objeto a guardar
     * @param file La ruta correspondiente al archivo
     * @param <T>  Cualquier tipo que extienda de la interfaz Serializable
     */
    public static <T extends Serializable> void escribirObjeto(T t, String file) {

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {

            out.writeObject(t);
            System.out.println("Se ha modificado y guardado informacion de " + file +
                                " exitosamente.\n");

        } catch (IOException e) {
            System.out.println("No se ha podido escribir el objeto " + t.getClass().getSimpleName() +
                                " en el archivo. Contacte al administrador.\n");
        }
    }

    /**
     * Lee un objeto serializable en un archivo y lo devuelve.
     *
     * @param file La ruta correspondiente al archivo
     * @param <T>  Cualquier tipo que extienda de la interfaz Serializable
     */
    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T leerObjeto(String file) {

        T t = null;

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {

            t = (T) in.readObject();

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error lectura.");
        }

        return t;
    }
}

