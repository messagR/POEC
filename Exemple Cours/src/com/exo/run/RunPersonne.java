package com.exo.run;

import java.io.IOException;
import java.sql.SQLException;

import javax.crypto.BadPaddingException;

import com.exo.classe.Directeur;
import com.exo.classe.Gens;
import com.exo.classe.IPersonne;
import com.exo.classe.Marchandise;
import com.exo.classe.MonException;
import com.exo.classe.Passager;
import com.exo.classe.Personne;
import com.exo.classe.SuperAvion;

public class RunPersonne {

	// enl�ve le warning sur la ligne ou il peut y avoir un pointeur null
	// @SuppressWarnings("null")
	// enl�ve les warnings de null pointer exception et de not used
	// @SuppressWarnings(value = { "null", "unused" })
	public static void main(String[] args) {

		IPersonne p31, p32;
		p31 = new Personne("Dupont", "Albert", 32);
		p32 = new Personne("Dupont", "Albert", 32);
		System.out.println("-- A --");
		try {
			System.out.println("-- B --");
			p31.setAge(55);
			System.out.println("-- C --");
			System.out.println("-- D --");
		} catch (BadPaddingException | IOException | SQLException e) { // uniquement en Java8
			// NE JAMAIS LAISSER UN CATCH SANS INSTRUCTION -> AU PIRE FAIRE UN
			// LOG D'ERREUR
			e.printStackTrace();
		} catch (MonException e1) {
			System.out.println("-- E --");
			// e.printStackTrace();
			System.out.println(e1.getMessage());
		} finally {

			System.out.println("Finally");
		}
		System.out.println("-- F --");
		Directeur p33 = new Directeur("Dupont", "Albert", 32);
		p31 = null;
		System.out.println(p31 + "<-'' - toString()->" + p31.toString());
		Class classeDeMonP1 = p31.getClass();
		System.out.println(classeDeMonP1.getName() + "<-getName() - getSimpleName()->" + classeDeMonP1.getSimpleName());
		int tab10[] = new int[5];
		String tab11[] = new String[5];
		System.out.println(tab10.getClass() + "<-tab10 - tab11->" + tab11.getClass());
		if (Personne.class == p31.getClass()) {
			// pareil que Personne.class.equals(p31.getClass())
			System.out.println("class ok ->" + Personne.class);
		} else {
			System.out.println("class ko : " + Personne.class + " - " + p31.getClass());
		}
		if (p31 == p32) {
			System.out.println("m�me objets");
		} else {
			System.out.println("objets diff�rents");
		}
		if (p31.equals(p32)) {
			System.out.println("p31.equals(p32) ok ->" + p31);
		} else {
			System.out.println("p31.equals(p32) ko->" + p31 + " - " + p32);
		}
		Directeur d1 = new Directeur("Dupont", "Albert", 32);
		if (p31.equals(d1)) { // = d1.equals(p31)
			System.out.println("p31.equals(d1) ok ->" + p31);
		} else {
			System.out.println("p31.equals(d1) ko->" + p31 + " - " + d1);
		}

		IPersonne p34 = new Gens();

		System.out.println(p31.hashCode());
		System.out.println(p32.hashCode());
		System.out.println(d1.hashCode());

		long start = System.currentTimeMillis();
		StringBuilder buff = new StringBuilder();
		for (int i = 0; i < 50000; i++) {
			IPersonne pLocal = new Personne("Dupont", "Albert", 25);
			buff.append("mon instance :").append(pLocal).append("\n");
		}
		System.out.println(buff.toString());
		long end = System.currentTimeMillis();
		System.out.println("mon temps en ms : " + (end - start));

		Marchandise m1 = new Marchandise();
		Marchandise m2 = new Marchandise();
		Passager p21 = new Passager();
		Passager p22 = new Passager();
		SuperAvion<Marchandise> avion = new SuperAvion<Marchandise>();
		avion.charger(0, m1);
		avion.charger(1, m2);
		SuperAvion<Passager> avionPassager = new SuperAvion<Passager>();
		avionPassager.charger(2, p21);
		avionPassager.charger(3, p22);
		Marchandise m3 = avion.decharger(0);
		Passager p23 = avionPassager.decharger(2);
		System.out.println(SuperAvion.TAILLE_DEFAULT);

		IPersonne p1 = new Personne();
		p1.setNom("Dupont");
		p1.setPrenom("Albert");
		p1.afficher();
		int x = 25;
		int y = 55;
		int result = p1.inverser(x, y);
		System.out.println("x(" + x + ") + y(" + y + ") = " + result);
		IPersonne p2 = new Personne("Durand", "John", 33);
		try {
			p2.inverser(p1);
		} catch (BadPaddingException | MonException | IOException | SQLException e) {
			e.printStackTrace();
		}
		p2.afficher();
		p1.afficher();
		p1 = null;
		p2 = null;
		System.gc();

	}

}
