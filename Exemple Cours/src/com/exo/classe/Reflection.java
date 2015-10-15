package com.exo.classe;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class Reflection {

	public static void main(String[] args) {
		try {
			Class maClassDate = Class.forName("java.util.Date");
			Object monInstanceDeDate = maClassDate.newInstance();
			Constructor[] mesConstructeurs = maClassDate.getConstructors();
			System.out.println(mesConstructeurs);
			Object monInstanceDeDate2 = mesConstructeurs[4].newInstance(System.currentTimeMillis() + 1000);
			Method[] mesMethodes = maClassDate.getMethods();
			System.out.println(mesMethodes);
			Method maMethodeGetTime = maClassDate.getMethod("getTime");
			Object resultatAppel = maMethodeGetTime.invoke(monInstanceDeDate2);
			System.out.println(monInstanceDeDate);
			System.out.println(monInstanceDeDate2);
			System.out.println(resultatAppel);
			java.util.Date date = new java.util.Date();
			System.out.println(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
