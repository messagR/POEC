package fr.thread.sample;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Thread qui va faire un wait.
 */
public class Waiter implements Runnable {

	private CommonObject co;

	public Waiter(CommonObject aCo) {
		this.co = aCo;
	}

	@Override
	public void run() {
		String name = Thread.currentThread().getName();
		while (true) {
			synchronized (this.co) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				try {
					System.out
							.println(name + " attend (Temps=" + sdf.format(new Date(System.currentTimeMillis())) + ")");
					// Pour faire un notify, il faut que l'objet soit dans un
					// bloque synchronized
					this.co.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
					break;
				}
				System.out.println(
						name + " a ete debloque (Temps=" + sdf.format(new Date(System.currentTimeMillis())) + ")");
			}
		}
	}

}