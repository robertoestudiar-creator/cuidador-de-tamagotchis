package org.cuatrovientos.psp.cuidadordetamagotchis;

import java.util.Random;
import java.util.Scanner;

public class App {

	private static final int CANTIDAD_TAMAGOTCHIS = 3;
	private static final int TIEMPO_COMER_MIN_MS = 1000;
	private static final int TIEMPO_COMER_MAX_MS = 5000;

	private static final int OPCION_COMER = 1;
	private static final int OPCION_BANAR = 2;
	private static final int OPCION_JUGAR = 3;
	private static final int OPCION_VER_ESTADO = 4;
	private static final int OPCION_ACABAR = 5;

	private static final String PREFIJO_NOMBRE = "Tama-";
	private static final String MENU = "\n--- CUIDADOR ---\n"
									+  "1. Dar de comer\n"
									+  "2. Bañar\n"
									+  "3. Jugar\n"
									+  "4. Ver estado\n"
									+  "5. Acabar";
	private static final String MENSAJE_OPCION_INVALIDA = "Opción no válida";
	private static final String MENSAJE_OCUPADO = " está ocupado";
	private static final String MENSAJE_ELEGIR_TAMAGOTCHI = "Elige un Tamagotchi del 1 al ";
	private static final String MENSAJE_TAMAGOTCHI_INVALIDO = "Ese Tamagotchi no existe";
	private static final String MENSAJE_PREGUNTAR_NUMERO = "Introduce un número: ";
	private static final String MENSAJE_ERROR_PREGUNTAR_NUMERO = "Error, debes introducir un número entero";

	public static void main(String[] args) {
		Tamagotchi[] tamagotchis = crearTamagotchis();
		lanzarTamagotchis(tamagotchis);
		Scanner scn = new Scanner(System.in);

		while (true) {
			mostrarMenu();
			int opcion = preguntarNumero(scn);
			switch (opcion) {
			case OPCION_COMER:
				lanzarAccion(tamagotchis, scn, Estado.COMIENDO);
				break;
			case OPCION_BANAR:
				lanzarAccion(tamagotchis, scn, Estado.BANANDOSE);
				break;
			case OPCION_JUGAR:
				break;
			case OPCION_VER_ESTADO:
				break;
			case OPCION_ACABAR:
				break;
			default:
				System.out.println(MENSAJE_OPCION_INVALIDA);
			}
		}
	}

	private static Tamagotchi[] crearTamagotchis() {
		Random random = new Random();
		Tamagotchi[] tamagotchis = new Tamagotchi[CANTIDAD_TAMAGOTCHIS];
		for (int i = 0; i < tamagotchis.length; i++) {
			int tiempoComerMs = random.nextInt(TIEMPO_COMER_MIN_MS, TIEMPO_COMER_MAX_MS + 1);
			tamagotchis[i] = new Tamagotchi(PREFIJO_NOMBRE + (i + 1), tiempoComerMs);
		}
		return tamagotchis;
	}

	private static void lanzarAccion(Tamagotchi[] tamagotchis, Scanner scn, Estado accion) {
		Tamagotchi tamagotchi = elegirTamagotchi(tamagotchis, scn);
		if (!tamagotchi.solicitarAccion(accion)) {
			System.out.println(tamagotchi.getName() + MENSAJE_OCUPADO);
		}
	}

	private static Tamagotchi elegirTamagotchi(Tamagotchi[] tamagotchis, Scanner scn) {
		while (true) {
			System.out.println(MENSAJE_ELEGIR_TAMAGOTCHI + tamagotchis.length);
			int numero = preguntarNumero(scn);
			if (numero >= 1 && numero <= tamagotchis.length) {
				return tamagotchis[numero - 1];
			}
			System.out.println(MENSAJE_TAMAGOTCHI_INVALIDO);
		}
	}

	private static int preguntarNumero(Scanner scn) {
		while (true) {
			System.out.println(MENSAJE_PREGUNTAR_NUMERO);
			String respuesta = scn.nextLine().trim();
			try {
				return Integer.parseInt(respuesta);
			} catch (NumberFormatException e) {
				System.out.println(MENSAJE_ERROR_PREGUNTAR_NUMERO);
			}
		}
	}

	private static void mostrarMenu() {
		System.out.println(MENU);
	}

	private static void lanzarTamagotchis(Tamagotchi[] tamagotchis) {
		for (Tamagotchi tamagotchi : tamagotchis) {
			tamagotchi.start();
			Thread controlador = new Thread(new ControladorTiempo(tamagotchi));
			controlador.setDaemon(true);
			controlador.start();
		}
	}

}
