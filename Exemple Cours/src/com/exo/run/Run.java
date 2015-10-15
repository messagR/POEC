package com.exo.run;

public class Run {

	// enlève le warning sur la ligne ou il peut y avoir un pointeur null
	// @SuppressWarnings("null")
	// enlève les warnings de null pointer exception et de not used
	// @SuppressWarnings(value = { "null", "unused" })
	public static void main(String[] args) {

		System.out.println("-- A --");
		try {
			Integer val = Integer.parseInt(null);
			System.out.println(val);
			;
		} catch (Exception e) {
			System.err.println(e);
		}
		System.out.println("-- B --");

		int j = -1;
		toto: do {
			j++;
			System.out.println(args[j]);
		} while (j < args.length);
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
