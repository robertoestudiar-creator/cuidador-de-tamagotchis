package org.cuatrovientos.psp.cuidadordetamagotchis;

import java.util.Random;
import java.util.Scanner;

public class Tamagotchi extends Thread {

	private static final int TIEMPO_BANO_MS = 5000;
	private static final int SUCIEDAD_AVISO = 5;
	private static final int SUCIEDAD_MUERTE = 10;
	private static final int NUMERO_MINIMO_SUMA = 1;
	private static final int PRIMER_NUMERO_MAXIMO_SUMA = 5;
	private static final int SEGUNDO_NUMERO_MAXIMO_SUMA = 4;

	private static final String MENSAJE_MUY_SUCIO = " está empezando a estar muy sucio";
	private static final String MENSAJE_MUERTE = " ha muerto...";
	private static final String MENSAJE_EMPIEZA_COMER = " empieza a comer";
	private static final String MENSAJE_TERMINA_COMER = " ha acabado de comer";
	private static final String MENSAJE_EMPIEZA_BANARSE = " empieza a bañarse";
	private static final String MENSAJE_TERMINA_BANARSE = " ha acabado de bañarse";
	private static final String MENSAJE_EMPIEZA_JUGAR = " quiere jugar";
	private static final String MENSAJE_TERMINA_JUGAR = " ha terminado de jugar";
	private static final String MENSAJE_FALLO_SUMA = "Ooops, has fallado";
	private static final String MENSAJE_ESTADO = " tiene una suciedad de ";

	private final int tiempoComerMs;
	private int suciedad = 0;
	private Estado estado = Estado.OCIOSO;
	private Integer respuesta = null;

	public synchronized Estado getEstado() {
		return estado;
	}

	public Tamagotchi(String nombre, int tiempoComerMs) {
		super(nombre);
		this.tiempoComerMs = tiempoComerMs;
	}

	private synchronized void cambiarEstado(Estado nuevoEstado) {
		if (estado != Estado.MUERTO) {
			estado = nuevoEstado;
		}
	}

	private void comer() throws InterruptedException {
		cambiarEstado(Estado.COMIENDO);
		System.out.println(getName() + MENSAJE_EMPIEZA_COMER);
		Thread.sleep(tiempoComerMs);
		System.out.println(getName() + MENSAJE_TERMINA_COMER);
		cambiarEstado(Estado.OCIOSO);
	}

	private synchronized void quitarSuciedad() {
		suciedad = 0;
	}

	private void limpiarse() throws InterruptedException {
		cambiarEstado(Estado.BANANDOSE);
		System.out.println(getName() + MENSAJE_EMPIEZA_BANARSE);
		Thread.sleep(TIEMPO_BANO_MS);
		quitarSuciedad();
		System.out.println(getName() + MENSAJE_TERMINA_BANARSE);
		cambiarEstado(Estado.OCIOSO);
	}

	public synchronized void responder(int numero) {
		respuesta = numero;
		notify();
	}

	private synchronized int esperarRespuesta() throws InterruptedException {
		while (respuesta == null) {
			wait();
		}
		int numeroRecibido = respuesta;
		respuesta = null;
		return numeroRecibido;
	}

	private void juegoSumar() throws InterruptedException {
		Random rnd = new Random();
		int primerNumeroSuma = rnd.nextInt(NUMERO_MINIMO_SUMA, PRIMER_NUMERO_MAXIMO_SUMA);
		int segundoNumeroSuma = rnd.nextInt(NUMERO_MINIMO_SUMA, SEGUNDO_NUMERO_MAXIMO_SUMA);
		int resultado = primerNumeroSuma + segundoNumeroSuma;
		boolean acertado = false;
		while (!acertado) {
			System.out.println(getName() + ": " + primerNumeroSuma + " + " + segundoNumeroSuma + "= ");
			int numeroRecibido = esperarRespuesta();
			acertado = numeroRecibido == resultado;
			if (!acertado) {
				System.out.println(getName() + ": " + MENSAJE_FALLO_SUMA);
			}
		}
	}

	private void jugar() throws InterruptedException {
		cambiarEstado(Estado.JUGANDO);
		System.out.println(getName() + MENSAJE_EMPIEZA_JUGAR);
		juegoSumar();
		System.out.println(getName() + MENSAJE_TERMINA_JUGAR);
		cambiarEstado(Estado.OCIOSO);
	}

	public synchronized void ensuciarse() {
		suciedad++;
		if (suciedad == SUCIEDAD_AVISO) {
			System.out.println(getName() + MENSAJE_MUY_SUCIO);
		} else if (suciedad == SUCIEDAD_MUERTE) {
			morir();
		}
	}

	public synchronized void morir() {
		if (estado != Estado.MUERTO) {
			estado = Estado.MUERTO;
			System.out.println(getName() + MENSAJE_MUERTE);
			interrupt();
		}
	}

	public synchronized boolean serAsesinado() {
		if (estado != Estado.OCIOSO) {
			return false;
		}
		morir();
		return true;
	}

	public synchronized void mostrarSuciedad() {
		System.out.println(getName() + MENSAJE_ESTADO + suciedad);
	}

	private synchronized Estado esperarAccion() throws InterruptedException {
		while (estado == Estado.OCIOSO) {
			wait();
		}
		return estado;
	}

	public synchronized boolean solicitarAccion(Estado accion) {
		if (estado != Estado.OCIOSO) {
			return false;
		}
		estado = accion;
		notify();
		return true;
	}

	@Override
	public void run() {
		try {
			while (getEstado() != Estado.MUERTO) {
				Estado accion = esperarAccion();
				if (accion == Estado.COMIENDO) {
					comer();
				} else if (accion == Estado.BANANDOSE) {
					limpiarse();
				} else if (accion == Estado.JUGANDO) {
					jugar();
				}
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}