package fr.banque;

public class Run {

	public static void main(String[] args) {

		Client c1 = new Client("DUPONT", "Jean", 35);
		Client c2 = new Client("DUPOND", "Jeanne", 30);
		Client[] listeClient = { c1, c2 };

		FactoryCompte fc = FactoryCompte.getInstance();
		Compte cpt1 = fc.creerCompte();
		cpt1.ajouter(100);
		Compte cpt2 = fc.creerCompte();
		cpt2.ajouter(100);
		CompteRemunere cpt3 = fc.creerCompteRemunere();
		cpt3.ajouter(100);
		cpt3.setTaux(0.2);
		CompteASeuil cpt4 = fc.creerCompteASeuil();
		cpt4.ajouter(100);
		cpt4.setSeuil(50);
		CompteRemunere cpt5 = fc.creerCompteRemunere();
		cpt5.ajouter(100);
		cpt5.setTaux(0.4);
		CompteASeuil cpt6 = fc.creerCompteASeuil();
		cpt6.ajouter(100);
		cpt6.setSeuil(0);

		c1.ajouterCompte(cpt1);
		c1.ajouterCompte(cpt3);
		c1.ajouterCompte(cpt5);
		c2.ajouterCompte(cpt2);
		c2.ajouterCompte(cpt4);
		c2.ajouterCompte(cpt6);

		for (Client client : listeClient) {
			System.out.println(client);
			for (Compte compte : client.getComptes()) {
				if(compte != null) {
					if ((compte.getNumero() % 2) == 0) {
						client.getCompte(compte.getNumero()).ajouter(100);
					} else {
						client.getCompte(compte.getNumero()).retirer(100);
					}
					System.out.println(compte);
				}
			}
		}


		// if (c1.getCompte(1) == null) {
		// System.out.println("compte inexistant pour client " + c1);
		// } else {
		// System.out.println(c1.getCompte(1));
		// }
	}

}
