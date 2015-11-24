import com.google.gson.Gson;
import com.google.gson.JsonObject;

import fr.banque.Client;
import fr.banque.Compte;
import fr.banque.CompteASeuil;
import fr.banque.CompteRemunere;

public class Main {

	public static void main(String[] args) {
		JsonObject obj = new JsonObject();
		obj.addProperty("age", 5);
		obj.addProperty("nom", "Smith");
		obj.addProperty("prenom", "Jhon");

		System.out.println(obj.toString());

		String unObjJson = "{\"age\":5,\"nom\":\"Smith\",\"prenom\":\"Jhon\"}";

		JsonObject objFromJson = null;
		Gson g = new Gson();
		objFromJson = g.fromJson(unObjJson, JsonObject.class);
		System.out.println(objFromJson.toString());

		Client client = new Client(5, "Smith", "Jhon", 55);
		Compte c0 = new Compte(0, 200);
		Compte c1 = new Compte(1, 1000);
		CompteASeuil c2 = new CompteASeuil(2, 400, 100);
		CompteRemunere c3 = new CompteRemunere(3, 400, 0.10);
		CompteRemunere c4 = new CompteRemunere(4, 600, 0.50);
		client.ajouterCompte(c0);
		client.ajouterCompte(c1);
		client.ajouterCompte(c2);
		client.ajouterCompte(c3);
		client.ajouterCompte(c4);
		String resu = g.toJson(client);
		System.out.println(resu);

		String jsonStr = "{\"nom\":\"Smith\",\"prenom\":\"Jhon\",\"age\":55,\"numero\":5,\"tabComptes\":{\"0\":{\"solde\":200.0,\"numero\":0},\"1\":{\"solde\":1000.0,\"numero\":1},\"2\":{\"seuil\":100.0,\"solde\":400.0,\"numero\":2},\"3\":{\"taux\":0.1,\"solde\":400.0,\"numero\":3},\"4\":{\"taux\":0.5,\"solde\":600.0,\"numero\":4}}}";
		Client fromJsonStr = g.fromJson(jsonStr, Client.class);
		// Mais ATTENTION, ne sais pas faire de CompteASeuil, CompteRemunere,
		// ...
		// Ne sait faire que des Compte
		System.out.println(fromJsonStr);



	}



}
