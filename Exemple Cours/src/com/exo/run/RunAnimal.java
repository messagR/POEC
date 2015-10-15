package com.exo.run;

import java.util.Date;

import com.exo.classe.Animal;
import com.exo.classe.Chat;
import com.exo.classe.Chien;

public class RunAnimal {

	public static void main(String[] args) {
		Chien c1 = new Chien();
		c1.setAge(3);
		c1.setNom("Toutou");
		c1.setAdoption(new Date());

		Chat c2 = new Chat();
		c2.setAge(2);
		c2.setNom("Chaton");
		c2.setPelage("Trigré");

		c1.crier();
		c2.crier();

		Animal tab[] = new Animal[5];
		tab[0] = new Chien();
		tab[1] = c1;
		tab[2] = c2;
		tab[3] = new Chat();

		for (Animal animal : tab) {
			if (animal != null) {
				animal.crier();
			}
		}
	}

}
