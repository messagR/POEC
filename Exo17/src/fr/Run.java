/**
 * test
 */
package fr;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import fr.xml.Adresse;
import fr.xml.Personne;

/**
 * @author PC
 *
 */
public class Run {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Adresse adresse1 = new Adresse("Versailles", "Pavé des gardes", "FRANCE", 78000);
		Adresse adresse2 = new Adresse("Paris", "rue des près", "FRANCE", 75000);
		List<Adresse> adresses = new ArrayList<Adresse>();
		adresses.add(adresse1);
		adresses.add(adresse2);

		GregorianCalendar cal = (GregorianCalendar) Calendar.getInstance();
		cal.set(1985, 12, 18);
		Personne personne1 = new Personne("Smith", "Jhon");
		personne1.setAdresses(adresses);
		personne1.setDateNaissance(cal.getTime());

		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Personne.class, Adresse.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			jaxbMarshaller.marshal(personne1, new File("c:/monFichier.xml"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Personne.class, Adresse.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			Personne personneLue = (Personne) jaxbUnmarshaller.unmarshal(new File("c:/unAutreFichier.xml"));
			System.out.println(personneLue.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
