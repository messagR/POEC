package com.exo.run;

public class Exp {

	public static void main(String[] args) {
		Integer i = new Integer(25);
		i = null;
		// boxing
		Integer a = 25; // -> Integer a = new Integer(25);
		a = null;
		// unboxing
		int b = new Integer(43); // -> int b = 43;
		int c = 25;

		String sc = ((Integer) 25).toString();
		// la mieux
		String sc2 = String.valueOf(25);
		String sc3 = "" + 25;

		// la mieux
		int c2 = Integer.parseInt("11");
		int c3 = new Integer("11");
		int c4 = Integer.valueOf("11");

		double d1 = Double.parseDouble("11.1");
		long l1 = Long.parseLong("1111");

		Object[] tab = new Object[5];
		// le mieux
		tab[0] = Integer.valueOf(5);
		tab[1] = 5;

		Integer ai = new Integer(5);
		double dd = ai.doubleValue();

		Double dd2 = 5.135415874684 / 1.2346854;
		System.out.println(dd2);

		System.out.println();
	}

}
