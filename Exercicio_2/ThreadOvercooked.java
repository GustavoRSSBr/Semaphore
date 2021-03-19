package Exercicio_2;

import java.util.concurrent.Semaphore;

public class ThreadOvercooked extends Thread{
	
	private Semaphore semaforo;
	private int id;
	private String nome; 
	
	public ThreadOvercooked(int id, Semaphore semaforo) {
		this.semaforo = semaforo;
		this.id = id;
		decidirNome();
	}

	private void decidirNome() {
		boolean eh_par = id % 2 == 0;
		if (eh_par) {
			this.nome = "Lasanha a Bolonhesa";
		}else {
			this.nome = "Sopa de Cebola";
		}
	}
	
	private void organizar() {
		comunicarInicio();
		cozinhar();
		comunicarFim();
		try {
			semaforo.acquire();
			entregarPrato();
			comunicarEntrega();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			semaforo.release();
		}
	}
	
	private void comunicarInicio() {
		System.out.println("O prato" + id + ": " + this.nome + " começou a ser cozinhado!");
	}
	
	private void cozinhar() {
		double tempo_total_de_cozimento = calcular_tempo_de_cozimento();
		double tempo_de_cozimento = 0;
		double percentual_de_cozimento = 0;
		
		while (tempo_de_cozimento < tempo_total_de_cozimento) {
			try {
				sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			tempo_de_cozimento += 100;
			percentual_de_cozimento = (tempo_de_cozimento / tempo_total_de_cozimento) * 100;
			if (percentual_de_cozimento > 100) percentual_de_cozimento = 100;
			System.out.printf("O prato%d: %s está %1.0f%% pronto.%n", id, this.nome, percentual_de_cozimento);
		}
	}

	private int calcular_tempo_de_cozimento() {
		int tempo_de_cozimento;
		boolean eh_par = id % 2 == 0;
		if (eh_par) {
			tempo_de_cozimento = (int)(Math.random() * 301) + 500;
		}else {
			tempo_de_cozimento = (int)(Math.random() * 601) + 600;
		}
		return tempo_de_cozimento;
	}
	
	private void comunicarFim() {
		System.out.println("O prato" + id + ": " + this.nome + " está pronto!! :)");
	}

	private void entregarPrato() {
		System.out.println("O prato" + id + ": " + this.nome + " está sendo entregue ao Cliente.");
		try {
			sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void comunicarEntrega() {
		System.out.println("O prato" + id + ": " + this.nome + " foi entregue ao Cliente.");
	}

	@Override
	public void run() {
		organizar();
	}

}
