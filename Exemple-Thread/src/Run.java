import fr.thread.sample.CommonObject;
import fr.thread.sample.Notifier;
import fr.thread.sample.Waiter;

/**
 * Exemple de wait et notify entre deux thread a travers un objet commun.
 */
public class Run {

	public static void main(String[] args) {
		// Objet qui nous servira entre les deux threads
		CommonObject msg = new CommonObject();
		// Les deux Runnable
		Runnable waiter = new Waiter(msg);
		Runnable notifier = new Notifier(msg);
		// Les deux Threads que l'on va pouvoir lancer
		Thread wt = new Thread(waiter, "waiter");
		Thread nt = new Thread(notifier, "notifier");
		// Elles demarrent
		wt.start();
		nt.start();
	}

}
