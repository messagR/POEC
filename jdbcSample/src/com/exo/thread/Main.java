/**
 * test
 */
package com.exo.thread;

/**
 * @author PC
 *
 */
public class Main {
	private volatile int a;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Exemple r1 = new Exemple();
		Exemple r2 = new Exemple();

		// NE JAMAIS FAIRE
		// r1.run();

		Thread t1 = new Thread(r1);
		Thread t2 = new Thread(r2);

		// Lancer la thread
		t1.start();
		t2.start();
		try {
			t2.join(); // attend que t2 est fini
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Apres mon t2");
	}

}
