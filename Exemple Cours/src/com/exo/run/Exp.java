package com.exo.run;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class Exp {

	public static void main(String[] args) {

		String test = "ttt;aaa;eee;uuu;ooo;sss;fff;hhh;kkk;mmm";
		String test2 = "lksdjfjhndfqnflskd";
		test.equalsIgnoreCase(test2);

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

		// TODO envoyer à christine

		Double dd2 = 00004254545.135415874684 / 1.2346854;
		System.out.println(dd2);
		DecimalFormat df = new DecimalFormat("#,###.##");
		System.out.println(df.format(dd2));
		DecimalFormat df1 = new DecimalFormat("0,000.00");
		System.out.println(df1.format(dd2));

		Locale l = Locale.getDefault();
		System.out.println(l);
		System.out.println(Locale.FRANCE);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date d2 = new Date();
		System.out.println(d2);
		System.out.println(sdf.format(d2));
		String s1 = "2015/12/16 16:24:10";
		Date d3 = null;
		try {
			d3 = sdf.parse(s1);
			System.out.println(d3);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String s2 = "2015/25/16 16:24:10";
		try {
			d3 = sdf.parse(s2);
			System.out.println(d3);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// pour ne pas que le format s'arrange pour ne pas planter
		sdf.setLenient(false);
		try {
			d3 = sdf.parse(s2);
			System.out.println(d3);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Calendar c1 = new GregorianCalendar();
		System.out.println(c1.getTime());
		c1.set(Calendar.DAY_OF_YEAR, c1.get(Calendar.DAY_OF_YEAR) + 3);
		System.out.println(c1.getTime());
		System.out.println(c1.get(Calendar.DAY_OF_WEEK));

	}

}
