package org.cuatrovientos.psp.cuidadordetamagotchis;

import java.util.Random;

public class App {

	private static final int CANTIDAD_TAMAGOTCHIS = 3;
	private static final int TIEMPO_COMER_MIN_MS = 1000;
	private static final int TIEMPO_COMER_MAX_MS = 5000;

	private static final String PREFIJO_NOMBRE = "Tama-";
	private static final String MENU = "\n--- CUIDADOR ---\n"
									+  "1. Dar de comer\n"
									+  "2. Bañar\n"
									+  "3. Jugar\n"
									+  "4. Ver estado\n"
									+  "5. Acabar";
	private static final String MENSAJE_PREGUNTAR_NUMERO = "Introduce la respuesta: ";
	private static final String MENSAJE_ERROR_PREGUNTAR_NUMERO = "Error, debes introducir un número entero";

	public static void main(String[] args) {
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
