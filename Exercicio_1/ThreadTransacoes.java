package Exercicio_1;

import java.util.concurrent.Semaphore;

public class ThreadTransacoes extends Thread {
	
	private int id;
	private Semaphore semaforo;
	
	public ThreadTransacoes(int id, Semaphore semaforo) {
		this.id = id;
		this.semaforo = semaforo;
	}
	
	private void organizar () {
		for (int i = 0; i < 3; i++) {
			
			calcular();
			
			try {
				semaforo.acquire();
				trasacao();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				semaforo.release();
			}
			
		}
		
	}

	private void calcular () {
		int resultado = id % 3;
		int tempo = 0;

		switch(resultado) {
		case 1: 
			tempo = (int)(Math.random() * 801) + 200;
			break;
		case 2:
			tempo = (int)(Math.random() * 1001) + 500;
			break;
		case 0: 
			tempo = (int)(Math.random() * 1001) + 1000;
			break;
		}
		calcularTempo(tempo);
	}
	
	private void calcularTempo(int tempo) {
		try {
			sleep(tempo);
			System.out.println("A tread#" + id + " está calculando...");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void trasacao() {
		int tempo;
		if (id % 3 == 0) {
			tempo = 1000;
		}else {
			tempo = 1500;
		}
		transacaoTempo(tempo);
	}

	private void transacaoTempo(int tempo) {
		try {
			System.out.println("A tread#" + id + " está realizando a transação...");
			sleep(tempo);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("A tread#" + id + " terminou transação...");
	}

	@Override
	public void run() {
		organizar();
	
	}
}
