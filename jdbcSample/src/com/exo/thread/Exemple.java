/**
 * test
 */
package com.exo.thread;

/**
 * @author PC
 *
 */
public class Exemple implements Runnable {
	private int etat;

	public synchronized int getEtat() {
		return this.etat;
	}

	public synchronized void setEtat(int etat) {
		this.etat = etat;
	}

	@Override
	public void run() {
		//		for(int i=0; i<10; i++){
		//			System.out.println("I=" + i);
		//		}
		int i = 0;
		while(true){
			System.out.println("I=" + i);
			i++;
			if (this.getEtat() == 1) {
				break;
			}
			if (this.getEtat() == 2) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
					break;
				}
			}
		}
	}
}