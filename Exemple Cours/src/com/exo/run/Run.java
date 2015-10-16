package com.exo.run;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import com.exo.classe.monComparator;

public class Run {

	// enlève le warning sur la ligne ou il peut y avoir un pointeur null
	// @SuppressWarnings("null")
	// enlève les warnings de null pointer exception et de not used
	// @SuppressWarnings(value = { "null", "unused" })
	@SuppressWarnings(value = { "rawtypes", "unchecked" })
	public static void main(String[] args) {

		List<String> maListeType = new ArrayList<String>();
		List<String> maListeType2 = new ArrayList<>(); // idem ligne du dessus
		// en Java8 mais
		// impossible avant
		maListeType.add("a");
		maListeType.add("aa");
		maListeType.add("b");
		maListeType.add("bb");
		maListeType.add("z");
		// System.out.println(maListeType.get(0));

		List maListe = new ArrayList();
		maListe.add(5);
		maListe.add("Toto");
		System.out.println(maListe);

		maListe = new Vector();
		maListe.add(new int[5]);
		maListe.add(Double.valueOf(50.5));

		try {
			System.out.println(maListe.get(0));
			System.out.println(maListe.get(1));
			System.out.println(maListe.get(2));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (maListe.size() > 3) {
			try {
				System.out.println(maListe.get(3));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println(maListe.contains("Toto"));
		System.out.println(maListe.contains(50.5));
		System.out.println(maListe.indexOf("Toto"));
		System.out.println(maListe.indexOf(50.5));

		maListe.addAll(maListeType);
		System.out.println(maListe);
		maListeType.add("Tyty");
		maListeType.remove(0);
		System.out.println(maListeType);
		System.out.println(maListe);

		for (Object object : maListe) {
			System.out.println(object);
		}

		// à ne pas utiliser
		for (int i = 0; i < maListe.size(); i++) {
			System.out.println(maListe.get(i));
		}

		// le mieux
		Iterator<String> iter = maListeType.iterator();
		while (iter.hasNext()) {
			String unElement = iter.next();
			System.out.println(unElement);
			// supprime
			// iter.remove();
		}

		// le mieux du mieux
		System.out.println(maListeType);
		ListIterator<String> iter2 = maListeType.listIterator();
		// while (iter2.hasNext()) {
		// à ce moment la l'index est entre -1 et 0
		// il prend l'elt suivant l'index : 0 ET DEPLACE L'INDEX EN +1
		String elementCourant = iter2.next();
		// remplace l'elt courant
		iter2.set("Youpi");
		// à ce moment la l'index est entre 0 et 1
		if (iter2.hasPrevious()) {
			// il prend l'elt precedent l'index : 0 ET DEPLACE L'INDEX EN -1
			// ATTENTION : ne pas oublier de refaire un .next apres dans les
			// boucles sinon boucle infinie
			elementCourant = iter2.previous();
			// à ce moment la l'index est entre -1 et 0
			// il ajoute un elt apres l'index (en 0) ET DEPLACE L'INDEX
			iter2.add("Test");
			// à ce moment la l'index est entre 0 et 1
			// il prend l'elt suivant l'index : 1 ET DEPLACE L'INDEX EN +1
			elementCourant = iter2.next();
			// à ce moment la l'index est entre 1 et 2
			// il supprime l'elt avant l'index (en 1) ET DEPLACE L'INDEX en -1
			iter2.remove();
			// à ce moment la l'index est entre 0 et 1
		}
		// ajoute l'elt apres l'index et deplace l'index en +1
		iter2.add("Tyty");
		// supprime l'elt courant et deplace l'index en -1
		// iter2.remove();
		System.out.println(maListeType);
		//		}

		// ATTENTION aux majuscules et au nombre de car
		// ne fonctionne qu'a partir de Java 8
		maListeType.sort(new monComparator());
		// ordre croissant par defaut
		maListeType.sort(null);
		// si moins de 8 elements
		Collections.sort(maListeType, new monComparator());
		// ne pas confondre Collection (iterator) et Collections (class)
		// pour eviter d'avoir a envoye null quand on a pas trouve de list
		List l = Collections.EMPTY_LIST;

		// on ne peut pas avoir 2 fois le meme elt dans un set
		Set<String> monSet = new HashSet<String>();
		monSet.add("Tata");
		monSet.add("Titi");
		monSet.add("Toto");
		monSet.add("Toto");
		System.out.println(monSet);

		// il fait lui-même un tri quelconque
		Map<Integer, String> maMap = new Hashtable<Integer, String>();
		maMap.put(5, "Toto");
		maMap.put(1, "Titi");
		maMap.put(10, "Toto");
		maMap.put(15, "Tata");
		System.out.println(maMap);
		System.out.println(maMap.get(10));
		String[] tab = maMap.values().toArray(new String[maMap.size()]);
		List<String> l2 = Arrays.asList(tab);

		// créer une liste verrouillee
		Collections.unmodifiableList(l);

		// sur les clefs
		Iterator<Integer> iterClef = maMap.keySet().iterator();
		while (iterClef.hasNext()) {
			Integer uneClef = iterClef.next();
			System.out.println(uneClef + "=" + maMap.get(uneClef));

		}

		// sur les valeurs
		Iterator<String> iterValeur = maMap.values().iterator();
		while (iterValeur.hasNext()) {
			String uneValeur = iterValeur.next();
			System.out.println(uneValeur);
		}

		// sur les clefs et valeurs
		Iterator<Map.Entry<Integer, String>> iterClefValeur = maMap.entrySet().iterator();
		while (iterClefValeur.hasNext()) {
			Map.Entry<Integer, String> uneClefValeur = iterClefValeur.next();
			System.out.println(uneClefValeur.getKey() + "=" + uneClefValeur.getValue());

		}

		System.out.println("-- A --");
		try {
			Integer val = Integer.parseInt(null);
			System.out.println(val);
			;
		} catch (Exception e) {
			System.err.println(e);
		} System.out.println("-- B --");

		int j = -1;

		toto:do{
			j++;
			System.out.println(args[j]);
		} while(j<args.length);

		int i = 0;

		toto: while (i < args.length) {
			i++;
			if (i == 3) {
				break; // sort de la boucle
			}
			if (i == 2) {
				continue; // retourne au début de la boucle sans passer par la
				// suite
			}
			if (i == 0) {
				continue toto; // retourne à toto (qui doit forcément être une
				// boucle sans passer par la suite
			}
			System.out.println(args[i]);
		}

		for (i = 0; i < args.length; i++) {
			System.out.println(args[i]);
		}
		for (String uneCase : args) {
			System.out.println(uneCase);
		}

		int var1 = 5;

		switch (var1) {
		case 0:
			System.out.println("zero");
			break;
		case 5:
			System.out.println("cinq");
			break;
		case 10:
			System.out.println("dix");
			break;
		default:
			System.out.println("je ne sais pas");
		}
	}

}
