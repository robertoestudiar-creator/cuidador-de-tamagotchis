package org.cuatrovientos.psp.cuidadordetamagotchis;

public class ControladorTiempo implements Runnable {

	private static final int INTERVALO_SUCIEDAD_MS = 20000;
	private static final int TIEMPO_VIDA_MS = 300000;

	private final Tamagotchi tamagotchi;

	public ControladorTiempo(Tamagotchi tamagotchi) {
		this.tamagotchi = tamagotchi;
	}

	@Override
	public void run() {
		int tiempoVividoMs = 0;
		try {
			while (tamagotchi.getEstado() != Estado.MUERTO && tiempoVividoMs < TIEMPO_VIDA_MS) {
				Thread.sleep(INTERVALO_SUCIEDAD_MS);
				tiempoVividoMs += INTERVALO_SUCIEDAD_MS;
				tamagotchi.ensuciarse();
			}
			tamagotchi.morir();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

}