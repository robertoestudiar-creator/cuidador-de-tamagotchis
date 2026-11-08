package org.cuatrovientos.psp.cuidadordetamagotchis;

public class Tamagotchi extends Thread {

	private static final int TIEMPO_BANO_MS = 5000;
	private static final int TIEMPO_VIDA_MS = 300000;
	private static final int INTERVALO_SUCIEDAD_MS = 20000;
	private static final int SUCIEDAD_AVISO = 5;
	private static final int SUCIEDAD_MUERTE = 10;
	private static final String MENSAJE_MUY_SUCIO = " está empezando a estar muy sucio";
	private static final String MENSAJE_MUERTE = " ha muerto...";

	private final int tiempoComerMs;
	private int suciedad = 0;
	private Estado estado = Estado.OCIOSO;

	public Tamagotchi(String nombre, int tiempoComerMs) {
		super(nombre);
		this.tiempoComerMs = tiempoComerMs;
	}

	private synchronized void cambiarEstado(Estado nuevoEstado) {
		estado = nuevoEstado;
	}

}