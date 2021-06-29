package app;

import controlador.Controlador;
import model.*;

public class Main {

	public static void main(String[] args) {

		/**
		 * Clase cuyo unico fin es el de proveer el metodo main que le dara funcionamiento al programa.
		 */
		       new Controlador (Hotel.getInstancia()).inicio();
		    }
	}


