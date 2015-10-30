/**
 * test
 */
package fr.jmx;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;

/**
 * @author PC
 *
 */
public class Run {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

		try {
			// Nom de l'objet
			ObjectName name = new ObjectName("fr.jmx:type=Calculatrice");
			// Construction de l'objet
			CalculatriceMBean mbean = new Calculatrice();
			// Inscription de l'objet
			mbs.registerMBean(mbean, name);
			// On attend
			System.out.println("Maintenant, ouvrez un shell et lancez jconsole");
			Thread.sleep(Long.MAX_VALUE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
