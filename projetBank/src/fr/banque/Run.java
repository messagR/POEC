package fr.banque;

public class Run {

	public static void main(String[] args) {
		Client c1 = new Client("DUPONT", "Jean", 25, 1);
		System.out.println(c1.toString());

		Compte cpt1 = new Compte(1, 100);
		Compte cpt2 = new Compte(2, 10);
		Compte cpt3 = new Compte(3, 1);
		System.out.println(cpt1);
		System.out.println(cpt2);
		System.out.println(cpt3);

		c1.ajouterCompte(cpt1);
		c1.ajouterCompte(cpt2);
		c1.ajouterCompte(cpt3);
		System.out.println(c1);
		int compte = 1;
		if (c1.getCompte(compte) == null) {
			System.out.println("compte " + compte + " inexistant");
		} else {
			System.out.println(c1.getCompte(compte));
		}
		compte = 4;
		if (c1.getCompte(compte) == null) {
			System.out.println("compte " + compte + " inexistant");
		} else {
			System.out.println(c1.getCompte(compte));
		}

		c1.getCompte(1).retirer(10);
		c1.getCompte(3).ajouter(10);
		System.out.println(cpt1);
		System.out.println(cpt2);
		System.out.println(cpt3);
	}

}
