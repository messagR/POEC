package fr.thread.sample;

import java.util.Random;

/**
 * Thread qui va lancer un notify.
 */
public class Notifier implements Runnable {

	private CommonObject co;

	public Notifier(CommonObject aCo) {
		this.co = aCo;
	}

	@Override
	public void run() {
		String name = Thread.currentThread().getName();
		System.out.println(name + " a demarree");
		while (true) {
			try {
				Random r = new Random();
				long waitTimeMs = Math.abs(r.nextLong() % 5000);
				System.out.println("Va attendre " + waitTimeMs + " ms");
				Thread.sleep(waitTimeMs);
				// Pour faire un notify, il faut que l'objet soit dans un bloque
				// synchronized
				synchronized (this.co) {
					this.co.notify();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}